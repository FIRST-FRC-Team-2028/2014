package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.can.CANTimeoutException;

/*
 * DriveUnit allocation
 */
public class DriveUnit {

    private Wheel front;
    private Wheel rear;
    private DriveMotor driveMotor;

    public DriveUnit(int frontCANID, int rearCANID, int driveMotorCANID
            , int driveShiftHighSolenoidChannel, int driveShiftLowChannel) throws CANTimeoutException {
        front = new Wheel(frontCANID);
        rear = new Wheel(rearCANID);
        driveMotor = new DriveMotor(driveMotorCANID, driveShiftHighSolenoidChannel, driveShiftLowChannel);
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
        front.setPosition(turnAngle);
        rear.setPosition(turnAngle * -1);           //Return to convrtjtp(turnangle) for sensor
    }

    public void crabDrive(double drivePower, double turnAngle) throws CANTimeoutException {
        driveMotor.set(drivePower);
        front.setPosition(convertJoystickToPosition(turnAngle));
        rear.setPosition(convertJoystickToPosition(turnAngle));
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
}