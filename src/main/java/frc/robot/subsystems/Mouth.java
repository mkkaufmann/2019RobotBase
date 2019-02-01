package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import frc.robot.Constants;
import frc.robot.lib.GenericPWMSpeedController;

public class Mouth extends Subsystem {

    private static Mouth mInstance = null;
    private GenericPWMSpeedController mMaster;
    private static final double kSlowShoot = 0.5;
    private static final double kFastShoot = 1.0;
    private static final double kIntake = -1.0;
    private MouthState mState = MouthState.NEUTRAL;
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
        mState = MouthState.NEUTRAL;
    }

    @Override
    public void zeroMechanism() {

    }

    @Override
    public void outputTelemetry() {

    }

    public synchronized void toggleIntake(){
        mState = mState == MouthState.INTAKE? MouthState.NEUTRAL : MouthState.INTAKE;
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
            case NEUTRAL:
                mPeriodicIO.demand = 0;
                break;
                //if outtake, its already set
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

    public enum MouthState{
        NEUTRAL,
        INTAKE,
        OUTTAKE
    }
}
