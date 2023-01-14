package org.firstinspires.ftc.teamcode.Programs;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Extras.ButtonPress;
import org.firstinspires.ftc.teamcode.Extras.Cycling;
import org.firstinspires.ftc.teamcode.Robot.FishPipeline;
import org.firstinspires.ftc.teamcode.Robot.Hardware;
import org.firstinspires.ftc.teamcode.Robot.Movement;
import org.firstinspires.ftc.teamcode.Robot.Storage;
import org.firstinspires.ftc.teamcode.Robot.Update;
import org.firstinspires.ftc.teamcode.Robot.Vision;

@TeleOp(name = "AAAA RUN THIS CODE THIS IS THE CODE YOU RUN", group = "TeleOp")
public class DriverControlledBase extends LinearOpMode {

    public static double MP = .63;
    ButtonPress a = new ButtonPress();
    ButtonPress LB = new ButtonPress();
    ButtonPress RB = new ButtonPress();
    double height = Storage.armPos;

    @Override
    public void runOpMode() {

        a.button = gamepad1.a;
        LB.button = gamepad1.left_bumper;
        RB.button = gamepad1.right_bumper;

        if (LB.press()) {
            height -= 6;
        }
        else if (RB.press()) {
            height += 6;
        }
        Movement.moveArm(height);

        Hardware.init(hardwareMap);
        Storage.x = 0;
        Storage.y = 0;
        Storage.a = Math.toRadians(90);

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        Vision.startVisionCV();

        dashboard.startCameraStream(Hardware.camera, Hardware.camera.getCurrentPipelineMaxFps());

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {



            Movement.directionControl(-gamepad1.left_stick_y * MP, gamepad1.left_stick_x * MP, -gamepad1.right_stick_x * MP);

            if (a.toggle()) {
                Movement.moveToPoint(0, 0, .15);
            }

            if (gamepad2.a) {
                Movement.movedArm(.3);
            }
            else if (gamepad2.b) {
                Movement.movedArm(-0.6);
            }
            else {
                Movement.movedArm(0);
            }

            Movement.openClaw(90 - gamepad1.right_trigger * 90);

            telemetry.addLine("Average: " + FishPipeline.avg1 + "\nColor: " + FishPipeline.getColor());
            telemetry.addLine("En 1: " + Hardware.DM1.getCurrentPosition() + "\nEn 2: " + Hardware.DM2.getCurrentPosition() + "\nEn 3: " + Hardware.DM3.getCurrentPosition());
            telemetry.addLine("ANGE FROM IMU: " + Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            //telemetry.addLine("Arm Pos = " + Storage.armPos + " Arm Encoder: " + Hardware.AM1.getCurrentPosition());
            telemetry.addData("X: ", Storage.x);
            telemetry.addData("X Velocity: ", Storage.xVelocity);
            telemetry.addData("X Acceleration: ", Storage.xAcceleration);
            telemetry.addData("Y: ", Storage.y);
            telemetry.addData("Y Velocity: ", Storage.yVelocity);
            telemetry.addData("Y Acceleration: ", Storage.yAcceleration);
            telemetry.addData("A: ", Math.toDegrees(Storage.a));
            telemetry.addData("A Velocity: ", Storage.aVelocity);
            telemetry.addData("A Acceleration: ", Storage.aAcceleration);
            telemetry.update();

            Update.update();

        }
    }
}
