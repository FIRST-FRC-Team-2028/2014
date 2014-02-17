package com.phantommentalists.Twenty14;

/**
 *
 * 
 * and open the template in the editor.
 *
 */
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Relay;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * ChopSticks allocation
 *
 * @author mburt001
 *
 */
public class ChopSticks {

    /**
     * State()
     *
     * This method sets the state that the loader can be in.
     */
    public class State {

        public State() {
            value = kRetracted;
        }
        public int value;
        public static final int kRetracted = 0;
        public static final int kDeployed = 1;
        public static final int kPickUpBall = 2;
    }
    public State state;
    public Relay left;
    public Relay right;
    public Solenoid extendSolenoid;
    public Solenoid retractSolenoid;
    private boolean deployed;
    private Timer deployTimer;
    private TimerTask TimerTask;

    protected class ChopSticksTimerTask extends TimerTask {

        private ChopSticks loader;

        public ChopSticksTimerTask(ChopSticks l) {
            loader = l;
        }

        public void run() {
            deployed = true;
        }
    }

    /**
     * ChopSticks()
     *
     * not finished public ChopSticks
     */
    public ChopSticks() {
        left = new Relay(Parameters.leftChopStickRelayChannel);
        right = new Relay(Parameters.rightChopStickRelayChannel);
        extendSolenoid = new Solenoid(Parameters.loaderOutSolenoidChannel);
        retractSolenoid = new Solenoid(Parameters.loaderInSolenoidChannel);
        extendSolenoid.set(false);
        retractSolenoid.set(true);
        deployTimer = null;
        TimerTask = null;

    }

    /**
     *
     * turnOnChopSticks()
     *
     * This method turns both left and right ChopSticks on.
     */
    public void turnOnChopSticks() {
        left.set(Relay.Value.kForward);
        right.set(Relay.Value.kReverse);
    }

    /**
     * turnOffChopSticks()
     *
     * This method turns both left and right ChopSticks off.
     */
    public void turnOffChopSticks() {
        left.set(Relay.Value.kOff);
        right.set(Relay.Value.kOff);
    }

    /**
     * isChopSticksOn()
     *
     * This method returns whether or not the ChopSticks are on or off, rotating
     * or stationary.
     */
    public boolean isChopSticksOn() {
        if (left.get() != Relay.Value.kOff && right.get() != Relay.Value.kOff) {
            return true;
        }
        return false;
    }

    /**
     * isChopSticksOff()
     *
     * This method returns whether or not the ChopSticks are on or off, rotating
     * or stationary.
     */
    public boolean isChopSticksOff() {
        if (left.get() == Relay.Value.kOff && right.get() == Relay.Value.kOff) {
            return true;
        }
        return false;
    }

    /**
     * deployChopSticks()
     *
     * This method deploys the ChopSticks and if they are off, turns them on.
     */
    public void deployChopSticks() {
        retractSolenoid.set(false);
        extendSolenoid.set(true);
        if (isChopSticksOff()) {
            turnOnChopSticks();
        }
        deployTimer = new Timer();
        TimerTask = new ChopSticksTimerTask(this);
        deployTimer.schedule(TimerTask, Parameters.ChopSticksdeployTimeOut);


    }

    /**
     * retractChopSticks()
     *
     * This method retracts the ChopSticks and if they are on, turns them off.
     */
    public void retractChopSticks() {
        extendSolenoid.set(false);
        retractSolenoid.set(true);
        if (isChopSticksOn()) {
            turnOffChopSticks();
        }

    }

    /**
     * isDeployed()
     *
     * This method returns if the ChopSticks are deployed.
     *
     * @return
     */
    public boolean isDeployed() {
        return deployed;
    }

    /**
     * isRetracted()
     *
     * This method returns if the ChopSticks are retracted.
     *
     * @return
     */
    public boolean isRetracted() {
        return !deployed;
    }

    /**
     * processChopSticks()
     *
     * Handles ChopSticks. 
     */
    public void processChopSticks() {
        if (state.value == State.kRetracted) {
            if (isDeployed()) {
                state.value = State.kDeployed;
            }
            if (isChopSticksOn() && isDeployed()) {
                state.value = State.kPickUpBall;
            }
        }
        if (state.value == State.kDeployed) {
            if (isDeployed() && isChopSticksOn()) {
                state.value = State.kPickUpBall;
            }
            if (isRetracted()) {
                state.value = State.kRetracted;
            }
        }
        if (state.value == State.kPickUpBall) {
            if (isRetracted()) {
                state.value = State.kPickUpBall;
            }
        }
    }
}
