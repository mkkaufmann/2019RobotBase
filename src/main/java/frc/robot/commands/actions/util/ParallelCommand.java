package frc.robot.commands.actions.util;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.commands.NoCommand;

import java.util.List;

public class ParallelCommand extends Command {

    private Command a;
    private Command b;

    public ParallelCommand(Command a, Command b){
        this.a = a;
        this.b = b;
    }

    public ParallelCommand(List<Command> list){
        if(list.isEmpty()){
            this.a = new NoCommand();
            this.b = new NoCommand();
        }else if(list.size() == 1){
            this.a = list.get(0);
            this.b = new NoCommand();
        }else if(list.size() == 2){
            this.a = list.get(0);
            this.b = list.get(1);
        }else{
            this.a = list.remove(0);
            this.b = new ParallelCommand(list);
        }
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
