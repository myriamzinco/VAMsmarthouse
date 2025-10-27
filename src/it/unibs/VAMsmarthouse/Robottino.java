package it.unibs.VAMsmarthouse;

import java.util.Random;

enum TipoErroreRobottino {
	ACQUA_SCARICA, SERBATOIO_PIENO, BLOCCATO;
}

public class Robottino extends Elettrodomestico {
	private final Random random = new Random();
	private TipoErroreRobottino tipoErrore;

	public Robottino(String id, long time, String type) {
		super(id, time, "Robot Lavapavimenti");
	}

	@Override
	public StatoElettrodomestico generaStato() {
		switch (stato) {
		case SPENTO:
			return StatoElettrodomestico.SPENTO;

		case RUNNING:
			// controlla se il sensore è presente
			if (sensore != null && sensore.getValue() != null) {
				// usa il sensore per decidere casualmente l'errore
				if (random.nextInt(100) < 90) {
					return StatoElettrodomestico.RUNNING;
				} else {
					stato = StatoElettrodomestico.ERRORE;
					tipoErrore = generaErroreCasuale();
					return StatoElettrodomestico.ERRORE;
				}
			} else {
				// se sensore assente, comportamento originale
				return StatoElettrodomestico.ERRORE;
			}

		case ERRORE:
			if (sensore != null && sensore.getValue() != null) {
				tipoErrore = generaErroreCasuale();
			}
			return StatoElettrodomestico.ERRORE;
		default:
			return stato;
		}
	}

	public TipoErroreRobottino getTipoErrore() {
		return tipoErrore;
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
}
