package frc.robot.controlboard.buttons;

import frc.robot.Robot;
import frc.robot.states.SuperstructureConstants;

public class CargoHigh extends HeightButton {

    public CargoHigh() {
        super(SuperstructureConstants.kRocketCargoHigh, () -> Robot.getControlBoard().getCargoHigh());
    }

}
