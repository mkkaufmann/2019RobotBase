package frc.robot;

public class Constants {

    public static double kLooperDt = 0.005;

    public static double kTrackScrubFactor = 0.924;
    public static double kTrackWidthInches = 27.5;
    public static double kPathFollowingMaxAccel = 120; //in/s^2
    public static double kPathFollowingMaxVel = 120;//110.4; // inches per second
    public static double kDriveMaxSetpoint = 17*12;//17.0 * 12.0; // 17 fps

    // Path constants
    public static double kMinLookAhead = 12.0; // inches
    public static double kMinLookAheadSpeed = 9.0; // inches per second
    public static double kMaxLookAhead = 24.0; // inches
    public static double kMaxLookAheadSpeed = 120.0; // inches per second
    public static double kInertiaSteeringGain = 0.0; // angular velocity command is multiplied by this gain *
    // our speed
    // in inches per sec

    public static double kPathFollowingProfileKp=5;// = 5.00;
    public static double kPathFollowingProfileKi=0.03;//= 0.03;
    public static double kPathFollowingProfileKv=0.02;//= 0.02;
    public static double kPathFollowingProfileKffv = 1.0;
    public static double kPathFollowingProfileKffa = 0.05;

    public static double kSegmentCompletionTolerance = 0.1;//inches
    public static double kPathFollowingGoalPosTolerance = 0.75;
    public static double kPathFollowingGoalVelTolerance = 12.0;
    public static double kPathStopSteeringDistance = 9.0;

    public static class kDrivetrain{
        public static final int leftMasterID = 3;//CAN
        public static final int leftFollowerID = 0;//CAN
        public static final int rightMasterID = 1;//CAN
        public static final int rightFollowerID = 4;//CAN
        public static final double WHEEL_DIAMETER_IN = 6;
        public static final double ENCODER_TICKS_PER_ROTATION = 4096;
    }
    public static class kElevator{
        public static final int masterID = 2;//
        //TODO add limit switches
    }
    public static class kMouth{
        public static final int masterPort = 1;//PWM
    }
    public static class kClaw{
        public static final int servoPort = 2;//PWM
    }
    public static class kArm{
        public static final int masterPort = 0;//PWM
    }
    public static class kClimber{
        public static final int mMasterPort = 3;//PWM
    }
    public static class kStrafe{
        public static final int masterPort = 5;//PWM
        public static final int encoderPort1 = 6;//DIO
        public static final int encoderPort2 = 7;//DIO
        public static final int limitPort = 3;//DIO
    }
    public static class kControlBoard{
        public static final int kDriveGamepadPort = 0;
        public static final int kButtonGamepadPort = 1;
    }
}
