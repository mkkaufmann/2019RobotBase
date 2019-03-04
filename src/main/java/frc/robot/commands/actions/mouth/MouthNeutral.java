package frc.robot.commands.actions.mouth;

import frc.robot.subsystems.Mouth;
import frc.robot.subsystems.RollerClaw;

public class MouthNeutral extends SetMouthState {

    public MouthNeutral()
    {
        super(Mouth.MouthState.NEUTRAL);
    }

}   