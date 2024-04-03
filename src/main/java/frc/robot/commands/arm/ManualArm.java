package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

public class ManualArm extends Command {
    private double power;
    private ArmSubsystem arm;

    public ManualArm(ArmSubsystem arm, double power) {
        this.power = power;
        this.arm = arm;
        addRequirements(arm);
    }

    public void initialize() {

    }

    public void execute() {
        arm.ManualArm(power);
    }

    public boolean isFinished() {
        return false;
    }

    public void end() {

    }
    
}
