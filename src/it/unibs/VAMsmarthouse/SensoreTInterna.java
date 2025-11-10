package it.unibs.VAMsmarthouse;

import java.util.Random;

public class SensoreTInterna extends Sensore<Double> {
	// Common seed per mantenere una generazione coerente con quella della t esterna
	// e tra le temperature dei due piani
	private static final long COMMON_SEED = 1234L;
	private final Random random = new Random(COMMON_SEED);
	private final double offset;
	private SensoreTEsterna sEsterno;
	private static final double TMIN = 15.0;
	private static final double TMAX = 26.0;

//costruttore con diverso l'offset in base al piano 
	public SensoreTInterna(String id, SensoreTEsterna esterna) {
		super(id, 500, "Temperatura interna");
		this.sEsterno = esterna;
		switch (id) {
		case "t-0" -> this.offset = 0.5;
		case "t-1" -> this.offset = 1.5;
		default -> this.offset = 0.0;
		}

	}

//override del genera valore per poter generare le temperature interne. Overridiamo in tutte le sosttoclassi di sensore i genera valore perchè è un metodo astratto con un tipo di ritorno generico T
	@Override
	protected Double generaValore() {
		Double esterna = sEsterno.getValue();
		// se la temperatura esterna non è ancora stata inizializzata supponiamo un
		// valore generico intorno ai 20 gradi
		if (esterna == null) {
			value = 20.0 + (-0.3 + random.nextDouble() * 0.6) + offset;
			return value;
		} else if (esterna <= TMIN) { /*
										 * aggiungiamo righe da 28 a 34 per mantenere una temperatura "vivibile" in casa
										 * anche se fuori fa freddo o caldo (supponiamo la presenza di un impianto di
										 * riscaldamento e condizionatore
										 */
			value = 17 + (21 - 17) * random.nextDouble();
			return value;
		} else if (esterna >= TMAX) {
			value = 20 + (24 - 20) * random.nextDouble();
			return value;
		}
		if (value == null) {
			value = esterna + (-0.5 + random.nextDouble() * 2) + offset;
			return value;
		} else
			value = (value * 0.8) + (esterna * 0.2) + offset;
		return value;
	}

//Override del to string 
	@Override
	public String toString() {
		return String.format("[%s] [%s] ID=%s, Valore=%.2f °C", Sensore.timeStamp(), type, id, value);
	}
}
