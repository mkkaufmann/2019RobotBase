package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.lib.GenericPWMSpeedController;
import frc.robot.loops.ILooper;

public class Projector extends Subsystem {


    private static Projector mInstance = null;
    private final GenericPWMSpeedController mMaster;
    private static ProjectorPosition mTargetPosition = ProjectorPosition.NEUTRAL;

    private Projector(){
        mMaster = new GenericPWMSpeedController(Constants.kProjector.masterPort);
    }

    public static synchronized Projector getInstance(){
        if(mInstance == null){
            mInstance = new Projector();
        }
        return mInstance;
    }

    public enum ProjectorPosition {
        STOW(1),
        SCORE(-1),
        NEUTRAL(0);

        public double value;
        ProjectorPosition(double value){
            this.value = value;
        }
    }

    @Override
    public void zeroMechanism(){
        setTargetPosition(ProjectorPosition.SCORE);
    }

    //only called when holding position (i.e. previous position is current position)
    public synchronized void setTargetPosition(ProjectorPosition wantedTargetPosition){
        mTargetPosition = wantedTargetPosition;
    }

    @Override
    public synchronized void writePeriodicOutputs(){
        mMaster.set(mTargetPosition.value);
    }

    public ProjectorPosition getPosition(){
        return mTargetPosition;
    }


    @Override
    public void stop() {
        mMaster.set(0);
    }

    @Override
    public void registerEnabledLoops(ILooper enabledLooper) {
    }

    @Override
    public void outputTelemetry() {

    }
}
