package it.unibs.VAMsmarthouse;

public class SensoreQualità extends Sensore<Double> {

	public SensoreQualità(String id) {
		super(id, 500, "Qualità");
	}

	@Override
	protected Double generaValore() {
		return -5 + (40 * Math.random());
	}
}
//Modificare il sensore in qualità dell'aria e mettere i vari metodi C02 e Umidità', aggiungere come per Testerna il metodo per uniformare con la temperatura precedente