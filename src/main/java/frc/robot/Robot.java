/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.drive.DriveAtVelocityForTime;
import frc.robot.commands.drive.EncoderDrive;
import frc.robot.commands.drive.pathfollowing.DrivePath;
import frc.robot.commands.drive.pathfollowing.ResetPoseDrivePath;
import frc.robot.commands.drive.pathfollowing.ResetPoseFromPath;
import frc.robot.commands.paths.*;
import frc.robot.controlboard.ControlBoard;
import frc.robot.lib.CheesyDriveHelper;
import frc.robot.lib.GenericPWMSpeedController;
import frc.robot.lib.LatchedBoolean;
import frc.robot.lib.Util;
import frc.robot.loops.Looper;
import frc.robot.states.SuperstructureConstants;
import frc.robot.subsystems.*;

import java.util.Arrays;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
//TODO add vision and cameras -> Auto align, auto pickup, auto line up, auto grab cargo
//TODO restrict mechanisms before climb
//TODO implement toggled controls
public class Robot extends TimedRobot {
    private static final String kDefaultAuto = "Default";
    private static final String kCustomAuto = "My Auto";
    private String m_autoSelected;
    private final SendableChooser<String> m_chooser = new SendableChooser<>();
    private Looper mEnabledLooper = new Looper();
    private Looper mDisabledLooper = new Looper();
    private Looper mElevatorLooper = new Looper();

    private Drive mDrive = Drive.getInstance();
    private CheesyDriveHelper mCheesyDriveHelper = new CheesyDriveHelper();
    private ControlBoard mControlBoard = new ControlBoard();
    private Arm mArm = Arm.getInstance();
    private Claw mClaw = Claw.getInstance();
    private Climber mClimber = Climber.getInstance();
    private Elevator mElevator = Elevator.getInstance();
    private Mouth mMouth = Mouth.getInstance();
    private Strafe mStrafe = Strafe.getInstance();
    private Superstructure mSuperStructure = Superstructure.getInstance();
    private Timer timer = new Timer();
    private boolean shortRumble = false;
    private boolean longRumble = false;

    private LatchedBoolean goToHighHeight = new LatchedBoolean();
    private LatchedBoolean goToNeutralHeight = new LatchedBoolean();
    private LatchedBoolean goToLowHeight = new LatchedBoolean();
    private LatchedBoolean goToCargoShipCargoHeight = new LatchedBoolean();
    private LatchedBoolean hatchOrCargo = new LatchedBoolean();
    private LatchedBoolean armToggle = new LatchedBoolean();
    private LatchedBoolean armToStart = new LatchedBoolean();
    private LatchedBoolean clawToggle = new LatchedBoolean();
    private LatchedBoolean runIntake = new LatchedBoolean();
    private LatchedBoolean enableClimbMode = new LatchedBoolean();
    private LatchedBoolean centerStrafe = new LatchedBoolean();
    private GenericPWMSpeedController elevator = new GenericPWMSpeedController(4);//TODO

    private Command command;


    private final SubsystemManager mSubsystemManager = new SubsystemManager(
            Arrays.asList(
                    Drive.getInstance(),
                    Superstructure.getInstance(),
                    //Elevator.getInstance(),
                    Claw.getInstance(),
                    Arm.getInstance(),
                    Climber.getInstance(),
                    Mouth.getInstance(),
                    //Strafe.getInstance(),
                    Dashboard.getInstance(),
                    RobotStateEstimator.getInstance())
    );

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
        m_chooser.addOption("My Auto", kCustomAuto);
        SmartDashboard.putData("Auto choices", m_chooser);

