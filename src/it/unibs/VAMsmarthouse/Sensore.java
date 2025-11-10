package it.unibs.VAMsmarthouse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract class Sensore<T> implements Runnable { // nel caso usi abstract uso implements

	protected boolean running;
	protected Date timestamp;
	protected long time;
	protected String id;
	protected String type;
	protected T value;

//costruttore
	public Sensore(String id, long time, String type) {
		this.id = id;
		this.time = time;
		this.type = type;
	}

//getter
	public String getType() {
		return type;
	}

	public String getID() {
		return id;
	}

	public T getValue() {
		return value;
	}

//overrie del metodo run per permettere il running dei thread e la generazione dei valori ogni tot
	@Override
	public void run() {
		running = true;
		while (running) {
			value = generaValore();
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				running = false;
			}
		}
	}

	protected abstract T generaValore(); // indica la specializzazione del sensore. Metodo astratto che caratterizza la
											// classe astratta
//metodo per formattare la data

	public static String timeStamp() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return LocalDateTime.now().format(formatter);
	}

//fa partire l'esecuzione del sensore 
	public void start() {
		if (!running) {
			running = true;
			new Thread(this).start();
		}
	}

//ferma l'esecuzione del sensore
	public void stop() {
		running = false;
	}

//override del to string per stampare a video i valori richiesti
	@Override
	public String toString() {
		return String.format("[%s]  [%s] ID=%s, Value=%.2f °C", Sensore.timeStamp(), type, id, value);
	}

}
