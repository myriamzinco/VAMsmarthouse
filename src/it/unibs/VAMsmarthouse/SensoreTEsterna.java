package it.unibs.VAMsmarthouse;

public class SensoreTEsterna extends Sensore<Double>// nel generics devi usare le classi wrapper
{

	public SensoreTEsterna(int id) {
		super(id, 500, "Temperatura");

	}

	@Override
	protected Double generaValore()// i metodi astratti si overridano tutti
	{
		return -5 + (40 * Math.random());
	}

}
