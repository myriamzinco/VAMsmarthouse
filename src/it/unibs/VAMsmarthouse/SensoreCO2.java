package it.unibs.VAMsmarthouse;

public class SensoreCO2 extends Sensore<Double>{

	public SensoreCO2(int id, long time, String type) {
		super(id, time, "Humidity");
	}

	@Override
	protected Double generaValore() {
		return -5 + (40 * Math.random());
	}
	
}
