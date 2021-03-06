package org.usfirst.frc.team766.robot.commands;

import java.util.ArrayList;

import org.usfirst.frc.team766.robot.Ultrasonic.UltrasonicInfo;
import org.usfirst.frc.team766.robot.Ultrasonic.UltrasonicSensor;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author PKao
 * @author EBlack
 */

//Use this class if you want an accurate ultrasonic

public class AccurateReading extends Command {
	private static final boolean PRINT=true;
    public AccurateReading() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	UltrasonicSensor.getInstance();
    	distances.clear();
    	isFinished = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	UltrasonicInfo currentValue = UltrasonicSensor.getInstance().getDistance();
    	if(currentValue.isNew()){
    		pr("Distance to target: " + new String(new Double(currentValue.getDistance1()).toString()));
    		distances.add(currentValue.getDistance1());
    		pr("Array Size: "+distances.size());
    		
    		if(distances.size()>=100){
    			double mean = 0;
    			for(double curValue: distances){
    				mean+=curValue;
    			}
    			mean/=distances.size();
    			double standardDev = 0;
    			for(double curValue: distances){
    				curValue-=mean;
    				curValue*=curValue;
    				standardDev+=curValue;
    			}
				standardDev/=distances.size();
    			standardDev = Math.sqrt(standardDev);
    			pr("Mean: "+mean);
    			pr("Standard Deviation: "+standardDev);
    			distances.clear();
    			isFinished = true;
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
    private void pr (String printData){
    	if(PRINT)System.out.println("Calibrate Ultrasonic: "+printData);
    }
    
    boolean isFinished = false;
    ArrayList<Double> distances = new ArrayList<Double>();
}
