package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;


import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.command.SubsystemBase;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utilities.Alliance;

public class pedroPathingSubsystem extends SubsystemBase {






        public Follower follower;

        public static double slowModeMultiplier = 0.4;



        Telemetry telemetry;

        public static Pose EndPose = new Pose(0,0);

        Alliance alliance;

        public pedroPathingSubsystem(Follower follower, Telemetry telemetry, Alliance alliance) {
            this.follower = follower;

            this.telemetry = telemetry;
            this.alliance = alliance;

        }

        @Override
        public void periodic() {
        /*telemetryM.debug("x:" + follower.getPose().getX());
        telemetryM.debug("y:" + follower.getPose().getY());
        telemetryM.debug("heading:" + follower.getPose().getHeading());
        telemetryM.debug("total heading:" + follower.getTotalHeading());

         */

        }

        public Command followPathCmd(Path path) {
            return new FollowPathCmd(path);
        }





        public Command fieldCentricCmd(Gamepad gamepad, double angleOffset) {
            return new FieldCentricCmd(gamepad, angleOffset);
        }



        class FieldCentricCmd extends CommandBase {
            Gamepad gamepad;

            double angleOffset;

            FieldCentricCmd(Gamepad gamepad, double angleOffset) {
                this.gamepad = gamepad;

                this.angleOffset = angleOffset;
                addRequirements(pedroPathingSubsystem.this);
            }

            @Override
            public void initialize() {

            }

            @Override
            public void execute() {
                double multiplier = 1;

                if (gamepad.right_trigger >= 0.4) {
                    multiplier = slowModeMultiplier;
                }

                DrivePowers powers = ManualDrive.fieldCentric(
                        -gamepad1.left_stick_y*multiplier,
                        gamepad1.left_stick_x*multiplier,
                        gamepad1.right_stick_x*multiplier,
                        follower.pose().heading()
                );
                follower.manual(powers);

            /*if (gamepad.yWasPressed()) {
                follower.setPose(new Pose(follower.getPose().getX(), follower.getPose().getY(), angleOffset));
            }

             */
            }

            @Override
            public void end(boolean interrupted) {
                //follower.breakFollowing();
            }

            @Override
            public boolean isFinished() {
                return false;
            }
        }

        class FollowPathCmd extends CommandBase {
            Path path;

            FollowPathCmd(Path path) {
                this.path = path;

                addRequirements(pedroPathingSubsystem.this);
            }

            @Override
            public void initialize() {
                follower.follow(path);

            }

            @Override
            public boolean isFinished() {

                return !follower.isBusy();

            }
        }





    }

