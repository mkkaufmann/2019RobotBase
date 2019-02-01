package frc.robot.lib;

import edu.wpi.first.wpilibj.PWMSpeedController;

public class GenericPWMSpeedController extends PWMSpeedController {
    /**
     * Constructor.
     *
     * @param channel The PWM channel that the controller is attached to. 0-9 are on-board, 10-19 are
     *                on the MXP port
     */
    public GenericPWMSpeedController(int channel) {
        super(channel);
    }
}
