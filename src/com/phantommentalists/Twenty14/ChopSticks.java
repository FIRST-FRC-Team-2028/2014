package com.phantommentalists.Twenty14;

/**
 *
 * 
 * and open the template in the editor.
 *
 */
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Relay;

/**
 *
 * ChopSticks allocation
 *
 * @author mburt001
 *
 */
public class ChopSticks {

    public Relay left;
    public Relay right;
    public Solenoid extendSolenoid;
    public Solenoid retractSolenoid;

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

    }

    /**
     *
     * turnOnChopSticks()
     *
     * This method turns both left and right ChopSticks on.
     */
    public void turnOnChopSticks() {
        left.set(Relay.Value.kReverse);
        right.set(Relay.Value.kForward);
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
        
        
    }
    /**
     * retractChopSticks()
     * 
     * This method retracts the ChopSticks and if the are on, turns them off.
     */
    public void retractChopSticks() {
        extendSolenoid.set(false);
        retractSolenoid.set(true);
        turnOffChopSticks();
        
    }
    public boolean isDeployed()
    {
        return extendSolenoid.get();
    }
    public boolean isRetracted()
    {
        return retractSolenoid.get();
    }
    public void processChopSticks()
    {
    
    }
    
}
