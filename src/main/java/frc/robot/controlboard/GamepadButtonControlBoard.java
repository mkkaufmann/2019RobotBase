package frc.robot.controlboard;

import edu.wpi.first.wpilibj.GenericHID;
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

    @Override
    public void setButtonRumble(boolean on) {
        setButtonRumble(on, on);
    }

    @Override
    public void setButtonRumble(boolean left, boolean right) {
        mJoystick.setRumble(GenericHID.RumbleType.kLeftRumble, left ? 1.0 : 0);
        mJoystick.setRumble(GenericHID.RumbleType.kRightRumble, right ? 1.0 : 0);
    }

    @Override
    public double getStrafePosition(){
        return mJoystick.getRawAxis(0);
    }

    @Override
    public boolean getStrafeManual() {
        return !mJoystick.getRawButton(9);
    }

    @Override
    public boolean getProjectorOut() {
        return mJoystick.getRawButton(7) || mJoystick.getRawButton(8);
    }

    @Override
    public boolean getCargoIn() {
        return mJoystick.getRawButton(5);
    }

    @Override
    public double getCargoOut() {
        return Util.deadband(mJoystick.getTriggerAxis(GenericHID.Hand.kLeft));
    }

    @Override
    public boolean getHatchIn() {
        return mJoystick.getRawButton(6);
    }

    @Override
    public boolean getHatchOut() {
        return Util.deadband(mJoystick.getTriggerAxis(GenericHID.Hand.kRight)) > 0;
    }

    @Override
    public boolean getCargoLow() {
        return mJoystick.getPOV() == 180;
    }

    @Override
    public boolean getCargoShip() {
        return mJoystick.getPOV() == 90;
    }

    @Override
    public boolean getCargoMid() {
        return mJoystick.getPOV() == 270;
    }

    @Override
    public boolean getCargoHigh() {
        return mJoystick.getPOV() == 0;
    }

    @Override
    public boolean getHatchLow() {
        return mJoystick.getRawButton(1);
    }

    @Override
    public boolean getHatchMid() {
        return mJoystick.getRawButton(2);
    }

    @Override
    public boolean getHatchHigh() {
        return mJoystick.getRawButton(4);
    }

    @Override
    public boolean getHoldStrafe() {
        return mJoystick.getRawButton(3);
    }

    @Override
    public double getElevatorThrottle() {
        return -Util.deadband(mJoystick.getRawAxis(5));
    }
}