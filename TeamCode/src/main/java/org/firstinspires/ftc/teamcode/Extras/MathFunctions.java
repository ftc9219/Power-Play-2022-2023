package org.firstinspires.ftc.teamcode.Extras;

public class MathFunctions {

    public static double angleWrap(double a) {

        while (a >= 2 * Math.PI) {
            a -= 2 * Math.PI;
        }
        while (a < 0) {
            a += 2 * Math.PI;
        }

        return a;
    }

}
