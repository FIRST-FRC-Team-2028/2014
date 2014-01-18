package com.phantommentalists.Twenty14;

/*
 * Parameters allocation
 */
public class Parameters {

    public static final int frontRightSteeringCanId = 20;
    public static final int frontLeftSteeringCanId = 10;
    public static final int rightDriveCanId = 23;
    public static final int leftDriveCanId = 14;
    public static final int rearLeftSteeringCanId = 40;
    public static final int rearRightSteeringCanId = 30;
    public static final int steeringProportionalValue = -1;
    public static final int steeringIntegralValue = -1;
    public static final int steeringDerivativeValue = -1;
    public static final double maxMotorVoltage = 12.0;
    public final static int leftDriveShifterHigh = -1;
    public final static int leftDriveShifterLow = -1;
    public final static int rightDriveShifterHigh = -1;
    public final static int rightDriveShifterLow = -1;
    public static final double kJoyStickDeadband = 0.05;
    public static final double TIMER_DELAY = 0.1;
    static double kJoystickDeadband;
    static int kLowGearButton;
    static int kHighGearButton;
}
