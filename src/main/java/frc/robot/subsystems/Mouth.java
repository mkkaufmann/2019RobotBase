package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.lib.GenericPWMSpeedController;

public class Mouth extends Subsystem {

    private static Mouth mInstance = null;
    private GenericPWMSpeedController mMaster;
    private MouthState mState = MouthState.NEUTRAL;
//    private PeriodicIO mPeriodicIO = new PeriodicIO();

    private Mouth(){
        mMaster = new GenericPWMSpeedController(Constants.kMouth.masterPort);
    }

    public static Mouth getInstance(){
        if(mInstance == null){
            mInstance = new Mouth();
        }
        return mInstance;
    }

    public MouthState getState() {
        return mState;
    }

//    public double getSpeed(){
//        return mPeriodicIO.demand;
//    }

    @Override
    public void stop() {
        mState = MouthState.NEUTRAL;
    }

    @Override
    public void zeroMechanism() {

    }

    @Override
    public void outputTelemetry() {

    }

    public synchronized void setState(MouthState state){
        mState = state;
    }

//    public synchronized void setManual(double speed){
//        mPeriodicIO.demand = speed;
//    }

    @Override
    public synchronized void readPeriodicInputs(){

    }

    @Override
    public synchronized void writePeriodicOutputs(){
        mMaster.set(-mState.value);
    }

//    public static class PeriodicIO{
//        //INPUT
//
//        //OUTPUT
//    }

    public enum MouthState{
        NEUTRAL(0),
        IN(-1),
        OUT(1),
        HOLDING(-0.2);

        public double value;

        MouthState(double value){
            this.value = value;
        }
    }
}
