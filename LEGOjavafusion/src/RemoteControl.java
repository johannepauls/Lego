import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;


public class RemoteControl {
	private static RegulatedMotor leftMotor;
	private static RegulatedMotor rightMotor;
	private static Wheel leftWheel;
	private static Wheel rightWheel;
	private static Chassis chassis;
	private static MovePilot pilot;
	private static EV3IRSensor irSensor ;
	private static Grip grip;
	private static int command;
	private static int c;
	
	
	/* 1 TOP-LEFT
	 * 2 BOTTOM-LEFT
	 * 3 TOP-RIGHT
	 * 4 BOTTOM-RIGHT
	 * 5 TOP-LEFT + TOP-RIGHT
	 * 6 TOP-LEFT + BOTTOM-RIGHT
	 * 7 BOTTOM-LEFT + TOP-RIGHT
	 * 8 BOTTOM-LEFT + BOTTOM-RIGHT
	 * 9 CENTRE/BEACON
	 * 10 BOTTOM-LEFT + TOP-LEFT
	 * 11 TOP-RIGHT + BOTTOM-RIGHT*/
	
	public static void init() {
		irSensor = new EV3IRSensor(SensorPort.S1);
		
		grip = new Grip();
		
		leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);

		leftWheel = WheeledChassis.modelWheel(leftMotor, 33).offset(-73);
		rightWheel = WheeledChassis.modelWheel(rightMotor, 33).offset(73);
		chassis = new WheeledChassis(new Wheel[] { leftWheel, rightWheel }, WheeledChassis.TYPE_DIFFERENTIAL);
		
		pilot = new MovePilot(chassis);
	}
	
	public static int getCommand(){
		command = irSensor.getRemoteCommand(1);
		return command;
	}
	
	public static void stop() {
		if(pilot.isMoving()) {
			pilot.stop();
		}
		
		if(leftMotor.isMoving()) {
			leftMotor.stop();
		}
		
		if(rightMotor.isMoving()) {
			rightMotor.stop();
		}
		
	}
	
	public static void grip() {
		if(grip.getLift()) grip.release();
		else grip.lift();
	}
	
	public static void main(String[] aprgs) {
		init();
		
		while(Button.readButtons() == 0) {
			c=getCommand();
			
			System.out.println(c);

	        switch (c) {
	        /* 1 TOP-LEFT
	    	 * 2 BOTTOM-LEFT
	    	 * 3 TOP-RIGHT
	    	 * 4 BOTTOM-RIGHT
	    	 * 5 TOP-LEFT + TOP-RIGHT
	    	 * 6 TOP-LEFT + BOTTOM-RIGHT
	    	 * 7 BOTTOM-LEFT + TOP-RIGHT
	    	 * 8 BOTTOM-LEFT + BOTTOM-RIGHT
	    	 * 9 CENTRE/BEACON
	    	 * 10 BOTTOM-LEFT + TOP-LEFT
	    	 * 11 TOP-RIGHT + BOTTOM-RIGHT*/
	        	case 0: stop();
	        			break;
	            case 1:  leftMotor.forward();
	                     break;
	            case 2:  leftMotor.backward();
	                     break;
	            case 3:  rightMotor.forward();
	                     break;
	            case 4:  rightMotor.backward();
	                     break;
	            case 5:  pilot.forward();
	                     break;
	            case 6:  pilot.rotateRight();
	                     break;
	            case 7:  pilot.rotateLeft();
	                     break;
	            case 8:  pilot.backward();
	                     break;
	            case 9:  grip();
	                     break;
	            case 10: stop();
	                     break;
	            case 11: stop();
	                     break;
	            default: stop();
	                     break;
	        }
			
		}
	}
	

}
