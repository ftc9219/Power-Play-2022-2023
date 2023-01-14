package org.firstinspires.ftc.teamcode.Robot;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

//Camera / Vision functions // If we end up having multiple pipelines and stuff I might change this so
//it can cycle through pipelines and stuff but most likely no and also this probably needs a target storage variable
public class Vision {

    static FishPipeline Fish = new FishPipeline();

    //Starts the camera and runs the "Fish" pipeline
    public static void startVisionCV() {

        Hardware.camera = OpenCvCameraFactory.getInstance().createWebcam(Hardware.webcamName, Hardware.cameraMonitorViewId);

        Hardware.camera.setPipeline(Fish);

        Hardware.camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                Hardware.camera.startStreaming(640, 360, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {}
        });

        //camera.openCameraDevice(); // if the above code is bad

    }

    public static void startVisionVuforia() {

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(Hardware.cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "Aaj4paf/////AAABmeUqmNSoekfSt+54UnzsXnoNvmO6uOgZ2aAAgV6srd++rMdyrqfhQ7oXiymewA+GFqy6AZavLLL2qp5LB2rTFKk6xM/uhtEYykj9cWDMPkR+gaYh3Awfnsb1cHAFPtv/MC79alN9nBn9nIM2lZDowMcCU0sldJj4OaWhR2FVgoT47m2pGowudbESNaOpPSbVY5Vxh7aqPlyq/VqsO0FH0daH1/gIOM/uprFhNyC3wMpVqZkotjgqGMDB/QRI0QzIJ/Bg8QCL31aWb6VgeyNDJn6OKus3LnT1QXC+RUsxqjaWiT2sJL1BU7tR0ifeCsyskb/JBlciDVaK9PYfI3Ee3g+Wx19dZFYPt9S3g9UxOX7W";

        parameters.cameraName = Hardware.webcamName;

        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(parameters);

        VuforiaTrackables relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        relicTrackables.activate();


        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getFtcCameraFromTarget();

                if (pose != null) {
                    VectorF trans = pose.getTranslation();
                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                    double tX = trans.get(0);
                    double tY = trans.get(1);
                    double tZ = trans.get(2);

                    double rX = rot.firstAngle;
                    double rY = rot.secondAngle;
                    double rZ = rot.thirdAngle;
                }
        }

    }

}
