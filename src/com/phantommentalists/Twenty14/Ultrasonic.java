/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.AnalogModule;

/**
 *
 * @author David
 */
public class Ultrasonic {

    AnalogModule ultrasonicSensor;
    int channel;
    double distance = 0.0;
    
    /**
     * Constructor that uses the default module slot
     *
     * @param analogChannel channel the ultrasonic sensor is connected to on the
     * cRIO 9201 module.
     */
    public Ultrasonic(int analogChannel) {
        channel = analogChannel;
        ultrasonicSensor = AnalogModule.getInstance(1);
    }

    /**
     * getDistance()
     * 
     * Return the actual distance to a target in inches.
     * The distance is based on the current voltage values.
     *
     * @returns double - distance to the nearest object in inches
     */
    public double getDistance() {
        double value = ultrasonicSensor.getVoltage(channel);
        return (value * 2077) / 20;
    }

    /**
     * getAveragedDistance()
     * 
     * Returns the averaged distance to the target.  Due to some instability in 
     * the value returned from the sensor, it is nice to smooth out the values 
     * that are returned.
     * 
     * @return double - distance to the nearest object in inches
     */
    public double getAveragedDistance() {
        double temp = getDistance();
        if (temp > (distance * 1.05) || temp < (distance * 0.95)) {
            distance = temp;
        } else {
            distance = (distance + temp) / 2;
        }
        return distance;
    }
    
    public double pidGet() {
        return getDistance();
    }

    public double getVoltage() {
        return ultrasonicSensor.getVoltage(channel);
    }
}