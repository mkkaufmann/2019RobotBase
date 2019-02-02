package frc.robot.controlboard;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;
import frc.robot.lib.Util;

public class GamepadButtonControlBoard implements IButtonControlBoard {
    private static GamepadButtonControlBoard mInstance = null;

    public static GamepadButtonControlBoard getInstance() {
        if (mInstance == null) {
            mInstance = new GamepadButtonControlBoard();
        }

        return mInstance;
    }

    private XboxController mJoystick;

    private GamepadButtonControlBoard() {
        mJoystick = new XboxController(Constants.kControlBoard.kButtonGamepadPort);
    }

    //Elevator
    @Override
    public boolean getGoToHighHeight() {
        return mJoystick.getRawButton(4);
    }

    @Override
    public boolean getGoToNeutralHeight() {
        return mJoystick.getRawButton(2);
    }

    @Override
    public boolean getGoToLowHeight() {
        return mJoystick.getRawButton(1);
    }

    @Override
    public boolean getGoToCargoShipCargoHeight() {
        return mJoystick.getRawButton(3);
    }

    @Override
    public boolean getHatchOrCargo() {
        return mJoystick.getRawButton(6);
    }

    @Override
    public boolean getArmToggle() {
        return mJoystick.getRawButton(5);
    }

    @Override
    public boolean getArmToStart() {
        return mJoystick.getRawButton(5) && mJoystick.getPOV() == 180;//TODO make sure other is not activated
    }

    @Override
    public boolean getClawToggle() {
        return Util.deadband(mJoystick.getTriggerAxis(GenericHID.Hand.kRight), 0.2) > 0;
    }

    @Override
    public boolean getCenterStrafe() {
        return Util.deadband(mJoystick.getTriggerAxis(GenericHID.Hand.kLeft), 0.2) > 0;
    }

    @Override
    public boolean getRunIntake() {
        return this.getClawToggle();//TODO make sure implemented properly
    }

    @Override
    public void setRumble(boolean on) {
        mJoystick.setRumble(GenericHID.RumbleType.kRightRumble, on ? 1.0 : 0);
    }


    @Override
    public boolean getEnableClimbMode() {
        return mJoystick.getRawButton(8) && mJoystick.getRawButton(7);
    }

    @Override
    public double getClimberThrottle() {
        return Util.deadband(mJoystick.getRawAxis(1));
    }

    @Override
    public double getStrafeThrottle(){
        return Util.deadband(mJoystick.getRawAxis(0));
    }

    @Override
    public double getElevatorThrottle() {
        return Util.deadband(mJoystick.getRawAxis(5));
    }

}