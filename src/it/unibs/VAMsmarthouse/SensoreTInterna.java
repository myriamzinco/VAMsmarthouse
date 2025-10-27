package it.unibs.VAMsmarthouse;

import java.util.Random;

public class SensoreTInterna extends Sensore<Double> {
	private static final long COMMON_SEED = 1234L;
	private final Random random = new Random(COMMON_SEED);
	private final double offset;
	private SensoreTEsterna esterna;

	public SensoreTInterna(String id, SensoreTEsterna esterna, double offset) {
		super(id, 500, "Temperatura interna");
		this.esterna = esterna;
		switch (id) {
		case "t-0" -> this.offset = 0.5;
		case "t-1" -> this.offset = 1.5;
		default -> this.offset = 0.0;
		}
	}

	@Override
	protected Double generaValore()// i metodi astratti si overridano tutti
	{
		return esterna.getValue() + (-5 + random.nextDouble() * 10) + offset;
	}

}
