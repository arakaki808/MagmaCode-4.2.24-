package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;


public class timedShooter extends Command {


    private double power, duration;
    private final Shooter ShooterMotor1;


    public timedShooter(Shooter ShooterMotor1, double power, double duration) {
        this.ShooterMotor1 = ShooterMotor1;
        this.power = power;
        this.duration=duration;
        addRequirements(ShooterMotor1);

    }


    // called just before this Command runs the first time
    // calculates when to end Command
    public void initialize() {
        double currentTime = System.currentTimeMillis();
        this.duration = (currentTime + this.duration);
    }


    // called repeatedly when this Command is scheduled to run
    public void execute() {
        this.ShooterMotor1.timedShooter(this.power);
    }


    // make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return System.currentTimeMillis() >= this.duration;
    }

    // called once after isFinished returns true
    protected void end() {
        this.ShooterMotor1.ShooterStop();
    }

    protected void interrupted() {
        this.end();
    } 
}