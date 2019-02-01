package frc.robot.controlboard;

public interface IButtonControlBoard {

    // Elevator
    boolean getGoToHighHeight(); //single

    boolean getGoToNeutralHeight(); //single

    boolean getGoToLowHeight(); //single

    boolean getGoToCargoShipCargoHeight(); //single

    boolean getHatchOrCargo(); //toggle

    boolean getArmToggle(); //toggle

    boolean getArmToStart(); //toggle

    boolean getClawToggle(); //toggle

    // Intake
    boolean getRunIntake(); //toggle

    void setRumble(boolean on);

    // Climbing
    boolean getEnableClimbMode(); //toggle

    double getClimberThrottle();

    double getElevatorThrottle();
}