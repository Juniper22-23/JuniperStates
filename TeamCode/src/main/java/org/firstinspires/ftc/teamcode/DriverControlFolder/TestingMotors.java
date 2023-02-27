package org.firstinspires.ftc.teamcode.DriverControlFolder;/*
package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mechanism;
import org.firstinspires.ftc.teamcode.util.Encoder;

public class TestingMotors extends Mechanism {
    public Encoder leftBackEncoder, rightBackEncoder, leftFrontEncoder, rightFrontEncoder;
    private int leftFrontOldPos = 0;
    private int rightFrontOldPos = 0;
    private int rightBackOldPos = 0;
    private int leftBackOldPos = 0;
    private ElapsedTime elapsedTime = new ElapsedTime();


    public void resetEncoder() {

    }

    public TestingMotors(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);

        leftBackEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftBackEncoder"));
        rightBackEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightBackEncoder"));
        leftFrontEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftFrontEncoder"));
        rightFrontEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightFrontEncoder"));

    }

    public void EncoderTelemetry() {

        telemetry.addData("leftBackEncoder position:", leftBackEncoder.getCurrentPosition());
        telemetry.addData("rightBackEncoder position:", rightBackEncoder.getCurrentPosition());
        telemetry.addData("leftFrontEncoder position:", leftFrontEncoder.getCurrentPosition());
        telemetry.addData("rightFrontEncoder position:", rightFrontEncoder.getCurrentPosition());
        telemetry.addData("leftBackEncoder velo:", (leftBackEncoder.getCurrentPosition() - leftBackOldPos) / elapsedTime.time());
        telemetry.addData("rightBackEncoder velo:", (rightBackEncoder.getCurrentPosition() - rightBackOldPos) / elapsedTime.time());
        telemetry.addData("leftFrontEncoder velo:", (leftFrontEncoder.getCurrentPosition() - leftFrontOldPos) / elapsedTime.time());
        telemetry.addData("rightFrontEncoder velo:", (rightFrontEncoder.getCurrentPosition() - rightFrontOldPos) / elapsedTime.time());
        leftBackOldPos = leftBackEncoder.getCurrentPosition();
        rightBackOldPos = rightBackEncoder.getCurrentPosition();
        leftFrontOldPos = leftFrontEncoder.getCurrentPosition();
        rightFrontOldPos = rightFrontEncoder.getCurrentPosition();
        elapsedTime.reset();

        telemetry.update();

    }
}
*/
