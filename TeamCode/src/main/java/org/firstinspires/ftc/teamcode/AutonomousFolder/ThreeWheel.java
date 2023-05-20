package org.firstinspires.ftc.teamcode.AutonomousFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.drive.TwoWheelTrackingLocalizer;

import java.util.ArrayList;
import java.util.List;

public class ThreeWheel extends LinearOpMode{
    StandardTrackingWheelLocalizer localizer = new StandardTrackingWheelLocalizer(hardwareMap, new ArrayList<Integer>(), new ArrayList<Integer>()) ;

    @Override
    public void runOpMode(){
        double currentHeading = 0;
        double currentLeftPosition = 0;
        double currentRightPosition = 0;

        waitForStart();

        while(opModeIsActive()) {
            //Position + Velocities
            List<Double> positions = localizer.getWheelPositions();
            telemetry.addData("LEFT DRIVE POS: ", positions.get(0));
            telemetry.addData("RIGHT DRIVE POS: ", positions.get(1));
            telemetry.addData("PERP DRIVE POS: ", positions.get(2));

            List<Double> velocities = localizer.getWheelVelocities();
            telemetry.addData("LEFT DRIVE POS: ", velocities.get(0));
            telemetry.addData("RIGHT DRIVE POS: ", velocities.get(1));
            telemetry.addData("PERP DRIVE POS: ", velocities.get(2));

            //Heading + Velocities
            double deltaLeftPosition = positions.get(0) - currentLeftPosition;
            double deltaRightPosition = positions.get(1) - currentRightPosition;
            double deltaHeading = (deltaLeftPosition - deltaRightPosition) / (StandardTrackingWheelLocalizer.LATERAL_DISTANCE);
            currentHeading += deltaHeading;
            currentLeftPosition = positions.get(0);
            currentRightPosition = positions.get(1);
            telemetry.addData("HEADING: ", currentHeading);

            telemetry.update();
        }
    }
}
