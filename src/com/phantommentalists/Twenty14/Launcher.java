package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.can.*;
import java.util.Timer;
import java.util.TimerTask;

/*
 * Launcher allocation
 */
public class Launcher
{

    public Timer launchTimer;
    public TimerTask timerTask;
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
            value = kUnknown;
        }
        public int value;
        public static final int kUnknown = 0;
        public static final int kSafe = 1;
        public static final int kShooting = 2;
        public static final int kRearming = 3;
    }

    /**
     *
     */
    protected class LauncherTimerTask extends TimerTask
    {

        private Launcher launcher;

        /**
         *
         * @param l
         */
        public LauncherTimerTask(Launcher l)
        {
            launcher = l;
        }

        /**
         *
         */
        public void run()
        {
            try
            {
                launcherStop();
            } catch (CANTimeoutException ex)
            {
                ex.printStackTrace();
            }

        }
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

        engageSolenoid = new Solenoid(Parameters.launcherEngageSolenoidChannel);
        disengageSolenoid = new Solenoid(Parameters.launcherDisengageSolenoidChannel);
        engageSolenoid.set(true);
        disengageSolenoid.set(false);
    }

    /**
     * processLaucher()
     *
     * Handles Launcher
     */
    public void processLauncher() throws CANTimeoutException
    {
        if (state.value == State.kUnknown)
        {
            disengage();
            retract();
        }
        if (!launchMotorOne.getReverseLimitOK())
        {
            state.value = State.kSafe;
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
        launchMotorOne.setX(0.0);
        launchMotorTwo.setX(0.0);
        launchTimer = null;
        timerTask = null;
        retract();
    }
    /* isShooting()
     *
     * checks if the launcher is shooting
     */

    public boolean isShot() throws CANTimeoutException
    {
        return !launchMotorOne.getForwardLimitOK();
    }
    /* canReaload()
     *
     * checks if the launcher is in the
     * right position to reload a ball
     */

    public boolean canReload() throws CANTimeoutException
    {
        return (state.value == State.kSafe);
    }

    /**
     * 
     * @param shootVariable
     * @throws CANTimeoutException 
     */
    public void timedShoot(double shootVariable) throws CANTimeoutException
    {
        if (launchTimer == null)
        {
            launchTimer = new Timer();
            timerTask = new LauncherTimerTask(this);
            launchMotorOne.setX(shootVariable);
            launchMotorTwo.setX(shootVariable);
            // System.out.println("Motors Set to 12.0 Volts");
            launchTimer.schedule(timerTask, 80);
        }

    }
    /* shoot()
     *
     * shoots the ball
     */

    public void shoot(double shootVariable) throws CANTimeoutException
    {
        if (state.value == State.kSafe)
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
    public void timedRetract() throws CANTimeoutException
    {
        if (launchTimer == null)
        {
            launchTimer = new Timer();
            timerTask = new LauncherTimerTask(this);
            // state.value = State.kRearming;
            launchMotorOne.setX(-0.33);
            launchMotorTwo.setX(-0.33);
            launchTimer.schedule(timerTask, 50);
        }
    }

    public void retract() throws CANTimeoutException
    {
        state.value = State.kRearming;
        launchMotorOne.setX(Parameters.klauncherRetractPower);
        launchMotorTwo.setX(Parameters.klauncherRetractPower);
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
    }

    /**
     * engage()
     *
     * Engages the locky thingy
     */
    protected void engage()
    {
        if (!isEngaged())
        {
            disengageSolenoid.set(false);
            engageSolenoid.set(true);
        }
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
        return engageSolenoid.get();
    }

    /**
     * disengage()
     *
     * Disengages the locky thingy
     */
    protected void disengage()
    {
        engageSolenoid.set(false);
        disengageSolenoid.set(true);
    }

    public boolean isSafe()
    {
        if (state.value == State.kSafe)
        {
            return true;
        } else
        {
            return false;
        }
    }

    public boolean isShooting()
    {
        if (state.value == State.kShooting)
        {
            return true;
        } else
        {
            return false;
        }
    }

    public boolean isRearming()
    {
        if (state.value == State.kRearming)
        {
            return true;
        } else
        {
            return false;
        }
    }
}
