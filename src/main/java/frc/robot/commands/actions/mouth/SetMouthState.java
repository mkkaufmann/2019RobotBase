package frc.robot.commands.actions.mouth;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Mouth;
import frc.robot.subsystems.RollerClaw;

public class SetMouthState extends Command {
    private final Mouth.MouthState mState;
    private Mouth mMouth;
    public SetMouthState(Mouth.MouthState state)
    {
        mMouth = Mouth.getInstance();
        mState = state;
    }

    @Override
    protected void initialize() {
        mMouth.setState(mState);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
