package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.can.CANTimeoutException;

/*
 *       CrabDrive allocation:
 *               Hunter Lawrence
 *          Author:
 *               Hunter Lawrence
 */
public class CrabDrive {
    
    private DriveUnit left;
    private DriveUnit right;
    
    public CrabDrive() {
        
    }
    /*   crabDrive()
     *   
     *   Makes all wheels turn in the same direction.
     */

    public void crabDrive(double drivePower, double turnAngle) throws CANTimeoutException {
        left.crabDrive(drivePower, turnAngle);
        right.crabDrive(drivePower, turnAngle);
    }
    /*  slewDrive()
     *
     *  Makes the robot turn on its axis.
     */

    public void slewDrive(double drivePower, double turnAngle) throws CANTimeoutException {
        left.slewDrive(drivePower, turnAngle);
        right.slewDrive(drivePower, turnAngle);
    }

    public void setDrive(double drivePower) {
        
    }
    
    public void setTurn(double turnAngle) {
        
    }
}