package frc.robot.controlboard;

public interface IButtonControlBoard {

    // Elevator
    boolean getGoToHighScaleHeight();

    boolean getGoToNeutralScaleHeight();

    boolean getGoToLowScaleHeight();

    boolean getGoToSwitchHeight();

    boolean getGoToStowHeight();

    boolean getBackwardsModifier();

    boolean getAutoHeightModifier();

    // Jog Elevator
    double getJogElevatorThrottle();

    // Intake
    boolean getRunIntake();

    boolean getIntakePosition();

    void setRumble(boolean on);

    // Climbing
    boolean getEnableHangMode();

    double getElevatorThrottle();
}