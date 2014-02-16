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
import edu.wpi.first.wpilibj.Compressor;
/*
 */
/**
 *
 * @author mburt001
 */
public class AerialAssist extends SimpleRobot
{

    public PIDController aimController;
    public PIDController turnController;
    protected Joystick driveStick;
    public CrabDrive drive;
    public GameMech gameMech;
    public Compressor  compressor;
    public DriverStation ds;

    public AerialAssist()
    {
        driveStick = new Joystick(1);
        try
        {
            drive = new CrabDrive();
            gameMech = null;
            compressor = new Compressor(Parameters.compressorSwitchChannel,
                    Parameters.compressorRelayChannel);
            ds = DriverStation.getInstance();
        } catch (CANTimeoutException ex)
        {
            ex.printStackTrace();
        }
    }

    public void autonomous()
    {
        int value;
        final int kDriving =0;
        final int kShooting =1;
        final int kWaitingForHot =2;
        value = kWaitingForHot;
    }

    public void operatorControl()
    {
        int count = 0;
       
        try
        {
            drive.enablePositionControl();
            while (isEnabled() && isOperatorControl())
            {
                double driveValue = driveStick.getMagnitude();
                double turnValue = (((ds.getAnalogIn(1)/3.3)*2)-1) * 2;
                double crabValue = FRCMath.convertDegreesToJoystick(driveStick.getDirectionDegrees());
                if (crabValue > 0.05 || crabValue < -0.05)  
                {
                    drive.crabDrive(driveValue, crabValue);
                } 
                else
                {
                    if (turnValue > 0.05 || turnValue < -0.05)
                    {
                        drive.slewDrive(driveValue, turnValue);
                    } else
                    {
                        drive.slewDrive(driveValue, 0); //3.14159265358979323846264338327950
                    }
                }
                count++;
                if (count % 5 == 0)
                {
                    count = 0;
                    System.out.println("Degrees : " + crabValue);
                    drive.printTelemetry(); 
                }
                Timer.delay(Parameters.TIMER_DELAY);
                drive.processCrabDrive();
                if (gameMech != null)
                {
                    gameMech.processGameMech();
                }
            }
            drive.disablePositionControl();
        } catch (CANTimeoutException ex)
        {
            ex.printStackTrace();
        }
    }
}
