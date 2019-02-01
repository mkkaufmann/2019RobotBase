package frc.robot;

public class Constants {

    public static double kLooperDt = 0.01;

    public static class kDrivetrain{
        public static final int leftMasterID = 0;//CAN
        public static final int leftFollowerID = 1;//CAN
        public static final int rightMasterID = 2;//CAN
        public static final int rightFollowerID = 3;//CAN
    }
    public static class kElevator{
        public static final int masterID = 4;//
        //TODO add limit switches
    }
    public static class kMouth{
        public static final int SRPort = 0;//PWM
    }
    public static class kClaw{
        public static final int servoPort = 1;//PWM
    }
    public static class kArm{
        public static final int SPPort = 2;//PWM
        public static final int scoreLimitPort = 0;//DIO
        public static final int stowLimitPort = 1;//DIO
        public static final int startLimitPort = 2;//DIO
    }
    public static class kClimber{
        public static final int leftSparkPort = 3;//PWM
        public static final int rightSparkPort = 4;//PWM
    }
    public static class kStrafe{
        public static final int victorPort = 5;//PWM
        public static final int encoderPort1 = 6;//PWM
        public static final int encoderPort2 = 7;//PWM
        public static final int limitPort = 3;//DIO
    }
    public static class kControlBoard{
        public static final int kDriveGamepadPort = 0;
        public static final int kButtonGamepadPort = 1;
        public static final double kJoystickThreshold = 0.5;//TODO what are these for?
        public static final double kJoystickJogThreshold = 0.4;
    }
}
