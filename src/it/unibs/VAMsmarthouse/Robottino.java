package it.unibs.VAMsmarthouse;

import java.util.Random;

enum TipoErroreRobottino {
	ACQUA_SCARICA, SERBATOIO_PIENO, BLOCCATO;
}

public class Robottino extends Elettrodomestico {
	private final Random random = new Random();
	private TipoErroreRobottino tipoErrore;

	public Robottino(String id) {
		super(id, 1000, "Robot Lavapavimenti");
		this.stato = (Math.random() < 0.5) ? StatoElettrodomestico.SPENTO : StatoElettrodomestico.RUNNING; // impostiamo
																											// lo stato
																											// iniziale
																											// a uno
																											// stato
																											// casuale
																											// tra
																											// spento e
																											// acceso

	}

	@Override
	public StatoElettrodomestico generaStato() {
		switch (stato) {
		case SPENTO:
			return StatoElettrodomestico.SPENTO;

		case RUNNING:
			if (random.nextInt(100) < 90) {
				return StatoElettrodomestico.RUNNING;
			} else {
				stato = StatoElettrodomestico.ERRORE;
				tipoErrore = generaErroreCasuale();
				return StatoElettrodomestico.ERRORE;

			}

		case ERRORE:
			return StatoElettrodomestico.ERRORE;
		default:
			return stato;
		}

	}

//L'errore non è unico, ci sono 3 tipi di errore, e quando il robottino entra in errore, ne genera uno dei 3 casuali e resta in quello
	public TipoErroreRobottino getTipoErrore() {
		return this.tipoErrore;
	}

	private TipoErroreRobottino generaErroreCasuale() {
		int n = random.nextInt(3);
		switch (n) {
		case 0:
			return TipoErroreRobottino.ACQUA_SCARICA;
		case 1:
			return TipoErroreRobottino.SERBATOIO_PIENO;
		case 2:
		default:
			return TipoErroreRobottino.BLOCCATO;
		}
	}

//override del to string solo nel caso in cui il robottino vada in errore
	@Override
	public String toString() {
		if (stato == StatoElettrodomestico.ERRORE && tipoErrore != null) {
			return String.format("[%s]  [%s] ID=%s, Stato=%s (%s)", Sensore.timeStamp(), type, id, stato, tipoErrore);

		}
		return super.toString();
	}
}
