package frc.robot.controlboard;

public interface IButtonControlBoard {

    boolean getCargoIn();

    double getCargoOut();

    boolean getHatchIn();

    boolean getHatchOut();

    boolean getCargoLow();

    boolean getCargoShip();

    boolean getCargoMid();

    boolean getCargoHigh();

    boolean getHatchLow();

    boolean getHatchMid();

    boolean getHatchHigh();

    boolean getHoldStrafe();

    double getElevatorThrottle();

    double getStrafePosition();

    boolean getStrafeManual();

    boolean getProjectorOut();

    void setButtonRumble(boolean on);

    void setButtonRumble(boolean left, boolean right);
}