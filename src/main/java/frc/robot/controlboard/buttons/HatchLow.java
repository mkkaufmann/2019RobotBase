package frc.robot.controlboard.buttons;

import frc.robot.Robot;
import frc.robot.states.SuperstructureConstants;

public class HatchLow extends HeightButton {

    public HatchLow() {
        super(SuperstructureConstants.kRocketHatchLow, () -> Robot.getControlBoard().getHatchLow());
    }

}
