package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author jcurtiss001
 */
public class DriveMotor {

    public CANJaguar frontMotor;
    public CANJaguar rearMotor;
    public Solenoid shiftSolenoid;
    

    public DriveMotor(int frontDriveCANID, int rearDriveCANID, int shiftChannel) throws CANTimeoutException {
        if (frontDriveCANID != 0)
        {
            frontMotor = new CANJaguar(frontDriveCANID, CANJaguar.ControlMode.kPercentVbus);
            frontMotor.configMaxOutputVoltage(Parameters.maxMotorVoltage);
            frontMotor.configNeutralMode(CANJaguar.NeutralMode.kBrake);
        }
        else
        {
            frontMotor = null;
        }
        if (rearDriveCANID != 0)
        {
            rearMotor = new CANJaguar(rearDriveCANID, CANJaguar.ControlMode.kPercentVbus);
            rearMotor.configMaxOutputVoltage(Parameters.maxMotorVoltage);
            rearMotor.configNeutralMode(CANJaguar.NeutralMode.kBrake);
        }
        else
        {
            rearMotor = null;
        }
        shiftSolenoid = new Solenoid(shiftChannel);
        setGear(Gear.kHigh);
    }

    public void set(double setpoint) throws CANTimeoutException {
        if (frontMotor != null)
        {
            frontMotor.setX(setpoint * -1.0);
        }
        if (rearMotor != null)
        {
            rearMotor.setX(setpoint * -1.0);
        }
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
            shiftSolenoid.set(false);
        } 
        else{
            shiftSolenoid.set(true);
        }
    }

    public boolean isLowGear() {
        if (shiftSolenoid.get() == false) //FIX ME!!! Verify true is really high gear
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
        if (!shiftSolenoid.get()) //FIX ME!!! Verify false is really low gear
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
    
    /**
     * 
     */
    public double getFrontMotorCurrent() throws CANTimeoutException
    {
        if(frontMotor != null)
        {
            return frontMotor.getOutputCurrent();
        }
        else
        {
            return -1;
        }
    }
    
    /**
     * 
     */
    public double getRearMotorCurrent() throws CANTimeoutException
    {
        if(rearMotor != null)
        {
            return rearMotor.getOutputCurrent();
        }
        else
        {
            return -1;
        }
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
