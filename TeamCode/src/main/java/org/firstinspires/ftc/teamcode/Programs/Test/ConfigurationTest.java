package org.firstinspires.ftc.teamcode.Programs.Test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot.Hardware;
import org.firstinspires.ftc.teamcode.Robot.Storage;
import org.firstinspires.ftc.teamcode.Robot.Update;
import org.firstinspires.ftc.teamcode.Robot.Vision;

@TeleOp(name = "CONFIGURATION TESTING", group = "TeleOp")
public class ConfigurationTest extends LinearOpMode {

    @Override
    public void runOpMode() {

        Hardware.init(hardwareMap);

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        Vision.startVisionCV();

        dashboard.startCameraStream(Hardware.camera, Hardware.camera.getCurrentPipelineMaxFps());

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {

            Hardware.AM1.setPower(gamepad1.left_stick_y);
            Hardware.AM2.setPower(gamepad1.right_stick_y);

            Hardware.clawRotator.setPosition(gamepad1.a ? 1 : -1);
            Hardware.armRotation1.setPosition(gamepad1.b ? 1 : -1);
            Hardware.armRotation2.setPosition(gamepad1.x ? 1 : -1);
            Hardware.claw1.setPosition(gamepad1.left_bumper ? 1 : -1);
            Hardware.claw2.setPosition(gamepad1.right_bumper ? 1 : -1);

            telemetry.addLine(gamepad1.left_stick_x + " " + gamepad1.left_stick_y + " " + gamepad1.right_stick_x + " "
                    + gamepad1.right_stick_y + " " + gamepad1.left_trigger + "\nArm Position: " + Storage.armPos + " " + Hardware.AM1.getCurrentPosition());
            telemetry.update();

            Update.update();

        }
    }
}
