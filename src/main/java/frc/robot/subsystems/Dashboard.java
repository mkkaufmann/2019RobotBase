package frc.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.commands.actions.projector.ScoreProjector;
import frc.robot.commands.actions.projector.StowProjector;
import frc.robot.commands.actions.miscellaneous.RemoveHatch;
import frc.robot.controlboard.ControlBoard;
import frc.robot.poofs.RobotState2;
import frc.robot.states.SuperstructureConstants;

public class Dashboard extends Subsystem {

    private static Dashboard mInstance = null;
    private ControlBoard mControlBoard;
    private Elevator mElevator = Elevator.getInstance();

    public static Dashboard getInstance(){
        if(mInstance == null){
            mInstance = new Dashboard();
        }
        return mInstance;
    }

    private Dashboard(){
        mControlBoard = new ControlBoard();
        SmartDashboard.putBoolean("ElevatorHeightChanged", false);
    }


    @Override
    public synchronized void readPeriodicInputs(){
        if(SmartDashboard.getBoolean("ElevatorHeightChanged", false)){
            System.out.println("changed");
            switch(SmartDashboard.getString("ElevatorHeight", "")){
                case "CargoLow":
                    mElevator.setMotionMagic(SuperstructureConstants.kRocketCargoLow);
                    break;
                case "CargoMid":
                    mElevator.setMotionMagic(SuperstructureConstants.kRocketCargoMiddle);
                    break;
                case "CargoHigh":
                    mElevator.setMotionMagic(SuperstructureConstants.kRocketCargoHigh);
                    break;
                case "CargoShip":
                    mElevator.setMotionMagic(SuperstructureConstants.kCargoShipCargo);
                    break;
                case "HatchLow":
                    mElevator.setMotionMagic(SuperstructureConstants.kRocketHatchLow);
                    break;
                case "HatchMid":
                    mElevator.setMotionMagic(SuperstructureConstants.kRocketHatchMiddle);
                    break;
                case "HatchHigh":
                    mElevator.setMotionMagic(SuperstructureConstants.kRocketHatchHigh);
                    break;
                case "JogUp":
                    mElevator.jog(2);
                    break;
                case "JogDown":
                    mElevator.jog(-2);
                    break;
                case "JogHatchToCargo": mElevator.jog(12);
                    break;
                case "ProjectorIn":
                    Robot.runCommand(new StowProjector());
                    break;
                case "ProjectorOut":
                    Robot.runCommand(new ScoreProjector());
                    break;
                case "RemoveHatchLeft":
                    Robot.runCommand(new RemoveHatch(true));
                    break;
                case "RemoveHatchRight":
                    Robot.runCommand(new RemoveHatch(false));
                    break;
            }
            SmartDashboard.putBoolean("ElevatorHeightChanged", false);
        };
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
//        SmartDashboard.putString("ProjectorState", Projector.getInstance().getState().toString());
        SmartDashboard.putString("ProjectorTarget", Projector.getInstance().getPosition().toString());
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
