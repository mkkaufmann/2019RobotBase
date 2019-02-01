package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import frc.robot.Constants;

public class Mouth extends Subsystem {

    private static Mouth mInstance = null;
    private Talon mMaster;
    private static final double kSlowShoot = 0.5;
    private static final double kFastShoot = 1.0;
    private static final double kIntake = -1.0;
    private MouthState mState = MouthState.NEUTRAL;
    private PeriodicIO mPeriodicIO = new PeriodicIO();

    private Mouth(){
        mMaster = new Talon(Constants.kMouth.SRPort);
    }

    public static Mouth getInstance(){
        if(mInstance == null){
            mInstance = new Mouth();
        }
        return mInstance;
    }

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

    public synchronized void setSpeed(boolean slow){
        mPeriodicIO.slow = slow;
    }

    @Override
    public synchronized void readPeriodicInputs(){
        switch(mState){
            case INTAKE:
                mPeriodicIO.demand = kIntake;
                break;
            case NEUTRAL:
                mPeriodicIO.demand = 0;
                break;
            case OUTTAKE:
                mPeriodicIO.demand = mPeriodicIO.slow ? kSlowShoot : kFastShoot;
        }
    }

    @Override
    public synchronized void writePeriodicOutputs(){
        mMaster.set(mPeriodicIO.demand);
    }

    public static class PeriodicIO{
        //INPUT
        public boolean slow = false;
        //OUTPUT
        public double demand = 0;
    }

    private enum MouthState{
        NEUTRAL,
        INTAKE,
        OUTTAKE
    }
}
