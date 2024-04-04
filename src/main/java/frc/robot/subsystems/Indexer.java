// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Indexer extends SubsystemBase {

    private CANSparkMax Indexer;

    public Indexer() {
        this.Indexer = new CANSparkMax(Constants.canID.indexMotor, MotorType.kBrushless);
        this.Indexer.setInverted(false);
        
        this.Indexer.burnFlash();
        this.Indexer.restoreFactoryDefaults();
    }

    public void IndexIn() {
        this.Indexer.set(Constants.Subsystems.Indexer.pwrIndexer);
    }

    public void IndexOut() {
        this.Indexer.set(-Constants.Subsystems.Indexer.pwrIndexer);
    }

    public void timedIndexOut() {
        this.Indexer.set(-Constants.Subsystems.Indexer.pwrIndexer);
    }

    public void IndexStop() {
        this.Indexer.stopMotor();
    }

    public void IndexInAuto(double power) {
        this.Indexer.set(power);
    }


     /**
     * second arm goes down by setting power on the arm motor
     */
    public void IndexOutAuto(double power) {
        this.Indexer.set(-power);
    }
  
}