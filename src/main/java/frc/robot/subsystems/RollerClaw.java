package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import frc.robot.Constants;
import frc.robot.lib.GenericPWMSpeedController;
import frc.robot.lib.Util;

public class RollerClaw extends Subsystem {

    private static RollerClaw mInstance = null;
    private ClawState mState;
    private GenericPWMSpeedController mMaster;

    private RollerClaw(){
        mMaster = new GenericPWMSpeedController(Constants.kClaw.servoPort);
        mState = ClawState.NEUTRAL;
    }

//    public synchronized void toggleState(){
//
//        //System.out.printlnln("toggled");
//    }

    public synchronized void setState(ClawState mState) {
        this.mState = mState;
    }

    public synchronized ClawState getState(){
        return mState;
    }

    public static RollerClaw getInstance(){
        if(mInstance == null){
            mInstance = new RollerClaw();
        }
        return mInstance;
    }

    @Override
    public void stop() {
        setState(ClawState.NEUTRAL);
    }

    @Override
    public void zeroMechanism() {

    }

    @Override
    public void outputTelemetry() {

    }


    @Override
    public synchronized void writePeriodicOutputs(){
        mMaster.set(-mState.value);
    }

    public enum ClawState{
        IN(-1),
        OUT(1),
        HOLDING(-0.5),
        NEUTRAL(0);

        public double value;

        ClawState(double value){
            this.value = value;
        }
    }

}
