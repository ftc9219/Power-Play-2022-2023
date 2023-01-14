package org.firstinspires.ftc.teamcode.Robot;

import java.util.concurrent.TimeUnit;

public class PID2 {

    private double output, error, derivative, secondDerivative, integral, dError, iError = 0;
    private double lastTime, time, dTime, lastError, lastActual, lastLastActual;
    public double kp = .05, ki = .05, kd = .05;
    public double[] outputLimit = {-1, 1}, integralErrorLimit = {-30, 30};
    //actualLimit = , derivativeLimit = , secondDerivativeLimit = ;

    public PID2 () {
        this.time = Hardware.runtime.time(TimeUnit.SECONDS);
    }

    public double runPID (double desired, double actual) {
        //Limits

        lastTime = time;
        time = Hardware.runtime.time(TimeUnit.SECONDS);
        dTime = time - lastTime;
        lastError = error;
        error = desired - actual;
        dError = (error - lastError) / dTime;
        iError += error * dTime;


        output = kp * error - ki * iError + kd * dError;

        return output;

    }

}
