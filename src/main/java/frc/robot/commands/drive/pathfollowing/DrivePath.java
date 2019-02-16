package frc.robot.commands.drive.pathfollowing;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.poofs.util.control.Path;
import frc.robot.subsystems.Drive;

public class DrivePath extends Command {

  private PathContainer mPathContainer;
  private Path mPath;
  private Drive drive = Drive.getInstance();

  public DrivePath(PathContainer p) {
    mPathContainer = p;
    mPath = mPathContainer.buildPath();
  }

  protected void initialize() {
    //System.out.printlnln("initializing DrivePath");
    drive.setWantDrivePath(mPath, mPathContainer.isReversed());
  }

  protected void execute() {
  }

  protected boolean isFinished() {
    return drive.isDoneWithPath();
  }

  protected void end() {
  }

  protected void interrupted() {
    end();
  }
}
