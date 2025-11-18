package it.unibs.VAMsmarthouse;

import java.util.Random;

public class SensoreTInterna extends Sensore<Double> {
	private static final long COMMON_SEED = 1234L;
	private final Random random = new Random(COMMON_SEED);
	private final double offset;
	private SensoreTEsterna sEsterno;
	private static final double TMIN = 15.0;
	private static final double TMAX = 26.0;

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
			value = 20.0 + (-0.3 + random.nextDouble() * 0.6) + offset;
			return value;
		} else if (esterna <= TMIN) {
			value = 17 + (21 - 17) * random.nextDouble();
			return value;
		} else if (esterna >= TMAX) {
			value = 20 + (24 - 20) * random.nextDouble();
			return value;
		}
		if (value == null) {
			value = esterna + (-0.05 + random.nextDouble() * 0.1) + offset;
			return value;
		} else
			value = (value * 0.95) + (esterna * 0.05);
		return value;
	}

	@Override
	public String toString() {
		return String.format("[%s] [%s] ID=%s, Valore=%.2f °C", Sensore.timeStamp(), type, id, value);
	}
}
