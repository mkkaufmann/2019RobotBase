package frc.robot.subsystems;

import frc.robot.loops.ILooper;

//TODO transition all states to switch statements?
public abstract class Subsystem {
    public abstract void stop();

    public abstract void zeroMechanism();

    public synchronized void readPeriodicInputs(){

    }
    public synchronized void writePeriodicOutputs(){

    }

    public void registerEnabledLoops(ILooper enabledLooper){

    };

    public abstract void outputTelemetry();
}
