/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.lib.CheesyDriveHelper;
import frc.robot.lib.LatchedBoolean;
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

    private LatchedBoolean goToHighHeight = new LatchedBoolean();
    private LatchedBoolean goToNeutralHeight = new LatchedBoolean();
    private LatchedBoolean goToLowHeight = new LatchedBoolean();
    private LatchedBoolean goToCargoShipCargoHeight = new LatchedBoolean();
    private LatchedBoolean hatchorCargo = new LatchedBoolean();
    private LatchedBoolean armToggle = new LatchedBoolean();
    private LatchedBoolean armToStart = new LatchedBoolean();
    private LatchedBoolean clawToggle = new LatchedBoolean();
    private LatchedBoolean runIntake = new LatchedBoolean();
    private LatchedBoolean enableClimbMode = new LatchedBoolean();
    private LatchedBoolean centerStrafe = new LatchedBoolean();


    private final SubsystemManager mSubsystemManager = new SubsystemManager(
            Arrays.asList(
                    Drive.getInstance(),
                    Superstructure.getInstance(),
                    Elevator.getInstance(),
                    Claw.getInstance(),
                    Arm.getInstance(),
                    Climber.getInstance(),
                    Mouth.getInstance(),
                    Strafe.getInstance())
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
    }

    @Override
    public void disabledInit() {
        mEnabledLooper.stop();
        mDisabledLooper.start();
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
        mEnabledLooper.start();
        mDisabledLooper.stop();
        // autoSelected = SmartDashboard.getString("Auto Selector",
        // defaultAuto);
        System.out.println("Auto selected: " + m_autoSelected);
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
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
        mEnabledLooper.start();
        mDisabledLooper.stop();
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        mDrive.setOpenLoop(mCheesyDriveHelper.cheesyDrive(mControlBoard.getThrottle(), mControlBoard.getTurn(), mControlBoard.getQuickTurn()));

        if(armToStart.update(mControlBoard.getArmToStart())){
            mArm.setWantedTargetPosition(Arm.ArmPosition.START);
        }else if(armToggle.update(mControlBoard.getArmToggle())){
            mArm.toggleTargetPosition();
        }

        if(enableClimbMode.update(mControlBoard.getEnableClimbMode())){
            mClimber.toggleState();
        }
        if(mClimber.getState() == Climber.ClimberState.PERCENT_OUTPUT){
            mClimber.setOutput(mControlBoard.getClimberThrottle());
        }else{
            if(mControlBoard.getStrafeThrottle() > 0){
                mStrafe.setSpeed(mControlBoard.getStrafeThrottle());
            }
            if(centerStrafe.update(mControlBoard.getCenterStrafe())){
                mStrafe.setSetpoint(SuperstructureConstants.kStrafeMidEncoderValue);//TODO implement vision
            }
        }

        //TODO move to superstructure
        if(hatchorCargo.update(mControlBoard.getHatchOrCargo())){
            mSuperStructure.toggleMode();
            SmartDashboard.putBoolean("isHatchMode", mSuperStructure.getMode() == Superstructure.MechanismMode.HATCH);
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
                mMouth.setState(Mouth.MouthState.OUTTAKE);
                mMouth.setSpeed(mControlBoard.getShootSpeed());
            }
            if (runIntake.update(mControlBoard.getRunIntake())) {
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

        if(mControlBoard.getElevatorThrottle() > 0){
            mElevator.setOpenLoop(mControlBoard.getClimberThrottle());
        }

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
