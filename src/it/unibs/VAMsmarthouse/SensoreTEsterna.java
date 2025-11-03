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
	{// sistemiamo il problema della coerenza con il valore precedente
		if (value == null) {
			return -5 + (40 * random.nextDouble());
		}

		double variazione = -0.5 + (random.nextDouble());
		value += variazione;

		return value;
	}

}
