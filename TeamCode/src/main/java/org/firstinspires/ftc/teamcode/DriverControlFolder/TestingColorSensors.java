package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.ColorSensorMechanism;

@TeleOp
public class TestingColorSensors extends LinearOpMode {
    public ColorSensorMechanism colorSensorMechanism;
    @Override
    public void runOpMode() {
        colorSensorMechanism = new ColorSensorMechanism(telemetry, hardwareMap);
         waitForStart();
         while(opModeIsActive()) {
             colorSensorMechanism.isDetectingBlueLine();
         }
    }

}
