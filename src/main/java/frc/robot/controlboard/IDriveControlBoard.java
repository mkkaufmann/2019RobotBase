package frc.robot.controlboard;

public interface IDriveControlBoard {
    double getThrottle();

    double getTurn();

//    boolean getPoopyShoot();
//
    boolean getQuickTurn();

//    boolean getOpenJaw();
//
//    boolean getShoot();

    void setDriverRumble(boolean on);

    void setDriverRumble(boolean left, boolean right);

    boolean getEnableClimbMode();

    double getClimberThrottle();
}