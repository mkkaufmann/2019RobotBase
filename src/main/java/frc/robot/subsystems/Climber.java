package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import frc.robot.Constants;
import frc.robot.lib.GenericPWMSpeedController;

public class Climber extends Subsystem{

    private static Climber mInstance = null;
    private GenericPWMSpeedController mMaster;
    private ClimberState mState = ClimberState.STOWED;
    private PeriodicIO mPeriodicIO = new PeriodicIO();

    private Climber(){
        mMaster = new GenericPWMSpeedController(Constants.kClimber.mMasterPort);
    }

    public static Climber getInstance(){
        if(mInstance == null){
            mInstance = new Climber();
        }
        return mInstance;
    }

    public ClimberState getState() {
        return mState;
    }

    public synchronized void toggleState(){
        mState = mState == ClimberState.PERCENT_OUTPUT ? ClimberState.STOWED : ClimberState.PERCENT_OUTPUT;
    }

    public synchronized void setState(ClimberState state){
        mState = state;
    }

    @Override
    public void stop() {
        setState(ClimberState.STOWED);
    }

    @Override
    public void zeroMechanism() {

    }

    @Override
    public void outputTelemetry() {

    }

    public void setOutput(double demand){
        mPeriodicIO.demand = demand;
    }

    @Override
    public synchronized void writePeriodicOutputs(){
        if(mState == ClimberState.PERCENT_OUTPUT){
            mMaster.set(mPeriodicIO.demand);
        }else{
            mMaster.set(0);
        }
    }

    public static class PeriodicIO{
        //INPUTS

        //OUTPUTS
        public double demand = 0;
    }

    public enum ClimberState{
        STOWED,
        PERCENT_OUTPUT
    }
}
