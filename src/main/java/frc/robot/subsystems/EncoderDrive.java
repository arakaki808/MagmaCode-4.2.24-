// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
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
public class EncoderDrive extends TrapezoidProfileSubsystem {
  private final CANSparkMax frontLeftDriveMotor = new CANSparkMax(Constants.canID.frontLeftDriveMotor, MotorType.kBrushless);
  private final CANSparkMax rearLeftDriveMotor = new CANSparkMax(Constants.canID.rearLeftDriveMotor, MotorType.kBrushless);
  private final CANSparkMax frontRightDriveMotor = new CANSparkMax(Constants.canID.frontRightDriveMotor, MotorType.kBrushless);
  private final CANSparkMax rearRightDriveMotor = new CANSparkMax(Constants.canID.rearRightDriveMotor, MotorType.kBrushless);
  

  private final SimpleMotorFeedforward m_feedforward =
      new SimpleMotorFeedforward(
          Constants.Drive.ksVolts, Constants.Drive.kvVoltSecondsPerMeter,Constants.Drive.kaVoltSecondsSquaredPerMeter);
  public SparkPIDController m_pidController;
  private final RelativeEncoder leftEncoder = 
      frontLeftDriveMotor.getEncoder(SparkRelativeEncoder.Type.kHallSensor, 42);
      
  


  /** Create a new ArmSubsystem. */
  public EncoderDrive() {
    super(
        new TrapezoidProfile.Constraints(
            Constants.Drive.kMaxSpeedMetersPerSecond, Constants.Drive.kMaxAccelerationMetersPerSecondSquared)
        );
        m_pidController = frontLeftDriveMotor.getPIDController();
        m_pidController.setFeedbackDevice(leftEncoder);

        
        rearRightDriveMotor.follow(frontLeftDriveMotor);
        rearLeftDriveMotor.follow(frontLeftDriveMotor);
        frontLeftDriveMotor.setInverted(false);
        frontRightDriveMotor.setInverted(true);

        // set PID coefficients
        m_pidController.setP(Constants.Drive.kp);
        m_pidController.setI(Constants.Drive.kI);
        m_pidController.setD(Constants.Drive.kD);
        m_pidController.setIZone(Constants.Drive.kIz);
        m_pidController.setFF(Constants.Drive.kFF);
        m_pidController.setOutputRange(Constants.Drive.kMinOutput, Constants.Drive.kMaxOutput);
        SmartDashboard.getNumber("encoder", this.leftEncoder.getPosition());
  }
  

  @Override
  public void useState(TrapezoidProfile.State setpoint) {
    // Calculate the feedforward from the sepoint
    double feedforward = m_feedforward.calculate(setpoint.position, setpoint.velocity);
    // Add the feedforward to the PID output to get the motor output
    m_pidController.setReference(setpoint.position,
        ControlType.kPosition, 1, feedforward);
  }

  public Command setDrive(double ksetPoint) {
    SmartDashboard.getNumber("Setpoint", ksetPoint);
    return Commands.runOnce(() -> setGoal(ksetPoint),this);
  }

}