package frc.robot.controlboard;

public interface IButtonControlBoard {

    // Elevator
    boolean getGoToHighHeight();

    boolean getGoToNeutralHeight();

    boolean getGoToLowHeight();

    boolean getGoToCargoShipCargoHeight();

    boolean getHatchOrCargo();

    boolean getArmToggle();

    boolean getArmToStart();

    boolean getClawToggle();

    // Intake
    boolean getRunIntake();

    void setRumble(boolean on);

    // Climbing
    boolean getEnableClimbMode();

    double getClimberThrottle();

    double getElevatorThrottle();
}