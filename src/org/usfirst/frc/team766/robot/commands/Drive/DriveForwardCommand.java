package org.usfirst.frc.team766.robot.commands.Drive;

import org.usfirst.frc.team766.lib.PIDController;
import org.usfirst.frc.team766.robot.RobotValues;
import org.usfirst.frc.team766.robot.commands.CommandBase;

/**
 * Command that uses the encoders to move the robot
 *
 * @author Blevenson
 * @author PKao
 */
// Note: PID code experimental
public class DriveForwardCommand extends CommandBase {
	private static final double ANGLE_TO_POWER_RATIO = .005;
	
	
	private PIDController DistancePID = new PIDController(RobotValues.DriveKp,
			RobotValues.DriveKi, RobotValues.DriveKd,
			RobotValues.Angleoutputmax_low, RobotValues.Angleoutputmax_high,
			RobotValues.AngleThreshold); // see PID class or hover for
											// definition of all
	
	private PIDController AnglePID = new PIDController(RobotValues.AngleKp,
			RobotValues.AngleKi, RobotValues.AngleKd,
			RobotValues.Angleoutputmax_low, RobotValues.Angleoutputmax_high,
			RobotValues.AngleThreshold);;

	public DriveForwardCommand() {
		this(0);
	}

	public DriveForwardCommand(double distance) {
		DistancePID.setSetpoint(distance);
		AnglePID.setSetpoint(distance);
	}

	protected void initialize() {
		Drive.resetGyro();
		Drive.resetEncoders();
		DistancePID.reset();
		AnglePID.reset();
		Drive.setShifter(false);
	}

	protected void execute() {
		DistancePID.calculate((Drive.getLeftEncoderDistance() + Drive
				.getRightEncoderDistance()) / 2.0);
		AnglePID.calculate(Drive.getAngle());
		Drive.setLeftPower(DistancePID.getOutput() + AnglePID.getOutput() * ANGLE_TO_POWER_RATIO);
		Drive.setRightPower(DistancePID.getOutput()- AnglePID.getOutput() * ANGLE_TO_POWER_RATIO);
	}

	protected boolean isFinished() {
		// added to remove error, needs to be changed
		return DistancePID.isDone();
	}

	protected void end() {
		Drive.setPower(0d);
	}

	protected void interrupted() {
		end();
	}

}