package frc.robot.controlboard.buttons;

import frc.robot.Robot;
import frc.robot.states.SuperstructureConstants;

public class CargoMid extends HeightButton {

    public CargoMid() {
        super(SuperstructureConstants.kRocketCargoMiddle, () -> Robot.getControlBoard().getCargoMid());
    }

}
