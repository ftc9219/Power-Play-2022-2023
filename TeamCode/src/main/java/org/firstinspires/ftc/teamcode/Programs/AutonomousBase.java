package org.firstinspires.ftc.teamcode.Programs;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot.FishPipeline;
import org.firstinspires.ftc.teamcode.Robot.Hardware;
import org.firstinspires.ftc.teamcode.Robot.Movement;
import org.firstinspires.ftc.teamcode.Robot.Storage;
import org.firstinspires.ftc.teamcode.Robot.Update;
import org.firstinspires.ftc.teamcode.Robot.Vision;


@Autonomous(name = "Auto", group = "Autonomous")
public class AutonomousBase extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        Vision.startVisionCV();

        dashboard.startCameraStream(Hardware.camera, Hardware.camera.getCurrentPipelineMaxFps());

        Storage.x = 0;
        Storage.y = 0;
        Storage.a = Math.toRadians(90);

        Hardware.init(hardwareMap);

        waitForStart();

        String color = FishPipeline.getColor();

        Hardware.runtime.reset();

        telemetry.update();

        Movement.directionControl(.25, 0, 0);

        while (Storage.y < 18) {
            Update.update();
        }
        Movement.directionControl(0, 0, 0);

        if (!color.equals("Purple")) {
            while (Math.abs(Storage.x) < 30) {
                if (color.equals("Blue")) {
                    Movement.directionControl(0, .25, 0);
                } else if (color.equals("Green")) {
                    Movement.directionControl(0, -.25, 0);
                }
                Update.update();
            }
            Movement.directionControl(0, 0, 0);
        }
        Movement.directionControl(0, 0, 0);

    }
}
