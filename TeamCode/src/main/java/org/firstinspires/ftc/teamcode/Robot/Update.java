package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Extras.MathFunctions;

//Functions to update position and vectors
public class Update {

    //public dynamic variables
    public static double angle, deltaX, deltaY, radius, totalDistance;
    public static double lPos1;
    public static double lPos2;
    public static double lPos3;
    public static double Pos1;
    public static double Pos2;
    public static double Pos3;

    //Constants
    public static double de1 = 2.75;
    public static double de2 = -1.75;
    public static double de3 = 5.25;
    public static double circumference = 2.3622 * Math.PI;
    public static double ticksPerRotation = 8192;
    public static double distancePerTick = circumference / ticksPerRotation;
    private static double lastAngle = Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

    //updates robot's position
    public static void updatePosition() {

        Pos1 = distancePerTick * Hardware.DM1.getCurrentPosition() - lPos1;
        Pos2 = -distancePerTick * Hardware.DM2.getCurrentPosition() - lPos2;
        Pos3 = -distancePerTick * Hardware.DM3.getCurrentPosition() - lPos3;

        angle = (Pos1 - Pos2) / (de1 - de2);
        angle = Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS) - lastAngle;
        lastAngle = Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        //double a = Storage.a - (angle / 2);


        if (angle != 0) {
            radius = (Pos1 / angle) + de1;
            totalDistance = 2 * radius * Math.sin(angle / 2);
        }
        else {
            totalDistance = Math.hypot(Pos1, Pos3);
        }
        deltaX = totalDistance * Math.cos(Storage.a - angle / 2);
        deltaY = totalDistance * Math.sin(Storage.a - angle / 2);

        Storage.a += angle;
        Storage.x += deltaX;
        Storage.y += deltaY;

        if (angle != 0) {
            radius = (Pos3 / angle) + de3;
            totalDistance = 2 * radius * Math.sin(angle);
            deltaX = totalDistance * Math.sin(Storage.a - angle / 2);
            deltaY = totalDistance * Math.cos(Storage.a - angle / 2);

            Storage.x += deltaX;
            Storage.y += deltaY;
        }

        Storage.a = MathFunctions.angleWrap(Storage.a);

        lPos1 = distancePerTick * Hardware.DM1.getCurrentPosition();
        lPos2 = -distancePerTick * Hardware.DM2.getCurrentPosition();
        lPos3 = -distancePerTick * Hardware.DM3.getCurrentPosition();

    }

    private static double lastX, lastY, lastA;
    private static double lastTimeVelocity;

    public static void updateVelocity() {

        Storage.xVelocity = (Storage.x - lastX) / (Hardware.runtime.time() - lastTimeVelocity);
        Storage.yVelocity = (Storage.y - lastY) / (Hardware.runtime.time() - lastTimeVelocity);
        Storage.aVelocity = (Storage.a - lastA) / (Hardware.runtime.time() - lastTimeVelocity);
        lastX = Storage.x;
        lastY = Storage.y;
        lastA = Storage.a;
        lastTimeVelocity =  Hardware.runtime.time();

    }

    private static double lastXVelocity, lastYVelocity, lastAVelocity;
    private static double lastTimeAcceleration;

    public static void updateAcceleration() {

        Storage.xAcceleration = (Storage.xVelocity - lastXVelocity) / (Hardware.runtime.time() - lastTimeAcceleration);
        Storage.yAcceleration = (Storage.yVelocity - lastYVelocity) / (Hardware.runtime.time() - lastTimeAcceleration);
        Storage.aAcceleration = (Storage.aVelocity - lastAVelocity) / (Hardware.runtime.time() - lastTimeAcceleration);
        lastXVelocity = Storage.xVelocity;
        lastYVelocity = Storage.yVelocity;
        lastAVelocity = Storage.aVelocity;
        lastTimeAcceleration =  Hardware.runtime.time();

    }

    public static double lastArmPos;

    public static void updateArmPos() {

        //double inchesPerRotation = 1;
        //Storage.armPos += (Hardware.AM1.getCurrentPosition() - lastArmPos) * inchesPerRotation;
        //lastArmPos = Hardware.AM1.getCurrentPosition();

    }

    public static void update() {
        updatePosition();
        updateVelocity();
        updateAcceleration();
        updateArmPos();
    }

}