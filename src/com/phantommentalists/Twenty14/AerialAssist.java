/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.PIDController;
/*
 */
/**
 *
 * @author mburt001
 */
public class AerialAssist extends SimpleRobot {
    
    public PIDController aimController;
    public PIDController turnController;
    protected Joystick driveStick;
    protected Joystick shooterStick;
    protected Joystick armStick;
    boolean turning = false;
    public AerialAssist(){
    }
    public void autonomous() {
    }
    public void operatorControl(String currentGear)
    {
    
    }
}