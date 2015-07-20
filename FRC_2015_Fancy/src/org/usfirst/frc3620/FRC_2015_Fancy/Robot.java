// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3620.FRC_2015_Fancy;

import java.io.File;
import java.util.TimeZone;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.usfirst.frc3620.FRC_2015_Fancy.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	// begin my fancy stuff
	public static Preferences preferences;
	static private RobotMode currentRobotMode = RobotMode.INIT, previousRobotMode;
	static Logger logger = LoggerFactory.getLogger(Robot.class);
	DataLogger dataLogger;
	DriverStation driverStation = DriverStation.getInstance();
	/// end my fancy stuff

    Command autonomousCommand;

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static RelaySubsystem relaySubsystem;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	// Set dataLogger and Time information
    			TimeZone.setDefault(TimeZone.getTimeZone("America/Detroit"));
    			
    			File logDirectory = null;
    			if (logDirectory == null) logDirectory = findLogDirectory(new File("/u"));
    			if (logDirectory == null) logDirectory = findLogDirectory(new File("/v"));
    			if (logDirectory == null) logDirectory = findLogDirectory(new File("/x"));
    			if (logDirectory == null) logDirectory = findLogDirectory(new File("/y"));
    			if (logDirectory == null) {
    				logDirectory = new File("/home/lvuser/logs");
    			    if (!logDirectory.exists())
    			    {
    				    logDirectory.mkdir();
    			    }
    			}
    			if (logDirectory != null && logDirectory.isDirectory())
    			{
    				String logMessage = String.format("Log directory is %s\n", logDirectory);
    				System.out.print (logMessage);
    				EventLogging.writeToDS(logMessage);
    				EventLogging.setup(logDirectory);
    				dataLogger = new DataLogger(logDirectory);
    				dataLogger.setMinimumInterval(1000);
    			}

    			logger.info ("Starting robotInit");
    			
    			preferences = Preferences.getInstance();
    			
    	
    RobotMap.init();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        relaySubsystem = new RelaySubsystem();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands 
        //(which it very likely will), subsystems are not guaranteed to be 
        // constructed yet. Thus, their requires() statements may grab null 
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // instantiate the command used for the autonomous period
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    	allInit(RobotMode.DISABLED);
    }

    public void disabledPeriodic() {
    	allBeginningOfPeriodic();
        Scheduler.getInstance().run();
        allEndOfPeriodic();
    }

    public void autonomousInit() {
    	allInit(RobotMode.AUTONOMOUS);
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	allBeginningOfPeriodic();
        Scheduler.getInstance().run();
        allEndOfPeriodic();
    }

    public void teleopInit() {
    	allInit(RobotMode.TELEOP);
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	allBeginningOfPeriodic();
        Scheduler.getInstance().run();
        allEndOfPeriodic();
    }
    
    public void testInit() {
    	allInit(RobotMode.TEST);
    	
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	allBeginningOfPeriodic();
        LiveWindow.run();
        allEndOfPeriodic();
    }
    
	void allInit (RobotMode newMode)
	{
		previousRobotMode = currentRobotMode;
		currentRobotMode = newMode;
		
		logger.info("Moving from {} to {}, position = {}, FMS = {}", previousRobotMode, currentRobotMode, driverStation.getAlliance() + " " + driverStation.getLocation(), driverStation.isFMSAttached());
		
		// if anyone needs to know about mode changes, let
		// them know here.
	}

	/**
	 * This gets called at the beginning of any *periodic().
	 * We're putting this here "just in case".
	 */
	void allBeginningOfPeriodic() {
		//
	}

	/**
	 * This gets called at the end of any *periodic().
	 * We're putting this here "just in case".
	 */
	void allEndOfPeriodic()
	{
		if (dataLogger.shouldLogData())
		{
			// fololowing two lijnes added 3/17/2015 DEW
			dataLogger.addDataItem("robotMode", currentRobotMode.toString());
			dataLogger.addDataItem("robotModeInt", currentRobotMode.ordinal());
			
			dataLogger.saveDataItems();
		}
	}
	
	public static RobotMode getCurrentRobotMode() {
		return currentRobotMode;
	}
	
	public static RobotMode getPreviousRobotMode() {
		return previousRobotMode;
	}
	
	public static void commandInitialized(Object o) {
		logger.info("Command init: {}", o);
	}

	public static void commandEnded(Object o) {
		logger.info("Command end:  {}", o);
	}

	public static void commandInterrupted(Object o) {
		logger.info("Command interrupt: {}", o);
	}
	
	public File findLogDirectory (File root) {
		// does the root directory exist?
		if (!root.isDirectory()) return null;
		
		File logDirectory = new File(root, "logs");
		if (!logDirectory.isDirectory()) return null;
		
		return logDirectory;
	}

	public static void dumpPreferences()
	{
		for(Object a: preferences.getKeys())
		{
			System.out.println(a + "=" + preferences.getString((String) a, "foobar"));
		}
	}

}
