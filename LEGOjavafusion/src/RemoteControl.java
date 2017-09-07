import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;


public class RemoteControl {
	private static EV3IRSensor irSensor ;
	
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
	}
	
	/*public RemoteControl() {
		irSensor = new EV3IRSensor(SensorPort.S1);
	}*/

	public static int getCommand(){
		command = irSensor.getRemoteCommand(1);
		return command;
	}
	
	public static void main(String[] aprgs) {
		init();
		
		while(Button.readButtons() == 0) {
			c=getCommand();
			
			System.out.println(c);
			
		}
	}
	

}
