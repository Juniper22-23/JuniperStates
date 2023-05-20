package org.firstinspires.ftc.teamcode.AutonomousFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.TwoWheelTrackingLocalizer;

import java.util.List;

public class TwoWheel extends LinearOpMode{
    private DcMotor leftDriveEncoder;
    private DcMotor perpDriveEncoder;

    TwoWheelTrackingLocalizer localizer = new TwoWheelTrackingLocalizer(hardwareMap, new SampleMecanumDrive(hardwareMap));

    @Override
    public void runOpMode(){
        waitForStart();

        while(opModeIsActive()) {
            //Headings + Velocities
            telemetry.addData("HEADING: ", localizer.getHeading());
            telemetry.addData("HEADING VELOCITY: ", localizer.getHeadingVelocity());

            //Position + Velocities
            List<Double> positions = localizer.getWheelPositions();
            telemetry.addData("LEFT DRIVE POS: ", positions.get(0));
            telemetry.addData("PERP DRIVE POS: ", positions.get(1));

            List<Double> velocities = localizer.getWheelVelocities();
            telemetry.addData("LEFT DRIVE VEL: ", velocities.get(0));
            telemetry.addData("PERP DRIVE VEL: ", velocities.get(1));

            telemetry.update();
        }
    }
}
