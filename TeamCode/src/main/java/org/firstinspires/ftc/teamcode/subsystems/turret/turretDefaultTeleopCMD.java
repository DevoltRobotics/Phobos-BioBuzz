package org.firstinspires.ftc.teamcode.subsystems.turret;


import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.vision.VisionSubsystem;

import java.util.function.BooleanSupplier;

@Configurable
public class turretDefaultTeleopCMD extends CommandBase {

    private final turretSubsystem turretSb;

    private final VisionSubsystem visionSb;

    BooleanSupplier isManual;

    BooleanSupplier isClose;

    BooleanSupplier isShooting;

    BooleanSupplier isPoseEstimate;

    Gamepad gamepad;

    static int manualIncrement = 5;

    private final ElapsedTime waitAimTimer;

    public turretDefaultTeleopCMD(turretSubsystem turretSb, VisionSubsystem visionSb, BooleanSupplier isManual, Gamepad gamepad) {
        this.turretSb = turretSb;

        this.visionSb = visionSb;

        this.gamepad = gamepad;

        this.isManual = isManual;

        this.isClose = isClose;

        this.isShooting = isShooting;

        this.isPoseEstimate = isPoseEstimate;


        waitAimTimer = new ElapsedTime();

        addRequirements(turretSb);
    }

    @Override
    public void execute() {

        /*double gx = Math.cos(turretSb.goalAngleRad);
        double gy = Math.sin(turretSb.goalAngleRad);

        double px = -gy;
        double py =  gx;

        double lateralVelocity = turretSb.velX * px + turretSb.velY * py;

        double motionFF = lateralVelocity * TurretSubsystem.kBotToTurretVel;


         */
        if (gamepad.right_bumper || gamepad.left_bumper) {
            turretSb.realIsManual = true;

        } else {
            turretSb.realIsManual = isManual.getAsBoolean();

        }

        Double tX = visionSb.getAllianceTX();

        /*if (turretSb.realIsManual) {
            if (gamepad.right_bumper) {
                waitAimTimer.reset();
                turretSb.targetAngle += manualIncrement;

            } else if (gamepad.left_bumper) {
                waitAimTimer.reset();
                turretSb.targetAngle -= manualIncrement;

            }

        }

         */

        double target = turretSb.getTargetAngle();

        if (tX != null){
            double llTarget = turretSb.llPidf.calculate(tX);
            target -= llTarget;

            PanelsTelemetry.INSTANCE.getFtcTelemetry().addData("llTarget", llTarget);

        } else if (gamepad.right_bumper) {
            waitAimTimer.reset();
            target += manualIncrement;

        } else if (gamepad.left_bumper) {
            waitAimTimer.reset();
            target -= manualIncrement;

        }
        PanelsTelemetry.INSTANCE.getFtcTelemetry().addData("angleTargetCommand", target);



        turretSb.setAngle(target);

        }


    }


