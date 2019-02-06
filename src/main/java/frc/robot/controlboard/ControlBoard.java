package frc.robot.controlboard;

public class ControlBoard implements IControlBoard {

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
    public void setButtonRumble(boolean on) {
        mButtonControlBoard.setButtonRumble(on);
    }

    @Override
    public void setButtonRumble(boolean left, boolean right) {
        mButtonControlBoard.setButtonRumble(left, right);
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
    public void setDriverRumble(boolean on) {
        mDriveControlBoard.setDriverRumble(on);
    }

    @Override
    public void setDriverRumble(boolean left, boolean right) {
        mDriveControlBoard.setDriverRumble(left, right);
    }

    public void setRumble(boolean left, boolean right){
        setButtonRumble(left, right);
        setDriverRumble(left, right);
    }

    public void setRumble(boolean on){
        setDriverRumble(on);
        setButtonRumble(on);
    }

    @Override
    public double getShootSpeed() {
        return mDriveControlBoard.getShootSpeed();
    }
}
