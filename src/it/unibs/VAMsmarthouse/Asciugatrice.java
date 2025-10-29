package it.unibs.VAMsmarthouse;

import java.util.Random;

public class Asciugatrice extends Elettrodomestico {
	private final Random random = new Random();

	public Asciugatrice(String id, long time) {
		super(id, time, "Asciugatrice");
	}

	@Override
	public StatoElettrodomestico generaStato() {
		switch (stato) {
		case SPENTO:
			return StatoElettrodomestico.SPENTO;

		case RUNNING:
			if (sensore != null && sensore.getValue() != null) {
				return random.nextInt(100) < 90 ? StatoElettrodomestico.RUNNING : StatoElettrodomestico.ERRORE;
			} else {
				return StatoElettrodomestico.ERRORE;
			}

		case ERRORE:
			return StatoElettrodomestico.ERRORE;

		default:
			return stato;
		}
	}

}
