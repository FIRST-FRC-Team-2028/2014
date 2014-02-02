package com.phantommentalists.Twenty14;

import edu.wpi.first.wpilibj.Relay;
 
/*
 */
public class GameMech {

    public boolean isShooting;
    public boolean isCatcherDeployed;
    public boolean isChopSticksDeployed;
    private ChopSticks loader;
    private Catcher catcher;
    private Launcher launcher;
    
    /**
     *  GameMech allocations
     * 
     * @authors mburt001 and jcurtiss001
     */
    public GameMech(){
        catcher = new Catcher();
//        loader = new ChopSticks();
    }
    
    /**
     * deployCatcher()
     * 
     * This method deploys the  catcher.
     */
    public void deployCatcher(){
        catcher.deploy();
    }
    
    /**
     * retractCatcher()
     * 
     * This method retracts the catcher.
     */
    public void retractCatcher(){
        catcher.retract();
    }            
    
//    /**
//     * turnOnChopSticks()
//     * 
//     * This method turns on both left and right ChopSticks.
//     */
//    public void turnOnChopSticks(){
//        loader.turnOnChopSticks();
//    }
//    
//    /**
//     * turnOffChopSticks()
//     * 
//     * This method turns off both left and right ChopSticks.
//     */
//    public void turnOffChopSticks(){
//        loader.turnOffChopSticks();
//    }

    public void airPass() {
    }

    public void shoot() {
    }

    public void useTheForce() {
    }
}