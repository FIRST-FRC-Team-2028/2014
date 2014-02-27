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
    private int xCenter; //TODO: Add in this value
    private int yCenter; //TODO: Add in this value
    private int xDeadband; //TODO: Add in this value
    private int yDeadband; //TODO: Add in this value
    private double aspectScore; //TODO: Add in this value
    
    public AimingSystem()
    {
        myUltrasonic = new Ultrasonic(-1);
        myCamera = AxisCamera.getInstance("10.20.28.11");
        myCamera.writeResolution(AxisCamera.ResolutionT.k640x480);
        myCamera.writeExposurePriority(AxisCamera.ExposurePriorityT.imageQuality);
        myCamera.writeExposureControl(AxisCamera.ExposureT.hold);
        myCamera.writeWhiteBalance(AxisCamera.WhiteBalanceT.fixedIndoor);
    }
    public boolean isHot(){
        return false;
    }
}
