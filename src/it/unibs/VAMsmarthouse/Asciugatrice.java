package it.unibs.VAMsmarthouse;

import java.util.Random;

public class Asciugatrice extends Elettrodomestico {
	private final Random random = new Random();

	public Asciugatrice(String id) {
		super(id, 1000, "Asciugatrice");
		this.stato = (Math.random() < 0.5) ? StatoElettrodomestico.SPENTO : StatoElettrodomestico.RUNNING;

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
