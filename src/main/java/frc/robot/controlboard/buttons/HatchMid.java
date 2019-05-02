package frc.robot.controlboard.buttons;

import frc.robot.Robot;
import frc.robot.states.SuperstructureConstants;

public class HatchMid extends HeightButton {

    public HatchMid() {
        super(SuperstructureConstants.kRocketHatchMiddle, () -> Robot.getControlBoard().getHatchMid());
    }

}
