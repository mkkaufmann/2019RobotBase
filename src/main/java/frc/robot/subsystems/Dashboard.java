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

    private Dashboard(){
        SmartDashboard.putBoolean("isHatchMode", true);
    }

    public static boolean getIsHatchMode(){
        return SmartDashboard.getBoolean("isHatchMode", true);
    }

    @Override
    public synchronized void readPeriodicInputs(){

    }

    @Override
    public synchronized void writePeriodicOutputs(){
        SmartDashboard.putNumber("Match_Time", Timer.getMatchTime());
        SmartDashboard.putBoolean("Claw_Is_Open", Claw.getInstance().getState() == Claw.ClawState.OPEN);
        SmartDashboard.putNumber("Battery_Voltage", RobotController.getBatteryVoltage());
        SmartDashboard.putNumber("Elevator_Height", Elevator.getInstance().getInchesFromBottom());
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
