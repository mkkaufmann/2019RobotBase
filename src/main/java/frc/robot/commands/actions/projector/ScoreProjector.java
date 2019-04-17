package frc.robot.commands.actions.projector;

import frc.robot.subsystems.Projector;

public class ScoreProjector extends MoveProjector {
    public ScoreProjector()
    {
        super(Projector.ProjectorPosition.SCORE);
    }
}
