package frc.robot.controlboard;

public interface IButtonControlBoard {

    // Elevator
    boolean getGoToHighHeight(); //single

    boolean getGoToNeutralHeight(); //single

    boolean getGoToLowHeight(); //single

    boolean getGoToCargoShipCargoHeight(); //single

    boolean getHatchOrCargo(); //toggle

    boolean getArmToggle(); //toggle

    boolean getClawToggle(); //toggle

    boolean getCenterStrafe();

    // Intake
    boolean getRunIntake(); //toggle

    void setButtonRumble(boolean on);

    void setButtonRumble(boolean left, boolean right);

    double getStrafeThrottle();

    double getElevatorThrottle();

    double getShootSpeed();
}