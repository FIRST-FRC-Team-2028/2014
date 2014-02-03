package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.*;

/*
 * Launcher allocation
 */
public class Launcher {

    public CANJaguar launchMotorOne;
    public CANJaguar launchMotorTwo;
    public Launcher(int motorOneCanID, int motorTwoCanID) throws CANTimeoutException
    {
        launchMotorOne = new CANJaguar(motorOneCanID,CANJaguar.ControlMode.kPercentVbus);
        launchMotorOne.configNeutralMode(CANJaguar.NeutralMode.kBrake);
        launchMotorOne.configMaxOutputVoltage(Parameters.maxMotorVoltage);
        launchMotorTwo = new CANJaguar(motorTwoCanID,CANJaguar.ControlMode.kPercentVbus);
        launchMotorTwo.configNeutralMode(CANJaguar.NeutralMode.kBrake);
        launchMotorTwo.configMaxOutputVoltage(Parameters.maxMotorVoltage);
    }
    /* isShooting()
     *
     * checks if the launcher is shooting
     */
    public boolean isShooting() throws CANTimeoutException {
        return !launchMotorOne.getForwardLimitOK();
    }
   /* canReaload()
    *
    * checks if the launcher is in the
    * right position to reload a ball
    */
    public boolean canReload() throws CANTimeoutException{
        return !launchMotorOne.getReverseLimitOK();
    }
    /* shoot()
     *
     * shoots the ball
     */
    public void shoot(double shootVariable) {
        
    }
    
        /* retract()
         *
         * make motors retract the shooter
         */
    public void retract() {
    }
        /*  airPass()
         * 
         * shoot the ball at a lesser power 
         * for an air pass to an ally or
         * to throw of over the truss
         */
    public void airPass() {
        
    }
}