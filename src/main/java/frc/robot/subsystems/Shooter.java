// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.units.MutableMeasure.mutable;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.MutableMeasure;
import edu.wpi.first.units.Velocity;
import edu.wpi.first.units.Voltage;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Constants;


public class Shooter extends SubsystemBase {


    /**
     * an abstract representation of a physical robot arm
     */
    private CANSparkMax ShooterMotor1, ShooterMotor2;
    private RelativeEncoder leftEncoder;
    private RelativeEncoder rightEncoder;

    private SimpleMotorFeedforward leftFeedforward= new SimpleMotorFeedforward(
        Constants.Subsystems.Shooter.kLeftS, Constants.Subsystems.Shooter.kLeftV, Constants.Subsystems.Shooter.kLeftA);
    
    private SimpleMotorFeedforward rightFeedforward= new SimpleMotorFeedforward(
        Constants.Subsystems.Shooter.kRightS, Constants.Subsystems.Shooter.kRightV, Constants.Subsystems.Shooter.kRightA);

  
    private final MutableMeasure<Voltage> m_appliedVoltage = mutable(Volts.of(0));
  // Mutable holder for unit-safe linear distance values, persisted to avoid reallocation.
  private final MutableMeasure<Angle> m_angle = mutable(Rotations.of(0));
  // Mutable holder for unit-safe linear velocity values, persisted to avoid reallocation.
  private final MutableMeasure<Velocity<Angle>> m_velocity = mutable(RPM.of(0));

  // Create a new SysId routine for characterizing the shooter.
  private final SysIdRoutine m_sysIdRoutine =
      new SysIdRoutine(
          // Empty config defaults to 1 volt/second ramp rate and 7 volt step voltage.
          new SysIdRoutine.Config(),
          new SysIdRoutine.Mechanism(
              // Tell SysId how to plumb the driving voltage to the motor(s).
              (Measure<Voltage> volts) -> {
                ShooterMotor1.setVoltage(volts.in(Volts));
                ShooterMotor2.setVoltage(volts.in(Volts));
              },
              // Tell SysId how to record a frame of data for each motor on the mechanism being
              // characterized.
              log -> {
                // Record a frame for the shooter motor.
                log.motor("shooter-left")
                    .voltage(
                        m_appliedVoltage.mut_replace(
                            ShooterMotor1.get() * RobotController.getBatteryVoltage(), Volts))
                    .angularPosition(m_angle.mut_replace(leftEncoder.getPosition(), Rotations))
                    .angularVelocity(
                        m_velocity.mut_replace(leftEncoder.getVelocity(), RPM));
                
                log.motor("shooter-right")
                    .voltage(
                        m_appliedVoltage.mut_replace(
                            ShooterMotor2.get() * RobotController.getBatteryVoltage(), Volts))
                    .angularPosition(m_angle.mut_replace(rightEncoder.getPosition(), Rotations))
                    .angularVelocity(
                        
                        m_velocity.mut_replace(rightEncoder.getVelocity(), RPM));
              },
              // Tell SysId to make generated commands require this subsystem, suffix test state in
              // WPILog with this subsystem's name ("shooter")
              this));
    /**
     * subsystem base object for arm
     */
    public Shooter() {
        this.ShooterMotor1 = new CANSparkMax(7, MotorType.kBrushless);
        this.ShooterMotor2 = new CANSparkMax(8, MotorType.kBrushless);
        ShooterMotor1.restoreFactoryDefaults();
        ShooterMotor2.restoreFactoryDefaults();
        this.ShooterMotor1.setInverted(true);
        this.ShooterMotor2.setInverted(false);
        leftEncoder = ShooterMotor1.getEncoder();
        rightEncoder = ShooterMotor2.getEncoder();
        this.ShooterMotor1.burnFlash();
        this.ShooterMotor2.burnFlash();
    }

    /**
   * Returns a command that will execute a quasistatic test in the given direction.
   *
   * @param direction The direction (forward or reverse) to run the test in
   */
  public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
    return m_sysIdRoutine.quasistatic(direction);
  }

  /**
   * Returns a command that will execute a dynamic test in the given direction.
   *
   * @param direction The direction (forward or reverse) to run the test in
   */
  public Command sysIdDynamic(SysIdRoutine.Direction direction) {
    return m_sysIdRoutine.dynamic(direction);
  }

  public void TargetFeedforward(double velocity) {
    ShooterMotor1.setVoltage(leftFeedforward.calculate(velocity));
    ShooterMotor2.setVoltage(rightFeedforward.calculate(velocity));
  }

    public void ShooterMotor1Stop() {
        this.ShooterMotor1.stopMotor();
    }
    
    public void ShooterMotor2Stop() {
        this.ShooterMotor2.stopMotor();
    }

    public void ShooterMotor1Forward(double power) {
        this.ShooterMotor1.set(power);
    }

    public void ShooterMotor1Backward(double power) {
        this.ShooterMotor1.set(-power);
    }

    public void ShooterMotor2Forward(double power) {
        this.ShooterMotor2.set(power);
    }

    public void ShooterMotor2Backward(double power) {
        this.ShooterMotor2.set(-power);
    }

}