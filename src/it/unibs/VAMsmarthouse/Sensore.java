package it.unibs.VAMsmarthouse;

public abstract class Sensore<T> implements Runnable { // nel ncaso usi abstract uso implements

	protected boolean running;
	protected long time;
	protected int id;
	protected String type;
	protected T value;

	public Sensore(int id, long time, String type) {
		this.id = id;
		this.time = time;
		this.type = type;
	}

	public String getType() {
		return "Sensore di Temperatura";
	}

	public int getID() {
		return id;
	}

	public T getValue() {
		return value;
	}

	@Override
	public void run() {
		while (running) {
			value = generaValore();
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				running = false;
			}
		}
	}

	protected abstract T generaValore(); // indica la specializzazione del sensore

	public void stop() {
		running = false;
	}

}
