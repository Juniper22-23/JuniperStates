package org.firstinspires.ftc.teamcode.AutonomousFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.drive.TwoWheelTrackingLocalizer;

import java.util.ArrayList;
import java.util.List;

public class ThreeWheelIMU extends LinearOpMode{
    StandardTrackingWheelLocalizer ThreeWheelLocalizer = new StandardTrackingWheelLocalizer(hardwareMap, new ArrayList<Integer>(), new ArrayList<Integer>());
    TwoWheelTrackingLocalizer TwoWheelLocalizer = new TwoWheelTrackingLocalizer(hardwareMap, new SampleMecanumDrive(hardwareMap));
    double odoHeadingWeight = 0.7;
    double IMUHeadingWeight = 0.3;
    @Override
    public void runOpMode(){
        double currentLeftPosition = 0;
        double currentRightPosition = 0;
        double currentHeading = 0;
        waitForStart();
        while(opModeIsActive()) {

            //Position + Velocities
            List<Double> positions = ThreeWheelLocalizer.getWheelPositions();
            telemetry.addData("LEFT DRIVE POS: ", positions.get(0));
            telemetry.addData("RIGHT DRIVE POS: ", positions.get(1));
            telemetry.addData("PERP DRIVE POS: ", positions.get(2));

            List<Double> velocities = ThreeWheelLocalizer.getWheelVelocities();
            telemetry.addData("LEFT DRIVE POS: ", velocities.get(0));
            telemetry.addData("RIGHT DRIVE POS: ", velocities.get(1));
            telemetry.addData("PERP DRIVE POS: ", velocities.get(2));

            //Headings

            //Odometry Heading
            double deltaLeftPosition = positions.get(0) - currentLeftPosition;
            double deltaRightPosition = positions.get(1) - currentRightPosition;
            double deltaHeading = -((deltaLeftPosition - deltaRightPosition) / (StandardTrackingWheelLocalizer.LATERAL_DISTANCE));
            deltaHeading = Math.toDegrees(deltaHeading);
            currentHeading += deltaHeading;
            currentLeftPosition = positions.get(0);
            currentRightPosition = positions.get(1);

            //IMU Heading
            double IMUHeading = TwoWheelLocalizer.getHeading();
            IMUHeading = Math.toDegrees(IMUHeading);

            //Weighted Average
            double avg = (IMUHeadingWeight * IMUHeading) + (odoHeadingWeight * currentHeading);
            telemetry.addData("AVG: ", avg);

            telemetry.update();
        }
    }
}
