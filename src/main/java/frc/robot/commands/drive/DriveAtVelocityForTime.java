package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Drive;

public class DriveAtVelocityForTime extends Command {

  private double leftVel, rightVel, time;

  private Drive drive = Drive.getInstance();

  public DriveAtVelocityForTime(double leftVel, double rightVel, double time) {
    this.leftVel = leftVel;
    this.rightVel = rightVel;
    this.time = time;
  }

  protected void initialize() {
    setTimeout(time);
  }
  protected void execute() {
    drive.setVelocity(leftVel, rightVel);
  }
  protected boolean isFinished() {
    return isTimedOut();
  }
  protected void end() {
    //System.out.printlnln("COMMAND DONE!");
    drive.stop();
  }
  protected void interrupted() {
    end();
  }
}
