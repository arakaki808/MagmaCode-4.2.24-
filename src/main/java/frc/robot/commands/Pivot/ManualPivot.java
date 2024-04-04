package frc.robot.commands.Pivot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Pivot;

public class ManualPivot extends Command {
    private double power;
    private Pivot arm;

    public ManualPivot(Pivot arm, double power) {
        this.power = power;
        this.arm = arm;
        addRequirements(arm);
    }

    public void initialize() {

    }

    public void execute() {
        arm.ManualPivot(power);
    }

    public boolean isFinished() {
        return false;
    }

    public void end() {

    }
    
}
