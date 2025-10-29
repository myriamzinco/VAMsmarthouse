package it.unibs.VAMsmarthouse;

public class SensoreMovimento extends Sensore<Boolean> {

	public SensoreMovimento(String id, long time) {
		super(id, time, "Movimento");
	}

	@Override
	protected Boolean generaValore() {
		int num = (int) (Math.random() * 2);
		return num == 1;
	}

}
