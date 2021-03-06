package org.usfirst.frc.team766.robot.commands.Elevator;

import org.usfirst.frc.team766.lib.PIDController;
import org.usfirst.frc.team766.robot.RobotValues;
import org.usfirst.frc.team766.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class MoveElevatorHeightVelocity extends CommandBase {

	private static final boolean PRINT = true;
	// Constants need to be tuned. Units: meters per second
	private static final double AMAX = .05;
	private static final double VMAX = .3;
	private static final double STOP_THRESHOLD = .1;

	private enum phaseType {
		RAMP_UP, MAX_VEL, RAMP_DOWN
	}

	public MoveElevatorHeightVelocity() {
		this(0);
	}

	public MoveElevatorHeightVelocity(double goal) {
		changeGoal(goal);
	}

	protected void initialize() {
		velocityPID.reset();
		lastTime = Timer.getFPGATimestamp();
		Elevator.setBrake(false);
	}

	protected void execute() {
		double curSpeed = Elevator.getVelocity();
		double curPosition = Elevator.getEncoders();
		double timeElapsed = Timer.getFPGATimestamp() - lastTime;

		pr("\nMoveElevatorHeightVelocity:\nDistanceInRamp: "
				+ distanceInRamp(curSpeed) + "\nCurrent Speed: " + curSpeed
				+ "\nCurrent Position: " + curPosition + "\nElapsed Time: "
				+ timeElapsed + "\nVelocity Target: " + velocityTarget);

		if (Math.abs(targetPosition - curPosition) <= distanceInRamp(curSpeed)) {
			pr("Phase type set to ramp down");
			phase = phaseType.RAMP_DOWN;
		}

		// should be a switch, but it's not!
		if (phase == phaseType.RAMP_UP) {
			if (Math.abs(curSpeed) < VMAX) {
				velocityTarget += AMAX * timeElapsed * direction;
			} else {
				phase = phaseType.MAX_VEL;
			}
		}

		if (phase == phaseType.MAX_VEL) {
			velocityTarget = VMAX * direction;
		} else if (phase == phaseType.RAMP_DOWN) {
			velocityTarget -= AMAX * timeElapsed * direction;
		}

		velocityPID.setSetpoint(velocityTarget);
		velocityPID.calculate(curSpeed, false);
		Elevator.setElevatorSpeed(velocityPID.getOutput());

		lastTime = Timer.getFPGATimestamp();
	}

	protected boolean isFinished() {
		return (Elevator.getVelocity() <= STOP_THRESHOLD && phase == phaseType.RAMP_DOWN)
				|| Elevator.getBottomStop() || Elevator.getTopStop();
	}

	protected void end() {
		Elevator.setElevatorSpeed(0);
		Elevator.setBrake(true);
	}

	protected void interrupted() {
		end();
	}

	public void changeGoal(double goal) {
		targetPosition = goal;
		direction = goal / Math.abs(goal);
		phase = phaseType.RAMP_UP;
	}

	private static double distanceInRamp(double velocity) {
		return Math.pow(velocity, 2) / (2 * AMAX);
	}

	private static void pr(Object text) {
		if (PRINT)
			System.out.println(text);
	}

	private PIDController velocityPID = new PIDController(
			RobotValues.ElevatorKp, RobotValues.ElevatorKp,
			RobotValues.ElevatorKd, RobotValues.ElevatorThreshold,
			RobotValues.ElevatorMaxSpeed, RobotValues.ElevatorMinSpeed);

	private phaseType phase;
	private double targetPosition, direction;
	private double velocityTarget = 0;
	private double lastTime;
}
