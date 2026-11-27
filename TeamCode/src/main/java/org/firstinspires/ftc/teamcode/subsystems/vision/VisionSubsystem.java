package org.firstinspires.ftc.teamcode.subsystems.vision;


import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utilities.Alliance;

public class VisionSubsystem extends SubsystemBase {

    public Limelight3A ll;

    public static int limelightTaRatio = 100;

    public LLResult result;


    public final Alliance alliance;

    public boolean isAuto;

    public Telemetry telemetry;

    Follower follower;

    public VisionSubsystem(HardwareMap hMap, Telemetry telemetry, Alliance alliance, boolean isAuto, Follower follower) {
        ll = hMap.get(Limelight3A.class, "limelight");

        this.alliance = alliance;

        this.isAuto = isAuto;

        this.telemetry = telemetry;

        this.follower = follower;

        ll.setPollRateHz(100);
        ll.start();


        if (alliance == Alliance.BLUE){
            ll.pipelineSwitch(0);
        } else if (alliance == Alliance.RED) {
            ll.pipelineSwitch(1);
        }

    }

    public void periodic() {
        result = ll.getLatestResult();

    }

    //double = shooterSubsystem.getAllianceTA

    public Double getAllianceTA() {
        if (result != null && result.isValid() && !result.getFiducialResults().isEmpty()) {

                return result.getTa() * limelightTaRatio;

        }

        return null;
    }


    public Double getAllianceTX() {

        if (result != null && result.isValid() && !result.getFiducialResults().isEmpty()) {


                return result.getTx();
            }


        return null;
    }


    public Double getAllianceTXDegrees() {

        if (result != null && result.isValid() && !result.getFiducialResults().isEmpty()) {

        }

        return null;
    }


}
