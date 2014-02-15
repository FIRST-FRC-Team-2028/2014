/*----------------------------------------------------------------------------*/ 
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
/*
 */
/**
 *
 * @author mburt001
 */
public class AerialAssist extends SimpleRobot
{
    private class AutoStates{
        public int value;
        public static final int kHolding = 0;
        public static final int kWaiting = 1;
        public static final int kDriving = 2;
        public static final int kShooting = 3;
        public static final int kStopped = 4;
        public AutoStates(){
            value = kHolding;
        }
    }
    public PIDController aimController;
    public PIDController turnController;
   // protected GamePadF310 driveStick;
    Joystick stick;
    public CrabDrive drive;
    public GameMech gameMech;
    public AimingSystem aimingSystem;
    public Ultrasonic ultrasonic;
    public DriverStation ds;

    public AerialAssist()
    {
       // driveStick = new GamePadF310(1);     
        stick = new Joystick(1);
        try
        {
            drive = new CrabDrive();
            gameMech = new GameMech();
            aimingSystem = new AimingSystem();
        } catch (CANTimeoutException ex)
        {
            ex.printStackTrace();
        }
    }

    public void autonomous()
    {
        AutoStates state = new AutoStates();
       while(isEnabled() && isAutonomous())
       {
           try {
               if(state.value == AutoStates.kHolding)
               {
                   gameMech.deployCatcher();
                   gameMech.deployChopSticks();
                   state.value = AutoStates.kWaiting;
               }
               if(state.value == AutoStates.kWaiting)
               {
                   if (/*aimingSystem.isHot() ||*/ ds.getMatchTime() >= 5.0)
                   {
                       drive.setDrive(Parameters.kAutonomousSpeed);
                       state.value = AutoStates.kDriving;
                   }
               }
               if(state.value == AutoStates.kDriving)
               {
                   if (ultrasonic.getDistance() <= Parameters.kShootDistance)
                   {
                       drive.setDrive(0.0);
                       state.value = AutoStates.kShooting;
                       gameMech.shoot();
                   }
               }
               if(state.value == AutoStates.kShooting)
               {
                   if(gameMech.isEmpty())
                   {
                       state.value = AutoStates.kStopped;
                   }
               }
               if(state.value == AutoStates.kStopped)
               {
                   if(Parameters.debug)
                   {
                        System.out.println("Done");
                   }
               }
               gameMech.shoot();

           } catch (CANTimeoutException ex) {
               ex.printStackTrace();
           }
           Timer.delay(Parameters.TIMER_DELAY);
           getWatchdog().feed();
       } 
        
    }

    public void operatorControl()
    {
        int count = 0;
        
        while(isEnabled() && isOperatorControl())
        {
            if (stick.getRawButton(1))
            {
                try{
                    gameMech.timedShoot();
                }
                catch(CANTimeoutException e)
                {
                    e.printStackTrace();
                }
            }
            Timer.delay(Parameters.TIMER_DELAY);
            getWatchdog().feed();
        }
//        try
//        {
//            drive.enablePositionControl();
//            while (isEnabled() && isOperatorControl())
//            {
//                double driveValue = driveStick.getAxisTrigger();
//                double turnValue = driveStick.getLeftThumbStickX();
//                double crabValue = driveStick.getRightThumbStickX();
//                if (crabValue > 0.05 || crabValue < -0.05)
//                {
//                    drive.crabDrive(driveValue, crabValue);
//                } 
//                else
//                {
//                    if (turnValue > 0.05 || turnValue < -0.05)
//                    {
//                        drive.slewDrive(driveValue, turnValue);
//                    } else
//                    {
//                        drive.slewDrive(driveValue, 0);
//                    }
//                }
//                //System.out.println("Sensor position");
//                //System.out.println(drive.getPosition());
//                // System.out.println("SetPoint");
//                // System.out.println("0.5");
//                count++;
//                if (count % 5 == 0)
//                {
//                    count = 0;
//                    drive.printTelemetry(); 
//                }
//                Timer.delay(Parameters.TIMER_DELAY);
//                drive.processCrabDrive();
//                if (gameMech != null)
//                {
//                    gameMech.processGameMech();
//                }
//            }
//            drive.disablePositionControl();
//        } catch (CANTimeoutException ex)
//        {
//            ex.printStackTrace();
//        }
    }
}
