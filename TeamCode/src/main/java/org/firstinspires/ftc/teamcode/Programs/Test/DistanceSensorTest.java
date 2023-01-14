package org.firstinspires.ftc.teamcode.Programs.Test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot.Hardware;

@TeleOp(name = "Distance Sensor Test", group = "TeleOp")

public class DistanceSensorTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() {

        Hardware.init(hardwareMap);

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        waitForStart();

        while(opModeIsActive()) {

            telemetry.addData("DS", Hardware.DS.getDistance(DistanceUnit.INCH));
            telemetry.update();

        }



    }

}
