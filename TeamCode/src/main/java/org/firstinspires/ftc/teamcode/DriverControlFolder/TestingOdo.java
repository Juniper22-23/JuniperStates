package org.firstinspires.ftc.teamcode.DriverControlFolder;/*
package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mechanism;
import org.firstinspires.ftc.teamcode.util.Encoder;

public class TestingOdo extends Mechanism {
    public static Encoder leftEncoder, rightEncoder, frontEncoder;
    private int frontOldVelo = 0;
    private int rightOldVelo = 0;
    private int leftOldVelo = 0;
    private ElapsedTime elapsedTime = new ElapsedTime();


    public TestingOdo(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);

        leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftEncoder"));
        rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightEncoder"));
        frontEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "frontEncoder"));

    }

    public void resetEncoder(){

    }

    public void EncoderTelemetry(){

        telemetry.addData("leftEncoder position:", leftEncoder.getCurrentPosition());
        telemetry.addData("rightEncoder position:", rightEncoder.getCurrentPosition());
        telemetry.addData("frontEncoder position:", frontEncoder.getCurrentPosition());
        telemetry.addData("leftEncoder velo:", (leftEncoder.getCurrentPosition() - leftOldVelo)/elapsedTime.time());
        telemetry.addData("rightEncoder velo:", (rightEncoder.getCurrentPosition() - rightOldVelo)/elapsedTime.time());
        telemetry.addData("frontEncoder velo:", (frontEncoder.getCurrentPosition() - frontOldVelo)/elapsedTime.time());
        leftOldVelo = leftEncoder.getCurrentPosition();
        rightOldVelo = rightEncoder.getCurrentPosition();
        frontOldVelo = frontEncoder.getCurrentPosition();
        elapsedTime.reset();

        telemetry.update();

    }
}
*/
