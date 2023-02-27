package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueLight;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;


public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myFirstBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(30, 30, 5.021304017899041, Math.toRadians(60), 9.55)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(35.5, 65, Math.toRadians(270)))
                                .forward(41.5)
                                .turn(Math.toRadians(90))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    ////coneTransporter.setRiseLevel(1);
                                    ////coneTransporter.lift();
                                })
                                .waitSeconds(1)
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    ////coneTransporter.setGripperPosition(1.0);
                                    ////coneTransporter.grip();
                                })
                                .forward(5)

                                .back(5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    ////coneTransporter.setRiseLevel(-1);
                                    ////coneTransporter.lift();
                                })
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(55, 12, Math.toRadians(0)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    ////coneTransporter.setRiseLevel(15);
                                    ////coneTransporter.lift();
                                })
                                .waitSeconds(1)
                                .forward(5)
                                .UNSTABLE_addTemporalMarkerOffset(1.25, () -> {
                                    ////coneTransporter.setPosLevel(15);
                                    ////coneTransporter.lift();
                                })
                                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                                    ////coneTransporter.setGripperPosition(.75);
                                    ////coneTransporter.grip();
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    ////coneTransporter.setRiseLevel(15);
                                    ////coneTransporter.lift();
                                })
                                .waitSeconds(2)
                                .back(5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    ////coneTransporter.setRiseLevel(-1);
                                    ////coneTransporter.lift();
                                })
                                .lineToLinearHeading(new Pose2d(23.5, 13.5, Math.toRadians(270)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    ////coneTransporter.setRiseLevel(3);
                                    ////coneTransporter.lift();
                                })
                                .waitSeconds(1)
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    ////coneTransporter.setGripperPosition(1.0);
                                    ////coneTransporter.grip();
                                })
                                .forward(5)

                                .back(5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    ////coneTransporter.setRiseLevel(-1);
                                    ////coneTransporter.lift();
                                })
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(55, 12, Math.toRadians(0)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    ////coneTransporter.setRiseLevel(15);
                                    ////coneTransporter.lift();
                                })
                                .waitSeconds(1)
                                .forward(5)
                                .UNSTABLE_addTemporalMarkerOffset(1.25, () -> {
                                    ////coneTransporter.setPosLevel(15);
                                    ////coneTransporter.lift();
                                })
                                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                                    ////coneTransporter.setGripperPosition(.75);
                                    ////coneTransporter.grip();
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    ////coneTransporter.setRiseLevel(15);
                                    ////coneTransporter.lift();
                                })
                                .waitSeconds(2)
                                .back(5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    ////coneTransporter.setRiseLevel(-1);
                                    ////coneTransporter.lift();
                                })
                                .lineToLinearHeading(new Pose2d(23.5, 13.5, Math.toRadians(270)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    ////coneTransporter.setRiseLevel(3);
                                    ////coneTransporter.lift();
                                })
                                .waitSeconds(1)
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    ////coneTransporter.setGripperPosition(1.0);
                                    ////coneTransporter.grip();
                                })
                                .forward(5)

                                .back(5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    ////coneTransporter.setRiseLevel(-1);
                                    ////coneTransporter.lift();
                                })
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(55, 12, Math.toRadians(0)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    ////coneTransporter.setRiseLevel(15);
                                    ////coneTransporter.lift();
                                })
                                .waitSeconds(1)
                                .forward(5)
                                .UNSTABLE_addTemporalMarkerOffset(1.25, () -> {
                                    ////coneTransporter.setPosLevel(15);
                                    ////coneTransporter.lift();
                                })
                                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                                    ////coneTransporter.setGripperPosition(.75);
                                    ////coneTransporter.grip();
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    ////coneTransporter.setRiseLevel(15);
                                    ////coneTransporter.lift();
                                })
                                .waitSeconds(2)
                                .back(5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    ////coneTransporter.setRiseLevel(-1);
                                    ////coneTransporter.lift();
                                })
                                .lineToLinearHeading(new Pose2d(23.5, 13.5, Math.toRadians(270)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    ////coneTransporter.setRiseLevel(3);
                                    ////coneTransporter.lift();
                                })
                                .waitSeconds(1)
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    ////coneTransporter.setGripperPosition(1.0);
                                    ////coneTransporter.grip();
                                })
                                .forward(5)

                                .back(5)
                                .lineToLinearHeading(new Pose2d(35.5, 12, Math.toRadians(270)))
                                .back(24)
                                .strafeRight(24 /* *numericalTag*/)
                                .build()
                );

        RoadRunnerBotEntity mySecondBot = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueLight())
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(30, 30, 5.021304017899041, Math.toRadians(60), 9.61)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(35.5, 65, Math.toRadians(270)))
                                .forward(65.5)
                                .turn(Math.toRadians(450))
                                .strafeLeft(64)
                                .build()
                );

        RoadRunnerBotEntity mythirdBot = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(45, 30, 5.021304017899041, Math.toRadians(60), 12)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(35.5, 65, Math.toRadians(270)))
                                .forward(4.5)
                                .strafeRight(12)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    ////coneTransporter.setRiseLevel(1);
                                    ////coneTransporter.lift();
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    ////coneTransporter.setGripperPosition(1.0);
                                    ////coneTransporter.grip();
                                })
                                .forward(5)

                                .back(5)
                                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                    ////coneTransporter.setRiseLevel(-1);
                                    ////coneTransporter.lift();
                                })
                                .waitSeconds(0.5)
                                .strafeLeft(12)
                                .forward(25)

                                .strafeRight(24 /* * numericalTag*/)
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myFirstBot)
                //.addEntity(mySecondBot)
                //.addEntity(mythirdBot)
                .start();
    }
}