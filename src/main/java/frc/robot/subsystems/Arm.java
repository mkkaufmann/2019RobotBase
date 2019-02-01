package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.Constants;
import frc.robot.loops.ILooper;
import frc.robot.states.SuperstructureConstants;

//TODO Check elevator height before moving to start position
public class Arm extends Subsystem {

    private static Arm mInstance = null;
    private final VictorSP mMaster;
    private static final DigitalInput mScoreLimit = new DigitalInput(Constants.kArm.scoreLimitPort);;
    private static final DigitalInput mStowLimit = new DigitalInput(Constants.kArm.stowLimitPort);;
    private static final DigitalInput mStartLimit = new DigitalInput(Constants.kArm.startLimitPort);;
    private static ArmPosition mTargetPosition = ArmPosition.SCORE;
    private static ArmPosition mWantedTargetPosition = ArmPosition.SCORE;
    private static ArmPosition mPreviousPosition = ArmPosition.START;
    private static ArmControlState mControlState = ArmControlState.HOLDING_POSITION;
    private static PeriodicIO mPeriodicIO = new PeriodicIO();

    private Arm(){
        mMaster = new VictorSP(Constants.kArm.SPPort);
        setTargetPosition(mTargetPosition);
    }

    public static synchronized Arm getInstance(){
        if(mInstance == null){
            mInstance = new Arm();
        }
        return mInstance;
    }

    public enum ArmPosition{
        START(mStartLimit),
        STOW(mStowLimit),
        SCORE(mScoreLimit);

        public DigitalInput limit;

        ArmPosition(DigitalInput limit){
            this.limit = limit;
        }
    }

    //TODO enum for between positions to change targets in transit


    public enum ArmControlState{
        HOLDING_POSITION,
        MOVING_TO_POSITION
    }

    public synchronized void setWantedTargetPosition(ArmPosition wantedTargetPosition){
        //TODO make it able to change targets in transit
        if(mControlState == ArmControlState.HOLDING_POSITION){
            setTargetPosition(wantedTargetPosition);
        }
        mWantedTargetPosition = wantedTargetPosition;//so we don't have spastic behaviors when not updated
    }

    @Override
    public void zeroMechanism(){
        setTargetPosition(ArmPosition.SCORE);
    }

    //only called when holding position (i.e. previous position is current position)
    private synchronized void setTargetPosition(ArmPosition wantedTargetPosition){
        if(wantedTargetPosition != mPreviousPosition){
            if(mPreviousPosition == ArmPosition.START){
                mPeriodicIO.isForward = true;
            }else if(mPreviousPosition == ArmPosition.SCORE){
                mPeriodicIO.isForward = false;
            }else{
                mPeriodicIO.isForward = wantedTargetPosition == ArmPosition.SCORE;
            }
        }
        mPeriodicIO.demand = SuperstructureConstants.kArmDefaultSpeed;
        mControlState = ArmControlState.MOVING_TO_POSITION;
    }

    @Override
    public synchronized void writePeriodicOutputs(){
        if(!mTargetPosition.limit.get()){
            mMaster.set(mPeriodicIO.demand * (mPeriodicIO.isForward ? 1 : -1) );
        }else{
            stopAtPosition();
        }
    }

    public static class PeriodicIO{
        //INPUTS

        //OUTPUTS
        public double demand = 0;
        public boolean isForward = true;
    }

    private synchronized void stopAtPosition(){
        mControlState = ArmControlState.HOLDING_POSITION;
        mPreviousPosition = mTargetPosition;
        mPeriodicIO.demand = 0;
        setTargetPosition(mWantedTargetPosition);//just in case changed target TODO remove
        stop();
    }

    public ArmControlState getState(){
        return mControlState;
    }

    public ArmPosition getLastPosition(){
        return mPreviousPosition;
    }

    @Override
    public void stop() {
        mMaster.set(0);
    }

    @Override
    public void registerEnabledLoops(ILooper enabledLooper) {
    }

    @Override
    public void outputTelemetry() {

    }
}
