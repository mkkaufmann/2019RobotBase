package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;

public class Elevator2 extends Subsystem{
    private static Elevator2 mInstance = null;
    private static TalonSRX mMaster;
    private ElevatorState mState = ElevatorState.OPEN_LOOP;
    private PeriodicIO mPeriodicIO = new PeriodicIO();

    private Elevator2(){
        mMaster = new TalonSRX(Constants.kElevator.masterID);
        mMaster.configFactoryDefault();
        mMaster.setNeutralMode(NeutralMode.Brake);
    }

    public static Elevator2 getInstance(){
        if(mInstance == null){
            mInstance = new Elevator2();
        }
        return mInstance;
    }

    @Override
    public void readPeriodicInputs(){
        mPeriodicIO.encoder_position_ticks = mMaster.getSelectedSensorPosition();
        mPeriodicIO.encoder_velocity_ticks = mMaster.getSelectedSensorVelocity();
    }

    @Override
    public void writePeriodicOutputs(){
        switch(mState){
            case OPEN_LOOP:
                mMaster.set(ControlMode.PercentOutput, mPeriodicIO.demand);
                break;
            case POSITION:
                mMaster.set(ControlMode.Position, mPeriodicIO.demand);
                break;
            case MOTION_MAGIC:
                mMaster.set(ControlMode.MotionMagic, mPeriodicIO.demand);
                break;
        }
    }

    @Override
    public void stop() {

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
