package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.lib.DriveSignal;
import frc.robot.poofs.Kinematics;
import frc.robot.poofs.RobotState;
import frc.robot.poofs.util.control.Lookahead;
import frc.robot.poofs.util.control.Path;
import frc.robot.poofs.util.control.PathFollower;
import frc.robot.poofs.util.drivers.NavX;
import frc.robot.poofs.util.math.RigidTransform2d;
import frc.robot.poofs.util.math.Rotation2d;
import frc.robot.poofs.util.math.Twist2d;

public class Drive extends Subsystem{

    private static Drive mInstance = null;
    private TalonSRX mLeftMaster;
    private TalonSRX mRightMaster;
    private VictorSPX mLeftFollower;
    private VictorSPX mRightFollower;
    private PeriodicIO mPeriodicIO = new PeriodicIO();
    private DriveState mState = DriveState.NEUTRAL;
//    private DriveState mWantedState = DriveState.NEUTRAL;TODO may need this?
    private NavX mNavX = new NavX(SPI.Port.kMXP);
    private Path mCurrentPath = null;
    private PathFollower mPathFollower;
    private RobotState mRobotState = RobotState.getInstance();

    private double mLeft_target = 0, mRight_target = 0;
    private double mPercentageComplete = 0;

    private Drive(){
        mLeftMaster = new TalonSRX(Constants.kDrivetrain.leftMasterID);
        mRightMaster = new TalonSRX(Constants.kDrivetrain.rightMasterID);
        mLeftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 100);
        mRightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 100);
        mLeftMaster.setSensorPhase(true);

        mLeftMaster.config_kP(0,0.0);
        mRightMaster.config_kP(0,0.0);
        mLeftMaster.config_kI(0,0);
        mRightMaster.config_kI(0,0);
        mLeftMaster.config_kD(0,0);
        mRightMaster.config_kD(0,0);
        mLeftMaster.config_kF(0,0.4625);
        mRightMaster.config_kF(0,0.4625);

        mLeftFollower = new VictorSPX(Constants.kDrivetrain.leftFollowerID);
        mRightFollower = new VictorSPX(Constants.kDrivetrain.rightFollowerID);
        mLeftFollower.follow(mLeftMaster);
        mRightFollower.follow(mRightMaster);
        zeroEncoders();
        zeroGyro();
    }

    public static Drive getInstance(){
        if(mInstance == null){
            mInstance = new Drive();
        }
        return mInstance;
    }

    @Override
    public void stop() {
        //System.out.printlnln("stopping drive");
        setOpenLoop(0,0);
    }

    @Override
    public void zeroMechanism() {

    }

    @Override
    public void outputTelemetry() {

    }

    public synchronized void setOpenLoop(double left, double right){
        if(mState != DriveState.OPEN_LOOP){
            mLeftMaster.setNeutralMode(NeutralMode.Brake);
            mRightMaster.setNeutralMode(NeutralMode.Brake);
            mState = DriveState.OPEN_LOOP;
            mLeftMaster.configNeutralDeadband(0.04, 0);
            mRightMaster.configNeutralDeadband(0.04, 0);
        }
        mPeriodicIO.wanted_left_demand = left;
        mPeriodicIO.wanted_right_demand = right;
    }

    public synchronized void setOpenLoop(DriveSignal signal){
        setOpenLoop(signal.getLeft(), signal.getRight());
    }

    private static Double rotationsToInches(double rotations) {
        return rotations * (Constants.kDrivetrain.WHEEL_DIAMETER_IN * Math.PI);
    }

    private static double inchesToRotations(double inches) {
        return inches / (Constants.kDrivetrain.WHEEL_DIAMETER_IN * Math.PI);
    }


    public double getLeftDistanceInches() {
        Double ret = rotationsToInches(getLeftRotations());
        if (ret.isNaN())
            return 0.0;

        return ret;
    }

    public double getRightDistanceInches() {
        Double ret = rotationsToInches(getRightRotations());
        if (ret.isNaN())
            return 0.0;

        return ret;
    }

    public Double getLeftRotations() {
        return mPeriodicIO.left_encoder_ticks / Constants.kDrivetrain.ENCODER_TICKS_PER_ROTATION;
    }

    public Double getRightRotations() {
        return mPeriodicIO.right_encoder_ticks / Constants.kDrivetrain.ENCODER_TICKS_PER_ROTATION;
    }

    public double getLeftVelocityInchesPerSec() {
        return rpmToInchesPerSecond((mPeriodicIO.left_encoder_vel / Constants.kDrivetrain.ENCODER_TICKS_PER_ROTATION));
    }

    public double getRightVelocityInchesPerSec() {
        return rpmToInchesPerSecond((mPeriodicIO.left_encoder_vel / Constants.kDrivetrain.ENCODER_TICKS_PER_ROTATION));
    }

    private static double rpmToInchesPerSecond(double rpm) {
        return rotationsToInches(rpm) / 60;
    }

    private static double inchesPerSecondToRpm(double inches_per_second) {
        return inchesToRotations(inches_per_second) * 60;
    }

    private static double rpmToTicksPer100ms(double rpm) {
        return rpm * (1.0/60.0) * Constants.kDrivetrain.ENCODER_TICKS_PER_ROTATION * (1.0/10.0);
    }

    public synchronized Rotation2d getGyroAngle() {
        return mNavX.getYaw();
    }

    public synchronized void setWantDrivePath(Path path, boolean reversed) {
        //System.out.printlnln("setting drive path");
        if (mCurrentPath != path || mState != DriveState.PATH_FOLLOWING) {
            RobotState.getInstance().resetDistanceDriven();
            mPathFollower = new PathFollower(path, reversed, new PathFollower.Parameters(
                    new Lookahead(Constants.kMinLookAhead, Constants.kMaxLookAhead, Constants.kMinLookAheadSpeed,
                            Constants.kMaxLookAheadSpeed),
                    Constants.kInertiaSteeringGain, Constants.kPathFollowingProfileKp,
                    Constants.kPathFollowingProfileKi, Constants.kPathFollowingProfileKv,
                    Constants.kPathFollowingProfileKffv, Constants.kPathFollowingProfileKffa,
                    Constants.kPathFollowingMaxVel, Constants.kPathFollowingMaxAccel,
                    Constants.kPathFollowingGoalPosTolerance, Constants.kPathFollowingGoalVelTolerance,
                    Constants.kPathStopSteeringDistance));
            mState = DriveState.PATH_FOLLOWING;
            mCurrentPath = path;

            mLeftMaster.setNeutralMode(NeutralMode.Brake);
            mRightMaster.setNeutralMode(NeutralMode.Brake);

        } else {
            stop();
            //setVelocitySetpoint(0, 0);
        }
    }

    public synchronized boolean isDoneWithPath() {
        if (mState == DriveState.PATH_FOLLOWING && mPathFollower != null) {
            return mPathFollower.isFinished();
        } else {
            //System.out.printlnln("Robot is not in path following mode");
            return true;
        }
    }

    public synchronized void setGyroAngle(Rotation2d angle) {
        mNavX.reset();
        mNavX.setAngleAdjustment(angle);
    }

    public synchronized boolean hasPassedMarker(String marker) {
        if (mState == DriveState.PATH_FOLLOWING && mPathFollower != null) {
            return mPathFollower.hasPassedMarker(marker);
        } else {
            //System.out.printlnln("Robot is not in path following mode");
            return false;
        }
    }

    public synchronized void setVelocity(double left, double right) {
        mState = DriveState.VELOCITY;
        mLeftMaster.setNeutralMode(NeutralMode.Brake);
        mRightMaster.setNeutralMode(NeutralMode.Brake);
        mPeriodicIO.wanted_left_demand = left;
        mPeriodicIO.wanted_right_demand = right;
        //System.out.printlnln("wanted speed: " + left + "\t" + right);
    }

    public synchronized void zeroGyro() {
        mNavX.reset();
    }

    public synchronized void zeroEncoders(){
        mLeftMaster.setSelectedSensorPosition(0);
        mRightMaster.setSelectedSensorPosition(0);
    }

    public synchronized void motionMagic(double leftRotations, double rightRotations, double leftAccel,
                                         double rightAccel, double leftSpeed, double rightSpeed) {

        mState = DriveState.MOTION_MAGIC;

        double topspeed = 4350;//TODO tune?

        mLeft_target = leftRotations * Constants.kDrivetrain.ENCODER_TICKS_PER_ROTATION;
        mRight_target = -rightRotations * Constants.kDrivetrain.ENCODER_TICKS_PER_ROTATION;

        mPercentageComplete = Math
                    .abs(((mPeriodicIO.left_encoder_ticks / mLeft_target) + (mPeriodicIO.right_encoder_ticks / mRight_target)) / 2);

        mLeftMaster.configMotionAcceleration((int) (topspeed * leftAccel), 10);
        mRightMaster.configMotionAcceleration((int) (topspeed * rightAccel), 10);

        mLeftMaster.configMotionCruiseVelocity((int) (topspeed * leftSpeed), 10);
        mRightMaster.configMotionCruiseVelocity((int) (topspeed * rightSpeed), 10);

        mLeftMaster.setNeutralMode(NeutralMode.Brake);
        mRightMaster.setNeutralMode(NeutralMode.Brake);

        mPeriodicIO.wanted_left_demand = mLeft_target;
        mPeriodicIO.wanted_right_demand = mRight_target;
    }

    private void updatePathFollower() {
        RigidTransform2d robot_pose = mRobotState.getLatestFieldToVehicle().getValue();
        Twist2d command = mPathFollower.update(Timer.getFPGATimestamp(), robot_pose,
                RobotState.getInstance().getDistanceDriven(), RobotState.getInstance().getPredictedVelocity().dx);

        if (!mPathFollower.isFinished()) {
            Kinematics.DriveVelocity setpoint = Kinematics.inverseKinematics(command);
            updateVelocitySetpoint(setpoint.left, setpoint.right);
        } else {
            updateVelocitySetpoint(0, 0);
        }
    }

    private synchronized void updateVelocitySetpoint(double left_inches_per_sec, double right_inches_per_sec) {
        final double max_desired = Math.max(Math.abs(left_inches_per_sec), Math.abs(right_inches_per_sec));
        final double scale = max_desired > Constants.kDriveMaxSetpoint
                ? Constants.kDriveMaxSetpoint / max_desired
                : 1.0;
        mPeriodicIO.left_demand = rpmToTicksPer100ms((inchesPerSecondToRpm(left_inches_per_sec * scale)));
        mPeriodicIO.right_demand = -rpmToTicksPer100ms(inchesPerSecondToRpm(right_inches_per_sec * scale));
        //System.out.printlnln(mPeriodicIO.left_demand + ", " + mPeriodicIO.right_demand);
    }



    @Override
    public synchronized void readPeriodicInputs(){
        if(mState != DriveState.PATH_FOLLOWING){
            mPeriodicIO.left_demand = mPeriodicIO.wanted_left_demand;
            mPeriodicIO.right_demand = mPeriodicIO.wanted_right_demand;
        }
        mPeriodicIO.left_encoder_ticks = (double) mLeftMaster.getSelectedSensorPosition(0);
        mPeriodicIO.left_encoder_vel = (double) mLeftMaster.getSelectedSensorVelocity(0);
        mPeriodicIO.right_encoder_ticks = (double) mRightMaster.getSelectedSensorPosition(0);
        mPeriodicIO.right_encoder_vel = (double) mRightMaster.getSelectedSensorVelocity(0);
//        if(!DriverStation.getInstance().isDisabled())
            //System.out.printlnln("Current speed: " + mPeriodicIO.left_encoder_vel + ", " + mPeriodicIO.right_encoder_vel);
    }

    @Override
    public synchronized void writePeriodicOutputs(){
        //if(!DriverStation.getInstance().isDisabled())
            //System.out.printlnln(mRobotState.getLatestFieldToVehicle().getValue());

        switch(mState){
            case NEUTRAL:
                mLeftMaster.set(ControlMode.PercentOutput, 0);
                mRightMaster.set(ControlMode.PercentOutput, 0);
                break;
            case OPEN_LOOP:
                mLeftMaster.set(ControlMode.PercentOutput, mPeriodicIO.left_demand);
                mRightMaster.set(ControlMode.PercentOutput, -mPeriodicIO.right_demand);
//                if(!DriverStation.getInstance().isDisabled()){
//                    //System.out.printlnln(inchesPerSecondToRpm(mPeriodicIO.left_encoder_vel * (1/Constants.kDrivetrain.ENCODER_TICKS_PER_ROTATION) * Constants.kDrivetrain.WHEEL_DIAMETER_IN * Math.PI));
//                }
                break;
            case PATH_FOLLOWING:
                if(mPathFollower != null){
                    updatePathFollower();
                }
                //System.out.printlnln("following path");
                SmartDashboard.putString("Velocity", Double.toString(mPeriodicIO.left_demand) + Double.toString(mPeriodicIO.right_demand));
                mLeftMaster.set(ControlMode.Velocity, mPeriodicIO.left_demand);
                mRightMaster.set(ControlMode.Velocity, mPeriodicIO.right_demand);
                break;
            case VELOCITY:
                mLeftMaster.set(ControlMode.Velocity, mPeriodicIO.left_demand);
                mRightMaster.set(ControlMode.Velocity, -mPeriodicIO.right_demand);
                break;
        }
    }

    public static class PeriodicIO{
        //INPUTS
        public double left_encoder_ticks = 0;
        public double right_encoder_ticks = 0;
        public double wanted_left_demand = 0;
        public double wanted_right_demand = 0;
        public double right_encoder_vel = 0;
        public double left_encoder_vel = 0;
        //OUTPUTS
        public double left_demand = 0;
        public double right_demand = 0;
    }

    private enum DriveState {
        OPEN_LOOP,
        NEUTRAL,
        PATH_FOLLOWING,
        MOTION_MAGIC,
        VELOCITY
    }

}
