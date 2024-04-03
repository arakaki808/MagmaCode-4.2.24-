package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

public class StopArm extends Command {
    private ArmSubsystem arm;

    public StopArm(ArmSubsystem arm) {
        this.arm = arm;
        addRequirements(arm);
    }

    public void initialize() {

    }

    public void execute() {
        arm.StopArm();
    }

    public boolean isFinished() {
        return false;
    }

    public void end() {

    }
    
}
