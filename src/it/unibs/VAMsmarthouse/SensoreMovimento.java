package it.unibs.VAMsmarthouse;

public class SensoreMovimento extends Sensore<Boolean> {

	public SensoreMovimento(String id) {
		super(id, 5000, "Movimento");
	}

	@Override
	protected Boolean generaValore() {
		int num = (int) (Math.random() * 2);
		return num == 1;
	}

	@Override
	public String toString() {
		return String.format("[%s] ID=%s, Value=%s", type, id, value);
	}
}
