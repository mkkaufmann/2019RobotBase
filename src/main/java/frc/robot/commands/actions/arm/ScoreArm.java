package frc.robot.commands.actions.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Arm;

public class ScoreArm extends MoveArm {
    public ScoreArm()
    {
        super(Arm.ArmPosition.SCORE);
    }
}
