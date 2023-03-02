package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mechanism;
//import org.firstinspires.ftc.teamcode.Mechanisms.LightsMechanism;

import java.util.ArrayList;

//TODO: Add fieldcenterauto for 1.5
public class ConeTransporter1_5 extends Mechanism {
    /*
    This is the general explanation for this class:
    liftPos0 = position to pick up the cone
    liftPos1 = lowest junction
    liftPos2 = medium junction
    liftPos3 = high junction

    gripperClose = gripper closed i.e. not grasping the cone - NOT ACTUALLY USED
    gripperOpen = gripper expanded i.e. grasping the cone
    */


    //    public Map<LinearSlidesLevels, Double> linearSlidesLevels;
    public double V15 = 0;
    //public LightsMechanism lightsMechanism = new LightsMechanism(telemetry, hardwareMap);

    // Tele-Op
    public double LINEAR_SLIDES_LOW = 367.5;// 13.5 inches converted to mm(low junction)
    public double LINEAR_SLIDES_MEDIUM = 627.5;// 23.5 inches converted to mm(medium junction)
    public double LINEAR_SLIDES_HIGH = 862.5;// 33.5 inches converted to mm(high junction) 2349
    public double LINEAR_SLIDES_NORM = 100;
    public double LINEAR_SLIDES_IN_CONE = 0;
    public double LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_NORM;
    public double ticks;
    //Autonomous
    private final double inFactor_MM = 125;
    public double AUTO_LINEAR_SLIDES_12 = 165.1;// 6.5 inches converted to mm(2 stack) + 10 mm to be above the cone
    public double AUTO_LINEAR_SLIDES_13 = 196.85;// 7.75 inches converted to mm(3 stack) + 10 mm to be above the cone
    public double AUTO_LINEAR_SLIDES_14 = 228.6;// 9 inches converted to mm(4 stack) + 10 mm to be above the cone
    public double AUTO_LINEAR_SLIDES_15 = 260.35;// 10.25 inches converted to mm(5 stack) + 10 mm to be above the cone
    public double AUTO_LINEAR_SLIDES_12_IN_CONE = AUTO_LINEAR_SLIDES_12 - inFactor_MM;
    public double AUTO_LINEAR_SLIDES_13_IN_CONE = AUTO_LINEAR_SLIDES_13 - inFactor_MM;
    public double AUTO_LINEAR_SLIDES_14_IN_CONE = AUTO_LINEAR_SLIDES_14 - inFactor_MM;
    public double AUTO_LINEAR_SLIDES_15_IN_CONE = AUTO_LINEAR_SLIDES_15 - inFactor_MM;
    public double ticksPerRotation = 384.5;// 435 RPM motor 5202 GoBilda TPR
    public int ticksAsInt;

    //GRIPPER______________________________________________________________________________________
    public Servo gripper;
    public double gripperPosition;

    //LINEAR SLIDES________________________________________________________________________________
    public DcMotor linearSlides;
    public int riseLevel = 0;
    public int posLevel = 0;
    public float diameterOfSpool = 34f;
    public float linearSlidesSpeed = 1f;
    public int level;
    public ArrayList<Double> stackLevel = new ArrayList<Double>();
    public ArrayList<String> telemetryLevel = new ArrayList<String>();
    public int arrayListIndex = -1;
    public String stackTelemetry;

    //LIMIT SWITCH_________________________________________________________________________________
//    public DigitalChannel limitSwitch;
//    public TouchSensor touchSensor;


    // Servos on encoder wheels for retracting and unretracting them
    public double LEFT_RETRACT_POS = 0.5;
    public double LEFT_UNRETRACT_POS = 0.0;
    public double RIGHT_RETRACT_POS = 0.0;
    public double RIGHT_UNRETRACT_POS = 0.5;
    public double FRONT_RETRACT_POS = 0.5;
    public double FRONT_UNRETRACT_POS = 1.0;
    public Servo leftServo;
    public Servo rightServo;
    public Servo frontServo;

    public ConeTransporter1_5(Telemetry telemetry, HardwareMap hardwareMap) {
        super(telemetry, hardwareMap);
        linearSlides = this.hardwareMap.get(DcMotor.class, "linearSlides");
        linearSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        gripper = this.hardwareMap.get(Servo.class, "gripper");

//        limitSwitch = this.hardwareMap.get(DigitalChannel.class, "limit switch");
//        touchSensor = this.hardwareMap.get(TouchSensor.class, "touchSensor");

        // Servos on encoder wheels for retracting and unretracting them
        leftServo = this.hardwareMap.get(Servo.class, "leftServo");
        rightServo = this.hardwareMap.get(Servo.class, "rightServo");
        frontServo = this.hardwareMap.get(Servo.class, "frontServo");
    }

    /*public void setLights() {
        if (linearSlides.getTargetPosition() == equate(AUTO_LINEAR_SLIDES_15) || linearSlides.getTargetPosition() == equate(AUTO_LINEAR_SLIDES_15_IN_CONE)) {
            lightsMechanism.runLights("magenta");
        } else if (linearSlides.getTargetPosition() == equate(AUTO_LINEAR_SLIDES_14) || linearSlides.getTargetPosition() == equate(AUTO_LINEAR_SLIDES_14_IN_CONE)) {
            lightsMechanism.runLights("red");
        } else if (linearSlides.getTargetPosition() == equate(AUTO_LINEAR_SLIDES_13) || linearSlides.getTargetPosition() == equate(AUTO_LINEAR_SLIDES_13_IN_CONE)) {
            lightsMechanism.runLights("green");
        } else if (linearSlides.getTargetPosition() == equate(AUTO_LINEAR_SLIDES_12) || linearSlides.getTargetPosition() == equate(AUTO_LINEAR_SLIDES_12_IN_CONE)) {
            lightsMechanism.runLights("blue");
        } else {
            lightsMechanism.runLights("clear");
        }
    }*/

