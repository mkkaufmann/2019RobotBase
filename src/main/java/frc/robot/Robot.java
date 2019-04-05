/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.NoCommand;
import frc.robot.commands.actions.arm.ScoreArm;
import frc.robot.commands.actions.arm.StowArm;
import frc.robot.commands.actions.claw.ClawHolding;
import frc.robot.commands.actions.claw.ClawIn;
import frc.robot.commands.actions.claw.ClawNeutral;
import frc.robot.commands.actions.claw.ClawOut;
import frc.robot.commands.actions.climber.EnableClimb;
import frc.robot.commands.actions.miscellaneous.RemoveHatch;
import frc.robot.commands.actions.mouth.MouthHolding;
import frc.robot.commands.actions.mouth.MouthIn;
import frc.robot.commands.actions.mouth.MouthNeutral;
import frc.robot.commands.actions.mouth.MouthOut;
import frc.robot.controlboard.ControlBoard;
import frc.robot.lib.*;
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
 * @author Michael Kaufmann
 * @version 2019
 */
//TODO add vision and cameras -> Auto align, auto pickup, auto line up, auto grab cargo
//TODO restrict mechanisms before climb
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
    private Climber mClimber = Climber.getInstance();
    private Elevator mElevator = Elevator.getInstance();
    private Mouth mMouth = Mouth.getInstance();
    private Strafe mStrafe = Strafe.getInstance();
    private Superstructure mSuperStructure = Superstructure.getInstance();
    private Timer timer = new Timer();
    private boolean shortRumble = false;
    private boolean longRumble = false;

    private LatchedBoolean goToLowHatchRocket = new LatchedBoolean();
    private LatchedBoolean goToMidHatchRocket = new LatchedBoolean();
    private LatchedBoolean goToHighHatchRocket = new LatchedBoolean();
    private LatchedBoolean goToLowCargoRocket = new LatchedBoolean();
    private LatchedBoolean goToMidCargoRocket = new LatchedBoolean();
    private LatchedBoolean goToHighCargoRocket = new LatchedBoolean();
    private LatchedBoolean goToCargoShip = new LatchedBoolean();
    private LatchedBoolean jog = new LatchedBoolean();

    private LatchedBoolean removeLeftHatch = new LatchedBoolean();
    private LatchedBoolean removeRightHatch = new LatchedBoolean();
    private LatchedBoolean grabHab = new LatchedBoolean();
    private LatchedBoolean runIntake = new LatchedBoolean();
    private LatchedBoolean enableClimbMode = new LatchedBoolean();
    private LatchedBoolean centerStrafe = new LatchedBoolean();
    private LatchedBoolean armOut = new LatchedBoolean();

    private GState hatchIn = new GState();
    private GState hatchOut = new GState();
    private GState cargoIn = new GState();
    private GState cargoOut = new GState();

    private Command command;

    private final SubsystemManager mSubsystemManager = new SubsystemManager(
            Arrays.asList(
                    Drive.getInstance(),
                    Superstructure.getInstance(),
                    Elevator.getInstance(),
                    Arm.getInstance(),
                    Climber.getInstance(),
                    Mouth.getInstance(),
                    RollerClaw.getInstance(),
                    Vision.getInstance(),
                    Strafe.getInstance(),
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
        CameraServer.getInstance().startAutomaticCapture();
        mClimber.closeSolenoid();
    }


    @Override
    public void disabledInit() {
        mEnabledLooper.stop();
        mDisabledLooper.start();
        mSubsystemManager.stop();
        mClimber.stop();
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
        System.out.println("Vision: "+Vision.Tape.getXOffsetInches());
        System.out.println(mStrafe.getPotPos());
    }

    public static void runCommand(Command command) {
        command.start();
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
//        command = new ResetPoseDrivePath(new Straight_Path());
        command = new NoCommand();
        command.start();
        teleopInit();
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        if (command.isRunning() && Math.abs(Util.deadband(mControlBoard.getThrottle(), 0.2)) > 0 || Math.abs(Util.deadband(mControlBoard.getTurn(), 0.2)) > 0){
            command.cancel();
        }
        if(!command.isRunning()){
            driveManually();
        }
        enabledPeriodic();
    }

    @Override
    public void teleopInit() {
        mDisabledLooper.stop();
        mEnabledLooper.start();
    }

    public void enabledPeriodic(){
//        if(Math.abs(Util.deadband(mControlBoard.getStrafePosition()))>0){
//            mStrafe.setManual(mControlBoard.getStrafePosition()*-5);
//        }else if(mControlBoard.getHoldStrafe()) {
//            mStrafe.setNeutral();
//        }if(!mControlBoard.getStrafeManual()){
//            mStrafe.setVision();
//        }
        mStrafe.setManual(mControlBoard.getStrafePosition());


        if (Timer.getMatchTime() < 30 && Timer.getMatchTime() > 29) {
            mControlBoard.setRumble(true);
        } else {
            mControlBoard.setRumble(false);//TODO this may become a problem if other rumbles are implemented
        }

        if(enableClimbMode.update(mControlBoard.getEnableClimbMode())){
            runCommand(new EnableClimb());
        }

        if (mClimber.getState() == Climber.ClimberState.PERCENT_OUTPUT) {
            mClimber.setOutput(mControlBoard.getClimberThrottle());
            if(grabHab.update(mControlBoard.getGrabHAB())){
                mClimber.openSolenoid();
            }
        }

        GState.StateValues cargoOutState = cargoOut.update(mControlBoard.getHatchOut());
        if (cargoOutState.pressed) {
            runCommand(new MouthOut());
        }
        if(cargoOutState.released){
            runCommand(new MouthNeutral());
        }

        GState.StateValues cargoInState = cargoIn.update(mControlBoard.getHatchIn());
        if (cargoInState.pressed) {
            runCommand(new MouthIn());
        }
        if(cargoInState.released){
            runCommand(new MouthHolding());
        }

        GState.StateValues hatchOutState = hatchOut.update(mControlBoard.getCargoOut() > 0);
        if(hatchOutState.pressed){
            runCommand(new ClawOut());
        }
        if(hatchOutState.released){
            runCommand(new ClawNeutral());
        }

        GState.StateValues hatchInState = hatchIn.update(mControlBoard.getCargoIn());
        if(hatchInState.pressed){
            runCommand(new ClawIn());
        }else if(hatchInState.released){
            runCommand(new ClawHolding());
        }

        if(cargoInState.pressed || cargoOutState.pressed) {
            runCommand(new ClawNeutral());
        }else if(hatchInState.pressed || hatchOutState.pressed){
            runCommand(new ScoreArm());
        }

        if(armOut.update(mControlBoard.getArmOut())){
            runCommand(new StowArm());
        }

        System.out.println("Elevator encoder" + mElevator.getInchesFromBottom());
        double elevatorThrottle = mControlBoard.getElevatorThrottle();
        if(elevatorThrottle > 0){
            runCommand(new ScoreArm());
        }

        if(goToLowHatchRocket.update(mControlBoard.getHatchLow())){
            mElevator.setMotionMagic(SuperstructureConstants.kRocketHatchLow);
        }
        if(goToMidHatchRocket.update(mControlBoard.getHatchMid())){
            mElevator.setMotionMagic(SuperstructureConstants.kRocketHatchMiddle);
        }
        if(goToHighHatchRocket.update(mControlBoard.getHatchHigh())){
            mElevator.setMotionMagic(SuperstructureConstants.kRocketHatchHigh);
        }
        if(goToLowCargoRocket.update(mControlBoard.getCargoLow())){
            mElevator.setMotionMagic(SuperstructureConstants.kRocketCargoLow);
        }
        if(goToMidCargoRocket.update(mControlBoard.getCargoMid())){
            mElevator.setMotionMagic(SuperstructureConstants.kRocketCargoMiddle);
        }
        if(goToHighCargoRocket.update(mControlBoard.getCargoHigh())){
            mElevator.setMotionMagic(SuperstructureConstants.kRocketCargoHigh);
        }
        if(goToCargoShip.update(mControlBoard.getCargoShip())){
            mElevator.setMotionMagic(SuperstructureConstants.kCargoShipCargo);
        }


        if(Math.abs(elevatorThrottle) > 0){
            mElevator.setOpenLoop(elevatorThrottle);
        }else if(mElevator.getState() == Elevator.ElevatorState.OPEN_LOOP){
            mElevator.setOpenLoop(0);
        }

        if(removeLeftHatch.update(mControlBoard.getRemoveLeftHatch())){
            runCommand(new RemoveHatch(true));
        }
        if(removeRightHatch.update(mControlBoard.getRemoveRightHatch())){
            runCommand(new RemoveHatch(false));
        }

        Scheduler.getInstance().run();
    }

    public void driveManually(){
        double turn = mControlBoard.getTurn();
        double pTurn = turn;//debug
        boolean quickTurn = mControlBoard.getQuickTurn() || (Util.deadband(mControlBoard.getThrottle()) == 0 && Math.abs(Util.deadband(turn)) > 0);

        if(quickTurn) {
            turn = turn / 2;
        }

        if (mControlBoard.getVisionAssist()) {
            turn = Dashboard.getInstance().getTargetYaw() / 250;
            quickTurn = true;
        }

//        System.out.println("Yaw" + Dashboard.getInstance().getTargetYaw() + "Vision Assist: " + mControlBoard.getVisionAssist() + " Turn: " + turn + " Quickturn:" + quickTurn + "quickturn pressed: " + mControlBoard.getQuickTurn() + "pturn: " + pTurn);

        double modifier = Timer.getMatchTime() < 15 ? 1 : 0.9;
        mDrive.setOpenLoop(mCheesyDriveHelper.cheesyDrive(mControlBoard.getThrottle()*modifier, turn,
                quickTurn));
    }
    /**
     * This function is called periodically during operator control.
     * TODO fix claw in ball mode
     */
    @Override
    public void teleopPeriodic() {
        driveManually();
        enabledPeriodic();
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
