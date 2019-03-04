package frc.robot.commands.actions.claw;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.RollerClaw;

public class ClawIn extends SetClawState {

    public ClawIn()
    {
        super(RollerClaw.ClawState.IN);
    }

}   