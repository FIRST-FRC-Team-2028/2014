package com.phantommentalists.Twenty14;

import java.lang.String;
import com.sun.squawk.util.MathUtils;

/*
 */
public class FRCMath {

    /**
     * This method will take the absolute value of a number
     */
    public static double abs(double value) {
        if (value < 0.0) {
            return value * -1.0;
        } else {
            return value;
        }
    }

    /**
     * This method takes a radian and returns degrees
     */
    public static double ConvertRadiansToDegrees(double radians) {
        return (radians * 180.0) / Math.PI;
    }
    
    /**
     * getPolarAngle()
     * 
     * This method takes the x and y values from the joystick and returns the 
     * Angle for the direction of the drive
     * 
     * @param double x - the x value of the joystick
     * @param double y - the y value of the joystick
     */
    public static double getPolarAngle(double x, double y)
    {
        return ConvertRadiansToDegrees(MathUtils.atan2(y,x));
    }
    
    /**
     * getPolarMagnitude()
     * 
     * This method takes coordinate plane values and returns the polar magnitude
     * 
     * @param x
     * @param y
     * @return 
     */
    public static double getPolarMagnitude(double x, double y)
    {
        return Math.sqrt(pow(x, 2) + pow(y, 2));
    }
    
    /**
     * 
     */
    public static double convertDegreesToJoystick(double degrees)
    {
        return degrees/180;
    }

    /**
     * *
     * Normalize()
     *
     * this method reduces a number to 5 decimal places
     *
     * @param num a very long decimal
     * @return
     */
    public static String Normalize(double num) {
        int t = ((int) (num * 1000.0));
        double d = t / 1000.0;
        String str = "" + d;
        while (str.length() < 5) {
            str += "0";
        }
        return str;
        //return String.format("%10.4f", num);
    }

    public static double pow(double number, double power) {
        return MathUtils.pow(number, power);
    }
}
