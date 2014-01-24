package com.phantommentalists.Twenty14;

/**
 *
 * and open the template in the editor.
 *
 */
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * ChopSticks allocation
 *
 * @author mburt001
 *
 */
public class ChopSticks {

    public Relay left;
    public Victor rotationMotor;
    public Relay right;

    /**
     *
     * ChopSticks()
     *
     * not finished public ChopSticks
     *
     */
    public ChopSticks(int leftArmChannel, int rightArmChannel, int rotationChannel) {
        left = new Relay(leftArmChannel);
        right = new Relay(rightArmChannel);
        rotationMotor = new Victor(rotationChannel);
    }

    public void turnOnChopSticks() {
        left.set(Relay.Value.kForward);
        right.set(Relay.Value.kReverse);
    }
    
    public void turnOffChopSticks() {
       left.set(Relay.Value.kOff);
       right.set(Relay.Value.kOff);
    }
    
    public void setRotation(double outputValue) {
        rotationMotor.set(outputValue);
    }
    
    public boolean isChopSticksOn() {
        if (left.get() != Relay.Value.kOff && right.get() != Relay.Value.kOff) {
         return true;
        }
        return false;
    }
    
    public boolean isChopSticksOff() {
        if (left.get() == Relay.Value.kOff && right.get() == Relay.Value.kOff) {
            return true;
        }
        return false;
        }
    }