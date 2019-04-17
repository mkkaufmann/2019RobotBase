package frc.robot.commands.actions.util;

import edu.wpi.first.wpilibj.command.Command;

public class SequentialCommand extends Command {

    private Command a;
    private Command b;

    public SequentialCommand(Command a, Command b){
        this.a = a;
        this.b = b;
    }

    @Override
    public void initialize(){
        a.start();
    }

    @Override
    public void execute(){
        if(a.isCompleted() || a.isCanceled()){
            b.start();
        }
    }

    @Override
    protected boolean isFinished() {
        return b.isCompleted() || b.isCanceled();
    }
}
