package frc.robot;

import frc.robot.controlboard.GamepadButtonControlBoard;
import frc.robot.controlboard.GamepadDriveControlBoard;

public class ControlBoard implements IControlBoard{

    GamepadButtonControlBoard mButtonControlBoard = GamepadButtonControlBoard.getInstance();
    GamepadDriveControlBoard mDriveControlBoard = GamepadDriveControlBoard.getInstance();

    @Override
    public boolean getGoToHighScaleHeight() {
        return mButtonControlBoard.getGoToHighScaleHeight();
    }

    @Override
    public boolean getGoToNeutralScaleHeight() {
        return mButtonControlBoard.getGoToNeutralScaleHeight();
    }

    @Override
    public boolean getGoToLowScaleHeight() {
        return mButtonControlBoard.getGoToLowScaleHeight();
    }

    @Override
    public boolean getGoToSwitchHeight() {
        return mButtonControlBoard.getGoToSwitchHeight();
    }

    @Override
    public boolean getGoToStowHeight() {
        return mButtonControlBoard.getGoToStowHeight();
    }

    @Override
    public boolean getBackwardsModifier() {
        return mButtonControlBoard.getBackwardsModifier();
    }

    @Override
    public boolean getAutoHeightModifier() {
        return mButtonControlBoard.getAutoHeightModifier();
    }

    @Override
    public double getJogElevatorThrottle() {
        return mButtonControlBoard.getJogElevatorThrottle();
    }

    @Override
    public boolean getRunIntake() {
        return mButtonControlBoard.getRunIntake();
    }

    @Override
    public boolean getIntakePosition() {
        return mButtonControlBoard.getIntakePosition();
    }

    @Override
    public void setRumble(boolean on) {
        mButtonControlBoard.setRumble(on);
    }

    @Override
    public boolean getEnableHangMode() {
        return mButtonControlBoard.getEnableHangMode();
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
    public boolean getPoopyShoot() {
        return mDriveControlBoard.getPoopyShoot();
    }

    @Override
    public boolean getQuickTurn() {
        return mDriveControlBoard.getQuickTurn();
    }

    @Override
    public boolean getOpenJaw() {
        return mDriveControlBoard.getOpenJaw();
    }

    @Override
    public boolean getShoot() {
        return mDriveControlBoard.getShoot();
    }
}
