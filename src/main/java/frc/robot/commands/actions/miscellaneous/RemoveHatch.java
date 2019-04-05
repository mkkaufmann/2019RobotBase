package frc.robot.commands.actions.miscellaneous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.commands.actions.claw.ClawHolding;
import frc.robot.commands.actions.claw.ClawIn;
import frc.robot.commands.actions.mouth.MouthIn;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.RollerClaw;
import frc.robot.subsystems.Strafe;

public class RemoveHatch extends Command {
    private double strafeJog = 2;
    private static final double elevatorJog = 6;
    private Strafe mStrafe;
    private Elevator mElevator;
    private double startPot;
    Timer timer = new Timer();
    boolean hasJoggedElevator;

    public RemoveHatch(boolean left)
    {
        mStrafe = Strafe.getInstance();
        mElevator = Elevator.getInstance();
        if(!left){
            strafeJog*=-1;
        }
        hasJoggedElevator = false;
    }

    @Override
    protected void initialize() {
        startPot = mStrafe.getPotPos();

        mElevator.jog(elevatorJog);
        Robot.runCommand(new ClawIn());
        timer.start();
        setTimeout(3);
    }

    @Override
    protected void execute(){
        if(timer.hasPeriodPassed(0.5) && !hasJoggedElevator){
//            mElevator.jog(elevatorJog);
            mStrafe.setManual(startPot + strafeJog);
            hasJoggedElevator = true;
        }
    }

    @Override
    protected boolean isFinished() {
        if(isTimedOut()){
            mStrafe.setManual(startPot);
            Robot.runCommand(new ClawHolding());
            return true;
        }
        return false;
    }
}
