package com.phantommentalists.Twenty14;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author jcurtiss001
 */
public class DriveMotor {

    public CANJaguar motor;
    public Solenoid shiftHighSolenoid;
    public Solenoid shiftLowSolenoid;
    

    public DriveMotor(int driveCANID, int shiftHighChannel, int shiftLowChannel) throws CANTimeoutException {
        motor = new CANJaguar(driveCANID, CANJaguar.ControlMode.kPercentVbus);
        motor.configMaxOutputVoltage(Parameters.maxMotorVoltage);
        motor.configNeutralMode(CANJaguar.NeutralMode.kBrake);
        shiftHighSolenoid = new Solenoid(shiftHighChannel);
        shiftLowSolenoid = new Solenoid(shiftLowChannel);
        setGear(Gear.kHigh);
    }

    public void set(double setpoint) throws CANTimeoutException {
        motor.setX(setpoint);
    }

    /**
     * setGear
     *
     * This method sets gear to low or high based on a boolean value
     * (true/false)
     *
     * @param gear - true equals shifting to low gear and false equals shifting
     * to high gear
     */
    public void setGear(Gear gear) {
        if (gear == Gear.kLow) {
            shiftHighSolenoid.set(false);
        } else {
            shiftHighSolenoid.set(true);
        }
    }

    public boolean isLowGear() {
        if (shiftHighSolenoid.get() == false) //FIX ME!!! Verify true is really high gear
        {
            return true;
        } else {
            return false;
        }
    }

    /**
     * isHighGear
     *
     * This method returns true if in high gear or false if in low gear
     *
     * @return
     */
    public boolean isHighGear() {
        if (!shiftHighSolenoid.get()) //FIX ME!!! Verify false is really low gear
        {
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * @return
     */
    public Gear getGear() {
        if (isHighGear()) {
            return Gear.kHigh;
        }
        return Gear.kLow;
    }

    public static class Gear {

        private static final int kLowValue = 1;
        private static final int kHighValue = 2;
        private final int value;
        public static final Gear kLow = new Gear(Gear.kLowValue);
        public static final Gear kHigh = new Gear(Gear.kHighValue);

        protected Gear(int gear) {
            this.value = gear;
        }
    }
}
