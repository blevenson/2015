package org.usfirst.frc.team766.robot.commands.Elevator;

import org.usfirst.frc.team766.robot.RobotValues;
import org.usfirst.frc.team766.robot.commands.CommandBase;

/**
 * Uses 3rd joystick to move elevator. For testing
 * 
 * @author Patrick Kao
 */
public class JoystickControl extends CommandBase {
	private static final double BOTTOM_TRAVEL = .01;
	private static final double TOP_TRAVEL = RobotValues.ElevatorTopHeight - .01;

	public JoystickControl() {
		requires(Elevator);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Elevator.setBrake(false);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double user = OI.getTestY();
		double curHeight = Elevator.getEncoders();
		
		if (curHeight < BOTTOM_TRAVEL&& user <0){
			Elevator.setElevatorSpeedRaw(0);
		} else if(curHeight > TOP_TRAVEL && user >0 ){
			Elevator.setElevatorSpeedRaw(0);
		}else
			Elevator.setElevatorSpeedRaw(
					user);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}