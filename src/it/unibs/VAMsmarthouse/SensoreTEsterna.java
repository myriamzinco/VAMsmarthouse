package it.unibs.VAMsmarthouse;

import java.util.Random;

public class SensoreTEsterna extends Sensore<Double>// nel generics devi usare le classi wrapper
{
	private static final long COMMON_SEED = 1234L;
	private final Random random = new Random(COMMON_SEED);

	public SensoreTEsterna(String id) {
		super(id, 500, "Temperatura esterna");

	}

	@Override
	protected Double generaValore()// i metodi astratti si overridano tutti
	{// sistemiamo il problema della coerenza con il valore precedente
		if (value == null) {
			value = 10 + 20 * random.nextDouble();
			return value;
		}

		double variazione = -0.03 + (random.nextDouble() * 0.2);
		value += variazione;

		return value;
	}

}
