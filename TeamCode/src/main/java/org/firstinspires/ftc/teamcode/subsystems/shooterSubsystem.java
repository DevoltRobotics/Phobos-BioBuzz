package org.firstinspires.ftc.teamcode.subsystems;


import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utilities.Alliance;

import java.util.function.DoubleSupplier;

public class shooterSubsystem extends SubsystemBase {

    public Follower follower;

    //SHOOTER
    DcMotorEx shootR;
    DcMotorEx shootL;


    PIDFController shooterController;
    PIDFCoefficients shooterCoeffs;

    Telemetry telemetry;

    double feedforward = 0;

    public double shooterkV = 0.00036;

    public DoubleSupplier shooterTarget = () -> 0;

    public double shooterError;

    //HOOD


    //TURRET


    public Alliance alliance;


    public double robotToGoalAngle;
    public double turretToGoalAngle;

    public double distanceToGoal = 0;

    boolean isAuto;

    public static boolean useSecondaryPID = true;


    public shooterSubsystem(HardwareMap hMap, Telemetry telemetry, Follower follower, Alliance alliance, boolean isAuto) {

        //SHOOTER
        shootL = hMap.get(DcMotorEx.class, "shootL");
        shootR = hMap.get(DcMotorEx.class, "shootR");

        shootL.setDirection(DcMotorSimple.Direction.REVERSE);
        shootL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shootR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        shootL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shootR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        shooterController = new PIDFController(shooterCoeffs);


        this.follower = follower;
        this.telemetry = telemetry;
        this.alliance = alliance;

    }

    @Override
    public void periodic() {


        double motorVel = shootL.getVelocity();

        double sTarget = shooterTarget.getAsDouble();

        shooterController.setSetPoint(sTarget);

        double shooterTargetPwr = (shooterkV * sTarget) + shooterController.calculate(motorVel);


        shooterError = (sTarget - motorVel);

        telemetry.addLine("-----------------------------");
        telemetry.addData("shooterTargetPwr", shooterTargetPwr);
        telemetry.addData("shooterTarget", shooterTarget);
        telemetry.addData("shooterError", shooterError);
        PanelsTelemetry.INSTANCE.getFtcTelemetry().addData("shooterTarget", shooterTarget);


    }

    public void setShooterTarget(double target) {
        shooterTarget = () ->target;

    }
    public void setShooterTarget(DoubleSupplier target) {
        shooterTarget = target;
    }

    public void setShooterPower(double leftShootPower, double rightShootPower ){
        shootL.setPower(leftShootPower);

        shootR.setPower(rightShootPower);

    }





}
