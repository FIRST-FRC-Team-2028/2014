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
        myCamera = AxisCamera.getInstance(Parameters.CAMERA_IP);
        myUltrasonic = new Ultrasonic(Parameters.ULTRASONIC_ANALOG_CHANNELl);
        myCamera.writeResolution(AxisCamera.ResolutionT.k640x480);
    }
}