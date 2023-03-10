package org.firstinspires.ftc.teamcode.AutonomousFolder;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.DriverControlFolder.ConeTransporter1_5;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Autonomous
public class Left1_3 extends LinearOpMode {
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
    private boolean runLoop = true;
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
            if (!coneTransportedSetup) {
                coneTransporter.unretractOdometryServos();
                coneTransporter.linearSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                coneTransporter.setGripperPosition(.75);
                coneTransporter.grip();
                sleep(2000);
                //coneTransporter.setRiseLevel(1);
                //coneTransporter.lift();
                //sleep(2000);
                coneTransportedSetup = true;
            }
        }
        coneTransporter.setRiseLevel(1);
        coneTransporter.lift();

        sleep(2000);
        ArrayList<AprilTagDetection> currentDetections = detection.getLatestDetections();
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
        timer.reset();
            startX = 30;
            startY = 65;
            startHeading = Math.toRadians(270);
            drive.setPoseEstimate(new Pose2d(startX, startY, startHeading));
            TrajectorySequence Auto1plus3 = drive.trajectorySequenceBuilder(new Pose2d(startX, startY, startHeading))
//dropping the preload__________________________________________________________________________
                    .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                        coneTransporter.setArrayList();
                    })
                    .lineToLinearHeading(new Pose2d(36, 40, Math.toRadians(270)))
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        coneTransporter.setRiseLevel(1);
                        coneTransporter.lift();
                    })
                    .lineToLinearHeading(new Pose2d(36, 10, Math.toRadians(270)))
                    .lineToLinearHeading(new Pose2d(48, 12, Math.toRadians(90)), SampleMecanumDrive.getVelocityConstraint(30.0, 2.5, DriveConstants.TRACK_WIDTH), SampleMecanumDrive.getAccelerationConstraint(30))
                    .forward(2.5)
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        coneTransporter.setGripperPosition(1.0);
                        coneTransporter.grip();
                    })
                    .back(2)
//CYCLE #1________________________________________________________________________________________
                    .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                        coneTransporter.setHeight(0);
                    })
                    .lineToLinearHeading(new Pose2d(58.15, 11.25, Math.toRadians(0)))
                    .forward(1)
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        coneTransporter.setHeight(1);
                    })
                    .waitSeconds(.5)
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        coneTransporter.setGripperPosition(.75);
                        coneTransporter.grip();
                    })
                    .waitSeconds(.25)
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        coneTransporter.setRiseLevel(1);
                        coneTransporter.lift();
                    })
                    .waitSeconds(.5)
                    .back(2)
                    .lineToLinearHeading(new Pose2d(48, 12.5, Math.toRadians(90)))
                    .forward(2)
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        coneTransporter.setGripperPosition(1.0);
                        coneTransporter.grip();
                    })
                    .back(2)
//CYCLE #2_______________________________________________________________________________________
                    .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                        coneTransporter.setHeight(0);
                    })
                    .lineToLinearHeading(new Pose2d(58.15, 11.25, Math.toRadians(357.5)))
                    .forward(1)
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        coneTransporter.setHeight(3);
                    })
                    .waitSeconds(.5)
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        coneTransporter.setGripperPosition(.75);
                        coneTransporter.grip();
                    })
                    .waitSeconds(.25)
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        coneTransporter.setRiseLevel(1);
                        coneTransporter.lift();
                    })
                    .waitSeconds(.5)
                    .back(2)
                    .lineToLinearHeading(new Pose2d(48, 12.5, Math.toRadians(90)))
                    .forward(2)
                    .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                        coneTransporter.setGripperPosition(1.0);
                        coneTransporter.grip();
                    })
                    .back(2)
//CYCLE #3________________________________________________________________________________________
                    .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                        coneTransporter.setHeight(0);
                    })
                    .lineToLinearHeading(new Pose2d(58.15, 11.25, Math.toRadians(355)))
                    .forward(1)
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        coneTransporter.setHeight(5);
                    })
                    .waitSeconds(.5)
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        coneTransporter.setGripperPosition(.75);
                        coneTransporter.grip();
                    })
                    .waitSeconds(.25)
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        coneTransporter.setRiseLevel(1);
                        coneTransporter.lift();
                    })
                    .waitSeconds(.5)
                    .back(2)
                    .lineToLinearHeading(new Pose2d(48, 12.5, Math.toRadians(90)))
                    .forward(2)
                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                        coneTransporter.setGripperPosition(1.0);
                        coneTransporter.grip();
                    })
                    //PARKING______________________________________________________________________________________
                    .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                        coneTransporter.setRiseLevel(-1);
                        coneTransporter.lift();
                    })
                    .lineToLinearHeading(new Pose2d(34 + (-24 * numericalTag), 15, Math.toRadians(270 + (90 * (Math.min(numericalTag,0) + 1)))), SampleMecanumDrive.getVelocityConstraint(48.0, 3.0, DriveConstants.TRACK_WIDTH), SampleMecanumDrive.getAccelerationConstraint(48.0))
                    .build();
            drive.followTrajectorySequence(Auto1plus3);

        runLoop = false;

        while(opModeIsActive()){
            coneTransporter.retractOdometryServos();
            IMUHeading.imuAngle = readFromIMU();

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
    public double readFromIMU() {
        return drive.getRawExternalHeading();
    }
    public void initializeIMU() {
        // don't touch please
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);
    }
}