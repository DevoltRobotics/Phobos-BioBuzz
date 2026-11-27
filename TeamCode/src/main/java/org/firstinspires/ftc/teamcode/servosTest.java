package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp
@Configurable
public class servosTest extends OpMode {

    public static double openPose = 0.5; // palito abierto
    public static double closePose = 0.4; //palito cerrado
    public static double flowerVert = 0.3; // triangulo arriba
    public static double flowerHor = 0.8; // triangklo abjo


    Servo tBlock, flower, turret, turret2;


    @Override
    public void init() {
        flower = hardwareMap.get(Servo.class, "flower");
        tBlock = hardwareMap.get(Servo.class, "tBlock");
        turret = hardwareMap.get(Servo.class, "turret");
        turret2 = hardwareMap.get(Servo.class, "turret2");


    }

    @Override
    public void loop() {
        if (gamepad1.a){
            flower.setPosition(flowerHor);
        } else if (gamepad1.b) {
            tBlock.setPosition(openPose);
        } else if (gamepad1.x) {
            turret.setPosition(0.5);

        } else if (gamepad1.y) {
            turret2.setPosition(0.5);

        } else if (gamepad1.dpad_down) {
            flower.setPosition(flowerVert);

        } else if (gamepad1.dpad_left) {
            tBlock.setPosition(closePose);
        }

    }
}
