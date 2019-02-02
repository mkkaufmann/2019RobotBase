package frc.robot;

import frc.robot.controlboard.GamepadButtonControlBoard;
import frc.robot.controlboard.GamepadDriveControlBoard;

public class ControlBoard implements IControlBoard{

    GamepadButtonControlBoard mButtonControlBoard = GamepadButtonControlBoard.getInstance();
    GamepadDriveControlBoard mDriveControlBoard = GamepadDriveControlBoard.getInstance();


    @Override
    public boolean getGoToHighHeight() {
        return mButtonControlBoard.getGoToHighHeight();
    }

    @Override
    public boolean getGoToNeutralHeight() {
        return mButtonControlBoard.getGoToNeutralHeight();
    }

    @Override
    public boolean getGoToLowHeight() {
        return mButtonControlBoard.getGoToLowHeight();
    }

    @Override
    public boolean getGoToCargoShipCargoHeight() {
        return mButtonControlBoard.getGoToCargoShipCargoHeight();//TODO finish
    }

    @Override
    public boolean getHatchOrCargo() {
        return mButtonControlBoard.getHatchOrCargo();
    }

    @Override
    public boolean getArmToggle() {
        return mButtonControlBoard.getArmToggle();
    }

    @Override
    public boolean getArmToStart() {
        return mButtonControlBoard.getArmToStart();
    }

    @Override
    public boolean getClawToggle() {
        return mButtonControlBoard.getClawToggle();
    }

    @Override
    public boolean getCenterStrafe() {
        return mButtonControlBoard.getCenterStrafe();
    }

    @Override
    public boolean getRunIntake() {
        return mButtonControlBoard.getRunIntake();
    }


    @Override
    public void setRumble(boolean on) {
        mButtonControlBoard.setRumble(on);
    }

    @Override
    public boolean getEnableClimbMode() {
        return mButtonControlBoard.getEnableClimbMode();
    }

    @Override
    public double getClimberThrottle() {
        return mButtonControlBoard.getClimberThrottle();
    }

    @Override
    public double getStrafeThrottle() {
        return mButtonControlBoard.getStrafeThrottle();
    }


    @Override
    public double getElevatorThrottle() {
        return mButtonControlBoard.getElevatorThrottle();
    }

    @Override
    public double getThrottle() {
        return mDriveControlBoard.getThrottle();
    }

    @Override
    public double getTurn() {
        return mDriveControlBoard.getTurn();
    }
    @Override
    public boolean getQuickTurn() {
        return mDriveControlBoard.getQuickTurn();
    }

    @Override
    public double getShootSpeed() {
        return mDriveControlBoard.getShootSpeed();
    }
}
