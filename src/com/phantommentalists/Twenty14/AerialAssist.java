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
import edu.wpi.first.wpilibj.CANTimeoutException;
import edu.wpi.first.wpilibj.PIDController;
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
    private final LauncherSystem launcher;
    private final CrabDrive drive;
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
            while (isAutonomous() && isEnabled()) {
                dash.updateDashboard();
                double time = Timer.getFPGATimestamp();
                switch (state.getState()) {
                    case RobotState.drive:
                       drive.drive(Parameters.autonomous_DRIVE_FORWARD_SPEED, 0.0,KDamp);
                        if (Timer.getFPGATimestamp() - time < 0.5) {
                            state.nextState();
                }
            }
    }
    public void operatorControl(String currentGear) {
         double kDamp = (ds.getAnalogIn(1) / 3.3) * 50;
//        double _P = (ds.getAnalogIn(1) / 3.3) * 100.0;
//        double _I = (ds.getAnalogIn(2) / 3.3) * 0.01;
//        double _D = (ds.getAnalogIn(3) / 3.3) * 0.01;
//        aimController.setPID(_P, _I, _D);
//        if (turnController != null)
//        {
//            turnController.setPID(_P, _I, _D);
//        }
        int i = 0;
        while (isOperatorControl() && isEnabled()) {
//            dash.updateDashboard();
            //
            // Driver Controls
            //
           try {
                double drivePercent = driveStick.getY() * -1.0;
                if (drivePercent < Parameters.kJoystickDeadband
                        && drivePercent > (-1.0 * Parameters.kJoystickDeadband)) {
                    drivePercent = 0.0;
                }
                double turnPercent = driveStick.getX();
                if (turnPercent < Parameters.kJoystickDeadband
                        && turnPercent > (-1.0 * Parameters.kJoystickDeadband)) {
                    turnPercent = 0.0;
                }
                switch (i) {
                    default:
                        i++;
                        break;
                    case 3:
                        System.out.println("Drive value: " + drivePercent + ", Turn value: " + turnPercent);
                        i = 0;
                        break;
                }
                drive.drive(drivePercent, turnPercent, kDamp);
            } catch (CANTimeoutException e) {
                System.out.println(e);
            }

            boolean lowGear = driveStick.getRawButton(Parameters.kLowGearButton);
            boolean highGear = driveStick.getRawButton(Parameters.kHighGearButton);
            Tread.Gear presentGear = drive.getGear();
            Tread.Gear newGear = presentGear;
            if (highGear) {
                newGear = Tread.Gear.kHigh;
                currentGear = "High Gear";
            }
            if (lowGear) {
                newGear = Tread.Gear.kLow;
                currentGear = "Low Gear";
            }
            if (presentGear != newGear) {
                drive.setGear(newGear);
            }