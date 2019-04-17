package frc.robot.commands.actions.elevator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.commands.actions.util.WaitUntilTrueCommand;
import frc.robot.lib.Boolean;
import frc.robot.lib.Util;
import frc.robot.subsystems.Elevator;

public class WaitUntilHeight extends WaitUntilTrueCommand {

    public WaitUntilHeight(double height, double threshold) {
        super(new Boolean() {
            @Override
            public boolean get() {
                return Util.epsilonEquals(Elevator.getInstance().getInchesFromBottom(), height, threshold);
            }
        });
    }

    public WaitUntilHeight(double height){
        this(height, 1);
    }
}
