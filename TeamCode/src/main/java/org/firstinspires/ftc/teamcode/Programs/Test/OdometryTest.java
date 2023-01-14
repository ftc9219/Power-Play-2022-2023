package org.firstinspires.ftc.teamcode.Programs.Test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Extras.ButtonPress;
import org.firstinspires.ftc.teamcode.Robot.Hardware;
import org.firstinspires.ftc.teamcode.Robot.Movement;
import org.firstinspires.ftc.teamcode.Robot.Update;
import org.firstinspires.ftc.teamcode.Robot.Storage;

@TeleOp(name = "Odometry", group = "TeleOp")
public class OdometryTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        Hardware.init(hardwareMap);

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        ButtonPress BB1 = new ButtonPress();

        double MP = .5;

        double wantX = 0;
        double wantY = 0;

        Storage.x = 0;
        Storage.y = 0;
        Storage.a = 0;

        waitForStart();

        while(opModeIsActive()) {

            BB1.button = gamepad1.b;

            double Pos1 = 0, Pos2 = 0, Pos3 = 0;


            Movement.powerControl(
                    (-gamepad1.left_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x) * MP,
                    (-gamepad1.left_stick_y - gamepad1.right_stick_x - gamepad1.left_stick_x) * MP,
                    (-gamepad1.left_stick_y + gamepad1.right_stick_x - gamepad1.left_stick_x) * MP,
                    (-gamepad1.left_stick_y - gamepad1.right_stick_x + gamepad1.left_stick_x) * MP);

            if (gamepad1.a) {
                Movement.moveToPoint(wantX, wantY, .5);
            }

            Pos1 = -Update.distancePerTick * Hardware.DM1.getCurrentPosition();
            Pos2 = -Update.distancePerTick * Hardware.DM2.getCurrentPosition();
            Pos3 = Update.distancePerTick * Hardware.DM3.getCurrentPosition();

            telemetry.addData("Current X: ", Storage.x);
            telemetry.addData("Current Y: ", Storage.y);
            telemetry.addData("Current Angle: ", Math.toDegrees(Storage.a));
            telemetry.addLine(Pos1 + "   " + Pos2 + "   " + Pos3);
            telemetry.update();

            if (BB1.toggle()) {
                Update.update();
            }

        }

    }

}
