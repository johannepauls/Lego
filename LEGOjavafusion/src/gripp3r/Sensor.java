package gripp3r;


import java.util.ArrayList;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;


public class Sensor extends Thread {
	private EV3IRSensor irSensor ;
	private SampleProvider distance;
	private SampleProvider average;
	private float[] samples;
	private ArrayList<Float> values = new ArrayList<Float>();
	

	public Sensor() {
		//irSensor.close();
		irSensor = new EV3IRSensor(SensorPort.S1);
		distance = irSensor.getDistanceMode();
		average = new MeanFilter(distance, 2);
		samples = new float[average.sampleSize()];

	}
	public void setData() {
		for(int i = 0; i < 10 ; i++) {
			average.fetchSample(samples, 0);
			values.add(0, samples[0]);
		}
		
	}
	
	public float[] getData() {
		System.out.println(samples[0]);
		
		return samples;
	}
	
	public void close() {
		irSensor.close();
	}
}
