package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import frc.robot.Constants;
import frc.robot.lib.GenericPWMSpeedController;
import frc.robot.lib.Util;
import frc.robot.states.SuperstructureConstants;

public class Strafe extends Subsystem {

    private static Strafe mInstance = null;
    private GenericPWMSpeedController mMaster;
    private Encoder mEncoder;
    private DigitalInput mLimitSwitch;//on left
    private static double kEncoderTicksPerInch = 0;//TODO Tune!!!
    private static double kTargetThreshold = 0.5 * kEncoderTicksPerInch;
    private static double kSpeed = 0.5;//TODO stagger speeds
    private StrafeState mState = StrafeState.HOLDING_POSITION;
    private PeriodicIO mPeriodicIO = new PeriodicIO();

    private Strafe(){
        mMaster = new GenericPWMSpeedController(Constants.kStrafe.masterPort);
        mEncoder = new Encoder(Constants.kStrafe.encoderPort1, Constants.kStrafe.encoderPort2);
        mLimitSwitch = new DigitalInput(Constants.kStrafe.limitPort);
    }

    public static Strafe getInstance(){
        if(mInstance == null){
            mInstance = new Strafe();
        }
        return mInstance;
    }

    @Override
    public void stop() {
        mMaster.set(0);
    }

    @Override
    public synchronized void zeroMechanism() {
        setSetpoint(Integer.MIN_VALUE);
    }

    @Override
    public void outputTelemetry() {

    }

    public synchronized void setSetpoint(double demand){
        mPeriodicIO.demand = demand;
        if(Util.epsilonEquals(mPeriodicIO.position_ticks, mPeriodicIO.demand, kTargetThreshold)){
            mState = StrafeState.HOLDING_POSITION;
            return;
        }
        mState = StrafeState.MOVING_TO_POSITION;
    }

    @Override
    public synchronized void readPeriodicInputs(){
        mPeriodicIO.position_ticks = mEncoder.get();
    }

    @Override
    public synchronized void writePeriodicOutputs(){
        if(mLimitSwitch.get()){
            mEncoder.reset();
        }
        if(mState == StrafeState.HOLDING_POSITION){
            return;
        }
        double direction = mPeriodicIO.position_ticks > mPeriodicIO.demand ? -1 : 1;
        if(mLimitSwitch.get() || mPeriodicIO.position_ticks >= SuperstructureConstants.kStrafeMaxEncoderValue || Util.epsilonEquals(mPeriodicIO.position_ticks, mPeriodicIO.demand, kTargetThreshold)){
            mState = StrafeState.HOLDING_POSITION;
        }
        mMaster.set(kSpeed * direction);
    }

    private static class PeriodicIO{
        //INPUT
        public double position_ticks = 0;
        //OUTPUT
        public double demand = 0;//ticks
    }

    private enum StrafeState{
        MOVING_TO_POSITION,
        HOLDING_POSITION
    }

}
