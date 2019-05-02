package frc.robot.controlboard.buttons;

import frc.robot.Robot;
import frc.robot.states.SuperstructureConstants;

public class HatchHigh extends HeightButton {

    public HatchHigh() {
        super(SuperstructureConstants.kRocketHatchHigh, () -> Robot.getControlBoard().getHatchHigh());
    }

}
