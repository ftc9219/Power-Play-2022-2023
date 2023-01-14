package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

//Stores and initializes robot components
public class Hardware {

    //Component Variables
    public static DcMotorEx DM1;
    public static DcMotorEx DM2;
    public static DcMotorEx DM3;
    public static DcMotorEx DM4;

    public static DcMotorEx AM1;
    public static DcMotorEx AM2;

    public static Servo claw1;
    public static Servo claw2;

    public static Servo clawRotator;

    public static Servo armRotation1;
    public static Servo armRotation2;

    public static DistanceSensor DS;

    public static OpenCvCamera camera;
    public static WebcamName webcamName;

    public static int cameraMonitorViewId;

    public static IMU imu;

    //Time and hardware map
    public static ElapsedTime runtime = new ElapsedTime();

    //Initializes components and resets runtime
    public static void init(HardwareMap hardwareMap) {

        //Drive Motor Initialization
        DM1 = hardwareMap.get(DcMotorEx.class, "DM1");
        DM2 = hardwareMap.get(DcMotorEx.class, "DM2");
        DM3 = hardwareMap.get(DcMotorEx.class, "DM3");
        DM4 = hardwareMap.get(DcMotorEx.class, "DM4");

        DM1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        DM2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        DM3.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        DM4.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        DM1.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        DM2.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        DM3.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        DM4.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        DM1.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        DM2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        DM3.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        DM4.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        DM1.setDirection(DcMotor.Direction.REVERSE); //                          front
        DM3.setDirection(DcMotor.Direction.REVERSE); //motors are lied out like 1^^^^^2
        //                                                                      3^^^^^4

        //Arm motors Initialization
        AM1 = hardwareMap.get(DcMotorEx.class, "AM1");
        AM1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        AM1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        AM1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //Update.lastArmPos = AM1.getCurrentPosition();

        AM2 = hardwareMap.get(DcMotorEx.class, "AM2");
        AM2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        AM2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        AM2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Servo Initialization
        claw1 = hardwareMap.get(Servo.class, "C1");
        claw2 = hardwareMap.get(Servo.class, "C2");
        claw2.setDirection(Servo.Direction.REVERSE);
        claw1.setPosition(1);
        claw2.setPosition(1); //open position

        clawRotator = hardwareMap.get(Servo.class, "clawRotator");
        armRotation1 = hardwareMap.get(Servo.class, "armRotation1");
        armRotation2 = hardwareMap.get(Servo.class, "armRotation2");

        clawRotator.setPosition(-1);
        armRotation1.setPosition(1);
        armRotation2.setPosition(1);

        //Distance Sensor Initialization
        DS = hardwareMap.get(DistanceSensor.class, "DS");

        //Camera Initialization
        webcamName = hardwareMap.get(WebcamName.class, "Webcam");

        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        imu = hardwareMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.RIGHT;

        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);

        imu.initialize(new IMU.Parameters(orientationOnRobot));

        imu.resetYaw();

        //Reset Time
        runtime.reset();

    }

}