        mSubsystemManager.registerEnabledLoops(mEnabledLooper);
        mSubsystemManager.registerDisabledLoops(mDisabledLooper);
        mElevator.registerEnabledLoops(mElevatorLooper);
//        mElevatorLooper.start();
    }



    @Override
    public void disabledInit() {
        mEnabledLooper.stop();
        mDisabledLooper.start();
        mSubsystemManager.stop();
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before
     * LiveWindow and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
//        mElevator.readPeriodicInputs();
//        mElevator.writePeriodicOutputs();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString line to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional comparisons to
     * the switch structure below with additional strings. If using the
     * SendableChooser make sure to add them to the chooser code above as well.
     */
    @Override
    public void autonomousInit() {
        m_autoSelected = m_chooser.getSelected();
        mSuperStructure.setMode(Dashboard.getIsHatchMode() ? Superstructure.MechanismMode.HATCH : Superstructure.MechanismMode.CARGO);
        mEnabledLooper.start();
        mDisabledLooper.stop();
        // autoSelected = SmartDashboard.getString("Auto Selector",
        // defaultAuto);
        command = new ResetPoseDrivePath(new TestPath());


        //System.out.printlnln("Auto selected: " + m_autoSelected);
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        if(!command.isRunning()){
            //System.out.printlnln("is not running");
            command.start();
        }
        Scheduler.getInstance().run();
        switch (m_autoSelected) {
            case kCustomAuto:
                // Put custom auto code here
                break;
            case kDefaultAuto:
            default:
                // Put default auto code here
                break;
        }
    }

    @Override
    public void teleopInit() {
        mDisabledLooper.stop();
        mEnabledLooper.start();
    }

    /**
     * This function is called periodically during operator control.
     * TODO fix claw in ball mode
     */
    @Override
    public void teleopPeriodic() {

        mDrive.setOpenLoop(mCheesyDriveHelper.cheesyDrive(mControlBoard.getThrottle(), mControlBoard.getTurn(),
                mControlBoard.getQuickTurn() || Util.deadband(mControlBoard.getThrottle()) == 0));

        if(armToStart.update(mControlBoard.getArmToStart())){
            mArm.setWantedTargetPosition(Arm.ArmPosition.START);
        }else if(armToggle.update(mControlBoard.getArmToggle())){
            mArm.toggleTargetPosition();
        }

        if(enableClimbMode.update(mControlBoard.getEnableClimbMode())){
            mClimber.toggleState();
            //System.out.printlnln("climb toggled");
        }
        Strafe.getInstance().getMaster().set(mControlBoard.getStrafeThrottle());
        if(mClimber.getState() == Climber.ClimberState.PERCENT_OUTPUT){
            mClimber.setOutput(mControlBoard.getClimberThrottle());
        }else{
            if(Math.abs(mControlBoard.getStrafeThrottle()) > 0){
                mStrafe.setSpeed(mControlBoard.getStrafeThrottle());
            }
            if(centerStrafe.update(mControlBoard.getCenterStrafe())){
                mStrafe.setSetpoint(SuperstructureConstants.kStrafeMidEncoderValue);//TODO implement vision
            }
        }

        if(hatchOrCargo.update(mControlBoard.getHatchOrCargo())){
            System.out.println("hatch or cargo");
            mSuperStructure.toggleMode();//TODO move to superstructure

            timer.reset();
            timer.start();
            shortRumble = true;
            if(mSuperStructure.getMode() == Superstructure.MechanismMode.HATCH){
                mControlBoard.setButtonRumble(false, true);
            }else{
                mControlBoard.setButtonRumble(true, false);
            }
        }

        if(Timer.getMatchTime() < 30 && Timer.getMatchTime() > 29 && !longRumble){
            longRumble = true;
            mControlBoard.setRumble(true);
        }

        if(timer.hasPeriodPassed(0.5) && shortRumble){
            shortRumble = false;
            mControlBoard.setButtonRumble(false);
        }
        if(timer.hasPeriodPassed(1) && longRumble){
            longRumble = false;
            mControlBoard.setRumble(false);
        }

        if(mSuperStructure.getMode() == Superstructure.MechanismMode.HATCH){
            if(clawToggle.update(mControlBoard.getClawToggle())){
                mClaw.toggleState();
            }

            if(goToLowHeight.update(mControlBoard.getGoToLowHeight())){
                mElevator.setPositionPID(SuperstructureConstants.kRocketHatchLow);
            }else if(goToNeutralHeight.update(mControlBoard.getGoToNeutralHeight())){
                mElevator.setPositionPID(SuperstructureConstants.kRocketHatchMiddle);
            }else if(goToHighHeight.update(mControlBoard.getGoToHighHeight())){
                mElevator.setPositionPID(SuperstructureConstants.kRocketHatchHigh);
            }

        }else {
            if (mControlBoard.getShootSpeed() > 0) {
                //System.out.printlnln("shooting");
                mMouth.setState(Mouth.MouthState.OUTTAKE);
                mMouth.setSpeed(mControlBoard.getShootSpeed());
            }else if(mMouth.getState() == Mouth.MouthState.OUTTAKE){
                mMouth.setState(Mouth.MouthState.NEUTRAL_CARGO);
            }
            if (runIntake.update(mControlBoard.getRunIntake())) {
                System.out.println("toggled");
                mMouth.toggleIntake();
            }
            if (goToCargoShipCargoHeight.update(mControlBoard.getGoToCargoShipCargoHeight())) {
                mElevator.setPositionPID(SuperstructureConstants.kCargoShipCargo);
            }
            if(goToLowHeight.update(mControlBoard.getGoToLowHeight())){
                mElevator.setPositionPID(SuperstructureConstants.kRocketCargoLow);
            }else if(goToNeutralHeight.update(mControlBoard.getGoToNeutralHeight())){
                mElevator.setPositionPID(SuperstructureConstants.kRocketCargoMiddle);
            }else if(goToHighHeight.update(mControlBoard.getGoToHighHeight())){
                mElevator.setPositionPID(SuperstructureConstants.kRocketCargoHigh);
            }
        }


//        if(Math.abs(mControlBoard.getElevatorThrottle()) > 0){
//            mElevator.setOpenLoop(mControlBoard.getElevatorThrottle());
//        }else if(mElevator.getState() == Elevator.ElevatorState.OPEN_LOOP){
//            mElevator.setOpenLoop(0);
//        }
        elevator.set(-Util.deadband(mControlBoard.getElevatorThrottle()));
//        mElevator.getMaster().set(ControlMode.PercentOutput,-Util.deadband(mControlBoard.getElevatorThrottle()));
    }

    @Override
    public void testInit() {
        mEnabledLooper.stop();
        mDisabledLooper.stop();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }
}
