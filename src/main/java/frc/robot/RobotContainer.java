// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.commands.Indexer.IndexIn;
import frc.robot.commands.Indexer.IndexStop;
import frc.robot.commands.Indexer.timedIndexOut;
import frc.robot.commands.Pivot.ManualPivot;
import frc.robot.commands.Pivot.StopPivot;
import frc.robot.commands.Indexer.IndexOut;
import frc.robot.commands.drive.DriveTrainCommand;
import frc.robot.commands.drive.DriveTrainCommandSlower;
import frc.robot.commands.intake.IntakeBackward;
import frc.robot.commands.intake.IntakeForward;
import frc.robot.commands.intake.IntakeStop;
import frc.robot.commands.shooter.ShooterRun;
import frc.robot.commands.shooter.ShooterStop;
import frc.robot.commands.shooter.autoSpeaker;
import frc.robot.commands.shooter.autoAmp;
import frc.robot.commands.shooter.TargetFeedforward;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    
    // The robot's subsystems and commands are defined here... 
    DriveTrain driveTrain = new DriveTrain();
    Shooter Shooter = new Shooter();
    Intake Intake = new Intake();
    Indexer Indexer = new Indexer();
    Pivot m_robotArm = new Pivot();

    
    SendableChooser<Command> m_auto_chooser = new SendableChooser<>();

    XboxController driverController, driverPartnerController;
    CommandXboxController testController = new CommandXboxController(3);
    JoystickButton buttonA, buttonB, buttonX, buttonY, rightBumper, leftBumper, driverRightBumper, driverButtonA, driverButtonB,driverButtonX;
    POVButton upPOV, downPOV, leftPOV, rightPOV;
    
    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        this.driverController = new XboxController(Constants.Control.ControllerPort.kDRIVER);
        this.driverPartnerController = new XboxController(Constants.Control.ControllerPort.kPARTNER);
        
        this.driverRightBumper = new JoystickButton(driverController, Constants.Control.Button.kRIGHT_BUMPER);
        this.driverButtonA = new JoystickButton(driverController, Constants.Control.Button.kA);
        this.driverButtonB = new JoystickButton(driverController, Constants.Control.Button.kB);
        this.driverButtonX = new JoystickButton(driverController, Constants.Control.Button.kX);
        

        this.buttonA = new JoystickButton(driverPartnerController, Constants.Control.Button.kA);
        this.buttonB = new JoystickButton(driverPartnerController, Constants.Control.Button.kB);
        this.buttonX = new JoystickButton(driverPartnerController, Constants.Control.Button.kX);
        this.buttonY = new JoystickButton(driverPartnerController, Constants.Control.Button.kY);
        this.rightBumper = new JoystickButton(driverPartnerController, Constants.Control.Button.kRIGHT_BUMPER);
        this.leftBumper = new JoystickButton(driverPartnerController, Constants.Control.Button.kLEFT_BUMPER);
        this.upPOV = new POVButton(driverPartnerController, Constants.Control.POVButton.kUP);
        this.downPOV = new POVButton(driverPartnerController, Constants.Control.POVButton.kDOWN);
        this.leftPOV = new POVButton(driverPartnerController, Constants.Control.POVButton.kLEFT);
        this.rightPOV = new POVButton(driverPartnerController, Constants.Control.POVButton.kRIGHT);
        
        this.driveTrain.setDefaultCommand(new DriveTrainCommand(this.driveTrain, this.driverController));

        SmartDashboard.putData(m_auto_chooser);
        
        // buildShuffleboard();
        // Configure the trigger bindings
        this.configureBindings();
    }


    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {
        // Shooting
        this.buttonY.onTrue(new autoSpeaker(Shooter,Indexer));
        this.buttonB.onTrue(new autoAmp(Shooter,Indexer));
        this.buttonX.onTrue(new ShooterRun(Shooter, -Constants.Subsystems.Shooter.pwrSpeaker)).onFalse(new ShooterStop(Shooter));
        this.buttonA.onTrue(new ShooterRun(Shooter, Constants.Subsystems.Shooter.pwrAmp)).onFalse(new ShooterStop(Shooter));
        
        //Intake In
        this.rightBumper.onTrue(new IntakeForward(this.Intake)).onFalse(new IntakeStop(this.Intake));
        this.rightBumper.onTrue(new ShooterRun(Shooter, -Constants.Subsystems.Shooter.pwrSpeaker)).onFalse(new ShooterStop(Shooter));
        //Intake Out
        this.leftBumper.onTrue(new IntakeBackward(this.Intake)).onFalse(new IntakeStop(this.Intake));
        
        // Indexing
        this.upPOV.onTrue(new IndexOut(this.Indexer)).onFalse(new IndexStop(this.Indexer));
        this.downPOV.onTrue(new IndexIn(this.Indexer)).onFalse(new IndexStop(this.Indexer));
        
        //Drive Slow
        this.driverRightBumper.onTrue(new DriveTrainCommandSlower(this.driveTrain, this.driverController)).onFalse(new DriveTrainCommand(this.driveTrain, this.driverController));
        
        //Pivot Positions
        driverButtonA.onTrue(m_robotArm.setPivot(Constants.Pivot.kHome));
        driverButtonB.onTrue(m_robotArm.setPivot(Constants.Pivot.kSpeaker));
        driverButtonX.onTrue(m_robotArm.setPivot(Constants.Pivot.kAmp));

        //Test Buttons (Controller 3)
        testController.a().onTrue(new ManualPivot(m_robotArm, 0.2)).onFalse(new StopPivot(m_robotArm));
        testController.b().onTrue(new ManualPivot(m_robotArm, -0.2)).onFalse(new StopPivot(m_robotArm));
        testController.y().onTrue(m_robotArm.ResetPivot());
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new SequentialCommandGroup(
            );
    }
}