package frc.robot.commands.actions.util;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.commands.NoCommand;

import java.util.List;

public class SequentialCommand extends Command {

    private Command a;
    private Command b;

    public SequentialCommand(Command a, Command b){
        this.a = a;
        this.b = b;
    }

    public SequentialCommand(List<Command> list){
        this(list, true);
    }

    private SequentialCommand(List<Command> list, boolean init){
        if(init){
            list.add(new NoCommand());
        }
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
            this.b = new SequentialCommand(list, false);
        }
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
