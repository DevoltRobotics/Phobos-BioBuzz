package org.firstinspires.ftc.teamcode.teleops;

import static org.firstinspires.ftc.teamcode.subsystems.pedroPathingSubsystem.EndPose;

import com.bylazar.configurables.annotations.Configurable;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.subsystems.commands.turretManual;
import org.firstinspires.ftc.teamcode.subsystems.pedroPathingSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.shooterToBasketTeleOpCMD;
import org.firstinspires.ftc.teamcode.subsystems.turretSubsystem;
import org.firstinspires.ftc.teamcode.utilities.Alliance;
import org.firstinspires.ftc.teamcode.utilities.OpModeCommand;

import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.button.Button;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

@Configurable
public abstract class Teleop extends OpModeCommand {
    public Teleop(Alliance alliance) {
        super(alliance, false);
    }

    GamepadEx chassis;
    GamepadEx garra;
    double angleOffSet;
    static double shooterTarget;
    turretSubsystem turretSb;

    @Override
    public void initialize() {

        //follower.setPose(new Pose(7.5, 8.2, 0));

        follower.setPose(EndPose);

        chassis = new GamepadEx(gamepad1);
        garra = new GamepadEx(gamepad2);


        ///CHASSIS

        CommandScheduler.getInstance().setDefaultCommand(pedroSb, pedroSb.fieldCentricCmd(gamepad1, angleOffSet));


        /// TORRETA (bumpers de la garra)

        turretSb = new turretSubsystem(hardwareMap);
        CommandScheduler.getInstance().registerSubsystem(turretSb);
        turretSb.setAngle(0);

        CommandScheduler.getInstance().setDefaultCommand(
                turretSb,
                new turretManual(
                        turretSb,
                        () -> gamepad2.left_bumper,
                        () -> gamepad2.right_bumper
                )
        );


        /// //////////////////////////////////

        Trigger intakeIn = new Trigger(() -> gamepad2.right_trigger >= 0.3);
        Trigger intakeOut = new Trigger(() -> gamepad2.left_trigger >= 0.3);

        intakeIn.whileActiveOnce(new InstantCommand(()-> intakeSb.setIntakePower(1,0.5)));
        intakeOut.whileActiveOnce(new InstantCommand(()-> intakeSb.setIntakePower(-1,-0.5)));


        Button blockPollen = new GamepadButton(
                garra,
                GamepadKeys.Button.DPAD_DOWN);

        blockPollen.whenPressed(new InstantCommand(() -> intakeSb.transBlock()));

        Button releasePollen = new GamepadButton(
                garra,
                GamepadKeys.Button.DPAD_UP);

        releasePollen.whenPressed(new InstantCommand(() -> intakeSb.transBlock()));


        Button flowerUp = new GamepadButton(
                garra,
                GamepadKeys.Button.Y);
        flowerUp.whenPressed(new InstantCommand(() -> intakeSb.flowerUp()));


        Button flowerDown = new GamepadButton(
                garra,
                GamepadKeys.Button.X);
        flowerDown.whenPressed(new InstantCommand(() -> intakeSb.flowerDown()));


        Button startShooter = new GamepadButton(
                garra,
                GamepadKeys.Button.B);
        startShooter.whenPressed(new InstantCommand(() -> shooterSb.setShooterTarget(() -> shooterTarget)));


        Button stopShooter = new GamepadButton(
                garra,
                GamepadKeys.Button.A);
        stopShooter.whenPressed(new InstantCommand(() -> shooterSb.setShooterTarget(0)));


        CommandScheduler.getInstance().setDefaultCommand(shooterSb,new shooterToBasketTeleOpCMD(shooterSb, VisionSb, turretSb, 1700.0));

    }

    @Override
    public void start() {
    }

    @Override
    public void run() {
        pedroPathingSubsystem.EndPose = follower.pose();

        telemetry.addLine("-----------------------------");
        telemetry.addData("EndPose", pedroPathingSubsystem.EndPose);
        telemetry.addData("Torreta (°)", "%.1f", turretSb.getTargetAngle());
    }
}

