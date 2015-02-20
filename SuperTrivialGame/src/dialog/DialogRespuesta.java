package dialog;

import java.util.Timer;
import java.util.TimerTask;

import org.pmm.supertrivialgame.Play;

import constants.Contants;

import modelo.XmlScores;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;

public class DialogRespuesta {
	public static void dialogUltimaRespuesta(final Play activity,String titulo, String mensaje, final int scoreGame){
		AlertDialog alerta=null;
		AlertDialog.Builder ventana=new AlertDialog.Builder(activity);
		ventana.setTitle(titulo);
		ventana.setMessage(mensaje);
		ventana.setPositiveButton(Contants.OK,new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				 final Handler handler = new Handler(); 
			        Timer t = new Timer(); 
			        t.schedule(new TimerTask() { //Prepara la ejecución de una tarea después de un tiempo determinado (ms). 
			            //Es una clase que representa una tarea a ejecutar en un tiempo especificado.
			            public void run() { //Dentro de este método definimos las operaciones de la tarea a realizar.
			                handler.post(new Runnable() { //Gracias al método post, podemos acceder desde el hilo secundario al hilo principal.
			                    public void run() { //Realizo la tarea que quiero realizar al acabar el tiempo del schedule (1000ms).
			                    	XmlScores.writeScoreGame(activity, scoreGame);
			                    	activity.reanudarMusicaPrincipal();
			            			activity.finish();
			                    } 
			                }); 
			            } 
			        }, 1500); 
			}
		});
		alerta=ventana.create();
		alerta.show();
	}
	
	public static void dialogSiguienteRespuesta(final Play activity,String titulo, String mensaje){
		AlertDialog alerta=null;
		AlertDialog.Builder ventana=new AlertDialog.Builder(activity);
		ventana.setTitle(titulo);
		ventana.setMessage(mensaje);
		ventana.setPositiveButton(Contants.OK,new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				 final Handler handler = new Handler(); 
			        Timer t = new Timer(); 
			        t.schedule(new TimerTask() { //Prepara la ejecución de una tarea después de un tiempo determinado (ms). 
			            //Es una clase que representa una tarea a ejecutar en un tiempo especificado.
			            public void run() { //Dentro de este método definimos las operaciones de la tarea a realizar.
			                handler.post(new Runnable() { //Gracias al método post, podemos acceder desde el hilo secundario al hilo principal.
			                    public void run() { //Realizo la tarea que quiero realizar al acabar el tiempo del schedule (1000ms).
			    					activity.launchQuestion();
			                    } 
			                }); 
			            } 
			        }, 1500); 
			}
		});
		alerta=ventana.create();
		alerta.show();
	}
}
