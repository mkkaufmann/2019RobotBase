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
}