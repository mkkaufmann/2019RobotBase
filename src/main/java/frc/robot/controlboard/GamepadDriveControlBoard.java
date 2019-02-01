package frc.robot.controlboard;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Constants;

public class GamepadDriveControlBoard implements IDriveControlBoard {
    private static GamepadDriveControlBoard mInstance = null;

    public static GamepadDriveControlBoard getInstance() {
        if (mInstance == null) {
            mInstance = new GamepadDriveControlBoard();
        }

        return mInstance;
    }

    private Joystick mJoystick;

    private GamepadDriveControlBoard() {
        mJoystick = new Joystick(Constants.kControlBoard.kDriveGamepadPort);
    }

    @Override
    public double getThrottle() {
        return -mJoystick.getRawAxis(1);
    }

    @Override
    public double getTurn() {
        return mJoystick.getRawAxis(4);
    }

    @Override
    public boolean getPoopyShoot() {
        return false;
    }

    @Override
    public boolean getQuickTurn() {
        return mJoystick.getRawButton(6);
    }

    @Override
    public boolean getOpenJaw() {
        return mJoystick.getRawAxis(3) != 0;
    }

    @Override
    public boolean getShoot() {
        return mJoystick.getRawAxis(2) != 0;
    }
}