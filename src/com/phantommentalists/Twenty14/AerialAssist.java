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

/**
 *
 * @author tstevens003
 */
public class AerialAssist extends SimpleRobot {

    public PIDController aimController;
    public PIDController turnController;
    protected GamePadF310 driveStick;
    public CrabDrive drive;

    public AerialAssist() {
        try {
            drive = new CrabDrive();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }

    public void autonomous() {
    }

    public void operatorControl() {
        while (isEnabled() && isOperatorControl()) {
            double driveValue = driveStick.getAxisTrigger();
            double turnValue = driveStick.getLeftThumbStickX();
            double crabValue = driveStick.getRightThumbStickX();
            try{
            if (crabValue <= 0.05 && crabValue >= -0.05) {
                crabValue = 0.0;
            }
            if (driveValue <= 0.05 && driveValue >= -0.05) {
                driveValue = 0.0;
            }
            if(turnValue <= 0.05 && turnValue >= -0.05)
            {
                turnValue = 0.0;
            }
            if(crabValue != 0.0)
            {
                drive.crabDrive(driveValue, crabValue);
            }
            else
            {
                drive.slewDrive(driveValue, turnValue);
            }
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            Timer.delay(Parameters.TIMER_DELAY);
        }
    }
}