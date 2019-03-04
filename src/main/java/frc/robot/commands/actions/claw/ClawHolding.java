package frc.robot.commands.actions.claw;

import frc.robot.subsystems.RollerClaw;

public class ClawHolding extends SetClawState {

    public ClawHolding()
    {
        super(RollerClaw.ClawState.HOLDING);
    }

}   