package frc.robot.lib;

import frc.robot.poofs.util.control.PathSegment;
import frc.robot.poofs.util.math.Rotation2d;
import frc.robot.poofs.util.math.Translation2d;

import java.util.List;

/**
 * Contains basic functions that are used often.
 */
public class Util {

    public static final double kEpsilon = 1e-12;

    /**
     * Prevent this class from being instantiated.
     */
    private Util() {
    }

    /**
     * Limits the given input to the given magnitude.
     */
    public static double limit(double v, double maxMagnitude) {
        return limit(v, -maxMagnitude, maxMagnitude);
    }

    public static double limit(double v, double min, double max) {
        return Math.min(max, Math.max(min, v));
    }

    public static double interpolate(double a, double b, double x) {
        x = limit(x, 0.0, 1.0);
        return a + (b - a) * x;
    }

    public static String joinStrings(final String delim, final List<?> strings) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.size(); ++i) {
            sb.append(strings.get(i).toString());
            if (i < strings.size() - 1) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    public static boolean epsilonEquals(double a, double b, double epsilon) {
        return (a - epsilon <= b) && (a + epsilon >= b);
    }

    public static boolean epsilonEquals(double a, double b) {
        return epsilonEquals(a, b, kEpsilon);
    }

    public static boolean epsilonEquals(int a, int b, int epsilon) {
        return (a - epsilon <= b) && (a + epsilon >= b);
    }

    public static boolean allCloseTo(final List<Double> list, double value, double epsilon) {
        boolean result = true;
        for (Double value_in : list) {
            result &= epsilonEquals(value_in, value, epsilon);
        }
        return result;
    }

    public static double deadband(double input, double minimum){
        return Math.abs(input) > minimum ? input : 0;
    }

    public static double deadband(double input){
        return deadband(input, 0.04);
    }

    public static Rotation2d angleOfPathSegment(PathSegment segment) {
        return angleBetweenPoints(segment.getStart(), segment.getEnd());
    }

    public static Rotation2d angleBetweenPoints(Translation2d point1, Translation2d point2) {
        Rotation2d ret = Rotation2d.identity();

        double yDelta = point1.y() - point2.y();
        double xDelta = point1.x() - point2.x();

        if (xDelta == 0 && yDelta == 0) {
            //System.out.printlnln(
//                    "Translations " + point1.toString() + " and " + point2.toString() + " are in the same place!");
            return null;
        }

        if (xDelta == 0) {
            if (point2.y() > point1.y())
                ret = Rotation2d.fromDegrees(270);
            else
                ret = Rotation2d.fromDegrees(90);
        } else if (yDelta == 0) {
            if (point2.x() > point1.x())
                ret = Rotation2d.fromDegrees(0);
            else
                ret = Rotation2d.fromDegrees(180);
        } else {
            ret = Rotation2d.fromDegrees(Math.toDegrees(Math.atan(yDelta / xDelta)));
            if (point2.y() < point1.y())
                ret = ret.rotateBy(Rotation2d.fromDegrees(180));
        }

        // if x delta 0, if point2 above point1 angle is 270, point2 is below angle is
        // 90
        // if y delta 0, if point2 in front of point1 angle is 0, if point2 is behind
        // 180

        return ret;
    }
}
