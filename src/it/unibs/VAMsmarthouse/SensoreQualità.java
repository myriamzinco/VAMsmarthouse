package it.unibs.VAMsmarthouse;

public class SensoreQualità extends Sensore<Double> {
	
	public double umidita; //private o public questi due?
	public double co2; //cercando su internet dovrebbero essere ppm

	public SensoreQualità(String id) {
		super(id, 500, "Qualità");
	}

	@Override
	protected Double generaValore() {
		 umidita = 30 + Math.random() * 50;
		
		//qui abbiamo chiesto a chat come poter avere una base dell'umidita per riuscire ad avere co2 coerente
		
		double baseCO2 = 400 + (umidita - 30) * 20;
		co2 = baseCO2 + (Math.random() - 0.5) * 100;
		
		System.out.printf("Valori di umidita[%] e CO2[ppm]: %.1f%% & %.0f ppm"); //stampiamo entrambi i valori con l'unità di misura, ma ho paura che non sia una scrittura omogeonea rispetto agli altri sensori
		
		if (co2 > 1500) {
			System.out.println( "Fortemente consigliato aprire le finestre");
		} else if (co2 > 1000) {
			System.out.println("Consigliabile aprire le finestre");
		} else {
			System.out.println("Qualità dell'aria ottimale!");
		}
		
	}
	public double getUmidita() {
		return umidita;
	}
	
	public double getCO2() {
		return co2;
	}
		
		
		return -5 + (40 * Math.random()); //questa riga molto sospicius... ma non so se serva o meno
}


//Modificare il sensore in qualità dell'aria e mettere i vari metodi C02 e Umidità', aggiungere come per Testerna il metodo per uniformare con la temperatura precedente