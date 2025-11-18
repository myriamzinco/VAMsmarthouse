package it.unibs.VAMsmarthouse;

import java.util.Random;

public class SensoreTEsterna extends Sensore<Double> {
	private static final long COMMON_SEED = 1234L;
	private final Random random = new Random(COMMON_SEED);

	public SensoreTEsterna(String id) {
		super(id, 500, "Temperatura esterna");

	}

	@Override
	protected Double generaValore() {
		if (value == null) {
			value = 10 + 20 * random.nextDouble();
			return value;
		}

		double variazione = -0.01 + (random.nextDouble() * 0.02);
		value += variazione;

		return value;
	}

}
