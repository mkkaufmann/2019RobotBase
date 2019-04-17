package frc.robot.commands.actions.util;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.lib.Boolean;

public class WaitUntilTrueCommand extends Command {

    private Boolean state;

    public WaitUntilTrueCommand(Boolean state){
        this.state = state;
    }
    @Override
    protected boolean isFinished() {
        return state.get();
    }
}
