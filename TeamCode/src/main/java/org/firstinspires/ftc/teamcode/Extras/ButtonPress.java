package org.firstinspires.ftc.teamcode.Extras;

public class ButtonPress {

    public boolean pressed = false;
    public boolean toggled = false;
    public boolean button;

    public boolean press () {
        if (button && !pressed) {
            pressed = true;
            return true;
        }
        else if (!button){
            pressed = false;
            return false;
        }
        else {
            return false;
        }
    }

    public boolean toggle () {
        if (this.press()) {
            toggled = !toggled;
        }
        return toggled;
    }

}
