package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.lib.GenericPWMSpeedController;

public class Mouth extends Subsystem {

    private static Mouth mInstance = null;
    private GenericPWMSpeedController mMaster;
    private static final double kIntake = -1.0;

    public MouthState getState() {
        return mState;
    }

    public double getSpeed(){
        return mPeriodicIO.demand;
    }

    private MouthState mState = MouthState.NEUTRAL_CARGO;
    private PeriodicIO mPeriodicIO = new PeriodicIO();

    private Mouth(){
        mMaster = new GenericPWMSpeedController(Constants.kMouth.masterPort);
    }

    public static Mouth getInstance(){
        if(mInstance == null){
            mInstance = new Mouth();
        }
        return mInstance;
    }

    @Override
    public void stop() {
        mState = MouthState.NEUTRAL_CARGO;
    }

    @Override
    public void zeroMechanism() {

    }

    @Override
    public void outputTelemetry() {

    }

    public synchronized void toggleIntake(){
        mState = mState == MouthState.INTAKE ? MouthState.NEUTRAL_CARGO : MouthState.INTAKE;
    }

    public synchronized void setState(MouthState state){
        mState = state;
    }

    public synchronized void setSpeed(double speed){
        mPeriodicIO.demand = speed;
    }

    @Override
    public synchronized void readPeriodicInputs(){
        switch(mState){
            case INTAKE:
                mPeriodicIO.demand = kIntake;
                break;
            case NEUTRAL_CARGO:
                mPeriodicIO.demand = -0.2;
                break;
            case NEUTRAL_NO_CARGO:
                mPeriodicIO.demand = 0;
                //if outtake, its already set
        }
    }

    @Override
    public synchronized void writePeriodicOutputs(){
        mMaster.set(-mPeriodicIO.demand);
    }

    public static class PeriodicIO{
        //INPUT

        //OUTPUT
        public double demand = 0;
    }

    public enum MouthState{
        NEUTRAL_CARGO,
        NEUTRAL_NO_CARGO,
        INTAKE,
        OUTTAKE
    }
}
