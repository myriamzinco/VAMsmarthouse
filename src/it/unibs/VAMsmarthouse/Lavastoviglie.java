package it.unibs.VAMsmarthouse;

import java.util.Random;

public class Lavastoviglie extends Elettrodomestico {
	private final Random random = new Random();

	public Lavastoviglie(String id) {
		super(id, 1000, "Lavastoviglie");
	}

	@Override
	public StatoElettrodomestico generaStato() {
		switch (stato) {
		case SPENTO:
			return StatoElettrodomestico.SPENTO;

		case RUNNING:

			return random.nextInt(100) < 90 ? StatoElettrodomestico.RUNNING : StatoElettrodomestico.ERRORE;

		case ERRORE:

			return StatoElettrodomestico.ERRORE;

		default:
			return stato;
		}
	}

}
