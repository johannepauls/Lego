import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.*;
import lejos.robotics.filter.MeanFilter;
import lejos.robotics.navigation.MovePilot;

public class MotorTest {
	private static RegulatedMotor leftMotor;
	private static RegulatedMotor rightMotor;
	private static Wheel leftWheel;
	private static Wheel rightWheel;
	private static Chassis chassis;
	private static MovePilot pilot;
	private static EV3IRSensor irSensor ;
	private static SampleProvider distance;
	private static SampleProvider average;
	private static float[] samples;
	private static float value;
	private static Grip grip;

	public static void init() {
		irSensor = new EV3IRSensor(SensorPort.S1);
		distance = irSensor.getDistanceMode();
		average = new MeanFilter(distance, 2);
		samples = new float[average.sampleSize()];
		
		grip = new Grip();
		
		leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);

		leftWheel = WheeledChassis.modelWheel(leftMotor, 33).offset(-73);
		rightWheel = WheeledChassis.modelWheel(rightMotor, 33).offset(73);
		chassis = new WheeledChassis(new Wheel[] { leftWheel, rightWheel }, WheeledChassis.TYPE_DIFFERENTIAL);
		

		pilot = new MovePilot(chassis);

	}

	public static void main(String[] aprgs) {
		init();

		//travel forward and back picking up and moving until button is pushed
		 while (Button.readButtons() == 0) {
			 average.fetchSample(samples, 0);
			 value= samples[0];
			 System.out.println(value);
			if (!pilot.isMoving()) {
				pilot.forward();
			}

			if (samples[0] < 55 && grip.getLift() == true) {
				pilot.stop();
				grip.release();
				pilot.rotate(180);

			}

			 average.fetchSample(samples, 0);
			 value= samples[0];
			 System.out.println(value);

			if (samples[0] < 55 && grip.getLift() == false) {
				pilot.stop();
				Sound.beep();
				grip.lift();
				pilot.rotate(180);
				pilot.forward();
				average.fetchSample(samples, 0);
			}

		}
		irSensor.close();
		leftMotor.close();
		rightMotor.close();
		
		
	}

}
