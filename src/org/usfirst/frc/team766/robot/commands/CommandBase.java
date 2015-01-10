package org.usfirst.frc.team766.robot.commands;

import org.usfirst.frc.team766.lib.logData;
import org.usfirst.frc.team766.robot.subsystems.Drive;
import org.usfirst.frc.team766.robot.OI;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team766.robot.subsystems.Elevator;

/**
 * Create instances of the subsystems here
 * Later in commands import this class to
 * gain access to the systems presets
 *
 * @author Brett Levenson
 */
public abstract class CommandBase extends Command{
	
	public static Drive Drive;
	public static OI OI;
	public static Elevator Elevator;
	//public static logData myLog = new logData(); 
	
	public static void init(){
		Drive = new Drive();
		OI = new OI();
		Elevator = new Elevator();
	}

}