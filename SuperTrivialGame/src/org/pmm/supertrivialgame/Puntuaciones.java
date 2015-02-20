package org.pmm.supertrivialgame;

public class Puntuaciones {
	private String nombre;
	private int score;
	private String champion;
	
	public Puntuaciones(String nombre, int score) {
		super();
		this.nombre = nombre;
		this.score = score;
	}
	
	public Puntuaciones(String nombre, int score,String champion) {
		super();
		this.nombre = nombre;
		this.score = score;
		this.champion= champion;
	}
	
	public String getChampion() {
		return champion;
	}
	
	public String getNombre() {
		return nombre;
	}
	public int getScore() {
		return score;
	}
	public void setChampion(String champion) {
		this.champion = champion;
	}
}
