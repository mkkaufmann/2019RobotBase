package frc.robot.commands.actions.claw;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.RollerClaw;

public class SetClawState extends Command {
    private final RollerClaw.ClawState mState;
    private RollerClaw mClaw;
    public SetClawState(RollerClaw.ClawState state)
    {
        mClaw = RollerClaw.getInstance();
        mState = state;
    }

    @Override
    protected void initialize() {
        mClaw.setState(mState);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
