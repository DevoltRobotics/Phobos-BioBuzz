package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp
public class testShooter extends OpMode {
    DcMotorEx inM;
    DcMotorEx transM;

    DcMotorEx shooterR;
    DcMotorEx shooterL;

    PIDFController shooterController;

    public double shooterTarget = 0.0;

    public double shooterkV = 0.00036;
    public Limelight3A ll;

    public static int limelightTaRatio = 100;

    public LLResult result;

    public PIDFCoefficients shooterCoeffs = new PIDFCoefficients(0.01, 0.0, 0.0, 0);

    @Override
    public void init() {
        inM = hardwareMap.get(DcMotorEx.class, "in");
        transM = hardwareMap.get(DcMotorEx.class, "trans");

        shooterR = hardwareMap.get(DcMotorEx.class, "shootR");
        shooterL = hardwareMap.get(DcMotorEx.class, "shootL");

        ll = hardwareMap.get(Limelight3A.class, "limelight");

        shooterR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        inM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        transM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        shooterR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooterL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        shooterController = new PIDFController(shooterCoeffs);

        ll.setPollRateHz(100);
        ll.start();
        ll.pipelineSwitch(0);
    }

    @Override
    public void loop() {

        if (gamepad1.right_trigger > 0.5){
            inM.setPower(1);
            transM.setPower(1);
        } else if (gamepad1.left_trigger > 0.3){
            inM.setPower(-1);
            transM.setPower(-1);

        }else {
            inM.setPower(0);
            transM.setPower(0);
        }

        if (gamepad1.dpad_up){
            shooterTarget += 5;
        } else if (gamepad1.dpad_down) {
            shooterTarget-= 5;

        }else if (gamepad1.dpad_right){
            shooterTarget = 0;
        }

        if (gamepad1.a){
            shooterkV += 0.000001;
        } else if (gamepad1.b) {
            shooterkV -= 0.000001;
        }



        double motorVel = -shooterR.getVelocity();

        shooterController.setSetPoint(shooterTarget);

        double shooterTargetPwr = Range.clip((shooterkV * shooterTarget) + shooterController.calculate(motorVel), -1, 1);

        double shooterTargetPwrReference = (shooterkV * shooterTarget);

        shooterR.setPower(shooterTargetPwr);
        shooterL.setPower(-shooterTargetPwr);

        double shooterError = (shooterTarget - motorVel);

        telemetry.addLine("-----------------------------");
        telemetry.addData("shooterTargetPwrReference", shooterTargetPwrReference);

        telemetry.addData("shooterTargetPwr", shooterTargetPwr);
        telemetry.addData("shooterTarget", shooterTarget);
        telemetry.addData("shooterError", shooterError);
        telemetry.addData("motorVel", motorVel);
        telemetry.addData("Kv (x10)", shooterkV * 10);


        telemetry.addData("shooterCurrent", shooterR.getCurrent(CurrentUnit.AMPS));
        telemetry.addData("allianceTA", getAllianceTA());
        result = ll.getLatestResult();

    }
    public Double getAllianceTA() {
        if (result != null && result.isValid() && !result.getFiducialResults().isEmpty()) {

            return result.getTa() * limelightTaRatio;

        }

        return null;
    }

}