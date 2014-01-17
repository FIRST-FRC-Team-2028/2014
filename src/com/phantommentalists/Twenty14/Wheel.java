package com.phantommentalists.Twenty14;

/*
 * 
 * and open the template in the editor.
 * 
 */
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 * @author mburt001
 *
 */
public class Wheel {

    CANJaguar steeringMotor;
    private double setPoint = 0.0;
    private boolean steering = false;

    /**
     *
     * @param steerID
     * @throws CANTimeoutException
     *
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
     *
     * @throws CANTimeoutException
     *
     */
    public void enablePositionControl() throws CANTimeoutException {
        if (steering) {
            steeringMotor.enableControl();
        }
    }

    /**
     *
     * @throws CANTimeoutException
     *
     */
    public void disablePositionControl() throws CANTimeoutException {
        if (steering) {
            steeringMotor.disableControl();
        }
    }

    /**
     *
     * @param outputValue
     * @return
     *
     */
    public double convertJoystickToPosition(double outputValue) {
        double scaled = (setPoint + 1) / 2;
//      double scaled = ((outputValue * 0.26) + 0.55);
        return scaled;
    }

    /**
     *
     * @param joystickPercentPower
     * @return
     * @throws CANTimeoutException
     *
     */
    public boolean setPosition(double joystickPercentPower) throws CANTimeoutException {
        if (steering) {
            if (joystickPercentPower == setPoint) {
                return false;
            }
            setPoint = joystickPercentPower;
            double outputValue = convertJoystickToPosition(joystickPercentPower);
            steeringMotor.setX(outputValue);
            return true;
        }
        return false;
    }

    /**
     *
     * @return @throws CANTimeoutException
     *
     */
    public double getPosition() throws CANTimeoutException {
        if (steering) {
            return steeringMotor.getPosition();
        }
        return -1;

    }
}