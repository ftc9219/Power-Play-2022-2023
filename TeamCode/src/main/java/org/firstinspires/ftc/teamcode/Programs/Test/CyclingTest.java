package org.firstinspires.ftc.teamcode.Programs.Test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Extras.Cycling;
import org.firstinspires.ftc.teamcode.Robot.Hardware;
import org.firstinspires.ftc.teamcode.Robot.Update;
import org.firstinspires.ftc.teamcode.Robot.Vision;

@TeleOp(name = "We Are Testing YEAH!!!", group = "TeleOp")
public class CyclingTest extends LinearOpMode {

    public static double MP = .63;
    /*ButtonPress DU = new ButtonPress(gamepad1.dpad_up);
    ButtonPress DD = new ButtonPress(gamepad1.dpad_down);
    ButtonPress DL = new ButtonPress(gamepad1.dpad_left);
    ButtonPress DR = new ButtonPress(gamepad1.dpad_right);*/

    @Override
    public void runOpMode() {

        Hardware.init(hardwareMap);

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        double orange = 1, yellow = 1, green = 1, blue = 1;


        Cycling cycle = new Cycling();

        double placeholder[];

        Vision.startVisionCV();

        dashboard.startCameraStream(Hardware.camera, Hardware.camera.getCurrentPipelineMaxFps());

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {

            cycle.setStuff(new String[] {"orange", "yellow", "green", "blue"}, new double[] {orange, yellow, green, blue},
                    new double[] {1, 1, 1, 1}, gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.dpad_left, gamepad1.dpad_right, telemetry);

            placeholder = cycle.returnValues();
            orange = placeholder[0];
            yellow = placeholder[0];
            green = placeholder[0];
            blue = placeholder[0];
            telemetry.addLine(orange + " " + yellow + " " + green + " " + blue);

            telemetry.update();

            /*DU.button = gamepad1.dpad_up;
            DD.button = gamepad1.dpad_down;
            DL.button = gamepad1.dpad_left;
            DR.button = gamepad1.dpad_right;*/

            Update.update();

        }
    }
}
