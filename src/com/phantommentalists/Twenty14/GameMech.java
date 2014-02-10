package com.phantommentalists.Twenty14;
 
import edu.wpi.first.wpilibj.can.CANTimeoutException;


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
     * @authors Mateo, Jeremy, and Jonathan
     */
    public GameMech() throws CANTimeoutException{ 
        state = new State();
        catcher = new Catcher();
        loader = new ChopSticks();
       launcher = new Launcher(Parameters.motorOneLauncherCANID, Parameters.motorTwoLauncherCANID);
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
    
    /**
     * turnOnChopSticks()
     * 
     * This method turns on both left and right ChopSticks.
     */
    public void turnOnChopSticks(){
        if(isCatching())
        {
            loader.turnOnChopSticks();
        }
    }
    
    /**
     * turnOffChopSticks()
     * 
     * This method turns off both left and right ChopSticks.
     */
    public void turnOffChopSticks(){
        if(isCatching())
        {
            loader.turnOffChopSticks();
        }
    }

    public void airPass() throws CANTimeoutException {
        if(isCatching())
        {
            launcher.shoot(Parameters.kshootPass);
        }
       
    }

    public void shoot() throws CANTimeoutException {
        if(isCatching())
        {
            launcher.shoot(Parameters.kshootGoal);  
        }
    }

    public void useTheForce() 
    {
        
    }
    
    public boolean isEmpty() 
    {
        if (state.value == State.kEmpty)
        {
        return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean isCatching() 
    {
        if(state.value == State.kCatching)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean isHolding() 
    {
        if(state.value == State.kHolding)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void processGameMech() throws CANTimeoutException
    {
        launcher.processLauncher();
        loader.processChopSticks();
        if(state.value == State.kHolding)
        {
            if (catcher.isDeployed() && loader.isDeployed())
            {
                state.value = State.kCatching;
            }   
        }
        if(state.value == State.kCatching)
        {
            if (catcher.isRetracted() && loader.isRetracted() && launcher.isSafe())
            {
                state.value = State.kHolding;
            }
            if (launcher.isRearming())
            {
                state.value = State.kEmpty;
            }
        }
        if(state.value == State.kEmpty)
        {
            if(launcher.isSafe())
            {
                state.value = State.kCatching;
            }
        }
    }
}
