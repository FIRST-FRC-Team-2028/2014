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
    private int imageState = 0;
    private boolean busy = false;
    private boolean hot = false;
    private int xCenter; //$$TODO: Add in this value
    private int yCenter; //$$TODO: Add in this value
    private int xDeadband = 20; //$$TODO: Make sure this value is right
    private int yDeadband = 20; //$$TODO: Make sure this value is right
    private double aspectScore = 75; //$$TODO: Make sure this value is right
    
    public AimingSystem()
    {
        myUltrasonic = new Ultrasonic(-1);
        myCamera = AxisCamera.getInstance("10.20.28.11");
        myCamera.writeResolution(AxisCamera.ResolutionT.k640x480);
        myCamera.writeExposurePriority(AxisCamera.ExposurePriorityT.imageQuality);
        myCamera.writeExposureControl(AxisCamera.ExposureT.hold);
        myCamera.writeWhiteBalance(AxisCamera.WhiteBalanceT.fixedIndoor);
    }
    
    public boolean processImage() {
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
                        threshold = image.thresholdRGB(0, 70, 185, 255, 145, 225);  //TODO: Check and make sure this is right
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
        return hot;
    }
    
    /**
     * 
     * @throws edu.wpi.first.wpilibj.image.NIVisionException
     */
    public void CheckHot() throws NIVisionException {
        for (int i = 0; i < reports.length; i++) {
            hot = reports[i].center_mass_x >= xCenter-xDeadband || reports[i].center_mass_x < xCenter+xDeadband;
            hot &= reports[i].center_mass_y >= yCenter-yDeadband || reports[i].center_mass_y < yCenter+yDeadband;
            hot &= scoreAspectRatio(threshold, reports[i], i) > aspectScore;
            if (hot) return;
        }
    }
    
    /**
     * scoreAspectRatio()
     *
     * This method scores the particle from 0 - 100 based on how similar its
     * aspect ratio is to the aspect ratio of either the high target or the
     * middle target. A score of 100 means that the target has an aspect ratio
     * identical to either the middle or high target.
     *
     * @param image the image from which the particle originates.
     * @param report the analysis of the particle
     * @param middle true if aspect ratio to be compared to is the middle
     * target, false if it is the high target.
     * @return the score of the particle, from 0 - 100
     * @throws NIVisionException
     */
    public double scoreAspectRatio(BinaryImage image, ParticleAnalysisReport report,
            int particleNumber) throws NIVisionException
    {
        double rectLong, rectShort, aspectRatio, idealAspectRatio;

        rectLong = NIVision.MeasureParticle(image.image, particleNumber, false,
                NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_LONG_SIDE);
        rectShort = NIVision.MeasureParticle(image.image, particleNumber, false,
                NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
        idealAspectRatio = 23.5/4;

        if (report.boundingRectWidth > report.boundingRectHeight)
        {
            aspectRatio = 100 * (1 - Math.abs((1 - ((rectLong / rectShort) / idealAspectRatio))));
        } else
        {
            aspectRatio = 100 * (1 - Math.abs((1 - ((rectShort / rectLong) / idealAspectRatio))));
        }

        return Math.max(0, Math.min(aspectRatio, 100.0));
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
