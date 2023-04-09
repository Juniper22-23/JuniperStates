package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueLight;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;


public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity slowBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(35, 35, 3.5, Math.toRadians(60), 9.75)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(36.00, 24.00, Math.toRadians(180.00)))
                        .splineToSplineHeading(new Pose2d(48.00, 12.00, Math.toRadians(180.00)), Math.toRadians(0.00))
                        .build()
                );
        RoadRunnerBotEntity fastBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(40, 40, 4, Math.toRadians(50), 9.75)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(36.00, 65, Math.toRadians(270)))
                                .splineToLinearHeading(new Pose2d(36.00, 24.00, Math.toRadians(0)), Math.toRadians(270))
                                .setTangent(Math.toRadians(270))
                                .splineToLinearHeading(new Pose2d(48.00, 12.00, Math.toRadians(90)), Math.toRadians(0))
                                .setTangent(Math.toRadians(300))
                                .splineToLinearHeading(new Pose2d(58.00, 12.00, Math.toRadians(0)), Math.toRadians(45))
                                .setTangent(Math.toRadians(225))
                                .splineToLinearHeading(new Pose2d(48.00, 12.00, Math.toRadians(90)), Math.toRadians(90))

                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                //.addEntity(slowBot)
                .addEntity(fastBot)
                .start();
    }
}