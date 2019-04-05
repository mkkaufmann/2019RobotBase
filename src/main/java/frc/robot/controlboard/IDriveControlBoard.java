package frc.robot.controlboard;

public interface IDriveControlBoard {
    double getThrottle();

    double getTurn();

    boolean getQuickTurn();

    void setDriverRumble(boolean on);

    void setDriverRumble(boolean left, boolean right);

    boolean getEnableClimbMode();

    double getClimberThrottle();

    boolean getVisionAssist();

    boolean getCargoLow();

    boolean getCargoShip();

    boolean getCargoMid();

    boolean getCargoHigh();

    boolean getHatchLow();

    boolean getHatchMid();

    boolean getHatchHigh();

    boolean getRemoveLeftHatch();

    boolean getRemoveRightHatch();

    boolean getGrabHAB();
}