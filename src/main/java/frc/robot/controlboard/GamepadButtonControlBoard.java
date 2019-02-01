package frc.robot.controlboard;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Constants;
//TODO configure
public class GamepadButtonControlBoard implements IButtonControlBoard {
    private static GamepadButtonControlBoard mInstance = null;

    public static GamepadButtonControlBoard getInstance() {
        if (mInstance == null) {
            mInstance = new GamepadButtonControlBoard();
        }

        return mInstance;
    }

    private Joystick mJoystick;

    private GamepadButtonControlBoard() {
        mJoystick = new Joystick(Constants.kControlBoard.kButtonGamepadPort);
    }

    //Elevator
    @Override
    public boolean getGoToHighScaleHeight() {
        return mJoystick.getRawButton(4);
    }

    @Override
    public boolean getGoToNeutralScaleHeight() {
        return mJoystick.getRawButton(2);
    }

    @Override
    public boolean getGoToLowScaleHeight() {
        return mJoystick.getRawButton(1);
    }

    @Override
    public boolean getGoToSwitchHeight() {
        return mJoystick.getRawButton(3);
    }

    @Override
    public boolean getGoToStowHeight() {
        return mJoystick.getRawButton(6);
    }

    @Override
    public boolean getBackwardsModifier() {
        return mJoystick.getRawAxis(3) > Constants.kControlBoard.kJoystickThreshold;
    }

    @Override
    public boolean getAutoHeightModifier() {
        return false;
    }

    //Jog Elevator
    @Override
    public double getJogElevatorThrottle() {
        return -mJoystick.getRawAxis(5);
    }


    //Intake
    @Override
    public boolean getIntakePosition() {
        return mJoystick.getRawAxis(2) > Constants.kControlBoard.kJoystickThreshold;
    }

    @Override
    public boolean getRunIntake() {
        return mJoystick.getRawButton(5);
    }

    @Override
    public void setRumble(boolean on) {
        mJoystick.setRumble(GenericHID.RumbleType.kRightRumble, on ? 1.0 : 0);
    }

    //Climbing
    @Override
    public boolean getEnableHangMode() {
        return mJoystick.getRawButton(8) && mJoystick.getRawButton(7);
    }

    @Override
    public double getElevatorThrottle() {
        return mJoystick.getRawAxis(5);
    }

}