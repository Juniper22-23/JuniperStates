package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueLight;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;


public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity slowBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(35, 35, 3.5, Math.toRadians(60), 9.75)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(36, 65.7, Math.toRadians(270)))

                                //dropping the preload__________________________________________________________________________
                                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                                    //conetransporter.setArrayList();
                                })
                                .lineToLinearHeading(new Pose2d(36, 10, Math.toRadians(270)))
                                .lineToLinearHeading(new Pose2d(48, 14.5, Math.toRadians(90)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setGripperPosition(1.0);
                                    //conetransporter.grip();
                                })
                                .back(1.5)
//CYCLE #1________________________________________________________________________________________
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    //conetransporter.setHeight(0);
                                })
                                .lineToLinearHeading(new Pose2d(59.5, 11.25, Math.toRadians(0)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setHeight(1);
                                })
                                .waitSeconds(.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setGripperPosition(.75);
                                    //conetransporter.grip();
                                })
                                .waitSeconds(.25)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setRiseLevel(1);
                                    //conetransporter.lift();
                                })
                                .waitSeconds(.5)
                                .back(1)
                                .lineToLinearHeading(new Pose2d(48, 14.5, Math.toRadians(90)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setGripperPosition(1.0);
                                    //conetransporter.grip();
                                })
                                .back(1.5)
//CYCLE #2_______________________________________________________________________________________
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    //conetransporter.setHeight(0);
                                })
                                .lineToLinearHeading(new Pose2d(59.5, 11.25, Math.toRadians(357.5)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setHeight(3);
                                })
                                .waitSeconds(.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setGripperPosition(.75);
                                    //conetransporter.grip();
                                })
                                .waitSeconds(.25)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setRiseLevel(1);
                                    //conetransporter.lift();
                                })
                                .waitSeconds(.5)
                                .back(1)
                                .lineToLinearHeading(new Pose2d(48, 14.5, Math.toRadians(90)))
                                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                                    //conetransporter.setGripperPosition(1.0);
                                    //conetransporter.grip();
                                })
                                .back(1.5)
                                //CYCLE #3_______________________________________________________________________________________
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    //conetransporter.setHeight(0);
                                })
                                .lineToLinearHeading(new Pose2d(59.5, 11.25, Math.toRadians(355)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setHeight(5);
                                })
                                .waitSeconds(.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setGripperPosition(.75);
                                    //conetransporter.grip();
                                })
                                .waitSeconds(.25)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setRiseLevel(1);
                                    //conetransporter.lift();
                                })
                                .waitSeconds(.5)
                                .back(1)
                                .lineToLinearHeading(new Pose2d(48, 14.5, Math.toRadians(90)))
                                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                                    //conetransporter.setGripperPosition(1.0);
                                    //conetransporter.grip();
                                })
                                .back(1.5)
                                //CYCLE #4_______________________________________________________________________________________
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    //conetransporter.setHeight(0);
                                })
                                .lineToLinearHeading(new Pose2d(59.5, 11.25, Math.toRadians(352.5)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setHeight(7);
                                })
                                .waitSeconds(.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setGripperPosition(.75);
                                    //conetransporter.grip();
                                })
                                .waitSeconds(.25)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setRiseLevel(1);
                                    //conetransporter.lift();
                                })
                                .waitSeconds(.5)
                                .back(1)
                                .lineToLinearHeading(new Pose2d(48, 14.5, Math.toRadians(90)))
                                .UNSTABLE_addTemporalMarkerOffset(0.0, () -> {
                                    //conetransporter.setGripperPosition(1.0);
                                    //conetransporter.grip();
                                })
                                .back(1.5)
//CYCLE #5________________________________________________________________________________________
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    //conetransporter.setHeight(0);
                                })
                                .lineToLinearHeading(new Pose2d(59.5, 11.25, Math.toRadians(350)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setRiseLevel(-1);
                                    //conetransporter.lift();
                                })
                                .waitSeconds(.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setGripperPosition(.75);
                                    //conetransporter.grip();
                                })
                                .waitSeconds(.25)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setRiseLevel(1);
                                    //conetransporter.lift();
                                })
                                .waitSeconds(.5)
                                .back(1)
                                .lineToLinearHeading(new Pose2d(48, 14.5, Math.toRadians(90)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //conetransporter.setGripperPosition(1.0);
                                    //conetransporter.grip();
                                })
                                .back(2)
//PARKING______________________________________________________________________________________
                                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                                    //conetransporter.setRiseLevel(-1);
                                    //conetransporter.lift();
                                })
                                .lineToLinearHeading(new Pose2d(36 + (-24*1), 12, Math.toRadians(270)))
                                .build()
                );

        RoadRunnerBotEntity fastBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(30, 30, 5.021304017899041, Math.toRadians(60), 9.75)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(36, 65.7, Math.toRadians(270)))
                                .splineToLinearHeading(new Pose2d(36,36),Math.toRadians(270))
                                .splineToLinearHeading(new Pose2d(36,23.75),Math.toRadians(0))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(slowBot)
                //.addEntity(fastBot)
                .start();
    }
}