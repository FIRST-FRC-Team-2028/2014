package com.phantommentalists.Twenty14;

import com.phantiommentalists.Twenty14.DriveMotor.Gear;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/*
 * DriveUnit allocation
 */
public class DriveUnit {

    public Wheel front;
    public Wheel rear;
    private DriveMotor driveMotor;

    public DriveUnit(String name, int frontCANID, int rearCANID, int frontDriveMotorCANID, int rearDriveMotorCANID
            , int driveShiftSolenoidChannel, double ForwardRev, double RearRev) throws CANTimeoutException {
        front = new Wheel(frontCANID, "Front" + name, ForwardRev);
        rear = new Wheel(rearCANID, "Rear" + name, RearRev);
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
    public void setGear(Gear gear){
        driveMotor.setGear(gear);
    }
//    public void axisTurn(char leftRight) {
//    }
    public double convertJoystickToPosition(double joystickValue) {
        double temp = joystickValue + 1;
        temp = temp / 2;
        if (temp >= 0.49 && temp <= 0.51) {
            temp = 0.5;
        }
        return temp;
    }
    
    public double getFrontPosition() throws CANTimeoutException 
    {
        return front.getPosition();
    }
    
    public double getRearPosition() throws CANTimeoutException
    {
        return rear.getPosition();
    }
    
    public void processDriveUnit() throws CANTimeoutException
    {
        front.processWheel();
        rear.processWheel();
    }
    
    /**
     * 
     */
    public double getFrontSteeringCurrent() throws CANTimeoutException
    {
        return front.getSteeringCurrent();
    }
    
    /**
     * 
     */
    public double getRearSteeringCurrent() throws CANTimeoutException
    {
        return rear.getSteeringCurrent();
    }
}
