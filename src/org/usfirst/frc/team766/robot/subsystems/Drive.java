package org.usfirst.frc.team766.robot.subsystems;

import org.usfirst.frc.team766.robot.Ports;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Drive subsystem. Includes all parts of the robot uses to drive.
 * 
 * @author Brett Levenson
 * @author PKao
 */

public class Drive extends Subsystem {
	private static final double WHEEL_DIAMETER = .09958, // meters
								PULSES_PER_ROTATION = 256,
								DISTANCE_PER_PULSE = (Math.PI * WHEEL_DIAMETER) / PULSES_PER_ROTATION; 
								
	private static final double ACCELEROMETER_STOP_THRESHOLD = .1;

	private double outputRight;
	private double outputLeft;
	
	private Victor leftDrive = new Victor(Ports.PWM_Left_Drive);
	private Victor rightDrive = new Victor(Ports.PWM_Right_Drive);

	private Encoder rightEncoder = new Encoder(Ports.DIO_RDriveEncA,
			Ports.DIO_RDriveEncB, false, CounterBase.EncodingType.k4X);
	private Encoder leftEncoder = new Encoder(Ports.DIO_LDriveEncA,
			Ports.DIO_LDriveEncB, false, CounterBase.EncodingType.k4X);

	private Solenoid Shifter = new Solenoid(Ports.Sol_Shifter);

	private AnalogGyro gyro = new AnalogGyro(Ports.GYRO);

	private PowerDistributionPanel PDP = new PowerDistributionPanel();
	
	private BuiltInAccelerometer accel = new BuiltInAccelerometer();
	private double leftTarget = 0;
	private double rightTarget = 0;
	private double rateOfChange = .05;// might need 2 variables
	private boolean smoothing = false;

	public Drive() {
		Periodic smoother = new Periodic() {
			private double lastRightOut, lastLeftOut; 
			
			protected void execute() {
				outputLeft = rateOfChange * lastLeftOut + (1 - rateOfChange)
						* getLeftTarget();
				outputRight = rateOfChange * lastRightOut + (1 - rateOfChange)
						* getRightTarget();

				rightDrive.set(outputRight);
				leftDrive.set(outputLeft);

				lastRightOut = outputRight;
				lastLeftOut = outputLeft;

				if (accel.getX() < ACCELEROMETER_STOP_THRESHOLD
						&& accel.getY() < ACCELEROMETER_STOP_THRESHOLD
						&& accel.getZ() < ACCELEROMETER_STOP_THRESHOLD
						&& (leftDrive.get() != 0 || rightDrive.get() != 0)) {
					System.out
							.println("URGENT: ROBOT MOTORS ENGAGED BUT ACCLEROMETERS REPORT LITTLE MOVEMENT. TERMINATING ALL MOTOR POWER!!!!");
				}
			}

		};
		smoother.start();
		rightEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
		leftEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
	}

	protected void initDefaultCommand() {
	}

	/**
	 * Set power to both motors, useful for shutting them off
	 * 
	 * @param power
	 *            power value
	 */

	// For set methods, to set raw you need to not be in smoothing mode
	public void setPower(double power) {
		if (smoothing) {
			leftTarget = -power;
			rightTarget = power;
		} else {
			leftDrive.set(-power);
			rightDrive.set(power);
		}
	}

	public void setLeftPower(double power) {
		// //Compensating for deadband
		// if(power < 0)
		// power -= .08;
		// else if(power > 0)
		// power += .08;

		if (smoothing) {
			leftTarget = -power;
		} else
			leftDrive.set(-power);
	}

	public void setRightPower(double power) {
		// Compensating for deadband
		// if(power < 0)
		// power -= .08;
		// else if(power > 0)
		// power += .08;

		if (smoothing) {
			rightTarget = power;
		} else
			rightDrive.set(power);
	}

	private double getRightTarget() { // Should I have these return
													// Double.NaN if you aren't
													// in smoothing mode?
		return rightTarget;
	}

	private double getLeftTarget() { // Should I have these return
													// Double.NaN if you aren't
													// in smoothing mode?
		return leftTarget;
	}

	public void setHighGear(boolean isHighGear) {
		Shifter.set(isHighGear);
	}

	public boolean isHighGear() {
		return Shifter.get();
	}

	public double getLeftEncoderDistance() {
		return leftEncoder.getDistance();
	}

	public double getRightEncoderDistance() {
		return rightEncoder.getDistance();
	}

	public int getRawLeftEncoder() {
		return leftEncoder.getRaw();
	}

	public int getRawRightEncoder() {
		return rightEncoder.getRaw();
	}

	public double getAverageEncoderDistance() {
		return (getLeftEncoderDistance() + getRightEncoderDistance()) / 2.0d;
	}

	public double getLeftVelocity() {
		return leftEncoder.getRate();
	}

	public double getRightVelocity() {
		return rightEncoder.getRate();
	}

	public double getAverageVelocity() {
		return (getLeftVelocity() + getRightVelocity()) / 2;
	}
	
	public double getAcceleration() {
		return accel.getY();
	}

	public void resetEncoders() {
		rightEncoder.reset();
		leftEncoder.reset();
	}

	public double getVoltage() {
		return PDP.getVoltage();
	}

	public double getTemp() {
		return PDP.getTemperature();
	}

	// Gyro
	public void resetGyro() {
		gyro.reset();
	}

	public double getAngle() {
		return gyro.getAngle();
	}
	

	public void gyroInit()
	{
		gyro.initGyro();
	}
	
	public double getGyroAngleInRadians() {
	    return (getAngle() * Math.PI) / 180.0;
	 }
	
	//Smoothing

	public boolean getSmoothing() {
		return smoothing;
	}

	public void setSmoothing(boolean setSmooth) {
		smoothing = setSmooth;
	}

}
