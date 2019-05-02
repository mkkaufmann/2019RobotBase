/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class PrintCommand extends Command {
  
  
  private String mPrintMessage;
  public PrintCommand(String message) {
    this.mPrintMessage = message;
  }

  @Override
  protected boolean isFinished() {
    System.out.println(mPrintMessage);
    this.cancel();
    return true;
  }


}
