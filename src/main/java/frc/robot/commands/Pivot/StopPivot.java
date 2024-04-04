package frc.robot.commands.Pivot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Pivot;

public class StopPivot extends Command {
    private Pivot arm;

    public StopPivot(Pivot arm) {
        this.arm = arm;
        addRequirements(arm);
    }

    public void initialize() {

    }

    public void execute() {
        arm.StopPivot();
    }

    public boolean isFinished() {
        return false;
    }

    public void end() {

    }
    
}
