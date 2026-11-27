package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.pedropathing.tuning.autotune.Procedure;
import com.pedropathing.tuning.autotune.Tuner;

import org.firstinspires.ftc.teamcode.pedro.procedures.ForesightTuner;
import org.firstinspires.ftc.teamcode.pedro.procedures.MecanumTuner;
import org.firstinspires.ftc.teamcode.pedro.procedures.PinpointTuner;
import org.firstinspires.ftc.teamcode.pedro.procedures.Tests;

public class Tuning {
    @Tuner
    // Tuners go here
    public   static Procedure mecanumTuner(){
        return new MecanumTuner();
    }
    @Tuner
    public static Procedure tests(){
        return new Tests(hardwaremap -> new Mecanum(hardwaremap, Constants.drivetrainConfig), null,null);
    }
    @Tuner
    public static Procedure pinpointTuner(){
        return new PinpointTuner();
    }
    @Tuner
    public static Procedure foresightTuner(){
        return new ForesightTuner(hardwaremap -> new PinpointLocalizer(hardwaremap, Constants.localizerConfig), (hardwaremap) -> new Mecanum(hardwaremap, Constants.drivetrainConfig));
    }
}
