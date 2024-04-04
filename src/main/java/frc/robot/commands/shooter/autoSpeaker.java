package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants;
import frc.robot.commands.Indexer.timedIndexOut;
import frc.robot.subsystems.Indexer;


public class autoSpeaker extends Command {

    private final Shooter ShooterMotor1;
    private final Indexer Indexer;

    public autoSpeaker(Shooter ShooterMotor1, Indexer Indexer) {
        this.ShooterMotor1=ShooterMotor1;
        this.Indexer=Indexer;
        addRequirements(ShooterMotor1);
        addRequirements(Indexer);


    }


    // called just before this Command runs the first time
    // calculates when to end Command
    public void initialize() {
    }


    // called repeatedly when this Command is scheduled to run
    public void execute() {
        new SequentialCommandGroup(
            new timedShooter(ShooterMotor1,Constants.Subsystems.Shooter.pwrSpeaker,1.5),
            new ParallelCommandGroup(
                new timedShooter(ShooterMotor1,Constants.Subsystems.Shooter.pwrSpeaker,1.5),
                new timedIndexOut(Indexer, 1.5)
                )
            );
    }


    // make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return false;
    }

    // called once after isFinished returns true
    protected void end() {
        this.ShooterMotor1.ShooterStop();
        this.Indexer.IndexStop();
    }

    protected void interrupted() {
        this.end();
    } 
}