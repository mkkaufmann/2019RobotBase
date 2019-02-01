package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import frc.robot.Constants;
import frc.robot.lib.Util;

public class Claw extends Subsystem {

    private static Claw mInstance = null;
    private static ClawState mState;
    private Servo mMaster;
    private double kTargetThreshold = 3;

    private Claw(){
        mMaster = new Servo(Constants.kClaw.servoPort);
        mState = mMaster.getAngle() >= ClawState.OPEN.value ? ClawState.OPEN : ClawState.CLOSED;
    }

    public static synchronized void setState(ClawState mState) {
        Claw.mState = mState;
    }

    public static Claw getInstance(){
        if(mInstance == null){
            mInstance = new Claw();
        }
        return mInstance;
    }

    @Override
    public void stop() {
        mMaster.stopMotor();
    }

    @Override
    public void zeroMechanism() {

    }

    @Override
    public void outputTelemetry() {

    }


    @Override
    public synchronized void writePeriodicOutputs(){
        if(Util.epsilonEquals(mMaster.getAngle(), mState.value, kTargetThreshold)){
            mMaster.setAngle(mState.value);
        }
    }

    private enum ClawState{
        CLOSED(60),
        OPEN(120);

        public double value;

        ClawState(double value){
            this.value = value;
        }
    }
}
