package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.can.CANTimeoutException;

/*
 *       CrabDrive allocation:
 *               Hunter Lawrence
 *          Author:
 *               Hunter Lawrence
 */
public class CrabDrive {
    
    public DriveUnit left;
    public DriveUnit right;
    
    
    public CrabDrive() throws CANTimeoutException {
//        left = new DriveUnit(Parameters.frontLeftSteeringCanId, 
//                Parameters.rearLeftSteeringCanId, Parameters.leftDriveCanId,
//                Parameters.leftDriveShifterHigh, Parameters.leftDriveShifterLow);
        right = new DriveUnit(Parameters.frontRightSteeringCanId, 
                Parameters.rearRightSteeringCanId, Parameters.rightDriveCanId,
                Parameters.rightDriveShifterHigh, Parameters.rightDriveShifterLow);
        
    }
    
    public void enablePositionControl() throws CANTimeoutException {
        right.enablePositionControl();
//        left.enablePositionControl();
    }
    
    public void disablePositionControl() throws CANTimeoutException {
        right.disablePositionControl();
//        left.disablePositionControl();
    }
    
    /*   crabDrive()
     *   
     *   Makes all wheels turn in the same direction.
     */

    public void crabDrive(double drivePower, double turnAngle) throws CANTimeoutException {
        //left.crabDrive(drivePower, turnAngle);
        right.crabDrive(drivePower, turnAngle);
       // System.out.println(turnAngle);
    }
    /*  slewDrive()
     *
     *  Makes the robot turn on its axis.
     */

    public void slewDrive(double drivePower, double turnAngle) throws CANTimeoutException {
        //left.slewDrive(drivePower, turnAngle);
        right.slewDrive(drivePower, turnAngle);
    }

    public void setDrive(double drivePower) {
        
    }
    
    public void setTurn(double turnAngle) {
        
    }
    /**
     * getRearPosition()
     * 
     * Gets the rear position of the crab drive.
     * 
     * @return
     * @throws CANTimeoutException
     */
    public double getRearPosition() throws CANTimeoutException {
        return right.getRearPosition();
    }
    
    /**
     *getFrontPosition()
     * 
     * Gets the front position of crab drive.
     * 
     * @return
     * @throws CANTimeoutException
     */
    public double getFrontPosition() throws CANTimeoutException {
        return left.getFrontPosition();
    }
}
