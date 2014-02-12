package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.Solenoid;

/*
 */
public class Arm {

    public Solenoid inSolenoid;
    public Solenoid outSolenoid;

    public Arm(int inSolenoidChannel, int outSolenoidChannel) {
        inSolenoid = new Solenoid(inSolenoidChannel);
        outSolenoid = new Solenoid(outSolenoidChannel);
    }

    public void extend() {
        inSolenoid.set(false);
        outSolenoid.set(true);
    }

    public boolean isExtended() {
        return outSolenoid.get();
    }

    public void retract() {
        outSolenoid.set(false);
        inSolenoid.set(true);
    }

    public boolean isRetracted() {
        return inSolenoid.get();
    }
}