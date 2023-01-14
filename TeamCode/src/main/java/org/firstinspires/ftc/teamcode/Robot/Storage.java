package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;

// Stores robots position and vectors in inches, radians, and i haven't decided on the velocity and accelerations yet
public class Storage {

    // (0, 0) is always going to be as if you're looking at it from the audience position, blue on the left red on the right same as in game manual part 2
    // Angles are unit circle angles in radians (I don't know what else this would be called) 0 is facing the positive x direction pi / 2 is directly up
    // Angle is stored between [0,2pi)
    public static double x;
    public static double y;
    public static double a;
    public static double armPos;
    public static double xVelocity, xAcceleration, yVelocity, yAcceleration, aVelocity, aAcceleration;

}
