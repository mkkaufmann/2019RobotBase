package frc.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import frc.robot.Constants;
import frc.robot.lib.GenericPWMSpeedController;

public class Strafe extends Subsystem {

    private static Strafe mInstance = null;
    private GenericPWMSpeedController mMaster;
    private PeriodicIO mPeriodicIO = new PeriodicIO();

    private AnalogInput input = new AnalogInput(Constants.kStrafe.mPotPort);
    private double factor = 1.29;
    private double fullRange = 26.25 * factor;
    private double offset = 0.19 * fullRange + 14.9;//positive is right
    private Potentiometer pot = new AnalogPotentiometer(input,fullRange,offset);

    private StrafePID pid = new StrafePID();

    private class StrafePID extends PIDSubsystem {

        public StrafePID(){
//            super("Strafe", 0.25,0.01,0.1);//I 0.01?
            super("Strafe", 0.25,0.0005,0.0);
            getPIDController().setContinuous(false);
        }

        @Override
        protected double returnPIDInput() {
            return getPotPos();
        }

        @Override
        protected void usePIDOutput(double output) {
            mMaster.pidWrite(-output);
        }

        @Override
        protected void initDefaultCommand() {
        }
    }

    public ControlState getControlState() {
        return mControlState;
    }

    private ControlState mControlState = ControlState.MANUAL_POSITION;

    private Strafe(){
        mMaster = new GenericPWMSpeedController(Constants.kStrafe.masterPort);
    }

    public static Strafe getInstance(){
        if(mInstance == null){
            mInstance = new Strafe();
        }
        return mInstance;
    }

    public boolean getPotIsBroken(){
        return getPotPos() < -6 || getPotPos() > 6;
    }

    public double getPotPos(){
        return mPeriodicIO.potPos;
    }

    public GenericPWMSpeedController getMaster() {
        return mMaster;
    }

    @Override
    public void stop() {
        setManual(0);
    }

    @Override
    public synchronized void zeroMechanism() {
        setVision();
    }

    @Override
    public void outputTelemetry() {

    }

    public synchronized void setManual(double demand){
        if(getPotIsBroken()){
            mControlState = ControlState.PERCENT_OUTPUT;
            pid.disable();
        }else{
            mControlState = ControlState.MANUAL_POSITION;
            pid.enable();
        }
        mPeriodicIO.demand = -demand;
    }

    public synchronized void setVision(){
        if (getPotIsBroken()) {
            setNeutral();
        }else {
            mControlState = ControlState.VISION;
            pid.enable();
        }
    }

    public synchronized void setNeutral(){
        mControlState = ControlState.NEUTRAL;
        pid.disable();
    }

    @Override
    public synchronized void readPeriodicInputs(){
        mPeriodicIO.potPos = Math.floor((fullRange - pot.get())*100)/100.0;
    }

    @Override
    public synchronized void writePeriodicOutputs(){
        System.out.println("pot:" + mPeriodicIO.potPos);

        switch(mControlState){
            case VISION:
                pid.setSetpoint(Math.min(Math.max(Vision.Tape.getXOffsetInches(),-5),5));
                //mMaster.set(0);
                break;
            case MANUAL_POSITION:
                pid.setSetpoint(mPeriodicIO.demand * -5);
                break;
            case PERCENT_OUTPUT:
                mMaster.set(mPeriodicIO.demand);
                break;
            case NEUTRAL:
                mMaster.set(0);
        }
    }

    private static class PeriodicIO{
        //INPUT
        public double potPos = 0;
        //OUTPUT
        public double demand = 0;//ticks in position mode, speed otherwise;
    }

    public enum ControlState{
        MANUAL_POSITION,
        VISION,
        NEUTRAL,
        PERCENT_OUTPUT
    }
}
