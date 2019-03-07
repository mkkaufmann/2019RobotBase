package frc.robot.commands.actions.arm;

import frc.robot.subsystems.Arm;

public class StowArm extends MoveArm {
    public StowArm()
    {
        super(Arm.ArmPosition.STOW);
    }
}
