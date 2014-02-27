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
    private boolean steering = false;
    private boolean enabled = false;
    private String name;

    /**
     * @param steerID
     * 
     * @throws CANTimeoutException 
     */
    public Wheel(int steerID, String name, double Reverse) throws CANTimeoutException {
        if (steerID != 0)   
        {
            if (Parameters.debug)
            {
                System.out.println(name + " CAN ID:  " + steerID);
            }
            steeringMotor = new CANJaguar(steerID, CANJaguar.ControlMode.kPosition);
            steeringMotor.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
            steeringMotor.configMaxOutputVoltage(Parameters.maxMotorVoltage);
            steeringMotor.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            steeringMotor.setPID(Parameters.steeringProportionalValue * Reverse,
                    Parameters.steeringIntegralValue * Reverse,
                    Parameters.steeringDerivativeValue * Reverse);
            steering = true;
            this.name = name;
        }
        else
        {
            steeringMotor = null;
        }   
    }

    /**
     * enablePositionControl()
     * 
     * @throws CANTimeoutException 
     * 
     * This method enables the position control of the wheel.
     */
    public void enablePositionControl() throws CANTimeoutException {
        if (steering && steeringMotor != null) 
        {
            steeringMotor.enableControl();
            steeringMotor.setX(setPoint);
            enabled = true;
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
        if (steering && steeringMotor != null) 
        {
            steeringMotor.disableControl();
            enabled = false;
        }
    }
    
    /**
     * setPosition()
     * 
     * @param outputValue
     * 
     * @return Returns true when the steering is already at the setpoint
     * 
     * @throws CANTimeoutException 
     * 
     * This method sets the starting position of the wheel.
     */
    public boolean setPosition(double outputValue) throws CANTimeoutException 
    {
        if (outputValue < 0.1)
        {
            outputValue = 0.5;
        }
        if (outputValue > 0.9)
        {
            outputValue = 0.5;
        }        
        setPoint = outputValue;
        if (isSteeringCloseEnough()) 
        {
            return true;
        }
        if (steeringMotor != null)
        {
            if (!enabled)
            {
                enablePositionControl();
            }
            steeringMotor.setX(outputValue);
        }
        return false;
    }        

    /**
     * getPosition()
     * 
     * @return
     * @throws CANTimeoutException 
     * 
     * This method returns a value as to where the position of the wheel is.
     */
    public double getPosition() throws CANTimeoutException {
        if (steering && steeringMotor != null) 
        {
            return steeringMotor.getPosition();
        }
        return -1;
    }
    
    public boolean isSteeringCloseEnough() throws CANTimeoutException
    {
        if (steeringMotor == null)
        {
            return false;
        }
        double steeringDelta = FRCMath.abs(steeringMotor.getPosition() - setPoint);
        if (steeringDelta <= Parameters.kSteeringDeadband)
        {
            return true;
        }
        return false;
    }
    
    public void processWheel() throws CANTimeoutException
    {
        if (steeringMotor != null)
        {
//            System.out.println("Wheel " + name + ": " + steeringMotor.getOutputCurrent());
            if (isSteeringCloseEnough())
            {
                disablePositionControl();
            }
            else
            {
                if (!enabled)
                {
                    enablePositionControl();
                }
            }
        }
    }
    
    /**
     * 
     */
    public double getSteeringCurrent() throws CANTimeoutException
    {
        if(steeringMotor != null)
        {
            return steeringMotor.getOutputCurrent();
        }
        else
        {
            return -1;
        }
    }
}
