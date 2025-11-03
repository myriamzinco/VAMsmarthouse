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

//aggiungere metodo del se e solo se oppure roba qui sotto ma è troppo difficile
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

	@Override
	public String toString() {
		if (stato == StatoElettrodomestico.ERRORE && tipoErrore != null) {
			return String.format("[%s] ID=%s, Stato=%s (%s)", type, id, stato, tipoErrore);
		}
		return super.toString();
	}
}
