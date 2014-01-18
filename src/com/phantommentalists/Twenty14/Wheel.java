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
    private double setPoint;
    private boolean driving = false;
    private boolean steering = false;

    public Wheel(Integer steerID, Integer driveID) throws CANTimeoutException {
        int ID = 0;
        if (steerID != null) {
            ID = steerID.intValue();
            steeringMotor = new CANJaguar(ID, CANJaguar.ControlMode.kPosition);
            steeringMotor.configMaxOutputVoltage(12.0); //TODO: Replace this with a Parameter
            steeringMotor.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            steeringMotor.setPID(Parameters.steeringProportionalValue,
                    Parameters.steeringIntegralValue,
                    Parameters.steeringDerivativeValue);
            steering = true;
        }
        if (driveID != null) {
            ID = driveID.intValue();
            driveMotor = new CANJaguar(ID, CANJaguar.ControlMode.kPercentVbus);
            driveMotor.configMaxOutputVoltage(12.0);//TODO: Replaxce this with a Parameter
            driveMotor.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            driving = true;
        }
    }

    public void enablePositionControl() throws CANTimeoutException {
        if (steering) {
            steeringMotor.enableControl();
        }
    }

    public void disablePositionControl() throws CANTimeoutException {
        if (steering) {
            steeringMotor.disableControl();
        }
    }

    public double convertJoystickToPosition(double outputValue) {
//        double scaled = (setPoint + 1) / 2;
        double scaled = ((outputValue * 0.26) + 0.55);
        return scaled;
    }

    public boolean setPosition(double outputValue) throws CANTimeoutException {
        if (steering) {
            if (outputValue == setPoint) {
                return false;
            }
            setPoint = outputValue;
            outputValue = convertJoystickToPosition(outputValue);
            steeringMotor.setX(outputValue);
            return true;
        }
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
