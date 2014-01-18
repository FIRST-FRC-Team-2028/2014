package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.can.CANTimeoutException;

/*
 * DriveUnit allocation
 */
public class DriveUnit {

    private Wheel front;
    private Wheel rear;
    private DriveMotor driveMotor;

    public void slewDrive(double drivePower, double turnAngle) throws CANTimeoutException 
    {
        double frontPOS = convertJoystickToPosition(turnAngle);
        double rearPOS = 0.0;
        if(frontPOS == 0.5)
        {
            rearPOS = 0.5;
        }
        else
        {
            rearPOS = 0.5 + (0.5 - frontPOS);
        }
        driveMotor.set(drivePower);
        front.setPosition(convertJoystickToPosition(turnAngle));
        rear.setPosition(rearPOS);
    }

    public void crabDrive(double drivePower, double turnAngle) throws CANTimeoutException 
    {
        driveMotor.set(drivePower);
        front.setPosition(convertJoystickToPosition(turnAngle));
        rear.setPosition(convertJoystickToPosition(turnAngle));
    }

//    public void axisTurn(char leftRight) {
//    }

    public double convertJoystickToPosition(double joystickValue) 
    {
        double temp = joystickValue +1;
        temp = temp/2;
        if(temp < 0.24)
        {
            temp += 0.5;
        }
        else if(temp > 0.76)
        {
            temp -= 0.5;
        }
        else if(temp >= 0.48 && temp <= 0.52)
        {
            temp = 0.5;
        }
        return temp;
    }
}