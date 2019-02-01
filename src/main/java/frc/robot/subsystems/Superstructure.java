package frc.robot.subsystems;

import frc.robot.loops.ILooper;
import frc.robot.loops.Loop;
import frc.robot.statemachines.SuperstructureStateMachine;
import frc.robot.states.SuperstructureCommand;
import frc.robot.states.SuperstructureConstants;
import frc.robot.states.SuperstructureState;

public class Superstructure extends Subsystem{

    private static Superstructure mInstance = null;
    private SuperstructureState mState = new SuperstructureState();
    private Elevator mElevator = Elevator.getInstance();
    private Arm mArm = Arm.getInstance();
    private Climber mClimber = Climber.getInstance();
    private Mouth mMouth = Mouth.getInstance();
    private Strafe mStrafe = Strafe.getInstance();
    private SuperstructureStateMachine mStateMachine = new SuperstructureStateMachine();
    private SuperstructureStateMachine.WantedAction mWantedAction = SuperstructureStateMachine.WantedAction.IDLE;

    private boolean isClimbMode;
    private boolean isElevatorJogging = true;

    public static synchronized Superstructure getInstance(){
        if(mInstance == null){
            mInstance = new Superstructure();
        }
        return mInstance;
    }

    @Override
    public void stop() {
    }

    @Override
    public void zeroMechanism() {

    }

    @Override
    public void outputTelemetry() {

    }

    public synchronized SuperstructureStateMachine.SystemState getSuperStructureState() {
        return mStateMachine.getSystemState();
    }

    private synchronized void updateObservedState(SuperstructureState state) {
        state.height = mElevator.getInchesFromBottom();
        state.armExtended = mArm.getLastPosition() == Arm.ArmPosition.SCORE
                && mArm.getState() == Arm.ArmControlState.HOLDING_POSITION;

        //TODO motion planner
//        state.elevatorSentLastTrajectory = mElevator.hasFinishedTrajectory();
//        state.wristSentLastTrajectory = mWrist.hasFinishedTrajectory();
    }

    synchronized void setFromCommandState(SuperstructureCommand commandState) {
        if (commandState.openLoopElevator) {
            mElevator.setOpenLoop(commandState.openLoopElevatorPercent);
        } else {
            if (isElevatorJogging) {
                mElevator.setPositionPID(commandState.height);
            } else {
                //TODO implement mElevator.setMotionMagicPosition(commandState.height);
            }
        }
        //TODO Implement arm etc
    }

    @Override
    public void registerEnabledLoops(ILooper enabledLooper) {
        enabledLooper.register(new Loop() {
            private SuperstructureCommand mCommand;

            @Override
            public void onStart(double timestamp) {
                mStateMachine.resetManual();
                //TODO mStateMachine.setUpwardsSubcommandEnable(!Infrastructure.getInstance().isDuringAuto());
            }

            @Override
            public void onLoop(double timestamp) {
                synchronized (Superstructure.this) {
                    updateObservedState(mState);

                    if (!isArmInStartingConfiguration()) {
                        // Kickstand is fired, so not engaged.
                        mStateMachine.setMaxHeight(SuperstructureConstants.kElevatorMaxHeight);
                    } else {
                        mStateMachine.setMaxHeight(SuperstructureConstants.kElevatorMaxHeightArmAtStart);
                    }

                    mCommand = mStateMachine.update(timestamp, mWantedAction, mState);
                    setFromCommandState(mCommand);
                }
            }

            @Override
            public void onStop(double timestamp) {

            }
        });
    }

    public synchronized double getScoringHeight() {
        return mStateMachine.getScoringHeight();
    }

    public synchronized void setDesiredHeight(double height) {
        isElevatorJogging = true;//TODO false
        mStateMachine.setScoringHeight(height);
        mWantedAction = SuperstructureStateMachine.WantedAction.GO_TO_POSITION;
    }


    public synchronized void setElevatorJog(double relative_inches) {
        isElevatorJogging = true;
        mStateMachine.jogElevator(relative_inches);
        mWantedAction = SuperstructureStateMachine.WantedAction.GO_TO_POSITION;
    }


    public synchronized void setWantedAction(SuperstructureStateMachine.WantedAction wantedAction) {
        mWantedAction = wantedAction;
    }

    public synchronized void setClimbMode(boolean activated) {
        isClimbMode = activated;
    }

    public synchronized boolean isArmInStartingConfiguration() {
        return mArm.getLastPosition() == Arm.ArmPosition.START
                && mArm.getState() == Arm.ArmControlState.HOLDING_POSITION;
    }

    //TODO add arm, etc. methods and values
}
