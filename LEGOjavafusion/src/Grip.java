import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class Grip {
	private RegulatedMotor midMotor;
	private boolean lift;
	
	public Grip() {
		midMotor = new EV3MediumRegulatedMotor(MotorPort.A);
		midMotor.setSpeed(1125); // 75% of top speed 1500, unit: degrees/sec
	}
	
	public void lift() {
		midMotor.rotate(1125);
		lift = true;
	}
	
	public void release() {
		midMotor.rotate(-1125);
		lift = false;
	}
	
	public boolean getLift() {
		return lift;
	}
	
}
