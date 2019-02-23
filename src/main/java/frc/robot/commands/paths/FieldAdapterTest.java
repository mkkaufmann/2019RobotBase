package frc.robot.commands.paths;

import java.util.ArrayList;
import frc.robot.commands.drive.pathfollowing.PathBuilder.Waypoint;
import frc.robot.commands.drive.pathfollowing.PathContainer;
import frc.robot.paths.FieldAdapter;
import frc.robot.poofs.util.math.Rotation2d;

public class FieldAdapterTest extends PathContainer {
    public FieldAdapterTest() {
        this.sWaypoints = new ArrayList<Waypoint>();
        sWaypoints.add(new Waypoint(FieldAdapter.kLeftFrontPlatform.x(), FieldAdapter.kLeftFrontPlatform.y(), 0, 0));
        sWaypoints.add(new Waypoint((FieldAdapter.kLeftFrontPlatform.x() + FieldAdapter.kTest.x())/2, (FieldAdapter.kLeftFrontPlatform.y() + FieldAdapter.kTest.y()), 45, 60));
        sWaypoints.add(new Waypoint(FieldAdapter.kTest.x(), FieldAdapter.kTest.y(), 0, 0));
    }

    @Override
    public boolean isReversed() {
        return false;
    }

}