package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.camera.*;
import edu.wpi.first.wpilibj.image.*;

/*
 * AimingSystem allocation
 */
public class AimingSystem {

    public AxisCamera myCamera;
    public Ultrasonic myUltrasonic;
    public ColorImage image;
    public BinaryImage threshold;
    public ParticleAnalysisReport[] reports = null;
    private int imageState;
    private boolean busy;
    private boolean hot;
    private int xCenter;
    private int yCenter;
    private int xDeadband;
    private int yDeadband;
    
    public AimingSystem()
    {
        myUltrasonic = new Ultrasonic(-1);
        myCamera = AxisCamera.getInstance("10.20.28.11");
        myCamera.writeResolution(AxisCamera.ResolutionT.k640x480);
        myCamera.writeExposurePriority(AxisCamera.ExposurePriorityT.imageQuality);
        myCamera.writeExposureControl(AxisCamera.ExposureT.hold);
        myCamera.writeWhiteBalance(AxisCamera.WhiteBalanceT.fixedIndoor);
    }
    
    public void processImage() {
        try {
            switch (imageState) {
                    case 0:
                        busy = true;
                        image = myCamera.getImage();
                        image.write("/image.jpg");
                        System.out.println("Got image");
                        imageState++;
                        busy = false;
                        break;
                    case 1:
                        busy = true;
                        threshold = image.thresholdRGB(0, 70, 185, 255, 145, 225);  // green values
                        threshold.write("/threshold.jpg");
                        System.out.println("Got threshold");
                        imageState++;
                        busy = false;
                        break;
                    case 2:
                        busy = true;
                        reports = threshold.getOrderedParticleAnalysisReports(10);
                        CheckHot();
                        threshold.free();
                        threshold = null;
                        imageState = 0;
                        break;
                }
        } catch (AxisCameraException ex) {
                ex.printStackTrace();
            } catch (NIVisionException ex) {
                ex.printStackTrace();
            }
    }
    
    /**
     * 
     */
    public void CheckHot() {
        for (int i = 0; i < reports.length; i++) {
            hot = reports[i].center_mass_x >= xCenter-xDeadband || reports[i].center_mass_x < xCenter+xDeadband;
            hot &= reports[i].center_mass_y >= yCenter-yDeadband || reports[i].center_mass_y < yCenter+yDeadband;
            if (hot) return;
        }
    }
    
    /**
     * 
     */
    public void DisableAimingSystem()
    {
        try
        {
            if (image != null) {
                image.free();
                image = null;
            }
            if (threshold != null) {
                threshold.free();
                threshold = null;
            }
        } catch (NIVisionException e)
        {
        }
        imageState = 0;
        busy = false;
    }
}
