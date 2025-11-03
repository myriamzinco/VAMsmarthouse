package it.unibs.VAMsmarthouse;

import java.util.Random;

public class SensoreTInterna extends Sensore<Double> {
	private static final long COMMON_SEED = 1234L;
	private final Random random = new Random(COMMON_SEED);
	private final double offset;
	private SensoreTEsterna sEsterno;

	public SensoreTInterna(String id, SensoreTEsterna esterna) {
		super(id, 500, "Temperatura interna");
		this.sEsterno = esterna;
		switch (id) {
		case "t-0" -> this.offset = 0.5;
		case "t-1" -> this.offset = 1.5;
		default -> this.offset = 0.0;
		}
	}

	@Override
	protected Double generaValore() {
		Double esterna = sEsterno.getValue();
		if (esterna == null) {
			return 20.0 + offset;
		}
		if (value == null) {
			value = esterna + (-1 + random.nextDouble() * 5) + offset;
			return value;
		}
		double nuova = (value * 0.8) + (esterna * 0.2) + offset;
		return nuova;

	}

}
