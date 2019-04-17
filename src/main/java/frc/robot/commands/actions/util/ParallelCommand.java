package frc.robot.commands.actions.util;

import edu.wpi.first.wpilibj.command.Command;

public class ParallelCommand extends Command {

    private Command a;
    private Command b;

    public ParallelCommand(Command a, Command b){
        this.a = a;
        this.b = b;
    }

    @Override
    public void initialize(){
        a.start();
        b.start();
    }

    @Override
    protected boolean isFinished() {
        return (b.isCompleted() || b.isCanceled()) && (a.isCompleted() || a.isCanceled());
    }
}
