package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;

public class Elevator extends Subsystem{
    private static Elevator mInstance = null;
    private static TalonSRX mMaster;
    private ElevatorState mState = ElevatorState.OPEN_LOOP;
    private PeriodicIO mPeriodicIO = new PeriodicIO();

    private Elevator(){
        mMaster = new TalonSRX(Constants.kElevator.masterID);
        mMaster.configFactoryDefault();

        mMaster.setInverted(true);
        mMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 100);
        mMaster.setSensorPhase(true);
        mMaster.configMotionCruiseVelocity(7350);
        mMaster.configMotionAcceleration(7350);
        mMaster.config_kP(0, 0.5,10);
        mMaster.config_kI(0, 0,10);
        mMaster.config_kD(0, 0.2,10);
//        mMaster.config_kF(0, 0,10);
        mMaster.setNeutralMode(NeutralMode.Brake);
    }

    public ElevatorState getState() {
        return mState;
    }

    public static Elevator getInstance(){
        if(mInstance == null){
            mInstance = new Elevator();
        }
        return mInstance;
    }

    public synchronized double getInchesFromBottom(){
        return -(double)mPeriodicIO.encoder_position_ticks / Constants.kElevator.ENCODER_TICKS_PER_INCH;
    }

    public void jog(double inches){
        setMotionMagic(getInchesFromBottom() + inches);
    }

    public void setMotionMagic(double demandInches){
        mState = ElevatorState.MOTION_MAGIC;
        mPeriodicIO.demand = demandInches  * Constants.kElevator.ENCODER_TICKS_PER_INCH;
    }

    public void setOpenLoop(double demand){
        mState = ElevatorState.OPEN_LOOP;
        mPeriodicIO.demand = -demand;
    }

    @Override
    public void readPeriodicInputs(){
        mPeriodicIO.encoder_position_ticks = mMaster.getSelectedSensorPosition();
        mPeriodicIO.encoder_velocity_ticks = mMaster.getSelectedSensorVelocity();

        //System.out.println("Elevator Encoder: " + mPeriodicIO.encoder_position_ticks);
    }

    @Override
    public void writePeriodicOutputs(){
        switch(mState){
            case OPEN_LOOP:
                double demand = mPeriodicIO.demand;
                if((getInchesFromBottom() < 4 && demand > 0)||(getInchesFromBottom() > 73.5 && demand < 0)){
                    demand /= 10;
                }
                mMaster.set(ControlMode.PercentOutput, demand);
                break;
            case POSITION:
                mMaster.set(ControlMode.Position, mPeriodicIO.demand);
                break;
            case MOTION_MAGIC:
                //System.out.println(mPeriodicIO.demand);
                mMaster.set(ControlMode.MotionMagic, -mPeriodicIO.demand);
                break;
        }
    }

    @Override
    public void stop() {
        setOpenLoop(0);
    }

    @Override
    public void zeroMechanism() {

    }

    @Override
    public void outputTelemetry() {
    }

    public class PeriodicIO{
        public double demand;

        public int encoder_position_ticks;
        public int encoder_velocity_ticks;
    }

    public enum ElevatorState{
        OPEN_LOOP,
        POSITION,
        MOTION_MAGIC
    }
}
