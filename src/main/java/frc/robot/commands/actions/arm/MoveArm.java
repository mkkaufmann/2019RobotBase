package frc.robot.commands.actions.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Arm;

public class MoveArm extends Command {
    private final Arm.ArmPosition mState;
    private Arm mArm;
    public MoveArm(Arm.ArmPosition state)
    {
        mArm = Arm.getInstance();
        mState = state;
    }

    @Override
    protected void initialize() {
        mArm.setTargetPosition(mState);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
