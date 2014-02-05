package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.Solenoid;

/*
 */
public class Catcher {

    private Solenoid retract;
    private Solenoid extend;
    private boolean deployed;
    
    
    
    /* not finished public catcher
     * 
     */
    public Catcher() {
        extend = new Solenoid(Parameters.CatcherOutSolenoidChannel);
        retract = new Solenoid(Parameters.CatcherInSolenoidChannel);
        retract();
    }

    /**
     * deploy()
     *
     * This method deploys the catcher
     *
     * @return
     */
    public void deploy() {
        retract.set(false);
        extend.set(true);
        deployed = true;
    }

    /**
     * retract()
     *
     * This method retracts the catcher
     *
     * @return
     */
    public void retract() {
        retract.set(true);
        extend.set(false);
        deployed = false;
    }

    /**
     * isDeployed()
     *
     * This method returns whether or not the catcher is deployed
     *
     * @return true - the catcher is deployed false - the catcher is not
     * deployed
     */
    public boolean isDeployed() {
        return deployed;
    }

    /**
     * isRetracted()
     *
     * This method returns whether or not the catcher is retracted
     *
     * @return true - the catcher is retracted false - the catcher is not
     * retracted
     */
    public boolean isRetracted() {

        return !deployed;
    }
}
