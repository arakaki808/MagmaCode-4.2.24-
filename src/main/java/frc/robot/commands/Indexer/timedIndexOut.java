package frc.robot.commands.Indexer;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;


public class timedIndexOut extends Command {


    /**
     * an instance of {@link frc.robot.subsystems.Indexer}
     */
    private final Indexer Indexer;
    private double duration;

    /**
     * @param Indexer an instance of {@link frc.robot.subsystems.Indexer}
     */
    public timedIndexOut(Indexer Indexer,double duration) {
        this.Indexer = Indexer;
        this.duration=duration;
        addRequirements(Indexer);
    }

    public void initialize() {
        double currentTime = System.currentTimeMillis();
        this.duration = (currentTime + this.duration);
    }


    /**
     * method that's being executed
     */

    public void execute() {
        this.Indexer.timedIndexOut();
    }

    public boolean isFinished() {
        return System.currentTimeMillis() >= this.duration;
    }

    protected void end() {
        this.Indexer.IndexStop();
    }
    
    protected void interrupted() {
        this.end();
    }


    


}