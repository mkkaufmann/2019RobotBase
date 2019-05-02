package frc.robot.controlboard.buttons;

import frc.robot.Robot;
import frc.robot.states.SuperstructureConstants;

public class CargoLow extends HeightButton {

    public CargoLow() {
        super(SuperstructureConstants.kRocketCargoLow, () -> Robot.getControlBoard().getCargoLow());
    }

}
