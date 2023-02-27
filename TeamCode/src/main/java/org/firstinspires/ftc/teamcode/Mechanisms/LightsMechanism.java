/*
package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Mechanism;

import java.util.Objects;

public class LightsMechanism extends Mechanism{

    public DigitalChannel blue;
    public DigitalChannel green;
    public DigitalChannel white;
    public DigitalChannel red;


    public LightsMechanism(Telemetry telemetry, HardwareMap hardwareMap) {

    super(telemetry, hardwareMap);
        blue = this.hardwareMap.get(DigitalChannel.class, "blue");//0
        green = this.hardwareMap.get(DigitalChannel.class, "green");//1
        white = this.hardwareMap.get(DigitalChannel.class, "white");//2
        red = this.hardwareMap.get(DigitalChannel.class, "red");//3
        blue.setMode(DigitalChannel.Mode.OUTPUT);
        green.setMode(DigitalChannel.Mode.OUTPUT);
        white.setMode(DigitalChannel.Mode.OUTPUT);
        red.setMode(DigitalChannel.Mode.OUTPUT);



    }

    public void runLights(String color){
        boolean redState = false;
        boolean greenState = false;
        boolean blueState = false;


        // blue wire from port 0 purple links to BLUE
        // white wire from port 1 oragane links to GREEN
        // white wire from port 3 links blue wire to RED






        //telemetry.addData("Clear", sensorRGB.alpha());
        telemetry.addData("Red  ", redState());
        telemetry.addData("Green", greenState());
        telemetry.addData("Blue ", blueState());



        if (Objects.equals(color, "red")) {
            redState = true;
            greenState = false;
            blueState = false;
        } else if (Objects.equals(color, "green")) {
            redState = false;
            greenState = true;
            blueState = false;
        } else if (Objects.equals(color, "blue")) {
            redState = false;
            greenState = false;
            blueState = true;
        } else if (Objects.equals(color, "yellow")) {
            redState = true;
            greenState = true;
            blueState = false;
        } else if (Objects.equals(color, "cyan")) {
            redState = false;
            greenState = true;
            blueState = true;
        }else if (Objects.equals(color, "magenta")) {
            redState = true;
            greenState = false;
            blueState = true;
        }else if (Objects.equals(color, "clear")) {
            redState = false;
            greenState = false;
            blueState = false;
        }else if (Objects.equals(color, "white")) {
            redState = true;
            greenState = true;
            blueState = true;
        }
        //blue is always the lower one

        // We have to flip the variables because they are reversed
        blue.setState(!blueState);
        green.setState(!greenState);
        red.setState(!redState);

    }

    private int blueState() {
        return 0;
    }

    private int greenState() {
        return 0;
    }

    private int redState() {
        return 0;
    }

    // This is the code that executes the connection of the lights in the robot

    public void runLightsPattern() {
    }

    public int getLightInformation(){
        int lightTime = 10;
        return lightTime;
    }
}

*/
