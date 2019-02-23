package frc.robot.states;

//TODO tune
public class SuperstructureConstants {
    public static final double kElevatorMaxHeight = 72.0; // TUNE THIS?
    public static final double kElevatorMaxHeightArmAtStart = 30.0;
    public static final double kElevatorMinHeight = 0.0; // TUNE THIS?

    // This is in inches / ~20ms
    public static final double kElevatorJogThrottle = 60.0 / 50.0; // TUNE THIS?


//    private static final double kBallDiameter = 13;
//    private static final double kBallRadius = kBallDiameter / 2;
    private static final double kPortDiameter = 16;
    private static final double kPortRadius = kPortDiameter / 2;

    //Elevator Heights (measured from bottom of mouth)
    public static final double kRocketCargoLow = 27.5 - kPortRadius;
    public static final double kRocketCargoMiddle = 55.5 - kPortRadius;
    public static final double kRocketCargoHigh = 83.5 - kPortRadius >= kElevatorMaxHeight ? kElevatorMaxHeight : 83.5 - kPortRadius;


    private static final double kClawOffset = 19.0;
    public static final double kRocketHatchLow = 19.0 - kClawOffset;
    public static final double kRocketHatchMiddle = 47.0 - kClawOffset;
    public static final double kRocketHatchHigh = 75.0 - kClawOffset;

    public static final double kCargoShipCargo = 45.0; // TUNE THIS?

    //Strafe values
    public static final double kStrafeInchesWidth = 12;//TUNE!!!!
    public static final double kStrafeMinEncoderValue = 0;//TUNE!!!!
    public static final double kStrafeMaxEncoderValue = 1000;//TUNE!!!!
    public static final double kStrafeMidEncoderValue = (kStrafeMinEncoderValue+kStrafeMaxEncoderValue)/2;

    //Arm values
    public static final double kArmDefaultSpeed = 1;

}
