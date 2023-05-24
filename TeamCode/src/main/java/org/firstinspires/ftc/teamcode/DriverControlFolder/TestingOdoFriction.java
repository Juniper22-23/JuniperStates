package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mechanism;
import org.firstinspires.ftc.teamcode.util.Encoder;

public class TestingOdoFriction extends Mechanism {
    public static Encoder EncoderFriction;

    public TestingOdoFriction(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);

        EncoderFriction = new Encoder(hardwareMap.get(DcMotorEx.class, "EncoderFriction"));

    }

    public void EncoderTelemetry(){

        telemetry.addData("Encoder position:", EncoderFriction.getCurrentPosition());
        /*telemetry.addData("rightEncoder position:", rightEncoder.getCurrentPosition());
        telemetry.addData("frontEncoder position:", frontEncoder.getCurrentPosition());*/
        /*
        telemetry.addData("leftEncoder velo:", (leftEncoder.getCurrentPosition() - leftOldVelo)/elapsedTime.time());
        telemetry.addData("rightEncoder velo:", (rightEncoder.getCurrentPosition() - rightOldVelo)/elapsedTime.time());
        telemetry.addData("frontEncoder velo:", (frontEncoder.getCurrentPosition() - frontOldVelo)/elapsedTime.time());
        leftOldVelo = leftEncoder.getCurrentPosition();
        rightOldVelo = rightEncoder.getCurrentPosition();
        frontOldVelo = frontEncoder.getCurrentPosition();
        elapsedTime.reset();
        */

        telemetry.update();
    }
}
