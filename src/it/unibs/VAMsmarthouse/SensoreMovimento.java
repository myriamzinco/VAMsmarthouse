package it.unibs.VAMsmarthouse;

public class SensoreMovimento extends Sensore<Boolean> {
	public SensoreMovimento(String id) {
		super(id, 1000, "Movimento");
	}

	@Override
	protected Boolean generaValore() {
		int num = (int) (Math.random() * 2);
		return num == 1;
	}

	@Override
	public String toString() {
		return String.format("[%s]  [%s] ID=%s, Value=%s ", Sensore.timeStamp(), type, id, value);
	}
}
