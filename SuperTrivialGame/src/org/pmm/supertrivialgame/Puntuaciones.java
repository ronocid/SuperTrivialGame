package org.pmm.supertrivialgame;

public class Puntuaciones {
	private String nombre;
	private int score;
	private static Puntuaciones [] array= new Puntuaciones [25];
	private static int posicion = 0;
	private String champion;
	
	public Puntuaciones(String nombre, int score) {
		super();
		this.nombre = nombre;
		this.score = score;
		if (posicion<25){
			array[posicion]= this;
			posicion++;
		}
	}
	
	public Puntuaciones(String nombre, int score,String champion) {
		super();
		this.nombre = nombre;
		this.score = score;
		this.champion= champion;
		if (posicion<25){
			array[posicion]= this;
			posicion++;
		}
	}
	
	public String getChampion() {
		return champion;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public static Puntuaciones [] getDatos() {
		return array;
	}
	
	public static void limpiarArray(){
		array= new Puntuaciones [25];
		posicion=0;
	}
}
