package frc.robot.states;

//TODO tune
public class SuperstructureConstants {
    public static final double kElevatorMaxHeight = 74.5; // TUNE THIS?
    public static final double kElevatorMaxHeightProjectorOut = 30.0;
    public static final double kElevatorMinHeight = 0.0; // TUNE THIS?

    // This is in inches / ~20ms
    public static final double kElevatorJogThrottle = 60.0 / 50.0; // TUNE THIS?


//    private static final double kBallDiameter = 13;
//    private static final double kBallRadius = kBallDiameter / 2;
    private static final double kPortDiameter = 16;
    private static final double kPortRadius = kPortDiameter / 2;

    //Elevator Heights (measured from bottom of mouth)
    public static final double kRocketCargoLow = 27.5 - kPortRadius + 4;
    public static final double kRocketCargoMiddle = 55.5 - kPortRadius + 4;
    public static final double kRocketCargoHigh = 83.5 - kPortRadius + 4 >= kElevatorMaxHeight ? kElevatorMaxHeight : 83.5 - kPortRadius + 4;
//    public static final double kLoadingStation = 37;

    private static final double kClawOffset = 19.0;
    public static final double kRocketHatchLow = 19.0 - kClawOffset;
    public static final double kRocketHatchMiddle = 48.5 - kClawOffset;
    public static final double kRocketHatchHigh = 76.5 - kClawOffset;

    public static final double kCargoShipCargo = 37.0; // TUNE THIS?

    //Strafe values
    public static final double kStrafeInchesWidth = 16;
    public static final int kStrafeEncoderTicksPerInch = 2048;//TUNE
    public static final double kStrafeMinEncoderValue = 0;
    public static final double kStrafeMaxEncoderValue = kStrafeInchesWidth * kStrafeEncoderTicksPerInch;//22898
    public static final double kStrafeMidEncoderValue = (kStrafeMinEncoderValue+kStrafeMaxEncoderValue)/2;

    //mouth
    public static final double kRunningCurrent = 0;
    public static final double kStalledCurrentThreshold = 0;
}
