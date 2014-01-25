package com.phantommentalists.Twenty14;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 * @author mburt001
 */
public class Wheel {

    CANJaguar steeringMotor;
    CANJaguar driveMotor;
    private double setPoint = 0.5;
    private boolean driving = false;
    private boolean steering = false;

    public Wheel(int steerID) throws CANTimeoutException {
        steeringMotor = new CANJaguar(steerID, CANJaguar.ControlMode.kPosition);
        steeringMotor.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
        steeringMotor.configMaxOutputVoltage(Parameters.maxMotorVoltage);
        steeringMotor.configNeutralMode(CANJaguar.NeutralMode.kBrake);
        steeringMotor.setPID(Parameters.steeringProportionalValue,
                Parameters.steeringIntegralValue,
                Parameters.steeringDerivativeValue);
        steering = true;

    }

    public void enablePositionControl() throws CANTimeoutException {
        if (steering) 
        {
            steeringMotor.enableControl();
            steeringMotor.setX(setPoint);            
        }
    }

    public void disablePositionControl() throws CANTimeoutException {
        if (steering) {
            steeringMotor.disableControl();
        }
    }
    
    /**
     * 
     * @param outputValue
     * @return
     * @throws CANTimeoutException 
     */
    public boolean setPosition(double outputValue) throws CANTimeoutException {
        System.out.println("\tOutput value:  " + outputValue + ", Position: " + steeringMotor.getPosition());
        if (setPoint == outputValue) 
        {
            return true;
        }
        if (outputValue < 0.2)
        {
            outputValue = 0.2;
        }
        if (outputValue > 0.8)
        {
            outputValue =0.8;
        }
        steeringMotor.setX(outputValue);
        setPoint = outputValue;
        return false;
    }

    public double getPosition() throws CANTimeoutException {
        if (steering) {
            return steeringMotor.getPosition();
        }
        return -1;
    }

    public void setSpeed(double outputValue) throws CANTimeoutException {
        if (driving) {
            driveMotor.setX(outputValue);
        }
    }
}
