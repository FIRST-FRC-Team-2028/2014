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
    private double setPoint;
    private boolean driving = false;
    private boolean steering = false;

    /**
     * @param steerID
     * 
     * @throws CANTimeoutException 
     */
    public Wheel(int steerID) throws CANTimeoutException {
        steeringMotor = new CANJaguar(steerID, CANJaguar.ControlMode.kPercentVbus);
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
     * 
     * This method enables the position control of the wheel.
     */
    public void enablePositionControl() throws CANTimeoutException {
        if (steering) {
            steeringMotor.enableControl();
        }
    }

    /**
     * disablePositionControl()
     * 
     * @throws CANTimeoutException 
     * 
     * This method disables position control of the wheel. 
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
     * 
     * This method sets the starting position of the wheel.
     */
    public boolean setPosition(double outputValue) throws CANTimeoutException {
        steeringMotor.setX(outputValue);
        if (steeringMotor.getPosition() == outputValue) {
            return true;
        }
        return false;
    }        

    /**
     * getPosition()
     * 
     * @return
     * 
     * @throws CANTimeoutException 
     * 
     * This method returns a value as to where the position of the wheel is.
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
     * 
     * This method sets the speed at which the wheel rotates.
     */
    public void setSpeed(double outputValue) throws CANTimeoutException {
        if (driving) {
            driveMotor.setX(outputValue);
        }
    }
}