package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.lib.GenericPWMSpeedController;

public class Vision extends Subsystem {

    private static Vision mInstance = null;

    private Vision(){

    }

    public static Vision getInstance(){
        if(mInstance == null){
            mInstance = new Vision();
        }
        return mInstance;
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

    @Override
    public synchronized void readPeriodicInputs(){
        Tape.yaw = (int)SmartDashboard.getNumber("Vision/tapeYaw", 0);
        Tape.centerX = (int)SmartDashboard.getNumber("Vision/targetCenterX", 0);
        Tape.centerY = (int)SmartDashboard.getNumber("Vision/targetCenterY", 0);
        Tape.pitch = (int)SmartDashboard.getNumber("Vision/targetPitch", 0);
        Tape.distance = Double.parseDouble(SmartDashboard.getString("Vision/targetDistance", "0"));
        Tape.xOffset = Double.parseDouble(SmartDashboard.getString("Vision/targetPixelsFromCenterX", "0"));
        Tape.detected = SmartDashboard.getBoolean("Vision/tapeDetected", false);
    }

    @Override
    public synchronized void writePeriodicOutputs(){

    }

    public static class Camera{
        public static final int WIDTH = 320;
        public static final int HEIGHT = 240;
    }

    public static class Tape{
        public static int yaw = 0;
        public static int centerX = 0;
        public static int centerY = 0;
        public static int pitch = 0;
        public static double distance = 0;
        public static double xOffset = 0;
        public static boolean detected = false;
        public static final double cameraOffset = -6;

        private static final double X_OFFSET_SCALAR = -0.001;

        public static double getXOffsetInches(){
            if(!detected){
                return 0;
            }
            return distance * xOffset * X_OFFSET_SCALAR + cameraOffset;
        }
    }
}
