package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;

//TODO add feedforward, motion magic?
public class Elevator extends Subsystem {

    private static Elevator mInstance = null;
    private TalonSRX mMaster;
    private ElevatorState mState = ElevatorState.OPEN_LOOP;
    private PeriodicIO mPeriodicIO = new PeriodicIO();
    private static double kEncoderTicksPerInch = 0;//TODO TUNE ME!!!!!!

    private Elevator(){
        mMaster = new TalonSRX(Constants.kElevator.masterID);
        System.out.println("elevator started");
        mMaster.config_kP(0, 0.05);
        mMaster.config_kI(0,0);
        mMaster.config_kD(0,0);
        //mMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0, 100);
        //TODO config limit switch(es)
        //TODO config PID
        mMaster.setNeutralMode(NeutralMode.Brake);
    }

    public static Elevator getInstance(){
        if(mInstance == null){
            mInstance = new Elevator();
        }
        return mInstance;
    }

    public ElevatorState getState() {
        return mState;
    }

    @Override
    public void stop() {
        setOpenLoop(0);
    }

    @Override
    public synchronized void zeroMechanism() {
        //TODO drive downward and reset encoder
        //setOpenLoop(0.7);
    }

    @Override
    public void outputTelemetry() {

    }

    public synchronized double getInchesFromBottom(){
        return mPeriodicIO.position_ticks / kEncoderTicksPerInch;
    }

    //TODO check that it is aligned properly to zero
    public synchronized void setPositionPID(double inchesFromBottom){
        double encoderPosition = inchesFromBottom * kEncoderTicksPerInch;
        if(mState != ElevatorState.POSITION){
            mState = ElevatorState.POSITION;
            //TODO profile slot
        }
        mPeriodicIO.demand = encoderPosition;
    }

    public synchronized void setOpenLoop(double demand){
        mState = ElevatorState.OPEN_LOOP;
        mPeriodicIO.demand = demand;
    }

    @Override
    public synchronized void readPeriodicInputs() {
        mPeriodicIO.position_ticks = mMaster.getSelectedSensorPosition(0);
    }

    @Override
    public synchronized void writePeriodicOutputs() {
        switch(mState){
            case OPEN_LOOP:
                mMaster.set(ControlMode.PercentOutput, mPeriodicIO.demand);
                break;
            case POSITION:
                mMaster.set(ControlMode.Position, mPeriodicIO.demand);
                break;
        }
    }

    public static class PeriodicIO{
        //INPUTS
        public int position_ticks = 0;
//        public boolean limit_switch = false;
        //OUTPUTS
        public double demand = 0;
    }

    public enum ElevatorState{
        OPEN_LOOP,
        POSITION
    }
}
