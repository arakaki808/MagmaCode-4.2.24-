package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;


public class ShooterSet extends Command {


    private double power;
    private final Shooter ShooterMotor1, ShooterMotor2;


    public ShooterSet(Shooter ShooterMotor1, Shooter ShooterMotor2, double power) {
        this.ShooterMotor1 = ShooterMotor1;
        this.ShooterMotor2 = ShooterMotor2;
        this.power = power;
        addRequirements(ShooterMotor1);
        addRequirements(ShooterMotor2);

    }


    // called just before this Command runs the first time
    // calculates when to end Command
    public void initialize() {
    }


    // called repeatedly when this Command is scheduled to run
    public void execute() {
        this.ShooterMotor1.ShooterMotor1Forward(this.power);
        this.ShooterMotor2.ShooterMotor2Forward(this.power);
    }


    // make this return true when this Command no longer needs to run execute()
    // checks if the time has passed the set duration
    public boolean isFinished() {
        return false;
    }


    // called once after isFinished returns true
    // drive train is stopped
    protected void end() {
        this.ShooterMotor1.ShooterMotor1Stop();
        this.ShooterMotor2.ShooterMotor2Stop();
    }


    protected void interrupted() {
        this.end();
    }

    
}