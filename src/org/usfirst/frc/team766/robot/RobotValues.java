package org.usfirst.frc.team766.robot;

public class RobotValues { // Note: All distances in units of meters
	// Conversion constants
	public static final double INCHES_TO_METERS = 0.0254;

	// Cheesy drive values
	public static final double sensitivityHigh = .85d;
	public static final double sensitivityLow = .75d;
	public static final double distanceFromBox = 5d;
	public static final double safteyDriveDistance = 10;
	public static final double driveDividor = 10;


	// Autonomous
	// Modes
	public static final int 
		Auton_VisionDrive = 0, 
		Auton_Move = 1,
		Auton_PickOneBox = 2, 
		Auton_Rotate = 3, 
		Auton_MoveToLandfill = 4,
		Auton_None = 5,
		Auton_Push3Boxes = 6,
		Auton_Coopertition = 7,
		Auton_Container = 8,
		Auton_3Tote = 9;
	
	public static final String[] Autons = {"Vision Drive", "Move Forward", "Pickup One Tote", "Rotate 90 degrees",
		"Move to landfill", "No Auton Selected", "3 Tote Hot", "Coopertition", "One Container"
		, "Three Tote"};
	
	public static final int Auton_Max = 9;
	public static final int Auton_Min = 0;

	// Values for Autons
	public static final double DriveForwardDistance = 107 * INCHES_TO_METERS;// Distance
																				// to
																				// landmark
	public static final double DistanceToLandfill = (646.88 / 2 - 102.81)
			* INCHES_TO_METERS;
	public static final double DistanceToStep = 646.88 / 2 - 102.81;
	public static final double OptimalGripperDistance = 0.45;
	public static final double DistanceBetweenBoxes = 33; // Distance from box
															// edge to box edge
	public static final double Box_Width = 48 * INCHES_TO_METERS;
	public static final double Box_Height = .305; //meters
	//Three Tote Auton
	public static double ThreeToteAutonDrivePastToteDistance1 = 1.25;
	public static double ThreeToteAutonDrivePastToteDistance2 = 1.25;
	public static double ThreeToteAutonDistanceToNextTote = .4;
	public static double ThreeToteAutonDistanceToAutoZone = 2;
	
	
	// Bearly Drive
	// Decrease for faster reaction times
	public static final double alpha = 0.4;
	public static final double SlowAlpha = 0.7;
	public static final double SlowModeSLowFactor = 0.85;
	public static final double leftSlowFactor = 0.7;
	public static final double rightSlowFactor = 0.7;

	// Elevator presets
	private static final double ElevatorPresetBase = Box_Height;// All elevator
																// presets based
																// on this
																// variable
	// Encoder Height of elevator, needs to be changed
	public static double ElevatorTopHeight = 1.59;//1.57
	public static double elevatorSavedHeight = 0;
	public static final double BoxFlange = .04;
	public static final double[] ElevatorPresets = { 0, ElevatorPresetBase - BoxFlange,
			ElevatorPresetBase * 2 - BoxFlange, ElevatorPresetBase * 3 - BoxFlange,
			ElevatorPresetBase * 4 - BoxFlange, ElevatorPresetBase * 5 - BoxFlange, ElevatorTopHeight};
	public static final double DriveGroundHeight = .06;
	public static boolean releasedChute = false;
	
	// Gyro DriveTurn
	public static double TurnAngleKp = 0.35;
	public static double TurnAngleKi = 0.01;
	public static double TurnAngleKd = 0.15;

	// drive straight
								//.2
	public static final double DriveKp = 0.1;// Decrease to decrease robot speed
	public static final double DriveKi = 0.01;
	public static final double DriveKd = 0.1;
	public static final double Driveoutputmax_low = -10;
	public static final double Driveoutputmax_high = 10;
	public static final double DriveThreshold = .05;

	// Ultrasonic
	public static final double UltrasonicDriveKp = 6.4;
	public static final double UltrasonicDriveKi = 0.0;// 0.01
	public static final double UltrasonicDriveKd = 0.0;

	// gyro turning in drive forward
	public static final double AngleKp = 0.01;//.05
	public static final double AngleKi = 0.00;
	
	public static final double AngleKd = 0.03;
	public static final double Angleoutputmax_low = -10;
	public static final double Angleoutputmax_high = 10;
	public static final double AngleThreshold = Double.MIN_VALUE;

	// drive smoothly
	public static final double SmootherLeftKp = .1;
	public static final double SmootherRightKp = .1;
	public static final double SmootherLeftKi = .2;
	public static final double SmootherRightKi = .2;

	// elevate smoothly
	public static final double ElevatorKp = 3.2;
	public static final double ElevatorKi = .05;//.005
	public static final double ElevatorKd = 3.2;
	public static final double ElevatorThreshold = 0.015;
	public static final double ElevatorMaxSpeed = .5;//Turned down for testing
	public static final double ElevatorMinSpeed = -.5;

	// Intake PID
	public static double IntakeKP = 0.04;
	public static double IntakeKI = 0.0;
	public static double IntakeKD = 0.0;
	public static double IntakeThreshold = 2;

	// Elevator
	public static final double SliderChangeTolerance = 0.01;
	public static int numTotes = 0;

	
	//Auton Driving
	public static final double k_moveToNextTote = 33 * INCHES_TO_METERS;
	public static final double k_moveBackToAutoZone = -3;
	public static final double turnNextTote = 45;
	public static final double DistanceToPassTote = 1;

}
