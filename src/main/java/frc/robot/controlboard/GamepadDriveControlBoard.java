package frc.robot.controlboard;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;
import frc.robot.lib.Util;

public class GamepadDriveControlBoard implements IDriveControlBoard {
    private static GamepadDriveControlBoard mInstance = null;

    public static GamepadDriveControlBoard getInstance() {
        if (mInstance == null) {
            mInstance = new GamepadDriveControlBoard();
        }

        return mInstance;
    }

    private XboxController mJoystick;

    private GamepadDriveControlBoard() {
        mJoystick = new XboxController(Constants.kControlBoard.kDriveGamepadPort);
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
    public boolean getQuickTurn() {
        return mJoystick.getRawButton(6);
    }

    @Override
    public double getShootSpeed() {
        double speed = Util.deadband(mJoystick.getTriggerAxis(GenericHID.Hand.kLeft));
        if(speed == 0){
            return 0;
        }else if(speed < 0.5){
            return 0.5;
        }else {
            return 1.0;
        }
    }
}