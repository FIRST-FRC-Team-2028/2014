package com.phantommentalists.Twenty14;

/*
 */
public class Catcher {

    public Arm arm1;
    public Arm arm2;
    public Arm arm3;
    public Arm arm4; 
    
 /* not finished public catcher
  * 
  */
    public Catcher() {
        arm1 = new Arm(Parameters.oneInSolenoidChannel, Parameters.oneOutSolenoidChannel);
        arm2 = new Arm(Parameters.twoInSolenoidChannel, Parameters.twoOutSolenoidChannel);
        arm3 = new Arm(Parameters.threeInSolenoidChannel, Parameters.threeOutSolenoidChannel);
        arm4 = new Arm(Parameters.fourInSolenoidChannel, Parameters.fourOutSolenoidChannel);
    }

    /**
     * deploy()
     * 
     * This method deploys the catcher
     * 
     * @return
     */
    public void deploy() {
        arm1.extend();
        arm2.extend();
        arm3.extend();
        arm4.extend();
    }

    public void retract() {
        arm1.retract();
        arm2.retract();
        arm3.retract();
        arm4.retract();
    }
    /**
     * isDeployed()
     * 
     * This method returns whether or not the catcher is deployed
     * 
     * @return true - the catcher is deployed
     *         false - the catcher is not deployed
     */
    public boolean isDeployed() {
        if (arm1.isExtended() && arm2.isExtended() && arm3.isExtended() && arm4.isExtended()) {
            return true;
        }
        return false;
    }

    public boolean isRetracted() {
        if (arm1.isRetracted() && arm2.isRetracted() && arm3.isRetracted() && arm4.isRetracted()){
            return true;
        }
        return false;
    }
}