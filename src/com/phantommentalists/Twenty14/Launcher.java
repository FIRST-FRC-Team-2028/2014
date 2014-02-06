package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.can.*;

/*
 * Launcher allocation
 */
public class Launcher {

    public class State
    {
        /** State()
         * 
         * Sets the States the Launcher can be in
         */
        public State()
        {
            value = kSafe;
        }
        public int value;
        public static final int kSafe = 0;
        public static final int kShooting = 1;
        public static final int kRearming = 2;
    }
    public State state;
    public CANJaguar launchMotorOne;
    public CANJaguar launchMotorTwo;
    public Solenoid engageSolenoid;
    public Solenoid disengageSolenoid;
    
    public Launcher(int motorOneCanID, int motorTwoCanID) throws CANTimeoutException
    {
        if(motorOneCanID == 0){
            launchMotorOne = null;}
        else{
            launchMotorOne = new CANJaguar(motorOneCanID,CANJaguar.ControlMode.kPercentVbus);
            launchMotorOne.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            launchMotorOne.configMaxOutputVoltage(Parameters.maxMotorVoltage);
        }
        if (motorTwoCanID == 0){
            launchMotorTwo = null;}
        else{
            launchMotorTwo = new CANJaguar(motorTwoCanID,CANJaguar.ControlMode.kPercentVbus);
            launchMotorTwo.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            launchMotorTwo.configMaxOutputVoltage(Parameters.maxMotorVoltage);
        }
        
        engageSolenoid = new Solenoid(-1);
        disengageSolenoid = new Solenoid(-1);
        engageSolenoid.set(true);
        disengageSolenoid.set(false);
    }
    /** processLaucher()
     * 
     * Handles Launcher
     */
    public void processLauncher(){
        
    }
    /* isShooting()
     *
     * checks if the launcher is shooting
     */
    public boolean isShot() throws CANTimeoutException {
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
    public void shoot(double shootVariable) throws CANTimeoutException {
        if(state.value == State.kSafe)
        {
            state.value = State.kShooting;
            disengage();
            launchMotorOne.setX(shootVariable);
            launchMotorTwo.setX(shootVariable);
        //while(!isShot()){}
        //retract();
        }
//        else{return;}
        
    }
    
        /* retract()
         *
         * make motors retract the shooter
         */
    public void retract() throws CANTimeoutException {
            state.value = State.kRearming;
            launchMotorOne.setX(-0.33);
            launchMotorTwo.setX(-0.33); 
        //Keep Code may use later
//        while(!canReload()){}
//        engage();
//        launchMotorOne.setX(0);
//        launchMotorTwo.setX(0);
//        state.value = State.kSafe;
        }
        /*  airPass()
         * 
         * shoot the ball at a lesser power 
         * for an air pass to an ally or
         * to throw of over the truss
         */
    public void airPass() {
        
    }
    /** engage()
     * 
     *  Engages the locky thingy
     */
    protected void engage()
    {
        if (!isEngaged())
        {
            disengageSolenoid.set(false);
            engageSolenoid.set(true);
        }
    }
    /** isEngaged()
     * 
     * Asks the locky thingy if it is engaged
     * @return 
     */
    protected boolean isEngaged()
    {
        
        return engageSolenoid.get();
    }
    /** disengage()
     * 
     * Disengages the locky thingy
     */
    protected void disengage()
    {
        if(isEngaged())
        {
            engageSolenoid.set(false);
            disengageSolenoid.set(true);
        }
    }
}
