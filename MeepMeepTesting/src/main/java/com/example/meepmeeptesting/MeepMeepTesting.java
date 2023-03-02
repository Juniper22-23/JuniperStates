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
                .setConstraints(30, 30, 5.021304017899041, Math.toRadians(60), 9.75)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(36, 65.7, Math.toRadians(270)))

                                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                                    //coneTransporter.setArrayList();
                                })
                                .lineToLinearHeading(new Pose2d(36, 23.75, Math.toRadians(270)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //coneTransporter.setRiseLevel(1);
                                    //coneTransporter.lift();
                                })
                                .turn(Math.toRadians(90))
                                .forward(2.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //coneTransporter.setGripperPosition(1.0);
                                    //coneTransporter.grip();
                                })
                                .back(2.5)
                                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                                    //coneTransporter.setRiseLevel(0);
                                    //coneTransporter.lift();
                                })
                                .turn(Math.toRadians(-90))
                                .lineToLinearHeading(new Pose2d(36, 11.25, Math.toRadians(270)))
                                .turn(Math.toRadians(90))
                                //.lineToLinearHeading(new Pose2d(59.5, 11.25, Math.toRadians(0)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //coneTransporter.setHeight(0);
                                })
                                .forward(23.5)
                                //Start of a Cycle
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //coneTransporter.setHeight(1);
                                })
                                .waitSeconds(0.5)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //coneTransporter.setGripperPosition(0.75);
                                    //coneTransporter.grip();
                                })
                                .waitSeconds(0.75)
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //coneTransporter.setRiseLevel(1);
                                    //coneTransporter.lift();
                                })
                                .waitSeconds(0.75)
                                .back(2)
                                .strafeLeft(4)
                                .turn(Math.toRadians(145))
                                .forward(2.5)
                                .strafeRight(2.25)
                                .forward(1)
                                //.lineToLinearHeading(new Pose2d(55, 18, Math.toRadians(145)))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //coneTransporter.setGripperPosition(1.0);
                                    //coneTransporter.grip();
                                })
                                .back(1)
                                .strafeLeft(2.25)
                                .back(2.5)
                                .turn(Math.toRadians(-145))
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    //coneTransporter.setHeight(0);
                                })
                                .lineToLinearHeading(new Pose2d(59.5, 11.65, Math.toRadians(0)))
                                //End of a Cycle
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
                .addEntity(fastBot)
                .start();
    }
}