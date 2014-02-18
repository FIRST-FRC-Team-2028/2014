/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.phantommentalists.Twenty14;

import com.phantommentalists.Twenty14.DriveMotor.Gear;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
/*
 */

/**
 *
 * @author mburt001
 */
public class AerialAssist extends SimpleRobot {

    private class AutoStates {

        public int value;
        public static final int kHolding = 0;
        public static final int kWaiting = 1;
        public static final int kDriving = 2;
        public static final int kShooting = 3;
        public static final int kStopped = 4;

        public AutoStates() {
            value = kHolding;
        }
    }
    public PIDController aimController;
    public PIDController turnController;
    protected Joystick gameStick;
    protected Joystick driveStick;
    protected DriverStationEnhancedIO dsio;
    public CrabDrive drive;
    public GameMech gameMech;
    public AimingSystem aimingSystem;
    public Ultrasonic ultrasonic;
    public DriverStation ds;
    public Compressor airCompressor;

    public AerialAssist() {
        driveStick = new Joystick(1);
        gameStick = new Joystick(2);
        airCompressor = new Compressor(Parameters.kAirPressureSwitchChanel, Parameters.compressorRelayChannel);
        dsio = ds.getEnhancedIO();
        try {
            drive = new CrabDrive();
            gameMech = new GameMech();
            aimingSystem = new AimingSystem();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }

    public void autonomous() {
        AutoStates state = new AutoStates();
        while (isEnabled() && isAutonomous()) {
            try {
                if (drive != null) {
                    drive.processCrabDrive();
                }
                if (gameMech != null) {
                    gameMech.processGameMech();
                }
                if (state.value == AutoStates.kHolding) {
                    gameMech.deployCatcher();
                    gameMech.deployChopSticks();
                    state.value = AutoStates.kWaiting;
                }
                if (state.value == AutoStates.kWaiting) {
                    if (aimingSystem.processImage() || ds.getMatchTime() >= 5.0) {
                        drive.setDrive(Parameters.kAutonomousSpeed);
                        state.value = AutoStates.kDriving;
                    }
                }
                if (state.value == AutoStates.kDriving) {
                    if (ultrasonic.getDistance() <= Parameters.kShootDistance) {
                        drive.setDrive(0.0);
                        state.value = AutoStates.kShooting;
                        gameMech.shoot();
                    }
                }
                if (state.value == AutoStates.kShooting) {
                    if (gameMech.isEmpty()) {
                        state.value = AutoStates.kStopped;
                    }
                }
                if (state.value == AutoStates.kStopped) {
                    if (Parameters.debug) {
                        System.out.println("Done");
                    }
                }

            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            Timer.delay(Parameters.TIMER_DELAY);
            getWatchdog().feed();
        }

    }

    public void operatorControl() {
        airCompressor.start();
        int count = 0;
        try {
            drive.enablePositionControl();
            while (isEnabled() && isOperatorControl()) {
                double drivePolarValue = driveStick.getMagnitude();
                double driveValue = driveStick.getX();
                double turnValue = (((ds.getAnalogIn(1) / 3.3) * 2) - 1) * 2;
                double crabValue = FRCMath.convertDegreesToJoystick(driveStick.getDirectionDegrees());

                if (drive != null) {

                    if (driveStick.getRawButton(5)) {
                        drive.setGear(Gear.kLow);
                    } else if (driveStick.getRawButton(4)) {
                        drive.setGear(Gear.kHigh);
                    }

                    if (driveStick.getTrigger()) {
                        drive.turnOnAxis(turnValue);
                    } else if (turnValue > 0.05 || turnValue < -0.05) {
                        drive.slewDrive(driveValue, crabValue);
                    } else if (drivePolarValue > 0.05 || drivePolarValue < -0.05) {
                        drive.crabDrive(drivePolarValue, turnValue);
                    } else {
                        drive.crabDrive(drivePolarValue, 0); //3.14159265358979323846264338327950
                    }

                    //System.out.println("Sensor position");
                    //System.out.println(drive.getPosition());
                    // System.out.println("SetPoint");
                    // System.out.println("0.5");
                    count++;
                    if (count % 5 == 0) {
                        count = 0;
                        drive.printTelemetry();
                    }           // if (drive != null)
                }

                if (gameMech != null) {
                    gameMech.processGameMech();
                    //Shoot Button
                    if (gameStick.getRawButton(1)) {
                        gameMech.shoot();
                    }
                    //Retract Button
                    if (gameStick.getRawButton(5)) {
                        gameMech.retract();
                    }
                    //Deploy ChopSticks Button
                    if (gameStick.getRawButton(2)) {
                        gameMech.deployChopSticks();
                    }
                    //Retract ChopSticks Button
                    if (gameStick.getRawButton(6)) {
                        gameMech.retractChopSticks();
                    }
                    //Turn on ChopSticks Button
                    if (gameStick.getRawButton(3)) {
                        gameMech.turnOnChopSticks();
                    }
                    //Turn off ChopSticks Button
                    if (gameStick.getRawButton(7)) {
                        gameMech.turnOffChopSticks();
                    }
                    //Deploy Catcher
                    if (gameStick.getRawButton(4)) {
                        gameMech.deployCatcher();
                    }
                    //Retract Catcher
                    if (gameStick.getRawButton(8)) {
                        gameMech.retractCatcher();
                    }
                }           // if (gameMech != null)
                Timer.delay(Parameters.TIMER_DELAY);
                getWatchdog().feed();
            }           // while
        } // try
        catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        airCompressor.stop();
    }
}
