package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.Constants;
import frc.robot.lib.DriveSignal;

public class Drive extends Subsystem{

    private static Drive mInstance = null;
    private TalonSRX mLeftMaster;
    private TalonSRX mRightMaster;
    private VictorSPX mLeftFollower;
    private VictorSPX mRightFollower;
    private PeriodicIO mPeriodicIO = new PeriodicIO();
    private DriveControlState mState = DriveControlState.NEUTRAL;

    private Drive(){
        mLeftMaster = new TalonSRX(Constants.kDrivetrain.leftMasterID);
        mRightMaster = new TalonSRX(Constants.kDrivetrain.rightMasterID);
        mLeftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 100);
        mRightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 100);

        mLeftFollower = new VictorSPX(Constants.kDrivetrain.leftFollowerID);
        mRightFollower = new VictorSPX(Constants.kDrivetrain.rightFollowerID);
        mLeftFollower.follow(mLeftMaster);
        mRightFollower.follow(mRightMaster);
    }

    public static Drive getInstance(){
        if(mInstance == null){
            mInstance = new Drive();
        }
        return mInstance;
    }

    @Override
    public void stop() {
        setOpenLoop(0,0);
    }

    @Override
    public void zeroMechanism() {

    }

    @Override
    public void outputTelemetry() {

    }

    public synchronized void setOpenLoop(double left, double right){
        if(mState != DriveControlState.OPEN_LOOP){
            mLeftMaster.setNeutralMode(NeutralMode.Coast);
            mState = DriveControlState.OPEN_LOOP;
            mLeftMaster.configNeutralDeadband(0.04, 0);
            mRightMaster.configNeutralDeadband(0.04, 0);
        }
        mPeriodicIO.wanted_left_demand = left;
        mPeriodicIO.wanted_right_demand = right;
    }

    public synchronized void setOpenLoop(DriveSignal signal){
        setOpenLoop(signal.getLeft(), signal.getRight());
    }

    @Override
    public synchronized void readPeriodicInputs(){
        mPeriodicIO.left_demand = mPeriodicIO.wanted_left_demand;
        mPeriodicIO.right_demand = mPeriodicIO.wanted_right_demand;
    }

    @Override
    public synchronized void writePeriodicOutputs(){
        switch(mState){
            case NEUTRAL:
                mLeftMaster.set(ControlMode.PercentOutput, 0);
                mRightMaster.set(ControlMode.PercentOutput, 0);
                break;
            case OPEN_LOOP:
                mLeftMaster.set(ControlMode.PercentOutput, mPeriodicIO.left_demand);
                mRightMaster.set(ControlMode.PercentOutput, mPeriodicIO.right_demand);
                break;
        }
    }

    public static class PeriodicIO{
        //INPUTS
        public double wanted_left_demand = 0;
        public double wanted_right_demand = 0;
        //OUTPUTS
        public double left_demand = 0;
        public double right_demand = 0;
    }

    //TODO add motion profiles
    private enum DriveControlState{
        OPEN_LOOP,
        NEUTRAL
    }

}
