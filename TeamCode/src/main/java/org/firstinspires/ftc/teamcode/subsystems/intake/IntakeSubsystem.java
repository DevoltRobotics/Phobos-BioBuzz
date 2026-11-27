package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Configurable
public class IntakeSubsystem extends SubsystemBase {

    public DcMotor  intakeM;
    public DcMotor transferM;
    public Servo tBlock;
    public Servo flower;

    public static double openPose = 0.5;
    public static double closePose = 0.43;
    public static double flowerVert = 0.3;
    public static double flowerHor = 0.75;



    public double intakePower = 0;
    public double transferPower = 0;


    public Telemetry telemetry;

    //CONSTANTS
/*
ta
 */
    public IntakeSubsystem(HardwareMap hMap) {
        intakeM = hMap.dcMotor.get("in");
        intakeM.setDirection(DcMotorSimple.Direction.REVERSE);

        transferM = hMap.dcMotor.get("trans");
        tBlock = hMap.servo.get("tBlock");
        flower = hMap.servo.get("flower");


        intakeM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        transferM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    @Override
    public void periodic() {

    }

    public void setIntakePower(double inPower, double transPower){
        intakeM.setPower(inPower);

        transferM.setPower(transPower);

    }

    public void flowerUp(){
        flower.setPosition(flowerVert);

    }
    public void flowerDown(){
        flower.setPosition(flowerHor);
    }
    public void transBlock(){
        tBlock.setPosition(closePose);
    }
    public void transRelease(){
        tBlock.setPosition(openPose);
    }


}