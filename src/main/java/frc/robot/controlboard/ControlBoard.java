package frc.robot.controlboard;

public class ControlBoard implements IControlBoard {

    GamepadButtonControlBoard mButtonControlBoard = GamepadButtonControlBoard.getInstance();
    GamepadDriveControlBoard mDriveControlBoard = GamepadDriveControlBoard.getInstance();



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
        return mDriveControlBoard.getEnableClimbMode();
    }

    @Override
    public double getClimberThrottle() {
        return mDriveControlBoard.getClimberThrottle();
    }

    @Override
    public double getStrafePosition() {
        return mButtonControlBoard.getStrafePosition();
    }

    @Override
    public boolean getStrafeManual() {
        return mButtonControlBoard.getStrafeManual();
    }

    @Override
    public boolean getArmOut() {
        return mButtonControlBoard.getArmOut();
    }


    @Override
    public boolean getCargoIn() {
        return mButtonControlBoard.getCargoIn();
    }

    @Override
    public double getCargoOut() {
        return mButtonControlBoard.getCargoOut();
    }

    @Override
    public boolean getHatchIn() {
        return mButtonControlBoard.getHatchIn();
    }

    @Override
    public boolean getHatchOut() {
        return mButtonControlBoard.getHatchOut();
    }

    @Override
    public boolean getCargoLow() {
        return mButtonControlBoard.getHatchLow();
    }

    @Override
    public boolean getCargoShip() {
        return mButtonControlBoard.getCargoShip();
    }

    @Override
    public boolean getCargoMid() {
        return mButtonControlBoard.getHatchMid();
    }

    @Override
    public boolean getCargoHigh() {
        return mButtonControlBoard.getHatchHigh();
    }

    @Override
    public boolean getHatchLow() {
        return mButtonControlBoard.getCargoLow() || mDriveControlBoard.getCargoLow();
    }

    @Override
    public boolean getHatchMid() {
        return mButtonControlBoard.getCargoMid();
    }

    @Override
    public boolean getHatchHigh() {
        return mButtonControlBoard.getCargoHigh();
    }

    @Override
    public boolean getHoldStrafe() {
        return mButtonControlBoard.getHoldStrafe();
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
    public boolean getVisionAssist(){
        return mDriveControlBoard.getVisionAssist();
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
}
