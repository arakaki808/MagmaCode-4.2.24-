// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.TrapezoidProfileSubsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.SparkRelativeEncoder;

/** A robot arm subsystem that moves with a motion profile. */
public class Pivot extends TrapezoidProfileSubsystem {
  private final CANSparkMax m_leftmotor =
      new CANSparkMax(Constants.canID.leftPivot,MotorType.kBrushless);
  
  private final CANSparkMax m_rightmotor =
      new CANSparkMax(Constants.canID.rightPivot,MotorType.kBrushless);
  
  private final ArmFeedforward m_feedforward =
      new ArmFeedforward(
          Constants.Pivot.kSVolts, Constants.Pivot.kGVolts,
          Constants.Pivot.kVVoltSecondPerRad, Constants.Pivot.kAVoltSecondSquaredPerRad);
  public SparkPIDController m_pidController;
  private final RelativeEncoder m_leftencoder = 
      m_leftmotor.getEncoder(SparkRelativeEncoder.Type.kHallSensor, 42);
      
  


  /** Create a new ArmSubsystem. */
  public Pivot() {
    super(
        new TrapezoidProfile.Constraints(
            Constants.Pivot.kMaxVelocityRadPerSecond, Constants.Pivot.kMaxAccelerationRadPerSecSquared)
        );
        m_pidController = m_leftmotor.getPIDController();
        m_pidController.setFeedbackDevice(m_leftencoder);

        
        m_rightmotor.follow(m_leftmotor,true);
        m_leftmotor.setInverted(true);

        // set PID coefficients
        m_pidController.setP(Constants.Pivot.kP);
        m_pidController.setI(Constants.Pivot.kI);
        m_pidController.setD(Constants.Pivot.kD);
        m_pidController.setIZone(Constants.Pivot.kIz);
        m_pidController.setFF(Constants.Pivot.kFF);
        m_pidController.setOutputRange(Constants.Pivot.kMinOutput, Constants.Pivot.kMaxOutput);
        SmartDashboard.getNumber("encoder", this.m_leftencoder.getPosition());
  }
  

  @Override
  public void useState(TrapezoidProfile.State setpoint) {
    // Calculate the feedforward from the sepoint
    double feedforward = m_feedforward.calculate(setpoint.position, setpoint.velocity);
    // Add the feedforward to the PID output to get the motor output
    m_pidController.setReference(setpoint.position,
        ControlType.kPosition, 0, feedforward);
  }

  public Command setPivot(double ksetPoint) {
    SmartDashboard.getNumber("Setpoint", ksetPoint);
    return Commands.runOnce(() -> setGoal(ksetPoint),this);
  }

  public void ManualPivot(double power) {
    m_leftmotor.set(power);

  }

  public void StopPivot() {
    m_leftmotor.stopMotor();
  }

  public Command ResetPivot() {
    return run(
      () -> {
      m_leftencoder.setPosition(0);
      }
    );
  }
}