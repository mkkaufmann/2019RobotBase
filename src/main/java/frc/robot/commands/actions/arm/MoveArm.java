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
        setTimeout(1);
    }

    @Override
    protected boolean isFinished() {
        if(isTimedOut()){
            mArm.setTargetPosition(Arm.ArmPosition.NEUTRAL);
            return true;
        }else{
            return false;
        }
    }
}
