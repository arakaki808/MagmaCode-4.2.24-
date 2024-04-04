// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public class Constants {
    
    public static class canID{
        public static int frontLeftDriveMotor = 1;
        public static int rearLeftDriveMotor= 2;
        public static int frontRightDriveMotor = 3;
        public static int rearRightDriveMotor= 4;
        public static int leftPivot= 5;
        public static int rightPivot= 6;
        public static int ShooterMotor1= 7;
        public static int ShooterMotor2= 8;
        public static int indexMotor = 9;

    }
    public static class Control {

        public static class ControllerPort {
            public static final int kDRIVER = 0;
            public static final int kPARTNER = 1; 
        }

        public static class JoystickController {
            public static final int kPOSITION = 1;
            public static final int kANGLE = 2;
            public static final int kSPEED = 3;
        }

        //daniels driver preference
        public static class XboxController {
            
            public static final int kLEFT = 1;
            public static final int kRIGHT = 5;
            public static final int kAngle = 4;
        }

        public static class Button {
            public static final int kA = 1;
            public static final int kB = 2;
            public static final int kX = 3;
            public static final int kY = 4;
            public static final int kLEFT_BUMPER = 5;
            public static final int kRIGHT_BUMPER = 6;
        }

        public static class POVButton {
            public static final int kUP = 0;
            public static final int kDOWN = 180;
            public static final int kLEFT = 90;
            public static final int kRIGHT = 270;
        }

    }


    public static class Subsystems{
        public static class Intake{
            public static final double pwrIntake = 1;
        }
        public static class Shooter{
            public static final double pwrSpeaker = 0.9;
            public static final double pwrWeak = 0.5;
            public static final double pwrAmp = 0.7;

            public static double kLeftS = 0.23643;
            public static double kLeftV = 0.38942;
            public static double kLeftA = 0.046395;

            public static double kRightS = 0.20658;
            public static double kRightV = 0.38488;
            public static double kRightA = 0.041661;

        }
        public static class Indexer{
            public static final double pwrIndexer = 1;
        }
    }


    public static final class Pivot {
        
    
        // FeedForward Constants
        public static double kSVolts = 0;
        public static double kGVolts = 0;
        public static double kVVoltSecondPerRad = 0;
        public static double kAVoltSecondSquaredPerRad = 0;
    
        //TrapezoidProfile Constraints
        public static double kMaxVelocityRadPerSecond = 3;
        public static double kMaxAccelerationRadPerSecSquared = 3;
    
        //PID coefficients
        public static double kP = .1; 
        public static double kI = 0;
        public static double kD = 0; 
        public static double kIz = 0; 
        public static double kFF = 10; 
        public static double kMaxOutput = 1; 
        public static double kMinOutput = -1;
        public static double  maxRPM = 11000;
        
        // The offset of the arm from the horizontal in its neutral position,
        // measured from the horizontal
        public static double kSpeaker = .78;
        public static double kAmp = 1.3
        ;
        public static double kHome = 0;
      }

      public static final class Drive {
        
        //Feedforward Constants
        public static final double ksVolts = 1;
        public static final double kvVoltSecondsPerMeter = 0.8;
        public static final double kaVoltSecondsSquaredPerMeter = 0.15;
    
        //PID Constants
        public static final double kp = 1;
        public static double kI = 0;
        public static double kD = 0; 
        public static double kIz = 0; 
        public static double kFF = 10; 
        public static double kMaxOutput = 1; 
        public static double kMinOutput = -1;
        public static double  maxRPM = 11000;
        
        //TrapezoidProfile Constraints
        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3;
      }
    


}