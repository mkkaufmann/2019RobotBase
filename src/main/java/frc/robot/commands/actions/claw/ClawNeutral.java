package frc.robot.commands.actions.claw;

import frc.robot.subsystems.RollerClaw;

public class ClawNeutral extends SetClawState {

    public ClawNeutral()
    {
        super(RollerClaw.ClawState.NEUTRAL);
    }

}   