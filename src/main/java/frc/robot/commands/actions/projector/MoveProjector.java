package frc.robot.commands.actions.projector;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Projector;

public class MoveProjector extends Command {
    private final Projector.ProjectorPosition mState;
    private Projector mProjector;
    public MoveProjector(Projector.ProjectorPosition state)
    {
        mProjector = Projector.getInstance();
        mState = state;
    }

    @Override
    protected void initialize() {
        mProjector.setTargetPosition(mState);
        setTimeout(1);
    }

    @Override
    protected boolean isFinished() {
        if(isTimedOut()){
            mProjector.setTargetPosition(Projector.ProjectorPosition.NEUTRAL);
            return true;
        }else{
            return false;
        }
    }
}
