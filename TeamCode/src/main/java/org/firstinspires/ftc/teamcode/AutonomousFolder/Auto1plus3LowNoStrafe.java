package org.firstinspires.ftc.teamcode.AutonomousFolder;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.DriverControlFolder.ConeTransporter1_5;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Autonomous
public class Auto1plus3LowNoStrafe extends LinearOpMode {

    private OpenCvCamera camera;
    private Detection detection;
    private BNO055IMU imu;


    public static double imuAngle;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    // Tag ID 1, 2, 3 from the 36h11 family
    int LEFT = 1;
    int MIDDLE = 2;
    int RIGHT = 3;
    AprilTagDetection tagOfInterest = null;

    private SampleMecanumDrive drive;
    private ConeTransporter1_5 coneTransporter;
    private ElapsedTime timer;

    public boolean coneTransportedSetup = false;
    public double startX;
    public double startY;
    public double startHeading;


    private int numberOfCycles = 1;
    private int numberOfCones = 15;

    @Override
    public void runOpMode() {

        drive = new SampleMecanumDrive(hardwareMap);
        coneTransporter = new ConeTransporter1_5(telemetry, hardwareMap);
        timer = new ElapsedTime();

        imu = this.hardwareMap.get(BNO055IMU.class, "imu");
        initializeIMU();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        detection = new Detection(tagsize, fx, fy, cx, cy);

        camera.setPipeline(detection);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        telemetry.setMsTransmissionInterval(50);

        /*
         * The INIT-loop:
         * This REPLACES waitForStart!
         */
        while (!isStarted() && !isStopRequested()) {
            ArrayList<AprilTagDetection> currentDetections = detection.getLatestDetections();

            if (!coneTransportedSetup) {
                coneTransporter.linearSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                coneTransporter.setGripperPosition(.75);
                coneTransporter.grip();
                sleep(2000);
                coneTransporter.setRiseLevel(0);
                coneTransporter.lift();
                sleep(2000);
                coneTransportedSetup = true;
            }

            if (currentDetections.size() != 0) {
                boolean tagFound = false;

                for (AprilTagDetection tag : currentDetections) {
                    if (tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT) {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }

                if (tagFound) {
                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                } else {
                    telemetry.addLine("Don't see tag of interest :(");

                    if (tagOfInterest == null) {
                        telemetry.addLine("(The tag has never been seen)");
                    } else {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }

            } else {
                telemetry.addLine("Don't see tag of interest :(");

                if (tagOfInterest == null) {
                    telemetry.addLine("(The tag has never been seen)");
                } else {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }

            }

            telemetry.update();
            sleep(20);

        }

        /*
         * The START command just came in: now work off the latest snapshot acquired
         * during the init loop.
         */

        /* Update the telemetry */
        if (tagOfInterest != null) {
            telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        } else {
            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            telemetry.update();
        }

        /* Start Loop */

        double numericalTag = 0;
        if (tagOfInterest != null) {
            if (tagOfInterest.id == LEFT) {
                numericalTag = tagOfInterest.id - 2;
            } else if (tagOfInterest.id == MIDDLE) {
                numericalTag = 0.001;
            } else if (tagOfInterest.id == RIGHT) {
                numericalTag = tagOfInterest.id - 2;
            }
        } else{
            numericalTag = -1;
        }

        startX = 36;
        startY = 65;
        startHeading = Math.toRadians(270);
        drive.setPoseEstimate(new Pose2d(startX, startY, startHeading));
        TrajectorySequence Auto1plus3 = drive.trajectorySequenceBuilder(new Pose2d(startX, startY, startHeading))
//dropping the preload__________________________________________________________________________
                .lineToLinearHeading(new Pose2d(36, 23.5, Math.toRadians(270)))
                .turn(Math.toRadians(90))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    coneTransporter.setRiseLevel(1);
                    coneTransporter.lift();
                })
                .waitSeconds(0.5)
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.grip();
                })
                .forward(3.5)
                .lineToLinearHeading(new Pose2d(39.5, 12, Math.toRadians(0)))

//CYCLE #1________________________________________________________________________________________
                .lineToLinearHeading(new Pose2d(60, 12, Math.toRadians(0)))
                .UNSTABLE_addTemporalMarkerOffset(1.25, () -> {
                    coneTransporter.setRiseLevel(1);
                    coneTransporter.lift();
                })
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    coneTransporter.setGripperPosition(.75);
                    coneTransporter.grip();
                })
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    coneTransporter.setRiseLevel(-1);
                    coneTransporter.lift();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(47, 12, Math.toRadians(0)))
                .turn(Math.toRadians(90))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    coneTransporter.setRiseLevel(1);
                    coneTransporter.lift();
                })
                .waitSeconds(0.5)
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.grip();
                })
                .forward(3.5)
                .back(3.5)
                .turn(Math.toRadians(-90))

//CYCLE #2_______________________________________________________________________________________
                .lineToLinearHeading(new Pose2d(60, 12, Math.toRadians(0)))
                .UNSTABLE_addTemporalMarkerOffset(1.25, () -> {
                    coneTransporter.setRiseLevel(1);
                    coneTransporter.lift();
                })
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    coneTransporter.setGripperPosition(.75);
                    coneTransporter.grip();
                })
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    coneTransporter.setRiseLevel(-1);
                    coneTransporter.lift();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(47, 12, Math.toRadians(0)))
                .turn(Math.toRadians(90))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    coneTransporter.setRiseLevel(1);
                    coneTransporter.lift();
                })
                .waitSeconds(0.5)
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.grip();
                })
                .forward(3.5)
                .back(3.5)
                .turn(Math.toRadians(-90))

//CYCLE #3________________________________________________________________________________________
                .lineToLinearHeading(new Pose2d(60, 12, Math.toRadians(0)))
                .UNSTABLE_addTemporalMarkerOffset(1.25, () -> {
                    coneTransporter.setRiseLevel(1);
                    coneTransporter.lift();
                })
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    coneTransporter.setGripperPosition(.75);
                    coneTransporter.grip();
                })
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    coneTransporter.setRiseLevel(-1);
                    coneTransporter.lift();
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(47, 12, Math.toRadians(0)))
                .turn(Math.toRadians(90))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    coneTransporter.setRiseLevel(1);
                    coneTransporter.lift();
                })
                .waitSeconds(0.5)
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    coneTransporter.setGripperPosition(1.0);
                    coneTransporter.grip();
                })
                .forward(3.5)
                .back(3.5)

//PARKING______________________________________________________________________________________
                .strafeLeft(12)
                .forward(24)
                .strafeRight(24)
                .build();
        drive.followTrajectorySequence(Auto1plus3);

        while(opModeIsActive()){
            IMUHeading.imuAngle = readFromIMU();
            telemetry.clear();
            telemetry.addData("IMUHeading.imuAngle: ", Math.toDegrees(IMUHeading.imuAngle));
            telemetry.update();
        }
    }


    void tagToTelemetry(AprilTagDetection detection) {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }
    public void initializeIMU() {
        // don't touch please
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);
    }

    public double readFromIMU() {
        return drive.getRawExternalHeading();
    }
}