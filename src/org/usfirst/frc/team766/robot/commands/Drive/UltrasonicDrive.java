package org.usfirst.frc.team766.robot.commands.Drive;

import org.usfirst.frc.team766.robot.RobotValues;
import org.usfirst.frc.team766.robot.commands.CommandBase;

/**
 * 	Uses a PID for the ultrasonic sensor to drive forward
 *	@author Blevenson
 */
public class UltrasonicDrive extends CommandBase {

	private double distanceFromBox;
	private boolean beSafe;
	private double currentDistance;
	
	public UltrasonicDrive()
	{
		this.beSafe = true;
		distanceFromBox = 0;
	}
	
    public UltrasonicDrive(double distance, boolean beSafe) {
    	distanceFromBox = distance;
    	this.beSafe = beSafe;
    }

    protected void initialize() {
    	Drive.resetEncoders();
    	currentDistance = 0;
    }

    protected void execute() {
    	//Drive forward using the PID here for the Ultrasonic sensor
    	currentDistance = Drive.getUltrasonicDistance();
    }

    protected boolean isFinished() {
        return (currentDistance <= distanceFromBox) || 
        		(beSafe && (Drive.getAverageEncoderDistance() >= RobotValues.safteyDriveDistance));
    }

    protected void end() {
    	Drive.setPower(0.0d);
    }
    
    protected void interrupted() {
    	System.out.println("0 Nose!!!  Someone interrupted the UltrasonicDrive Command...");
    	end();
    }
}