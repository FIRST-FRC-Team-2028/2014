/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.phantommentalists.Twenty14;

import com.phantiommentalists.Twenty14.DriveMotor.Gear;

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
        public static final int kWaiting = 2;
        public static final int kDriving = 1;
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
    public boolean low = true;

    public AerialAssist() {
        driveStick = new Joystick(1);
        gameStick = new Joystick(2);
        airCompressor = new Compressor(Parameters.kAirPressureSwitchChanel, Parameters.compressorRelayChannel);
        ds = DriverStation.getInstance();
        dsio = ds.getEnhancedIO();
        ultrasonic = new Ultrasonic(Parameters.kUltraSonicAnalogInput);
        try {
//            drive = null;
            drive = new CrabDrive();
            gameMech = new GameMech();
            aimingSystem = null;
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }

    public void autonomous() {
        AutoStates state = new AutoStates();
        double starttime = 0.0;
        while (isEnabled() && isAutonomous()) {
            if (Parameters.debug) {
                System.out.println("State = " + state.value);
            }
            try {
                gameMech.processGameMech();
                if (state.value == AutoStates.kHolding) {
                    gameMech.deployCatcher();
                    gameMech.deployChopSticks();
                    if (gameMech.isCatching())
                    {
                        gameMech.retract();
                    }
                    if (gameMech.isLauncherSafe()) {
                        gameMech.retractCatcher();
                        gameMech.retractChopSticks();        
                    }
                    if (gameMech.isHolding())
                        {
                            state.value = AutoStates.kDriving;
                            starttime = ds.getMatchTime();
                        }
                }
                if (state.value == AerialAssist.AutoStates.kWaiting) {
                    
                    boolean timeout = (ds.getMatchTime() - starttime) >= 1.0;
                    if (timeout)
                    {
                        state.value = AutoStates.kShooting;
                    }
                }
                if (state.value == AerialAssist.AutoStates.kDriving) {
                    
                    if (drive != null) {
                        drive.setDrive(Parameters.kAutonomousSpeed);
                    }
                    boolean timeout = (ds.getMatchTime() - starttime) >= 2;
                    if (Parameters.debug) {
                    System.out.println("Distance: " + ultrasonic.getDistance() + ", Voltage: " + ultrasonic.getVoltage() + ", Time: " + (ds.getMatchTime() - starttime));
                    }
                    if (timeout) 
                    {
                        if (drive != null) 
                        {
                            drive.setDrive(0.0);
                        }
                        gameMech.deployCatcher();
                        gameMech.deployChopSticks();
                        if (gameMech.isCatching()) 
                        {
                            state.value = AerialAssist.AutoStates.kWaiting;
                            starttime = ds.getMatchTime();
                        }
                    }
                }
                if (state.value == AerialAssist.AutoStates.kShooting) {
                    gameMech.shoot(Parameters.kshootAutoGoal);
                    if (Parameters.debug) {
                        System.out.println("Shoot");
                    }
                    state.value = AerialAssist.AutoStates.kStopped;
                }
                if (state.value == AerialAssist.AutoStates.kStopped) {
                    if (Parameters.debug) {
                        System.out.println("Done"); //3.14159265358979323846264338327950\
                    }
                    if (gameMech.isCatching() && gameMech.isLauncherEmpty()) {
                        gameMech.retract();
                    }
                    if (gameMech.isLauncherSafe()) {
                        gameMech.retractCatcher();
                        gameMech.retractChopSticks();
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
        airCompressor.start(); //3.14159265358979323846264338327950
        int count = 0;
        try {
            drive.enablePositionControl();
            while (isEnabled() && isOperatorControl()) {
//                if(aimingSystem.getUltrasonicDistance() < Parameters.kMaxShootDistance
//                        && aimingSystem.getUltrasonicDistance() > Parameters.kMinShootDistsance)
//                {
//                    try {
//                        dsio.setDigitalOutput(4, true);
//                    } catch (DriverStationEnhancedIO.EnhancedIOException ex) {
//                        ex.printStackTrace();
//                    }
//                }
                double drivePolarValue = driveStick.getMagnitude();
                double driveValue = driveStick.getY() * -1.0;
                double turnValue = (((ds.getAnalogIn(1) / 3.3) * 2) - 1);
                double crabValue = FRCMath.convertDegreesToJoystick(driveStick.getDirectionDegrees());
                if (driveStick.getRawButton(2)) {
                    low = true;
                } else if (driveStick.getRawButton(3)) {
                    low = false;
                }
                if (drivePolarValue < 0.075) {
                    crabValue = 0.0;
                }

//                if (Parameters.debug) {
//                    if(count%2 == 0)
//                {
////                    drive.printTelemetry();
//                
//                    System.out.println("Degrees: " + crabValue + ", Polar: " + drivePolarValue + ", Turn: " + turnValue);
//                }
//                }
                if (drive != null) {
                    if (low) {
                        drive.setGear(Gear.kLow); //3.14159265358979323846264338327950
                    } else {
                        drive.setGear(Gear.kHigh);
                    }
                    if (driveStick.getTrigger()) {
                        drive.turnOnAxis(turnValue * -2.0);
                    } else if (turnValue > 0.10 || turnValue < -0.10) {
                        drive.slewDrive(driveValue, (turnValue * 0.75)); //3.14159265358979323846264338327950
                    } else {
                        if (crabValue > 0.05 || crabValue < -0.05) //3.14159265358979323846264338327950
                        {
                            drive.crabDrive(drivePolarValue, crabValue); //3.14159265358979323846264338327950
                        } else {
                            drive.crabDrive(driveValue, 0); //3.14159265358979323846264338327950
                        }
                    }
                }
                // if (drive != null)
                if (gameMech != null) {
                    gameMech.processGameMech();
                    //Shoot Button
                    if (gameStick.getRawButton(1)) {
                        gameMech.shoot(Parameters.kshootGoal);
                    }
                    //Pass Button
                    if (gameStick.getRawButton(9)) {
                        gameMech.pass();
                    }
                    //Retract Button
                    if (gameStick.getRawButton(5)) {
                        gameMech.retract();
                    }
                    //Deploy ChopSticks Button
                    if (gameStick.getRawButton(2)) {
                        gameMech.deployChopSticks(); //3.14159265358979323846264338327950
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
                        gameMech.turnOffChopSticks(); //3.14159265358979323846264338327950
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
        catch (CANTimeoutException ex) //3.14159265358979323846264338327950
        {
            ex.printStackTrace(); //3.14159265358979323846264338327950
        }
        airCompressor.stop();
    }
}
