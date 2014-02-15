package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.camera.AxisCamera;

/*
 * AimingSystem allocation
 */
public class AimingSystem {

    public AxisCamera myCamera;
    public Ultrasonic myUltrasonic;
    
    public AimingSystem()
    {
        myUltrasonic = new Ultrasonic(-1);
        myCamera = AxisCamera.getInstance("10.20.28.11");
        myCamera.writeResolution(AxisCamera.ResolutionT.k640x480);
    }
}
