package frc.robot.commands.actions.mouth;

import frc.robot.subsystems.Mouth;

public class MouthHolding extends SetMouthState {

    public MouthHolding()
    {
        super(Mouth.MouthState.HOLDING);
    }

}   