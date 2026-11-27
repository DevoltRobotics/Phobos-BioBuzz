package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.util.InterpLUT;

import org.firstinspires.ftc.teamcode.subsystems.turret.turretSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.vision.VisionSubsystem;

public class shooterToBasketTeleOpCMD extends CommandBase {


    public static InterpLUT vsFunc = new InterpLUT();

    private Double provTarget;


    static {
        vsFunc.add(1, 1530);
        vsFunc.add(24.5, 1520);
        vsFunc.add(28.5, 1465);
        vsFunc.add(37.5, 1400);
        vsFunc.add(65, 1270);
        vsFunc.add(100, 1230);
        vsFunc.add(130, 1150);
        vsFunc.add(200, 1100);
        vsFunc.add(400, 1100);

        vsFunc.createLUT();



    }

    private final shooterSubsystem shooterSb;

    private final VisionSubsystem visionSb;

    private final turretSubsystem turretSb;

    public shooterToBasketTeleOpCMD(shooterSubsystem shooterSb, VisionSubsystem visionSb, turretSubsystem turretSb, Double provTarget) {
        this.shooterSb = shooterSb;

        this.visionSb = visionSb;

        this.turretSb = turretSb;

        this.provTarget = provTarget;

        addRequirements(this.shooterSb);
    }


    @Override
    public void execute() {
        Double TargetA = visionSb.getAllianceTA();

        if (TargetA != null) {
            double targetA = Range.clip(TargetA, 24.5, 200);
            shooterSb.shooterTarget = () ->vsFunc.get(targetA);



        /*else if (waitAimTimer.milliseconds() > 150){
            double tagSizeDistance = Range.clip(distanceLUT.get(turretSb.getDistanceToGoal()), 50.5, 154);

            shooterSb.shooterTarget = vsFunc.get(tagSizeDistance);
        }
        */



    }

}
}
