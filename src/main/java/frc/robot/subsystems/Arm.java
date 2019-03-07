package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.Constants;
import frc.robot.commands.actions.claw.ClawHolding;
import frc.robot.lib.GenericPWMSpeedController;
import frc.robot.loops.ILooper;
import frc.robot.states.SuperstructureConstants;

public class Arm extends Subsystem {

    //TODO add timer and fix
    private static Arm mInstance = null;
    private final GenericPWMSpeedController mMaster;
    private static ArmPosition mTargetPosition = ArmPosition.STOW;
    private static ArmControlState mControlState = ArmControlState.HOLDING_POSITION;
    private static PeriodicIO mPeriodicIO = new PeriodicIO();
    private static Timer timer = new Timer();

    private Arm(){
        mMaster = new GenericPWMSpeedController(Constants.kArm.masterPort);
        setTargetPosition(mTargetPosition);
    }

    public static synchronized Arm getInstance(){
        if(mInstance == null){
            mInstance = new Arm();
        }
        return mInstance;
    }

    public enum ArmPosition{
        STOW(1),
        SCORE(-1);

        public double value;
        ArmPosition(double value){
            this.value = value;
        }
    }

    public enum ArmControlState{
        HOLDING_POSITION,
        MOVING_TO_POSITION
    }

    public synchronized void toggleTargetPosition(){
        setTargetPosition(mTargetPosition == ArmPosition.STOW ? ArmPosition.SCORE : ArmPosition.STOW);
    }

    @Override
    public void zeroMechanism(){
        setTargetPosition(ArmPosition.SCORE);
    }

    //only called when holding position (i.e. previous position is current position)
    public synchronized void setTargetPosition(ArmPosition wantedTargetPosition){
        if(!(mTargetPosition == wantedTargetPosition && mControlState == ArmControlState.MOVING_TO_POSITION)){
            mTargetPosition = wantedTargetPosition;
            mPeriodicIO.demand = wantedTargetPosition.value;
            mControlState = ArmControlState.MOVING_TO_POSITION;
            timer.reset();
            timer.start();
        }
    }

    @Override
    public synchronized void writePeriodicOutputs(){
        if(!timer.hasPeriodPassed(1)){
            mMaster.set(mPeriodicIO.demand);
        }else{
            mMaster.set(0);
            mControlState = ArmControlState.HOLDING_POSITION;
        }
    }

    public static class PeriodicIO{
        //INPUTS

        //OUTPUTS
        public double demand = 0;
    }

    public ArmPosition getPosition(){
        return mTargetPosition;
    }

    public ArmControlState getState(){
        return mControlState;
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
