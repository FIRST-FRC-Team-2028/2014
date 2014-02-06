package com.phantommentalists.Twenty14;
 
import edu.wpi.first.wpilibj.Relay;


/*
 */
public class GameMech {

    public class State{
        public State(){
            value = kHolding;
        }
        public int value;
        public static final int kHolding = 0;
        public static final int kCatching = 1;
        public static final int kEmpty = 2;
    }
    
    private State state;
    private ChopSticks loader;
    private Catcher catcher;
    private Launcher launcher;
    
    /**
     *  GameMech allocations11
     * 
     * @authors mburt001 and jcurtiss001
     */
    public GameMech(){ 
        state = new State();
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
    public void processGM(){
        
    }
}
