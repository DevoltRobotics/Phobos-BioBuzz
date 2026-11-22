package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntakeSubsystem extends SubsystemBase {

    public DcMotor  intakeM;
    public DcMotor transferM;
    public Servo tBlock;
    public Servo flower;

    public double intakePower = 0;
    public double transferPower = 0;


    public Telemetry telemetry;

    //CONSTANTS

    public IntakeSubsystem(HardwareMap hMap) {
        intakeM = hMap.dcMotor.get("in");
        intakeM.setDirection(DcMotorSimple.Direction.REVERSE);

        transferM = hMap.dcMotor.get("trans");
        tBlock = hMap.servo.get("tBlock");
        tBlock = hMap.servo.get("flower");


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
        flower.setPosition(0.5);

    }
    public void flowerDown(){
        flower.setPosition(0.8);
    }
    public void transBlock(){
        tBlock.setPosition(0.6);
    }
    public void transRelease(){
        tBlock.setPosition(0.5);
    }


}