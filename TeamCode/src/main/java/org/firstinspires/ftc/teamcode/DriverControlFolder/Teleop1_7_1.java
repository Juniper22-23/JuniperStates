package org.firstinspires.ftc.teamcode.DriverControlFolder;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Controller;

import java.util.Objects;

@TeleOp(name = "Tele-op(1.5)", group = "Tele-Op")
public class Teleop1_7_1 extends LinearOpMode {

    // declare class variables here
    private Controller controller;
    private FieldCenterAuto1_5 fieldCenterAuto;
    private ConeTransporter1_5 coneTransporter;
    // Check if B is pressed
    private boolean b_Press = false;
    public int triggerPressCount = 0;
    private boolean stackState = false;

    public enum TIP {
        TIPPING, NOT_TIPPING, ON_STACKS
    }

    public void runOpMode() {
        telemetry.clear();


        try {
            // setup
            controller = new Controller(gamepad1, gamepad2);
            fieldCenterAuto = new FieldCenterAuto1_5(telemetry, hardwareMap);
            coneTransporter = new ConeTransporter1_5(telemetry, hardwareMap);


        } catch (Exception exception) {
            telemetry.addLine("Outside of the while loop:");
            telemetry.addLine(exception.getMessage());
            telemetry.update();
            if (Objects.equals(exception.getMessage(), "The IMU was not initialized")) {
                telemetry.addData("-", "Detected");
                telemetry.update();
            }

        }

        telemetry.update();
        coneTransporter.init();
        TIP tip = TIP.NOT_TIPPING;
        waitForStart();
        while (opModeIsActive()) {
            try {
                controller.update();
                //FIELDCENTER_______________________________________________________________________________
                double gamepadX;
                double gamepadY;
                double gamepadRot;
                boolean rotationToggle = false;
                boolean strafeToggle = false;
                if (tip == TIP.NOT_TIPPING || tip == TIP.ON_STACKS) {
                    if (Math.abs(controller.gamepad1X) > 0.01) {
                        gamepadX = controller.gamepad1X;
                    } else if (Math.abs(controller.gamepad2X) > 0.01) {
                        gamepadX = controller.gamepad2X;
                    } else {
                        gamepadX = 0;
                    }
                    if (Math.abs(controller.gamepad1Y) > 0.01) {
                        gamepadY = controller.gamepad1Y;
                    } else if (Math.abs(controller.gamepad2Y) > 0.01) {
                        gamepadY = controller.gamepad2Y;
                    } else {
                        gamepadY = 0;
                    }
                    if (Math.abs(controller.gamepad1Rot) > 0.01) {
                        gamepadRot = -controller.gamepad1Rot;
                    } else if (Math.abs(controller.gamepad2Rot) > 0.01) {
                        gamepadRot = -controller.gamepad2Rot;
                    } else {
                        gamepadRot = 0;
                    }
                    fieldCenterAuto.drive(gamepadX, gamepadY, gamepadRot, rotationToggle, strafeToggle);



                }

                if (controller.dpadDown){
                    ConeTransporter1_5.zeroMode = true;
                }

                //Code for each stack Height
                if (controller.y) {
                    coneTransporter.automation = false;
                    stackState = false;
                    triggerPressCount = 0;
                    ConeTransporter1_5.setHeight(ConeTransporter1_5.equate(ConeTransporter1_5.LINEAR_SLIDES_HIGH));
                    ConeTransporter1_5.ledTimer.reset();
                } else if (controller.a) {
                    coneTransporter.automation = false;
                    stackState = false;
                    triggerPressCount = 0;
                    ConeTransporter1_5.setHeight(ConeTransporter1_5.equate(ConeTransporter1_5.LINEAR_SLIDES_LOW));
                    ConeTransporter1_5.ledTimer.reset();

                } else if (controller.x) {
                    coneTransporter.automation = false;
                    stackState = false;
                    triggerPressCount = 0;
                    ConeTransporter1_5.setHeight(ConeTransporter1_5.equate(ConeTransporter1_5.LINEAR_SLIDES_MEDIUM));
                    ConeTransporter1_5.ledTimer.reset();
                } else if (controller.rightTrigger) {
                coneTransporter.automation = false;
                stackState = false;
                triggerPressCount = 0;
                ConeTransporter1_5.setHeight(ConeTransporter1_5.equate(32));
                }

                //This will check if b is pressed if yes then it will check the position of the slides and decide where it should go
                if (controller.b & !b_Press) {
                    ConeTransporter1_5.ledTimer.reset();
                    coneTransporter.automation = false;
                    b_Press = true;
                    stackState = false;
                    triggerPressCount = 0;
                    if (ConeTransporter1_5.target == ConeTransporter1_5.equate(ConeTransporter1_5.LINEAR_SLIDES_NORM)) {
                        ConeTransporter1_5.setHeight(ConeTransporter1_5.equate(ConeTransporter1_5.LINEAR_SLIDES_IN_CONE));
                    } else {
                        ConeTransporter1_5.setHeight(ConeTransporter1_5.equate(ConeTransporter1_5.LINEAR_SLIDES_NORM));
                    }
                } else {
                    b_Press = false;
                }

                if (controller.leftTrigger) {
                        if(!stackState && (coneTransporter.linearSlides.getTargetPosition() != ConeTransporter1_5.equate(ConeTransporter1_5.AUTO_LINEAR_SLIDES_15))){
                            ConeTransporter1_5.ledTimer.reset();
                            coneTransporter.automation = false;
                            ConeTransporter1_5.setHeight((ConeTransporter1_5.equate(ConeTransporter1_5.AUTO_LINEAR_SLIDES_15)));
                            stackState = true;
                        } else {
                            coneTransporter.automation = true;
                            coneTransporter.setGripperPosition(1.0);
                            coneTransporter.coneSense();
                            stackState = false;
                        }
                    tip = TIP.ON_STACKS;
                }
                coneTransporter.coneSense();

                //GRIPPER__________________________________________________________________________________

                if (controller.leftBumper && !(controller.rightBumper)) {
                    triggerPressCount = 0;
                    coneTransporter.setGripperPosition(.75);
                }

                if (controller.rightBumper && !(controller.leftBumper)) {
                    triggerPressCount = 0;
                    coneTransporter.setGripperPosition(1.0);
                }

                coneTransporter.lightUpdate();

                telemetry.addData("-", "tip is activated");

                float roll = fieldCenterAuto.getRoll();
                if (tip != TIP.ON_STACKS) {
                    if (roll <= 75) {
                        tip = TIP.TIPPING;
                        fieldCenterAuto.checkifrobotnottipping();
                    } else if (roll >= 110) {
                        tip = TIP.TIPPING;
                        fieldCenterAuto.checkifrobotnottipping();
                    } else {
                        tip = TIP.NOT_TIPPING;
                        fieldCenterAuto.rightBackMotor.setDirection(DcMotor.Direction.FORWARD);
                        fieldCenterAuto.rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
                        fieldCenterAuto.leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
                        fieldCenterAuto.leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
                    }
                }
                coneTransporter.loop();
                coneTransporter.zeroSlides();

            } catch (Exception exception) {
                telemetry.addLine("Inside of the while loop:");
                telemetry.clear();
                telemetry.addLine(exception.getMessage());
            }
        }
    }
}
