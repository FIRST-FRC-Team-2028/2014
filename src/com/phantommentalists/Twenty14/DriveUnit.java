package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.can.CANTimeoutException;

/*
 * DriveUnit allocation
 */
public class DriveUnit {

    public Wheel front;
    public Wheel rear;
    private DriveMotor driveMotor;

    public DriveUnit(int frontCANID, int rearCANID, int frontDriveMotorCANID, int rearDriveMotorCANID
            , int driveShiftSolenoidChannel) throws CANTimeoutException {
        front = new Wheel(frontCANID);
        rear = new Wheel(rearCANID);
        driveMotor = new DriveMotor(frontDriveMotorCANID, rearDriveMotorCANID, driveShiftSolenoidChannel);
    }
    
    public void enablePositionControl() throws CANTimeoutException
    {
        front.enablePositionControl();
        rear.enablePositionControl();
    }
    
    public void disablePositionControl() throws CANTimeoutException
    {
        front.disablePositionControl();
        rear.disablePositionControl();
    }

    public void slewDrive(double drivePower, double turnAngle) throws CANTimeoutException {
        double frontPOS = convertJoystickToPosition(turnAngle);
        double rearPOS = 0.0;
        if (frontPOS == 0.5) {
            rearPOS = 0.5;
        } else {
            rearPOS = 0.5 + (0.5 - frontPOS);
        }
        driveMotor.set(drivePower);
        front.setPosition(frontPOS);
        rear.setPosition(rearPOS);           //Return to convrtjtp(turnangle) for sensor
    }

    public void crabDrive(double drivePower, double turnAngle) throws CANTimeoutException {
        driveMotor.set(drivePower);
        front.setPosition(convertJoystickToPosition(turnAngle));
        rear.setPosition(convertJoystickToPosition(turnAngle));
        //System.out.println(turnAngle);
    }

//    public void axisTurn(char leftRight) {
//    }
    public double convertJoystickToPosition(double joystickValue) {
        double temp = joystickValue + 1;
        temp = temp / 2;
        if (temp >= 0.48 && temp <= 0.52) {
            temp = 0.5;
        }
        return temp;
    }
    
    public double getFrontPosition() throws CANTimeoutException {
        return front.getPosition();
    }
    public double getRearPosition() throws CANTimeoutException
    {
        return rear.getPosition();
    }
    public void processDriveUnit() {
        
    }
}