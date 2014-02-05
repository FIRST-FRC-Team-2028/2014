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
 * @author tstevens003
 */
public class AerialAssist extends SimpleRobot {
    
    public PIDController aimController;
    public PIDController turnController;
    protected Joystick driveStick;
    protected GamePadF310 shooterStick;
    public Compressor compressor;
    public GameMech game;
    boolean turning = false;
    public AerialAssist(){
        compressor = new Compressor(1,1);
        game = new GameMech();
        shooterStick = new GamePadF310(1);
    }
    public void autonomous() {
    }
    public void operatorControl(){
        while(isEnabled() && isOperatorControl()){
            if(shooterStick.getButtonB()){
                game.deployCatcher();
                
            }
            else if(shooterStick.getButtonA()){
                game.retractCatcher();
            }
        }
    
    }
}