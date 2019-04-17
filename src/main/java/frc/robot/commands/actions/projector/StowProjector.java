package frc.robot.commands.actions.projector;

import frc.robot.subsystems.Projector;

public class StowProjector extends MoveProjector {
    public StowProjector()
    {
        super(Projector.ProjectorPosition.STOW);
    }
}
