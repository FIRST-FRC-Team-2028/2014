package com.phantommentalists.Twenty14;

/*
 * Parameters allocation
 */
public class Parameters {
    // Debug
    public final static boolean debug = false;
    
    //Pneumatics
    public final static int leftDriveShifter = 1;
    public final static int rightDriveShifter = 2;
    public final static int CatcherInSolenoidChannel = 5;
    public final static int CatcherOutSolenoidChannel = 6;
    public static final int loaderOutSolenoidChannel = 7;
    public static final int loaderInSolenoidChannel = 8;
    
    //CAN IDs
    public static final int frontRightSteeringCanId = 30;
    public static final int frontLeftSteeringCanId = 20;    
    public static final int rightFrontDriveCanId = 43;
    public static final int rightRearDriveCanId = 34;
    public static final int leftFrontDriveCanId = 12;
    public static final int leftRearDriveCanId = 21;
    public static final int rearLeftSteeringCanId = 10;
    public static final int rearRightSteeringCanId = 40;  
    public static final int launcherMotorOneCANID = 50;
    public static final int launcherMotorTwoCANID = 60;

    //Relays
    public static final int compressorRelayChannel = 1;
    public static final int leftChopStickRelayChannel = 2;
    public static final int rightChopStickRelayChannel = 3;
   
    //Control
    public static final double kJoyStickDeadband = 0.05;
    public static final double kSteeringDeadband = 0.05;    
    public static final double TIMER_DELAY = 0.1;
    static double kJoystickDeadband = -1;
    static int kLowGearButton = -1;
    static int kHighGearButton = -1;
    public static final int compressorSwitchChannel = 1;

    //etc
    public static final double steeringProportionalValue = 600.0;
    public static final double steeringIntegralValue = 0.75;
    public static final double steeringDerivativeValue = 0.75;
    public static final double maxMotorVoltage = 12.0;
    public static final long CatcherdeployTimeOut = 500;
    
    public static final double leftFrontRev = 1.0;
    public static final double leftRearRev = 1.0;
    public static final double rightFrontRev = 1.0;
    public static final double rightRearRev = 1.0;
      
}
