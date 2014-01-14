/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CANTimeoutException;

/*
 */
/**
 *
 * @author tstevens003
 */
public class AerialAssist extends SimpleRobot {
    
    public PIDController aimController;
    public PIDController turnController;
    protected Joystick driveStick;
    protected Joystick shooterStick;
    protected Joystick armStick;
    DriverStation ds;
    boolean turning = false;
    public AerialAssist(){
        try {
            drive = new CrabDrive();
            launcher = new LauncherSystem();
            //gamemech = New GameMech();
        } catch (CANTimeoutException e) {
            
        }
    }
    public void autonomous() {
        try {
            double _P = (ds.getAnalogIn(1) / 3.3) * 500.0;
            double _I = (ds.getAnalogIn(2) / 3.3);
            double _D = (ds.getAnalogIn(3) / 3.3);
            double kDamp = (ds.getAnalogIn(1) / 3.3) * 50;
            System.out.println("P: " + _P + ", I: " + _I + ", D: " + _D);
            aimController.setPID(_P, _I, _D);
            if (turnController != null) {
                turnController.setPID(_P, _I, _D);
            }
            RobotState state = new RobotState();
            while (isAutonomous() &&isEnabled()) {
                dash.updateDashboard();
                double time = Timer.getFPGATimestamp();
                switch (state.getState()) {
                    
                }
            }
    }
    public void operatorControl() {
        
    }
    }
