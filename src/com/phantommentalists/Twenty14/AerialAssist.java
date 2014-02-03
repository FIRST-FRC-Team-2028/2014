/*--------------------------------------------w--------------------------------*/
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

/**
 *
 * @author mburt001
 */
public class AerialAssist extends SimpleRobot {

    public PIDController aimController;
    public PIDController turnController;
    protected GamePadF310 driveStick;
    public CrabDrive drive;

    public AerialAssist() {
        try {
            drive = new CrabDrive();
            driveStick = new GamePadF310(1);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }

    public void autonomous() {
    }

    public void operatorControl() {
        try {
            drive.enablePositionControl();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        while (isEnabled() && isOperatorControl()) {
            double driveValue = driveStick.getAxisTrigger();
            double turnValue = driveStick.getLeftThumbStickX();
            double crabValue = driveStick.getRightThumbStickX();
            try {
                if (crabValue > 0.05 || crabValue < -0.05) {
                    drive.crabDrive(driveValue, crabValue);
                } else {
                    drive.crabDrive(driveValue, 0);
                    if (turnValue > 0.05 || turnValue < -0.05) {
                        drive.slewDrive(driveValue, turnValue);
                    } else {
                        drive.slewDrive(driveValue, 0);
                    }
                }
                //System.out.println("Sensor position");
                //System.out.println(drive.getPosition());
                // System.out.println("SetPoint");
                // System.out.println("0.5");
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            Timer.delay(Parameters.TIMER_DELAY);
        }
        try {
            drive.disablePositionControl();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
}
