package it.unibs.VAMsmarthouse;

public class SensoreTInterna extends Sensore<Double> {

	private SensoreTEsterna esterna;

	public SensoreTInterna(int id, SensoreTEsterna esterna) {
		super(id, 500, "Temperatura interna");
		this.esterna = esterna;

	}

	@Override
	protected Double generaValore()// i metodi astratti si overridano tutti
	{
		return esterna.getValue() + (-5 + Math.random() * 10);
	}

}// sicuramente poi dovremo sistemare il fatto che se ho due sensori interni che
	// lavorano in sincro, mi devono generare sicuramente la sessa temperatura
