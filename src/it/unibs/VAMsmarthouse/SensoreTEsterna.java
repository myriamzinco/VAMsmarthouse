package it.unibs.VAMsmarthouse;

import java.util.Random;

public class SensoreTEsterna extends Sensore<Double>// nel generics devi usare le classi wrapper
{
	private static final long COMMON_SEED = 1234L;
	private final Random random = new Random(COMMON_SEED);

	public SensoreTEsterna(String id) {
		super(id, 500, "Temperatura");

	}

	@Override
	protected Double generaValore()// i metodi astratti si overridano tutti
	{
		return -5 + (40 * Math.random());
	}

}
