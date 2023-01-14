package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.DcMotorEx;

//Functions and variables for speed and position (i haven't done this yet) PIDs
public class PID {

    //Dynamic variables
    private double sumOfErrors;
    private double lastError;
    private double lastOutput;
    public double lTime;
    public double output;

    //Constants
    public double kp;
    public double ki;
    public double kd;
    public double sumLimit;
    public double outputLimit;
    public double rotationsPerInch;
    public int ticksPerRotation;
    public DcMotorEx Motor;

    PID() {
        this.kp = .5;
        this.ki = .5;
        this.kd = .5;
        this.sumLimit = 1;
        this.outputLimit = 1;
        this.rotationsPerInch = 1;
        this.ticksPerRotation = 2650;
    }

    //sets a motor to a constant speed
    public void setSpeed (double targetSpeed) {
        //need to make it so targetSpeed is in an actual unit like rpm or something
        double error = targetSpeed - (60 * Motor.getVelocity() / ticksPerRotation);
        double dTime = Hardware.runtime.time() - lTime;
        output = (kp * error) + ki * sumOfErrors - (kd * (output - lastOutput)) / dTime;
        if (output > outputLimit) {
            output = outputLimit;
        }
        else if (output < -outputLimit) {
            output = -outputLimit;
        }
        Motor.setPower(output);
        lastError = error;
        lastOutput = output;
        lTime = Hardware.runtime.time();
        if ((sumOfErrors < sumLimit && sumOfErrors > -sumLimit) ||
                (sumOfErrors > sumLimit && error < 0) ||
                (sumOfErrors < -sumLimit && error > 0)) {
            sumOfErrors += error * dTime;
        }
    }

    //Sets
    public void setPos (double targetPosition) {
        //need to make it so targetSpeed is in an actual unit like rpm or something
        double error = targetPosition - (rotationsPerInch * Motor.getCurrentPosition() / ticksPerRotation);
        double dTime = Hardware.runtime.time() - lTime;
        output = (kp * error) + ki * sumOfErrors - (((rotationsPerInch * Motor.getCurrentPosition() / ticksPerRotation) - lastOutput)) / dTime;
        if (output > .5) {
            output = .5;
        }
        else if (output < -.5) {
            output = -.5;
        }
        Motor.setPower(output);
        lastError = error;
        lastOutput = (rotationsPerInch * Motor.getCurrentPosition() / ticksPerRotation);
        lTime = Hardware.runtime.time();
        if ((sumOfErrors < sumLimit && sumOfErrors > -sumLimit) ||
                (sumOfErrors > sumLimit && error < 0) ||
                (sumOfErrors < -sumLimit && error > 0)) {
            sumOfErrors += error * dTime;
        }
    }


}
