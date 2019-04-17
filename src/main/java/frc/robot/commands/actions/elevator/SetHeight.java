package frc.robot.commands.actions.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Elevator;

public class SetHeight extends Command {

    private double height;
    private Elevator mElevator;

    public SetHeight(double height){
        this.height = height;
        this.mElevator = Elevator.getInstance();
    }

    @Override
    public void initialize(){
        mElevator.setMotionMagic(height);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
