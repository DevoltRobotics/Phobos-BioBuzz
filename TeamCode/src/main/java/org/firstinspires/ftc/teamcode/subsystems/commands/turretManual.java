package org.firstinspires.ftc.teamcode.subsystems.commands;


import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.turret.turretSubsystem;

import java.util.function.BooleanSupplier;

public class turretManual extends CommandBase {

    private static final double SPEED_DEG_PER_SEC = 120.0;
    private static final double MAX_DT_SEC = 0.1;

    private final turretSubsystem turret;
    private final BooleanSupplier leftBumper;
    private final BooleanSupplier rightBumper;

    private long lastTimeNs;

    public turretManual(turretSubsystem turret,
                               BooleanSupplier leftBumper,
                               BooleanSupplier rightBumper) {
        this.turret = turret;
        this.leftBumper = leftBumper;
        this.rightBumper = rightBumper;
        addRequirements(turret);
    }

    @Override
    public void initialize() {
        lastTimeNs = System.nanoTime(); // evita un dt gigante en el primer ciclo
    }

    @Override
    public void execute() {
        long now = System.nanoTime();
        double dt = Math.min((now - lastTimeNs) / 1e9, MAX_DT_SEC);
        lastTimeNs = now;

        double direction = 0;
        if (rightBumper.getAsBoolean()) direction += 1;
        if (leftBumper.getAsBoolean()) direction -= 1;

        if (direction != 0) {
            // setAngle limita a ±160° y manda la posición a los dos servos
            turret.setAngle(turret.getTargetAngle() + direction * SPEED_DEG_PER_SEC * dt);
        }
    }

    @Override
    public boolean isFinished() {
        return false; // corre siempre como comando por defecto
    }
}