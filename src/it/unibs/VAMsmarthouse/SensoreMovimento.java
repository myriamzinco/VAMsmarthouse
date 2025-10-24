package it.unibs.VAMsmarthouse;

public class SensoreMovimento extends Sensore<Boolean>{

	public SensoreMovimento(int id, long time, String type) {
		super(id, time, "Movimento");
	}

	@Override
	protected Boolean generaValore() {
	    int num = (int) (Math.random() * 2);
	    return num == 1; // true se num = 1
	}

	
}
