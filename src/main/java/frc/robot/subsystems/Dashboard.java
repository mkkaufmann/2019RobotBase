package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard extends Subsystem {

    private static Dashboard mInstance = null;

    public static Dashboard getInstance(){
        if(mInstance == null){
            mInstance = new Dashboard();
        }
        return mInstance;
    }

    @Override
    public synchronized void readPeriodicInputs(){

    }

    @Override
    public synchronized void writePeriodicOutputs(){
        SmartDashboard.putNumber("Match_Time", Timer.getFPGATimestamp());
        SmartDashboard.putNumber("Battery_Voltage", RobotController.getBatteryVoltage());
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


}
