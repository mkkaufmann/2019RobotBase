package frc.robot.controlboard;

import edu.wpi.first.wpilibj.GenericHID;
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
    public void setDriverRumble(boolean on) {
        setDriverRumble(on, on);
    }

    @Override
    public void setDriverRumble(boolean left, boolean right) {
        mJoystick.setRumble(GenericHID.RumbleType.kLeftRumble, left ? 1.0 : 0);
        mJoystick.setRumble(GenericHID.RumbleType.kRightRumble, right ? 1.0 : 0);
    }



    @Override
    public boolean getEnableClimbMode() {
        return mJoystick.getRawButton(8) && mJoystick.getRawButton(7);
    }


    @Override
    public double getClimberThrottle() {
        double up = Util.deadband(mJoystick.getTriggerAxis(GenericHID.Hand.kRight));
        double down = Util.deadband(mJoystick.getTriggerAxis(GenericHID.Hand.kLeft));
        if(down > 0 && up > 0){
            return 0;
        }else if(down > 0){
            if(down > 0.5){
                return -1;
            }
            return -0.5;
        }else if(up > 0){
            if(up > 0.5){
                return 1;
            }
            return 0.5;
        }
        return 0;
    }

    @Override
    public boolean getVisionAssist() {
        return mJoystick.getRawButton(4);
    }

    @Override
    public boolean getCargoLow() {
        return mJoystick.getPOV() == 180  || Util.deadband(mJoystick.getTriggerAxis(GenericHID.Hand.kRight)) > 0;
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
    public boolean getRemoveLeftHatch(){
        return mJoystick.getRawButton(5);
    }

    @Override
    public boolean getRemoveRightHatch(){
        return mJoystick.getRawButton(6);
    }

    @Override
    public boolean getGrabHAB() {
        return mJoystick.getRawButton(4);
    }
}