    public void setArrayList() {
        stackLevel.add(AUTO_LINEAR_SLIDES_15);
        stackLevel.add(AUTO_LINEAR_SLIDES_15_IN_CONE);
        stackLevel.add(AUTO_LINEAR_SLIDES_14);
        stackLevel.add(AUTO_LINEAR_SLIDES_14_IN_CONE);
        stackLevel.add(AUTO_LINEAR_SLIDES_13);
        stackLevel.add(AUTO_LINEAR_SLIDES_13_IN_CONE);
        stackLevel.add(AUTO_LINEAR_SLIDES_12);
        stackLevel.add(AUTO_LINEAR_SLIDES_12_IN_CONE);

        telemetryLevel.add("AUTO_LINEAR_SLIDES_15");
        telemetryLevel.add("AUTO_LINEAR_SLIDES_15_IN_CONE");
        telemetryLevel.add("AUTO_LINEAR_SLIDES_14");
        telemetryLevel.add("AUTO_LINEAR_SLIDES_14_IN_CONE");
        telemetryLevel.add("AUTO_LINEAR_SLIDES_13");
        telemetryLevel.add("AUTO_LINEAR_SLIDES_13_IN_CONE");
        telemetryLevel.add("AUTO_LINEAR_SLIDES_12");
        telemetryLevel.add("AUTO_LINEAR_SLIDES_12_IN_CONE");
//        stackLevel.add("AUTO_LINEAR_SLIDES_11");
//        stackLevel.add("AUTO_LINEAR_SLIDES_11_IN_CONE");


    }

    public int equate(double height) {
        ticks = ticksPerRotation * (height / (diameterOfSpool * Math.PI));
        ticksAsInt = (int) ticks;
        return ticksAsInt;
    }


    public void grip() {
        gripper.setPosition(gripperPosition);
    }

    public void lift() {
        rise(riseLevel);
    }

    private void rise(int riseLevel) {
        if (riseLevel == 0) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_NORM;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (riseLevel == 1) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_LOW;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (riseLevel == 2) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_MEDIUM;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (riseLevel == 3) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_HIGH;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        } else if (riseLevel == -1) {
            LINEAR_SLIDES_CURRENT = LINEAR_SLIDES_IN_CONE;
            linearSlides.setTargetPosition(equate(LINEAR_SLIDES_CURRENT));
            linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            linearSlides.setPower(linearSlidesSpeed);
        }

    }

    public void moveDown() {
        if (arrayListIndex <= 7) {
            arrayListIndex++;
            setHeight(arrayListIndex);
        }
    }


    public void moveUp() {
        if (arrayListIndex > 0) {
            if (arrayListIndex % 2 == 0) {
                arrayListIndex = Math.max(arrayListIndex - 2, 0);
            } else {
                arrayListIndex -= 1;
            }
            setHeight(arrayListIndex);
        }
    }

    public void reset() {
        if (arrayListIndex < 9) {
            if (!(arrayListIndex % 2 == 0)) {
                arrayListIndex += 1;
            }
        }
    }

    public void stackTelemetry() {
        stackTelemetry = telemetryLevel.get(arrayListIndex);
    }

    public void setHeight(int index) {
        double linearSlides_MM = stackLevel.get(index);
        linearSlides.setTargetPosition(equate(linearSlides_MM));
        linearSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlides.setPower(linearSlidesSpeed);
    }

    public void setRiseLevel(int level) {
        riseLevel = level;
    }

    public void setGripperPosition(double position) {
        gripperPosition = position;
    }


    //    public void limitSwitch(){
//        //true means pressed(Slides are at home), false means it isn't pressed(Slides are up)
//        if (limitSwitch.getState()) {// This means the state = true
//            linearSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            linearSlides.setTargetPosition(equate(2));
//        }
//    }
    public void init() {
        linearSlides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setArrayList();
        setGripperPosition(1.0);
        grip();
        setRiseLevel(0);
        lift();
        retractOdometryServos();
    }

    /**
     * <img src="https://static.wikia.nocookie.net/looneytunes/images/4/43/Road-runner.png/revision/latest?cb=20230106103454" width="150" height="150"></img>
     * <p style="font-size: 12px; font-family: Arial;">Retracts the odometry servos to their respective RETRACT_POS.</p>
     */
    public void retractOdometryServos() {
        leftServo.setPosition(LEFT_RETRACT_POS);
        rightServo.setPosition(RIGHT_RETRACT_POS);
        frontServo.setPosition(FRONT_RETRACT_POS);
    }

    /**
     * <img src="https://static.wikia.nocookie.net/looneytunes/images/4/43/Road-runner.png/revision/latest?cb=20230106103454" width="150" height="150"></img>
     * <p style="font-size: 12px; font-family: Arial;">Unretracts the odometry servos to their respective UNRETRACT_POS.</p>
     */
    public void unretractOdometryServos() {
        leftServo.setPosition(LEFT_UNRETRACT_POS);
        rightServo.setPosition(RIGHT_UNRETRACT_POS);
        frontServo.setPosition(FRONT_UNRETRACT_POS);
    }


}