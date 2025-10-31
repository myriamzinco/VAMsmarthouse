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
			if (sensore != null && sensore.getValue() != null) {
				if (random.nextInt(100) < 90) {
					return StatoElettrodomestico.RUNNING;
				} else {
					stato = StatoElettrodomestico.ERRORE;
					tipoErrore = generaErroreCasuale();
					return StatoElettrodomestico.ERRORE;
				}
			} else {
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

//aggiungere metodo del se e solo se oppure roba qui sotto ma è troppo difficile
//dizionario  elementi chiave-valore chiave stato spento (0), running(1), errore con ognuno codice di errore 
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
}
