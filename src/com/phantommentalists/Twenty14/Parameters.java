package com.phantommentalists.Twenty14;

/*
 * Parameters allocation
 */
public class Parameters {
    
    //Pneumatics
    public final static int leftDriveShifterHigh = 1;
    public final static int leftDriveShifterLow = 2;
    public final static int rightDriveShifterHigh = 3;
    public final static int rightDriveShifterLow = 4;
    public final static int CatcherInSolenoidChannel = -1;
    public final static int CatcherOutSolenoidChannel = -1;
    public static final int loaderOutSolenoidChannel = -1;
    public static final int loaderInSolenoidChannel = -1;
    
    //CAN IDs
    public static final int frontRightSteeringCanId = 23;
    public static final int frontLeftSteeringCanId = 10;
    public static final int rightDriveCanId = 20;
    public static final int leftDriveCanId = 14;
    public static final int rearLeftSteeringCanId = 40;
    public static final int rearRightSteeringCanId = 14;
    
    //Relays
    public static final int leftChopStickRelayChannel = -1;
    public static final int rightChopStickRelayChannel = -1;
    
    //Control
    public static final double kJoyStickDeadband = 0.05;
    public static final double TIMER_DELAY = 0.1;
    static double kJoystickDeadband = -1;
    static int kLowGearButton = -1;
    static int kHighGearButton = -1;
    
    //PWM Channels
    public static final int kCameraPanChannel = 6;
    public static final int kCameraTiltChannel = 7;

    //etc
    public static final double steeringProportionalValue = -350.0;
    public static final double steeringIntegralValue = -0.2;
    public static final double steeringDerivativeValue = -0.05;
    public static final double maxMotorVoltage = 12.0;
    
}
