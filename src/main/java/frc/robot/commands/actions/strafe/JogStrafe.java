package frc.robot.commands.actions.strafe;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.commands.actions.claw.ClawIn;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.RollerClaw;
import frc.robot.subsystems.Strafe;

public class JogStrafe extends Command {
    private double strafeJog = 2;
    private Strafe mStrafe;
    private double startPot;

    public JogStrafe(boolean left)
    {
        mStrafe = Strafe.getInstance();
        if(!left){
            strafeJog*=-1;
        }
    }
    //todo use this file
    @Override
    protected void initialize() {
        startPot = mStrafe.getPotPos();
        mStrafe.setManual(startPot + strafeJog);
        Robot.runCommand(new ClawIn());
        setTimeout(3);
    }

    @Override
    protected void execute(){
        mStrafe.setManual(startPot + strafeJog);
    }

    @Override
    protected boolean isFinished() {
        if(isTimedOut()){
            mStrafe.setManual(startPot);
            return true;
        }
        return false;
    }
}
