package frc.robot.commands.actions.climber;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Climber;

public class EnableClimb extends Command {
    private Climber mClimber;
    public EnableClimb()
    {
        mClimber = Climber.getInstance();
    }

    @Override
    protected void initialize() {
        mClimber.setState(Climber.ClimberState.PERCENT_OUTPUT);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}