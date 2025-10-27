package it.unibs.VAMsmarthouse;

import java.util.Random;

public class Lavatrice extends Elettrodomestico {
	private final Random random = new Random();

	public Lavatrice(String id, long time) {
		super(id, time, "Lavatrice");

	}

	@Override
	public StatoElettrodomestico generaStato() {
		switch (stato) {
		case SPENTO:
			return StatoElettrodomestico.SPENTO;

		case RUNNING:
			// se il sensore è presente e ha un valore
			if (sensore != null && sensore.getValue() != null) {
				// mantieni il comportamento casuale originale
				return random.nextInt(100) < 90 ? StatoElettrodomestico.RUNNING : StatoElettrodomestico.ERRORE;
			} else {
				return StatoElettrodomestico.ERRORE;
			}

		case ERRORE:
			// usa il sensore per confermare la presenza del valore
			if (sensore != null && sensore.getValue() != null) {
				return StatoElettrodomestico.ERRORE;
			} else {
				return StatoElettrodomestico.ERRORE;
			}

		default:
			return stato;
		}
	}
}
