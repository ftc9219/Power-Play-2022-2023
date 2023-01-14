package org.firstinspires.ftc.teamcode.Programs.Test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot.FishPipeline;
import org.firstinspires.ftc.teamcode.Robot.Hardware;
import org.firstinspires.ftc.teamcode.Robot.Movement;
import org.firstinspires.ftc.teamcode.Robot.Storage;
import org.firstinspires.ftc.teamcode.Robot.Update;
import org.firstinspires.ftc.teamcode.Robot.Vision;


@Autonomous(name = "ODOMETRY CONFIG", group = "Autonomous")
public class OdometryConfiguration extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        Vision.startVisionCV();

        dashboard.startCameraStream(Hardware.camera, Hardware.camera.getCurrentPipelineMaxFps());

        Storage.x = 0;
        Storage.y = 0;
        Storage.a = 0;

        Hardware.init(hardwareMap);

        waitForStart();

        Hardware.DM1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Hardware.DM1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Hardware.DM2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Hardware.DM2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Hardware.DM3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Hardware.DM3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Hardware.DM4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Hardware.DM4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) < 360) {
            Movement.directionControl(0, 0, .2);
        }

        Movement.directionControl(0, 0, 0);

        double Pos1 = Update.distancePerTick * Hardware.DM1.getCurrentPosition();
        double Pos2 = -Update.distancePerTick * Hardware.DM2.getCurrentPosition();
        double Pos3 = -Update.distancePerTick * Hardware.DM3.getCurrentPosition();

        double de1 = -Pos1 / Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        double de2 = -Pos2 / Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        double de3 = -Pos3 / Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        Hardware.imu.resetYaw();

        while (Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) > -360) {
            Movement.directionControl(0, 0, .2);
        }

        Movement.directionControl(0, 0, 0);

        Pos1 = Update.distancePerTick * Hardware.DM1.getCurrentPosition();
        Pos2 = -Update.distancePerTick * Hardware.DM2.getCurrentPosition();
        Pos3 = -Update.distancePerTick * Hardware.DM3.getCurrentPosition();

        de1 = (de1 + -Pos1 / Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS)) / 2;
        de2 = (de2 + -Pos2 / Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS)) / 2;
        de3 = (de3 + -Pos3 / Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS)) / 2;

        telemetry.addLine("de1: " + de1 + "\n" + "de2: " + de2 + "\n" + "de3: " + de3);
        telemetry.update();


    }
}
