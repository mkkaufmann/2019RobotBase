package frc.robot.statemachines;

import frc.robot.lib.Util;
import frc.robot.states.SuperstructureCommand;
import frc.robot.states.SuperstructureConstants;
import frc.robot.states.SuperstructureState;
import frc.robot.subsystems.Superstructure;

public class SuperstructureStateMachine {
    public enum WantedAction{
        IDLE,
        GO_TO_POSITION,
        WANT_MANUAL
    }

    public enum SystemState{
        HOLDING_POSITION,
        MOVING_TO_POSITION,
        MANUAL
    }

    private SystemState mSystemState = SystemState.HOLDING_POSITION;

    private SuperstructureCommand mCommand = new SuperstructureCommand();
    private SuperstructureState mCommandedState = new SuperstructureState();
    private SuperstructureState mDesiredEndState = new SuperstructureState();

    //TODO add motion planner?

    private double mScoringHeight = SuperstructureConstants.kElevatorMinHeight;

    private double mOpenLoopPower = 0.0;
    private double mMaxHeight = SuperstructureConstants.kElevatorMaxHeight;

    public synchronized void resetManual(){
        mOpenLoopPower = 0.0;
    }

    public synchronized void setMaxHeight(double height){
        mMaxHeight = height;
    }

    public synchronized void setOpenLoopPower(double power){
        mOpenLoopPower = power;
    }

    public synchronized void setScoringHeight(double inches){
        mScoringHeight = inches;
    }

    public synchronized double getScoringHeight(){
        return mScoringHeight;
    }

    public synchronized void jogElevator(double relative_inches) {
        mScoringHeight += relative_inches;
        mScoringHeight = Math.min(mScoringHeight, mMaxHeight);
        mScoringHeight = Math.max(mScoringHeight, SuperstructureConstants.kElevatorMinHeight);
    }

    public synchronized boolean scoringPositionChanged() {
        return !Util.epsilonEquals(mDesiredEndState.height, mScoringHeight);
    }

    public synchronized SystemState getSystemState() {
        return mSystemState;
    }

    //with motion planner
//    public synchronized void setUpwardsSubcommandEnable(boolean enabled) {
//        mPlanner.setUpwardsSubcommandEnable(enabled);
//    }

    public synchronized SuperstructureCommand update(double timestamp, WantedAction wantedAction,
                                                     SuperstructureState currentState){
        synchronized (SuperstructureStateMachine.this){
            SystemState newState;

            //TODO Handle state transitions
            switch(mSystemState){
                case HOLDING_POSITION:
                    newState = handleHoldingPositionTransitions(wantedAction,currentState);
                    break;
                case MOVING_TO_POSITION:
                    newState = handleMovingToPositionTransitions(wantedAction, currentState);
                    break;
                case MANUAL:
                    newState = handleManualTransitions(wantedAction, currentState);
                    break;
                default:
                    newState = mSystemState;
                    break;
            }

            if(newState != mSystemState){
                mSystemState = newState;
            }

            //with motion planner
//            if (!mCommand.openLoopElevator) {
//                mCommandedState = mPlanner.update(currentState);
//                mCommand.height = Math.min(mCommandedState.height, mMaxHeight);
//            }

            switch (mSystemState) {
                case HOLDING_POSITION:
                    getHoldingPositionCommandedState();
                    break;
                case MOVING_TO_POSITION:
                    getMovingToPositionCommandedState();
                    break;
                case MANUAL:
                    getManualCommandedState();
                    break;
                default:
                    //System.out.printlnln("Unexpected superstructure state output state: " + mSystemState);
                    break;
            }
        }
        return mCommand;
    }

    //with motion planning
//    private void updateMotionPlannerDesired(SuperstructureState currentState) {
//        mDesiredEndState.height = mScoringHeight;
//
//        //System.out.printlnln("Setting motion planner to height: " + mDesiredEndState.height);
//
//        // Push into elevator planner.
//        if (!mPlanner.setDesiredState(mDesiredEndState, currentState)) {
//            //System.out.printlnln("Unable to set elevator planner!");
//        }
//
//        mScoringHeight = mDesiredEndState.height;
//    }
//with motion planning
    private SystemState handleDefaultTransitions(WantedAction wantedAction, SuperstructureState currentState) {
        if (wantedAction == WantedAction.GO_TO_POSITION) {
//            if (scoringPositionChanged()) {
//                //TODO updateMotionPlannerDesired(currentState);
//            } else if (mPlanner.isFinished(currentState)) {
//                return SystemState.HOLDING_POSITION;
//            }
            return SystemState.MOVING_TO_POSITION;
        } else if (wantedAction == WantedAction.WANT_MANUAL) {
            return SystemState.MANUAL;
        } else {
//TODO            if (mSystemState == SystemState.MOVING_TO_POSITION && !mPlanner.isFinished(currentState)) {
//                return SystemState.MOVING_TO_POSITION;
//            } else {
//                return SystemState.HOLDING_POSITION;
//            }
            return SystemState.HOLDING_POSITION;//TODO Remove
        }
    }

    // HOLDING_POSITION
    private SystemState handleHoldingPositionTransitions(WantedAction wantedAction,
                                                         SuperstructureState currentState) {
        return handleDefaultTransitions(wantedAction, currentState);
    }

    private void getHoldingPositionCommandedState() {
        mCommand.openLoopElevator = false;
    }

    // MOVING_TO_POSITION
    private SystemState handleMovingToPositionTransitions(WantedAction wantedAction,
                                                          SuperstructureState currentState) {

        return handleDefaultTransitions(wantedAction, currentState);
    }

    private void getMovingToPositionCommandedState() {
        mCommand.openLoopElevator = false;
    }

    // MANUAL
    private SystemState handleManualTransitions(WantedAction wantedAction,
                                                SuperstructureState currentState) {
        if (wantedAction != WantedAction.WANT_MANUAL) {
            // Freeze height.
            mScoringHeight = currentState.height;
            return handleDefaultTransitions(WantedAction.GO_TO_POSITION, currentState);
        }
        return handleDefaultTransitions(wantedAction, currentState);
    }

    private void getManualCommandedState() {
        mCommand.openLoopElevator = true;
        mCommand.openLoopElevatorPercent = mOpenLoopPower;
    }

}
