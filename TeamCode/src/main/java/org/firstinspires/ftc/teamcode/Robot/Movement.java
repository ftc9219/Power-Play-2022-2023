package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Extras.MathFunctions;

//Robot movement functions
public class Movement {

    //sets power to drive motors
    public static void powerControl(double one, double two, double three, double four) {

        Hardware.DM1.setPower(one);
        Hardware.DM2.setPower(two);
        Hardware.DM3.setPower(three);
        Hardware.DM4.setPower(four);

    }

    public static void directionControl(double yPower, double xPower, double turnPower) {

        Movement.powerControl(yPower + xPower - turnPower,
                yPower - xPower + turnPower,
                yPower - xPower - turnPower,
                yPower + xPower + turnPower);

    }



    //moves robot to a point at a set speed

    public static boolean atPoint = false;

    /*public static void moveToPoint (double x, double y, double speed) {

        double distanceToPoint = Math.hypot(Storage.x - x, Storage.y - y);
        double angleToPoint = MathFunctions.angleWrap(Math.atan2(Storage.y - y, Storage.x - x) - Storage.a);

        double yPower = -Range.clip(Math.cos(angleToPoint) * distanceToPoint, -1, 1) * speed;
        double xPower =Range.clip(Math.sin(angleToPoint) * distanceToPoint, -1, 1) * speed;
        double turnPower = .3 * Range.clip(Math.tan(angleToPoint/Math.toRadians(30)), -1, 1);

        if (distanceToPoint < 3) {
            turnPower = 0;
            atPoint = true;
        }
        else {atPoint = false;}

        Movement.directionControl(yPower, xPower, turnPower);

    }*/

    public static void moveToPoint (double x, double y, double speed) {

        double distanceToPoint = Math.hypot(y - Storage.y, x - Storage.x);
        double relativeAngleToPoint = Math.atan2(y - Storage.y, x - Storage.x) - Storage.a;
        while (relativeAngleToPoint > Math.PI) {
            relativeAngleToPoint -= Math.PI;
        }
        while (relativeAngleToPoint < -Math.PI) {
            relativeAngleToPoint += Math.PI;
        }
        double yPower = Range.clip(distanceToPoint * Math.sin(relativeAngleToPoint), -1, 1) * speed;
        double xPower = Range.clip(distanceToPoint * Math.cos(relativeAngleToPoint), -1, 1) * speed;
        double turnPower = Math.pow(relativeAngleToPoint / Math.PI, 1) * speed;

        if (distanceToPoint < 5) {
            turnPower = 0;
            atPoint = true;
        } else {
            atPoint = false;
        }

        Movement.directionControl(yPower, xPower, turnPower);
    }

    public static double[][] points;
    public static int currentPoint = 0;
    public static boolean pathFollowed = false;

    public static boolean followPath (double speed) {

        if (currentPoint == points.length && atPoint) {
            return true;
        }
        else {
            moveToPoint(points[currentPoint][0], points[currentPoint][1], speed);
            if (atPoint && currentPoint != points.length - 1) {
                currentPoint++;
                atPoint = false;
            }
            return false;

        }

    }

    //moves the arm
    public static void movedArm (double power) {
        Hardware.AM1.setPower(power);
        Hardware.AM2.setPower(power);
    }

    public static double maxPosition = 20;
    public static  PID armPID = new PID();

    public static void moveArm (double height) {

        armPID.Motor = Hardware.AM1;

        if (height > maxPosition) {
            armPID.setPos(maxPosition);
            //Hardware.AM2.setPower(armPID.output);
        }
        else if (height < 0) {
            armPID.setPos(0);
            //Hardware.AM2.setPower(armPID.output);
        }
        else {
            armPID.setPos(height);
            //Hardware.AM2.setPower(armPID.output);
        }

    }

    public static void openClaw(double angle) {

        Hardware.claw1.setPosition(Range.clip(angle / 180, 0, 1));
        Hardware.claw2.setPosition(Range.clip(angle / 180, 0, 1));

    }

    private static boolean side = true;

    public static void rotateClaw () {

        if (side) {
            Hardware.clawRotator.setPosition(-1);
            Hardware.armRotation1.setPosition(-1);
            Hardware.armRotation2.setPosition(-1);
            side = false;
        }
        else {
            Hardware.clawRotator.setPosition(1);
            Hardware.armRotation1.setPosition(1);
            Hardware.armRotation2.setPosition(1);
            side = true;
        }

    }

}
