package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AutonomousFolder.IMUHeading;
import org.firstinspires.ftc.teamcode.Mechanism;

public abstract class Drivetrain1_5 extends Mechanism {

    protected DcMotorEx leftBackMotor;
    protected DcMotorEx leftFrontMotor;
    protected DcMotorEx rightBackMotor;
    protected DcMotorEx rightFrontMotor;


    public static BNO055IMU imu;

    public Drivetrain1_5(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);
        leftBackMotor = this.hardwareMap.get(DcMotorEx.class, "leftBackMotor");
        leftFrontMotor = this.hardwareMap.get(DcMotorEx.class,"leftFrontMotor");
        rightBackMotor = this.hardwareMap.get(DcMotorEx.class,"rightBackMotor");
        rightFrontMotor = this.hardwareMap.get(DcMotorEx.class,"rightFrontMotor");
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        imu = this.hardwareMap.get(BNO055IMU.class, "imu");
        //initializeIMU();

        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public static void initializeIMU() {
        // don't touch please
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);
    }

    public double readFromIMU() {
        IMUHeading.imuAngle = -imu.getAngularOrientation().firstAngle;
        return IMUHeading.imuAngle;
    }

    public DcMotorEx getLeftBackMotor() { return leftBackMotor; }
    public DcMotorEx getLeftFrontMotor() { return leftFrontMotor; }
    public DcMotorEx getRightBackMotor() { return rightBackMotor; }
    public DcMotorEx getRightFrontMotor() { return rightFrontMotor; }


    public abstract void drive(double gamepadX, double gamepadY, double gamepadRot, boolean rotationToggle, boolean strafeToggle);

}