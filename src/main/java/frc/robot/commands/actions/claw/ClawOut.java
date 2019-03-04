package frc.robot.commands.actions.claw;

import frc.robot.subsystems.RollerClaw;

public class ClawOut extends SetClawState {

    public ClawOut()
    {
        super(RollerClaw.ClawState.OUT);
    }

}   