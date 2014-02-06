package com.phantommentalists.Twenty14;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * 
 * Wheel allocation
 * 
 * @author mburt001
 * 
 */
public class Wheel {

    CANJaguar steeringMotor;
    CANJaguar driveMotor;
    private double setPoint = 0.5;
    private boolean driving = false;
    private boolean steering = false;

    /**
     * @param steerID
     * 
     * @throws CANTimeoutException 
     */
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

    /**
     * enablePositionControl()
     * 
     * @throws CANTimeoutException 
     */
    public void enablePositionControl() throws CANTimeoutException {
        if (steering) 
        {
            steeringMotor.enableControl();
            steeringMotor.setX(setPoint);            
        }
    }

    /**
     * disablePositionControl()
     * 
     * @throws CANTimeoutException 
     */
    public void disablePositionControl() throws CANTimeoutException {
        if (steering) {
            steeringMotor.disableControl();
        }
    }
    
    /**
     * setPosition()
     * 
     * @param outputValue
     * 
     * @return
     * 
     * @throws CANTimeoutException 
     */
    public boolean setPosition(double outputValue) throws CANTimeoutException {
        steeringMotor.setX(outputValue);
        if (steeringMotor.getPosition() == outputValue) {
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

    /**
     * getPosition()
     * 
     * @return
     * @throws CANTimeoutException 
     */
    public double getPosition() throws CANTimeoutException {
        if (steering) {
            return steeringMotor.getPosition();
        }
        return -1;
    }

    /**
     * setSpeed()
     * 
     * @param outputValue
     * 
     * @throws CANTimeoutException 
     */
    public void setSpeed(double outputValue) throws CANTimeoutException {
        if (driving) {
            driveMotor.setX(outputValue);
        }
    }
    
    public void processWheel() {
        
    }
}
