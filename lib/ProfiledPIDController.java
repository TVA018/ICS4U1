import java.util.Optional;

public class ProfiledPIDController {
    public double kP;
    public double kI;
    public double kD;

    public Optional<Double> peakOutput;
    public Optional<Double> rampRate;

    private double setpoint = 0;
    private double previousMeasurement = 0;
    private double currentLimit = 0;

    public ProfiledPIDController(double kP, double kI, double kD, Optional<Double> peakOutput, Optional<Double> rampRate) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.peakOutput = peakOutput;
        this.rampRate = rampRate;
    }

    public ProfiledPIDController(double kP, double kI, double kD) {
        this(kP, kI, kD, Optional.empty(), Optional.empty());
    }

    public ProfiledPIDController() {
        this(0, 0, 0);
    }

    public double calculate(double measured, double setpoint, double deltaSeconds) {
        if(this.setpoint != setpoint) {
            this.setpoint = setpoint;
            this.currentLimit = 0.0;
        }

        double error = setpoint - measured;

        double kP_effort = kP * error;
        double kD_effort = kD * (-(measured - previousMeasurement)/deltaSeconds);
        double totalEffort = kP_effort + kD_effort;

        if(peakOutput.isPresent()) {
            if(rampRate.isPresent()) {
                currentLimit += Math.min(peakOutput.get(), rampRate.get());
            } else {
                currentLimit = peakOutput.get();
            }

            totalEffort = Math.min(totalEffort, currentLimit);
        }

        previousMeasurement = measured;

        return totalEffort;
    }
    
    public double calculate(double measured, double deltaSeconds) {
        return calculate(measured, setpoint, deltaSeconds);
    }

    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
    }

    public double getSetpoint() {
        return setpoint;
    }
}
