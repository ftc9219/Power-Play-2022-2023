package org.firstinspires.ftc.teamcode.Extras;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Cycling {

    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    private boolean update = true;
    private int active = 0;
    public double increment[];
    public String variables[];
    public double values[];
    public Telemetry telemetry;

    public void setStuff(String variables[], double values[], double increment[], boolean up, boolean down, boolean left, boolean right, Telemetry telemetry) {

        this.variables = variables;
        this.increment = increment;
        this.values = values;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.telemetry = telemetry;

    }

    private void calculate() {

        if (left && active != 0) {
            active -= 1;
            update = true;
        }
        else if (right && active < values.length - 1) {
            active += 1;
            update = true;
        }

        if (update) {
            telemetry.addLine(variables[active] + ": " + values[active]);
        }
        if (up) {
            values[active] += increment[active];
            update = true;
        }
        else if (down) {
            values[active] -= increment[active];
            update = true;
        }
        else {
            update = false;
        }

    }

    public double[] returnValues() {

        calculate();
        return values;

    }


}
