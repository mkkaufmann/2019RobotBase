package frc.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.controlboard.ControlBoard;
import frc.robot.poofs.RobotState;
import frc.robot.poofs.RobotState2;

public class Dashboard extends Subsystem {

    private static Dashboard mInstance = null;
    private ControlBoard mControlBoard;

    public static Dashboard getInstance(){
        if(mInstance == null){
            mInstance = new Dashboard();
        }
        return mInstance;
    }

    private Dashboard(){
        SmartDashboard.putBoolean("isHatchMode", true);
        mControlBoard = new ControlBoard();
    }

    public static boolean getIsHatchMode(){
        return SmartDashboard.getBoolean("isHatchMode", true);
    }

    @Override
    public synchronized void readPeriodicInputs(){

    }

    @Override
    public synchronized void writePeriodicOutputs(){
        SmartDashboard.putNumber("Match_Time", Timer.getMatchTime());
        //SmartDashboard.putBoolean("Claw_Is_Open", Claw.getInstance().getState() == Claw.ClawState.OPEN);
        SmartDashboard.putNumber("Battery_Voltage", RobotController.getBatteryVoltage());
        SmartDashboard.putNumber("Elevator_Height", Elevator.getInstance().getInchesFromBottom());
        SmartDashboard.putNumber("Robot_State_X", RobotState2.getInstance().getLatestFieldToVehicle().getValue().getTranslation().x());
        SmartDashboard.putNumber("Robot_State_Y", RobotState2.getInstance().getLatestFieldToVehicle().getValue().getTranslation().y());
        SmartDashboard.putNumber("Robot_State_R", RobotState2.getInstance().getLatestFieldToVehicle().getValue().getRotation().getDegrees());
//        SmartDashboard.putNumber("Robot_State_X", RobotState.getInstance().getLatestFieldToVehicle().getValue().getTranslation().x());
//        SmartDashboard.putNumber("Robot_State_Y", RobotState.getInstance().getLatestFieldToVehicle().getValue().getTranslation().y());
//        SmartDashboard.putNumber("Robot_State_R", RobotState.getInstance().getLatestFieldToVehicle().getValue().getRotation().getDegrees());
        SmartDashboard.putString("Mouth_State", Mouth.getInstance().getState().toString());
        SmartDashboard.putBoolean("isHatchMode", Superstructure.getInstance().getMode() == Superstructure.MechanismMode.HATCH);

        SmartDashboard.putNumber("Turn", mControlBoard.getTurn());
        SmartDashboard.putNumber("Throttle", mControlBoard.getThrottle());
        SmartDashboard.putBoolean("QuickTurn", mControlBoard.getQuickTurn());
        SmartDashboard.putString("ClawState", RollerClaw.getInstance().getState().toString());
//        SmartDashboard.putString("ProjectorState", Arm.getInstance().getState().toString());
        SmartDashboard.putString("ProjectorTarget", Arm.getInstance().getPosition().toString());
    }

    public double getTargetYaw(){
        return SmartDashboard.getNumber("Vision/tapeYaw", 0);
//        return SmartDashboard.getBoolean("Vision/tapeDetected", false)? SmartDashboard.getNumber("Vision/tapeYaw", 0):0;
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


}
