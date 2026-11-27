package org.firstinspires.ftc.teamcode.subsystems.turret;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
@Configurable
public class turretSubsystem extends SubsystemBase {

    private final Servo leftTurret;
    private final Servo rightTurret;

    // Límites de software para la torreta (-160° a 160°)
    private static final double MIN_TARGET_ANGLE = -160.0;
    private static final double MAX_TARGET_ANGLE = 160.0;

    // Rango físico real del servo (350° en total, centrado en 0°)
    private static final double SERVO_MIN_ANGLE = -175.0; // -350 / 2
    private static final double SERVO_MAX_ANGLE = 175.0;  //  350 / 2

    // Rango de señal en FTC (0.0 a 1.0)
    private static final double MIN_POSITION = 0.0;
    private static final double MAX_POSITION = 1.0;

    public double targetAngle = 0.0;

    public static PIDFCoefficients llPidCoeffs = new PIDFCoefficients(0.2, 0.0, 0.013, 0);
    public PIDFController llPidf;
    public boolean realIsManual;

    public turretSubsystem(HardwareMap hwMap) {
        leftTurret = hwMap.get(Servo.class, "turret");
        rightTurret = hwMap.get(Servo.class, "turret2");

        llPidf = new PIDFController(llPidCoeffs);

        // Si los dos servos están en espejo mecánico y giran en sentido opuesto,
        // uno de ellos debe invertirse:
        // rightTurret.setDirection(Servo.Direction.REVERSE);
    }

    /**
     * Mueve la torreta a un ángulo específico en grados.
     * @param degrees Ángulo objetivo entre -160 y 160 grados.
     */
    public void setAngle(double degrees) {
        // Restringir el ángulo a los límites seguros (-160 a 160)
        targetAngle = Math.max(MIN_TARGET_ANGLE, Math.min(MAX_TARGET_ANGLE, degrees));

        // Convertir los grados (-175° a 175°) a señal del servo (0.0 a 1.0)
        double servoPosition = map(targetAngle, SERVO_MIN_ANGLE, SERVO_MAX_ANGLE, MIN_POSITION, MAX_POSITION);

        leftTurret.setPosition(servoPosition);
        rightTurret.setPosition(servoPosition);


    }

    private double map(double val, double inMin, double inMax, double outMin, double outMax) {
        return (val - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }

    public double getTargetAngle() {
        return targetAngle;
    }

    @Override
    public void periodic() {
        llPidf.setCoefficients(llPidCoeffs);
        llPidf.setSetPoint(0);

        PanelsTelemetry.INSTANCE.getFtcTelemetry().addData("turretTarget", getTargetAngle());


        // Ejecución continua si se requiere en el futuro
    }
}