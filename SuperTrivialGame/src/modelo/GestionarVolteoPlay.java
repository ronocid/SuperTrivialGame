package modelo;

import constants.Contants;
import android.content.Context;
import android.content.SharedPreferences;

public class GestionarVolteoPlay {
	private static SharedPreferences getSharedPreferences(Context c){
		SharedPreferences preferencias= c.getSharedPreferences(Contants.FILE_NAME_XML_GAME, Context.MODE_PRIVATE);
		return preferencias;
	}
	
	public static void saveData(Context c, int scoreGame, int numPregunta, int progressTime){
		SharedPreferences preferencias = getSharedPreferences(c);
		SharedPreferences.Editor editor=preferencias.edit();
		editor.putBoolean("voltear", true);
		editor.putInt("puntuacion", scoreGame );
		editor.putInt("numeroPregunta", numPregunta-1);
		editor.putInt("tiempo", progressTime);
		editor.commit();
	}
	
	public static void saveNotTurn(Context c){
		SharedPreferences preferencias = getSharedPreferences(c);
		SharedPreferences.Editor editor=preferencias.edit();
		editor.putBoolean("voltear", false);
		editor.commit();
	}
	
	public static int getTimeProgressBar(Context c){
		SharedPreferences preferencias = getSharedPreferences(c);
		int progress = preferencias.getInt("tiempo", 0);
		SharedPreferences.Editor editor = preferencias.edit();
		editor.putInt("tiempo", -1);
		editor.commit();
		return progress;
	}
}