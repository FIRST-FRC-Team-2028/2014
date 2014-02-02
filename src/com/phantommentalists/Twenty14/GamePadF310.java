/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phantommentalists.Twenty14;

/**
 *
 * @author Administrator
 */
import edu.wpi.first.wpilibj.Joystick;

public class GamePadF310 extends Joystick {

    // constants for GamePad F310 analog inputs
    public final static int kLeftThumbstick_X = 1;
    public final static int kLeftThumbstick_Y = 2;
    public final static int kRightThumbstick_X = 4;
    public final static int kRightThumbstick_Y = 5;
    public final static int kAxisTrigger = 3; //left trigger is 1 right trigger is -1
    
    // constants for GamePad F310 digital inputs
    public final static int kA = 1;
    public final static int kB = 2;
    public final static int kX = 3;
    public final static int kY = 4;
    public final static int kLeftBumper = 5;
    public final static int kRightBumper = 6;
    public final static int kBack = 7;
    public final static int kStart = 8;
    public final static int kLeftJoystickButton = 9;
    public final static int kRightJoystickButton = 10;

    public GamePadF310(int id) {
        super(id, 5, 10);
        setAxisChannel(Joystick.AxisType.kX, 1);
        setAxisChannel(Joystick.AxisType.kY, 2);
        setAxisChannel(Joystick.AxisType.kZ, 3);
        setAxisChannel(Joystick.AxisType.kTwist, 4);
    }

    public double getLeftThumbStickX() {
        return getRawAxis(kLeftThumbstick_X);
    }

    public double getLeftThumbStickY() {
        return getRawAxis(kLeftThumbstick_Y);
    }

    public double getRightThumbStickX() {
        return getRawAxis(kRightThumbstick_X);
    }

    public double getRightThumbStickY() {
        return getRawAxis(kRightThumbstick_Y);
    }

    /***
     * This method return the analog value of the either of the triggers in
     * the range -1.0...0...1.0, where 1.0 is left fully depressed and 0.0 is
     * neither depressed and -1.0 is right fully depressed.
     *
     * @return double in the range -1.0 ... 0 ... 1.0
     */
    public double getAxisTrigger() {
        return getRawAxis(kAxisTrigger);
    }

    /***
     * This method return the analog value of the left trigger in the range
     * 0...1.0, where 1.0 is fully depressed and 0.0 is not depressed.
     *
     * @return double in the range 0 ... 1.0
     */
    public double getLeftTrigger()
    {
        double value = getAxisTrigger();
        if (value <= 0.0)
            return 0.0;
        return value;
    }

    /***
     * This method return the analog value of the right trigger in the range
     * 0...1.0, where 1.0 is fully depressed and 0.0 is not depressed.
     *
     * @return double in the range 0 ... 1.0
     */
    public double getRightTrigger()
    {
        double value = getAxisTrigger();
        if (value >= 0.0)
            return 0.0;
        return -1.0 * value;
    }

    public boolean getButtonX() {
        return getRawButton(kX);
    }

    public boolean getButtonA() {
        return getRawButton(kA);
    }

    public boolean getButtonB() {
        return getRawButton(kB);
    }

    public boolean getButtonY() {
        return getRawButton(kY);
    }

    public boolean getButtonLeftBumper() {
        return getRawButton(kLeftBumper);
    }

    public boolean getButtonRightBumper() {
        return getRawButton(kRightBumper);
    }

    public boolean getButtonLeftJoystickButton() {
        return getRawButton(kLeftJoystickButton);
    }

    public boolean getButtonRightJoystickButton() {
        return getRawButton(kRightJoystickButton);
    }

    public boolean getButtonBack() {
        return getRawButton(kBack);
    }

    public boolean getButtonStart() {
        return getRawButton(kStart);
    }
}