package org.firstinspires.ftc.teamcode.utilities;

import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.Subsystem;

import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.vision.VisionSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.pedroPathingSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.shooterSubsystem;

public abstract class OpModeCommand extends OpMode {

    public Follower follower;
    private final ElapsedTime timer = new ElapsedTime();

    private double lastTime = 0;
    public final Alliance currentAlliance;

    public final boolean isAuto;

    public VisionSubsystem VisionSb;

    public pedroPathingSubsystem pedroSb;
    public shooterSubsystem shooterSb;
    public IntakeSubsystem intakeSb;

    public boolean isRobotFar = false;

    public OpModeCommand(Alliance alliance, boolean isAuto ) {
        this.currentAlliance = alliance;
        this.isAuto = isAuto;
    }

    //reinicia la lista de comandos
    public void reset() {
        CommandScheduler.getInstance().reset();
    }

    //corre el scheduler
    public void run() {
        //run();

    }

    //programa comandos al scheduler
    public void schedule(Command... commands) {
        CommandScheduler.getInstance().schedule(commands);
    }

    //registra subsistemas al scheduler
    public void register(Subsystem... subsystems) {
        CommandScheduler.getInstance().registerSubsystem(subsystems);
    }

    @Override
    public void init() {
        CommandScheduler.getInstance().setBulkReading(hardwareMap, LynxModule.BulkCachingMode.MANUAL);

        telemetry.addData("Alliance", currentAlliance);


        telemetry = new JoinedTelemetry(
                telemetry,
                PanelsTelemetry.INSTANCE.getFtcTelemetry()
        );

       follower = Constants.create(hardwareMap);

        follower.update();

        //Drawing.init();

        register(
                pedroSb = new pedroPathingSubsystem(follower, telemetry, currentAlliance),
                intakeSb = new IntakeSubsystem(hardwareMap),
                shooterSb = new shooterSubsystem(hardwareMap, telemetry, follower, currentAlliance, isAuto),
                VisionSb = new VisionSubsystem(hardwareMap, telemetry, currentAlliance, isAuto, follower)


        );


        //imu = hardwareMap.get(IMU.class, "imu");

        initialize();
    }

    @Override
    public void init_loop() {
        if (isAuto) {
            CommandScheduler.getInstance().run();

        }
    }

    double dt;

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        run();

        dt = timer.seconds() - lastTime;

        telemetry.addLine("-----------------------------");
        telemetry.addData("deltaT", dt);

        PanelsTelemetry.INSTANCE.getFtcTelemetry().update();

        lastTime = timer.seconds();

        follower.update();


    }

    public void stop() {
        reset();
    }

    public abstract void initialize();

}
