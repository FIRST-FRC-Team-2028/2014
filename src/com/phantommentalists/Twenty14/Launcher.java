package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.can.*;
import edu.wpi.first.wpilibj.Timer;

/*
 * Launcher allocation
 */
public class Launcher
{

    public State state;
    public CANJaguar launchMotorOne;
    public CANJaguar launchMotorTwo;
    public Solenoid engageSolenoid;
    public Solenoid disengageSolenoid;

    /**
     *
     */
    public class State
    {

        /**
         * State()
         *
         * Sets the States the Launcher can be in
         */
        public State()
        {
            value = kSafe;
        }
        public int value;
        public static final int kUnknown = 0;
        public static final int kSafe = 1;
        public static final int kShooting = 2;
        public static final int kRearming = 3;
    }

    /**
     *
     * @param motorOneCanID
     * @param motorTwoCanID
     * @throws CANTimeoutException
     */
    public Launcher(int motorOneCanID, int motorTwoCanID) throws CANTimeoutException
    {
        if (motorOneCanID == 0)
        {
            launchMotorOne = null;
        } else
        {
            launchMotorOne = new CANJaguar(motorOneCanID, CANJaguar.ControlMode.kPercentVbus);
            launchMotorOne.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            launchMotorOne.configMaxOutputVoltage(Parameters.maxMotorVoltage);
        }
        if (motorTwoCanID == 0)
        {
            launchMotorTwo = null;
        } else
        {
            launchMotorTwo = new CANJaguar(motorTwoCanID, CANJaguar.ControlMode.kPercentVbus);
            launchMotorTwo.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            launchMotorTwo.configMaxOutputVoltage(Parameters.maxMotorVoltage);
        }
//        state.value = State.kSafe;
        engageSolenoid = new Solenoid(Parameters.launcherEngageSolenoidChannel);
        //disengageSolenoid = new Solenoid(Parameters.launcherDisengageSolenoidChannel);
        engageSolenoid.set(false);
       // disengageSolenoid.set(false);
    }

    /**
     * processLaucher()
     *
     * Handles Launcher
     */
    public void processLauncher() throws CANTimeoutException
    {
//        if (state.value == State.kUnknown)
//        {
//            disengage();
//            retract();
//        }
        if (!launchMotorOne.getReverseLimitOK())
        {
//            state.value = State.kSafe;
            engage();
        } else if (!launchMotorOne.getForwardLimitOK())
        {
            retract();
        }
    }

    /**
     *
     * @throws CANTimeoutException
     */
    public void launcherStop() throws CANTimeoutException
    {
        if (launchMotorOne != null)
        {
            launchMotorOne.setX(0.0);
        }
        if (launchMotorTwo != null)
        {
            launchMotorTwo.setX(0.0);
        }
        retract();
    }
    /* isShooting()
     *
     * checks if the launcher is shooting
     */

    public boolean isShot() throws CANTimeoutException
    {
        boolean launcherAtLimit = false;
        if (launchMotorOne != null)
        {
            launcherAtLimit = !launchMotorOne.getForwardLimitOK();
        }
        return launcherAtLimit;
    }
    /* canReaload()
     *
     * checks if the launcher is in the
     * right position to reload a ball
     */

    public boolean canReload() throws CANTimeoutException
    {
        return true;
    }

    /* shoot()
     *
     * shoots the ball
     */

    public void shoot(double shootVariable) throws CANTimeoutException
    {
        
//        if (state.value == State.kSafe)
//        {
//            state.value = State.kShooting;
            disengage();
            Timer.delay(0.05);
 if (launchMotorOne != null)
            {
                launchMotorOne.setX(shootVariable);
            }
            if (launchMotorTwo != null)
            {
                launchMotorTwo.setX(shootVariable);
            }           
            //while(!isShot()){}
            //retract();
//        }
//        else{return;}
    }
    

    public void retract() throws CANTimeoutException
    {
//        state.value = State.kRearming;
        disengage();
        if (launchMotorOne != null)
        {
            launchMotorOne.setX(Parameters.klauncherRetractPower);
        }
        if (launchMotorTwo != null)
        {
            launchMotorTwo.setX(Parameters.klauncherRetractPower);
        }
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
    public void airPass()
    {
        //  AAAARRRRRGGGGGHHHH!!!!
    }

    /**
     * engage()
     *
     * Engages the locky thingy
     */
    protected void engage()
    {
//            disengageSolenoid.set(true);
            engageSolenoid.set(false);
        
    }

    /**
     * isEngaged()
     *
     * Asks the locky thingy if it is engaged
     *
     * @return
     */
    protected boolean isEngaged()
    {
        return true;
    }

    /**
     * disengage()
     *
     * Disengages the locky thingy
     */
    protected void disengage()
    {
        engageSolenoid.set(true);
//        disengageSolenoid.set(false);
    }

    public boolean isSafe()
    {
//        if (state.value == State.kSafe)
//        {
//            return true;
//        } else
//        {
//            return false;
//        }
        return true;
    }

    public boolean isShooting()
    {
//        if (state.value == State.kShooting)
//        {
//            return true;
//        } else
//        {
//            return false;
//        }
        return true;
    }

    public boolean isRearming()
    {
//        if (state.value == State.kRearming)
//        {
//            return true;
//        } else
//        {
//            return false;
//        }
        return true;
    }
}
