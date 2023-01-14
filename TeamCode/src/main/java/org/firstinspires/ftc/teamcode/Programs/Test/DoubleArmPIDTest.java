package org.firstinspires.ftc.teamcode.Programs.Test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Extras.ButtonPress;
import org.firstinspires.ftc.teamcode.Robot.Hardware;
import org.firstinspires.ftc.teamcode.Robot.Movement;
import org.firstinspires.ftc.teamcode.Robot.Storage;
import org.firstinspires.ftc.teamcode.Robot.Update;
import org.firstinspires.ftc.teamcode.Robot.Vision;

@TeleOp(name = "ARM AND CLAW AND STUFF TESTING", group = "TeleOp")
public class DoubleArmPIDTest extends LinearOpMode {

    @Override
    public void runOpMode() {

        Hardware.init(hardwareMap);

        Movement.armPID.outputLimit = .2;

        ButtonPress AB1 = new ButtonPress();
        ButtonPress RB1 = new ButtonPress();

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        Vision.startVisionCV();

        dashboard.startCameraStream(Hardware.camera, Hardware.camera.getCurrentPipelineMaxFps());

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {

            AB1.button = gamepad1.a;
            RB1.button = gamepad1.right_bumper;

            //Movement.moveArm(-gamepad1.left_stick_y * Movement.maxPosition);

//            if (AB1.press()) {
//                Movement.rotateClaw();
//            }
//
//            if (RB1.toggle()) {
//                Movement.openClaw(180);
//            }
//            else {
//                Movement.openClaw(0);
//            }

            telemetry.addLine(gamepad1.left_stick_x + " " + gamepad1.left_stick_y + " " + gamepad1.right_stick_x + " "
            + gamepad1.right_stick_y + " " + gamepad1.left_trigger + "\nArm Position: " + Storage.armPos);
            telemetry.update();

            Update.update();

        }
    }
}
