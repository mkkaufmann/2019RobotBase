package frc.robot.commands.paths;

import java.util.ArrayList;
import frc.robot.commands.drive.pathfollowing.PathBuilder.Waypoint;
import frc.robot.commands.drive.pathfollowing.PathContainer;
import frc.robot.poofs.util.math.Rotation2d;

public class Straight_Path extends PathContainer {
    public Straight_Path() {
        this.sWaypoints = new ArrayList<Waypoint>();
        sWaypoints.add(new Waypoint(68, 151, 0, 0));
        sWaypoints.add(new Waypoint(201, 151, 0, 50));
    }

    @Override
    public boolean isReversed() {
        return false;
    }
}