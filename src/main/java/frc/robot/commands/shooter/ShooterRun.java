package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;


public class ShooterRun extends Command {


    private double power;
    private final Shooter ShooterMotor1;


    public ShooterRun(Shooter ShooterMotor1, double power) {
        this.ShooterMotor1 = ShooterMotor1;
        this.power = power;
        addRequirements(ShooterMotor1);

    }


    // called just before this Command runs the first time
    // calculates when to end Command
    public void initialize() {
    }


    // called repeatedly when this Command is scheduled to run
    public void execute() {
        this.ShooterMotor1.ShooterRun(this.power);
    }


    // make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return false;
    }

    // called once after isFinished returns true
    protected void end() {
        this.ShooterMotor1.ShooterStop();
    }

    protected void interrupted() {
        this.end();
    } 
}