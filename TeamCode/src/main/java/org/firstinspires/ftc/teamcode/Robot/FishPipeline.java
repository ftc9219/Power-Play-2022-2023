package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.config.Config;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class FishPipeline extends OpenCvPipeline {

    //Variables

    //Literally just the colors blue and green
    static final Scalar BLUE = new Scalar(0,0,255);
    static final Scalar GREEN = new Scalar(0,255,0);

    //Derived Variables (they kind of suck though)
    public static int avg1;
    static String currentColor;

    //Color Thresholds
    //green 120 purple 162 yellow 142
    static final int BLUE_THRESHOLD = 100;
    static final int GREEN_THRESHOLD = 108;
    static final int PURPLE_THRESHOLD = 145;

    //Mats
    Mat region1_Cb;
    Mat YCrCb = new Mat();
    Mat Cb = new Mat();

    //Processed Region
    public static Point REGION1_TOP_LEFT_ANCHOR_POINT = new Point(320,180);
    public static int REGION_WIDTH = 80;
    public static int REGION_HEIGHT = 150;

    static Point region1_pointA = new Point(
            REGION1_TOP_LEFT_ANCHOR_POINT.x,
            REGION1_TOP_LEFT_ANCHOR_POINT.y);

    static Point region1_pointB = new Point(
            REGION1_TOP_LEFT_ANCHOR_POINT.x + REGION_WIDTH,
            REGION1_TOP_LEFT_ANCHOR_POINT.y + REGION_HEIGHT);

    //Functions

    //Sets Mat YCrCb to input except YCrCb and then sets Mat Cb to the Cb channel of Mat YCrCb
    void inputToCb(Mat input) {

        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);

        Core.extractChannel(YCrCb, Cb, 1);

    }

    //runs when pipeline is called
    @Override
    public void init(Mat firstFrame) {

    }

    //Converts frame to Cb sets avg1 to average int of Cb and draws the rectangle to the view and returns the input
    @Override
    public Mat processFrame(Mat input) {

        inputToCb(input);

        avg1 = (int) Core.mean(Cb.submat(new Rect(region1_pointA, region1_pointB))).val[0];

        region1_pointA.x = REGION1_TOP_LEFT_ANCHOR_POINT.x;
        region1_pointA.y = REGION1_TOP_LEFT_ANCHOR_POINT.y;

        region1_pointB.x = REGION1_TOP_LEFT_ANCHOR_POINT.x + REGION_WIDTH;
        region1_pointB.y = REGION1_TOP_LEFT_ANCHOR_POINT.y + REGION_HEIGHT;

        Imgproc.rectangle(input, region1_pointA, region1_pointB, BLUE, 5);
        Imgproc.rectangle(input, region1_pointA, region1_pointB, GREEN, -1);

        return input;

    }

    //Sets currentColor depending on avg1
    public static String getColor() {

        if (avg1 > PURPLE_THRESHOLD) {
            return "Purple";
        }

        else if (avg1 > GREEN_THRESHOLD) {
            return "Green";
        }

        else {
            return "Blue";
        }

    }
}