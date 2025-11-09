package it.unibs.VAMsmarthouse;

public class SensoreMovimento extends Sensore<Boolean> {

	public SensoreMovimento(String id) {
		super(id, 1000, "Movimento");
	}

//Per questo metood ipotizziamo ci sia una telecamera che da sul vialetto e sulla strada, e che quindi rilevi i movimenti per esempio di pedoni e/o macchine
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
