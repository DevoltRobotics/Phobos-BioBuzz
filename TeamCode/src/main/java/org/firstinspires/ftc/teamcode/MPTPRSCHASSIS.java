package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class MPTPRSCHASSIS extends OpMode {

    DcMotor fr, br, fl, bl;

    @Override
    public void init() {
        fr = hardwareMap.get(DcMotor.class, "fr");
        br = hardwareMap.get(DcMotor.class, "br");
        fl = hardwareMap.get(DcMotor.class, "fl");
        bl = hardwareMap.get(DcMotor.class, "bl");


    }

    @Override
    public void loop() {
if (gamepad1.a){
    fr.setPower(1);
} else if (gamepad1.b) {
    br.setPower(1);
} else if (gamepad1.x) {
    fl.setPower(1);

} else if (gamepad1.y) {
    bl.setPower(1);

}
else {
    fr.setPower(0);
    br.setPower(0);
    fl.setPower(0);
    bl.setPower(0);
}
    }
}
