package frc.robot;

public class Constants {

    public static double kLooperDt = 0.01;

    public static class kDrivetrain{
        public static final int leftMasterID = 3;//CAN
        public static final int leftFollowerID = 0;//CAN
        public static final int rightMasterID = 1;//CAN
        public static final int rightFollowerID = 4;//CAN
    }
    public static class kElevator{
        public static final int masterID = 2;//
        //TODO add limit switches
    }
    public static class kMouth{
        public static final int masterPort = 0;//PWM
    }
    public static class kClaw{
        public static final int servoPort = 1;//PWM
    }
    public static class kArm{
        public static final int masterPort = 2;//PWM
        public static final int scoreLimitPort = 0;//DIO
        public static final int stowLimitPort = 1;//DIO
        public static final int startLimitPort = 2;//DIO
    }
    public static class kClimber{
        public static final int leftMasterPort = 3;//PWM
        public static final int rightMasterPort = 4;//PWM
    }
    public static class kStrafe{
        public static final int masterPort = 5;//PWM
        public static final int encoderPort1 = 6;//PWM
        public static final int encoderPort2 = 7;//PWM
        public static final int limitPort = 3;//DIO
    }
    public static class kControlBoard{
        public static final int kDriveGamepadPort = 0;
        public static final int kButtonGamepadPort = 1;
    }
}
