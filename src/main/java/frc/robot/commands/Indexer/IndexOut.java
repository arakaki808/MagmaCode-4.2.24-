package frc.robot.commands.Indexer;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;


public class IndexOut extends Command {


    /**
     * an instance of {@link frc.robot.subsystems.Indexer}
     */
    private final Indexer Indexer;

    /**
     * @param Indexer an instance of {@link frc.robot.subsystems.Indexer}
     */
    public IndexOut(Indexer Indexer) {
        this.Indexer = Indexer;
        addRequirements(Indexer);
    }


    @Override
    public void initialize() {
    }


    /**
     * method that's being executed
     */
    @Override
    public void execute() {
        this.Indexer.IndexOut();
    }


    @Override
    public void end(boolean interrupted) {
    }


    @Override
    public boolean isFinished() {
        return false;
    }


}