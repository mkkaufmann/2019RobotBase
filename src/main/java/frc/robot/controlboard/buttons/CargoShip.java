package frc.robot.controlboard.buttons;

import frc.robot.Robot;
import frc.robot.states.SuperstructureConstants;

public class CargoShip extends HeightButton {

    public CargoShip() {
        super(SuperstructureConstants.kCargoShipCargo, () -> Robot.getControlBoard().getCargoShip());
    }

}
