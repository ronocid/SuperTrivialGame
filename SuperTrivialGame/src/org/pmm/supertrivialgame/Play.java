package org.pmm.supertrivialgame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Play extends Musica {

	private static final String SCORES = "scores";
	private static final String RANKING = "ranking";
	private static final String SCORE2 = "score";
	private static final String USERNAME = "username";
	private static final String RESPUESTA_ERRONEA = "Respuesta Erronea. Siguiente pregunta";
	private static final String ERRONEA_UTLIMA_PREGUNTA = "Respuesta Erronea. Era la utlima pregunta. Tu puntuación es: ";
	private static final String RESPUESTA_CORRECTA = "Respuesta Correcta. Siguiente pregunta";
	private static final String CORRECTA_UTLIMA_PREGUNTA = "Respuesta Correcta. Era la utlima pregunta. Tu puntuación es: ";
	private static final String RESPUESTA = "Respuesta";
	private static final String CHAMPION= "champion";

	private ArrayList <Question> preguntas;
	private int numPregunta=0;
	TextView pregunta;
	TextView score;
	Button respuesta1;
	Button respuesta2;
	Button respuesta3;
	Button respuesta4;
	Question question;
	ProgressBar barraTiempo;
	Tiempo hiloTiempo;
	private int puntuacion;
	private boolean comodin=false;
	private int progress=0;
	SharedPreferences preferencias;

	protected void onStop() {
		hiloTiempo.cancel(true);//Esto evita un error al volver atras
		super.onStop();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	
		
		Puntuaciones.limpiarArray();
		pregunta =(TextView)findViewById(R.id.pregunta);
		respuesta1=(Button)findViewById(R.id.respuesta1);
		respuesta2=(Button)findViewById(R.id.respuesta2);
		respuesta3=(Button)findViewById(R.id.respuesta3);
		respuesta4=(Button)findViewById(R.id.respuesta4);
		score=(TextView)findViewById(R.id.score);
		barraTiempo=(ProgressBar)findViewById(R.id.progressBar1);
		puntuacion=0;
		
		preferencias= getSharedPreferences("play", MODE_PRIVATE);
		if(preferencias !=null &&preferencias.getBoolean("voltear", false)){
			recargarActividad();
			preguntas= Question.getPreguntas();
			if(preguntas == null)
				inicializarPreguntas();
		}else{
			inicializarPreguntas();
			Collections.shuffle(preguntas);//ordena aleatoriamente el arraylist
			Question.setPreguntas(preguntas);
		}
		score.setText("Score: "+puntuacion);
		realizarPregunta();
		
		respuesta1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hiloTiempo.cancel(true);
				pausarMusicaPlay();
				if(question.getRightAnswer()==0){
					puntuacion+=(100-barraTiempo.getProgress())*10;
					score.setText("Score: "+puntuacion);
					respuesta1Correcta();
					comenzarMusicaAcierto();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,CORRECTA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA, RESPUESTA_CORRECTA);
					}
				}else if(question.getRightAnswer()==1){
					respuesta2Correcta();
					comenzarMusicaFallo();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}else if(question.getRightAnswer()==2){
					respuesta3Correcta();
					comenzarMusicaFallo();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}else if(question.getRightAnswer()==3){
					respuesta4Correcta();
					comenzarMusicaFallo();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}
			}
		});
		
		
		respuesta2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hiloTiempo.cancel(true);
				pausarMusicaPlay();
				if(question.getRightAnswer()==1){
					puntuacion+=(100-barraTiempo.getProgress())*10;
					score.setText("Score: "+puntuacion);
					respuesta2Correcta();
					comenzarMusicaAcierto();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,CORRECTA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA, RESPUESTA_CORRECTA);
					}
				}else if(question.getRightAnswer()==0){
					respuesta1Correcta();
					comenzarMusicaFallo();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}else if(question.getRightAnswer()==2){
					respuesta3Correcta();
					comenzarMusicaFallo();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}else if(question.getRightAnswer()==3){
					respuesta4Correcta();
					comenzarMusicaFallo();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}
			}
		});

		respuesta3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hiloTiempo.cancel(true);
				pausarMusicaPlay();
				if(question.getRightAnswer()==2){
					puntuacion+=(100-barraTiempo.getProgress())*10;
					score.setText("Score: "+puntuacion);
					respuesta3Correcta();
					comenzarMusicaAcierto();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,CORRECTA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA, RESPUESTA_CORRECTA);
					}
				}else if(question.getRightAnswer()==1){
					respuesta2Correcta();
					comenzarMusicaFallo();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}else if(question.getRightAnswer()==0){
					respuesta1Correcta();
					comenzarMusicaFallo();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}else if(question.getRightAnswer()==3){
					respuesta4Correcta();
					comenzarMusicaFallo();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}
			}
		});
		
		respuesta4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hiloTiempo.cancel(true);
				pausarMusicaPlay();
				if(question.getRightAnswer()==3){
					puntuacion+=(100-barraTiempo.getProgress())*10;
					score.setText("Score: "+puntuacion);
					respuesta4Correcta();
					comenzarMusicaAcierto();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,CORRECTA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA, RESPUESTA_CORRECTA);
					}
				}else if(question.getRightAnswer()==1){
					respuesta2Correcta();
					comenzarMusicaFallo();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}else if(question.getRightAnswer()==2){
					respuesta3Correcta();
					comenzarMusicaFallo();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}else if(question.getRightAnswer()==0){
					respuesta1Correcta();
					comenzarMusicaFallo();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}
			}
		});
	}
	
	private void recargarActividad() {
		puntuacion=preferencias.getInt("puntuacion", 0);
		numPregunta=preferencias.getInt("numeroPregunta", 0);
	}

	private void realizarPregunta() {
		comenzarMusicaPlay();
		botonesVisible();
		respuesta1.setTextColor(Color.BLACK);
		respuesta2.setTextColor(Color.BLACK);
		respuesta3.setTextColor(Color.BLACK);
		respuesta4.setTextColor(Color.BLACK);
		question=preguntas.get(numPregunta);
		pregunta.setText(question.getQuestionText());
		String[]aswers=question.getAnswers();
		inicializarRespuesta(aswers);
		hiloTiempo=new Tiempo();
		hiloTiempo.execute();
		numPregunta++;
}

	private void inicializarRespuesta(String[] aswers) {
		respuesta1.setText(aswers[0]);
		respuesta2.setText(aswers[1]);
		respuesta3.setText(aswers[2]);
		respuesta4.setText(aswers[3]);
	}
	
	private void botonesVisible() {
		respuesta1.setEnabled(true);
		respuesta2.setEnabled(true);
		respuesta3.setEnabled(true);
		respuesta4.setEnabled(true);
	}
	
	private void inicializarPreguntas() {
		preguntas=new ArrayList <Question>();
			
		SQLiteDatabase db=null;
		try{
			db= SQLiteDatabase.openDatabase(this.getFilesDir().getPath()+"/supertrivialgame.db", null, SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		}catch(SQLiteException e){
			System.out.println("database SQLITE "+e);
			try {
				db=null;
				OutputStream dataOS = new FileOutputStream (this.getFilesDir().getPath()+"/supertrivialgame.db");
				InputStream dataIS;
					
				byte[] buffer = new byte[1024];
				dataIS=this.getResources().openRawResource(R.raw.supertrivialgame);
				while(dataIS.read(buffer)>0){
					dataOS.write(buffer);
				}
				dataIS.close();
				dataOS.flush();
				dataOS.close();
						
				db= SQLiteDatabase.openDatabase(this.getFilesDir().getPath()+"/supertrivialgame.db", null, SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
			} catch (FileNotFoundException e1) {
				System.out.println("database FileNotFound "+e);					
			} catch (IOException e1) {
				System.out.println("database IOE "+e);
			}
		}
		if(db!=null){
			Cursor c1=db.rawQuery("Select * from questions", null);
			int cont=0;
			while(c1.moveToNext() && cont<5){
				preguntas.add(new Question(c1.getString(1),c1.getString(2),new String[]{c1.getString(3),c1.getString(4),c1.getString(5),c1.getString(6)},c1.getInt(7),c1.getInt(8)));
				cont++;
			}
			c1.close();
		}
		db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.action_help:
				int ayuda= question.help;
				switch(ayuda){
				case 0:
					respuesta1.setEnabled(false);
					break;
				case 1:
					respuesta2.setEnabled(false);
					break;
				case 2:
					respuesta3.setEnabled(false);
					break;
				case 3:
					respuesta4.setEnabled(false);
					break;
				}
				return true;
			case R.id.action_comodin:
				comodin=true;
				System.out.println(comodin);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}	
	}

	public class Tiempo extends AsyncTask<Void,Integer,Void>{
		private static final String SE_ACABO_EL_TIEMPO = "Se acabo el tiempo";
		private static final String ACABO_EL_TIEMPO_UTLIMA_PREGUNTA = "Se acabo el tiempo. Era la utlima pregunta. Tu puntuación es: ";
		private static final String TIEMPO = "Tiempo";
		private int cont;
		
		@Override
		protected void onPreExecute() {
			progress=preferencias.getInt("tiempo", 0);
			SharedPreferences.Editor editor = preferencias.edit();
			editor.putInt("tiempo", -1);
			editor.commit();
			cont=0;
		}
		
		@Override
		protected Void doInBackground(Void... params) {
				while(!isCancelled()&&progress<100){
					if(comodin){
						for(;cont<7;cont++){
							try{
								publishProgress(cont);
								Thread.sleep(2000);
							}catch(Exception e){
								System.out.println("AsyncTask "+e);
							}
						}
						comodin=false;
					}else{
					progress++;
					try{
						Thread.sleep(100);
					}catch(Exception e){
						System.out.println("AsyncTask "+e);
					}
					publishProgress(progress);
				}
				}
			return null;
		}

		

		@Override
		protected void onProgressUpdate(Integer... values) {
			barraTiempo.setProgress(progress);
			if(comodin){
				cuenta(cont);
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			this.cancel(true);
			if(preguntas.size()==numPregunta){
				dialogRespuesta(TIEMPO, ACABO_EL_TIEMPO_UTLIMA_PREGUNTA+puntuacion);
			}else{
				dialogRespuesta(TIEMPO, SE_ACABO_EL_TIEMPO);
			}
		}	
	}
	
	private void respuesta4Correcta() {
		respuesta1.setTextColor(Color.RED);
		respuesta2.setTextColor(Color.RED);
		respuesta3.setTextColor(Color.RED);
		respuesta4.setTextColor(Color.GREEN);
	}

	private void respuesta3Correcta() {
		respuesta1.setTextColor(Color.RED);
		respuesta2.setTextColor(Color.RED);
		respuesta3.setTextColor(Color.GREEN);
		respuesta4.setTextColor(Color.RED);
	}

	private void respuesta2Correcta() {
		respuesta1.setTextColor(Color.RED);
		respuesta2.setTextColor(Color.GREEN);
		respuesta3.setTextColor(Color.RED);
		respuesta4.setTextColor(Color.RED);
	}

	private void respuesta1Correcta() {
		respuesta1.setTextColor(Color.GREEN);
		respuesta2.setTextColor(Color.RED);
		respuesta3.setTextColor(Color.RED);
		respuesta4.setTextColor(Color.RED);
	}
	
	
	private void dialogRespuesta(String titulo, String mensaje){
		bloquearBotones();
		hiloTiempo.cancel(true);
		AlertDialog alerta=null;
		AlertDialog.Builder ventana=new AlertDialog.Builder(this);
		ventana.setTitle(titulo);
		ventana.setMessage(mensaje);
		ventana.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				 final Handler handler = new Handler(); 
			        Timer t = new Timer(); 
			        t.schedule(new TimerTask() { //Prepara la ejecución de una tarea después de un tiempo determinado (ms). 
			            //Es una clase que representa una tarea a ejecutar en un tiempo especificado.
			            public void run() { //Dentro de este método definimos las operaciones de la tarea a realizar.
			                handler.post(new Runnable() { //Gracias al método post, podemos acceder desde el hilo secundario al hilo principal.
			                    public void run() { //Realizo la tarea que quiero realizar al acabar el tiempo del schedule (1000ms).
			                        //Aquí paso a la siguiente pregunta
			                    	if(preguntas.size()==numPregunta){
			                    		recuperarXMLScore();
			                    		reanudarMusicaPrincipal();
			                    		//Intent i= new Intent(Play.this, Main.class);
			            				//startActivity(i);
			            				finish();
			    					}else{
			    						realizarPregunta();
			    					}
			                    	
			                    } 
			                }); 
			            } 
			        }, 1500); 
			}
		});
		alerta=ventana.create();
		alerta.setCancelable(false);
		alerta.setCanceledOnTouchOutside(false);
		alerta.show();
	}
	
	private void bloquearBotones() {
		respuesta1.setEnabled(false);
		respuesta2.setEnabled(false);
		respuesta3.setEnabled(false);
		respuesta4.setEnabled(false);
	}

	private void cuenta(int cont) {
		Context contexto=getApplicationContext();
		CharSequence mensaje= cont+"...";
		int duracion =  (int) (1 * 0.001);
		Toast toast=Toast.makeText(contexto, mensaje, duracion);
		toast.show();
	}
	
	private void recuperarXMLScore() {
		try {
			FileInputStream inputStream = openFileInput("scores.xml");
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(inputStream, "UTF-8");
			int eventType = XmlPullParser.START_DOCUMENT;
			boolean datoIntroducido = false;
			
			while(eventType != XmlPullParser.END_DOCUMENT){
				if(eventType == XmlPullParser.START_TAG){
					if(parser.getAttributeValue(null,USERNAME)!=null){
						if(Integer.parseInt(parser.getAttributeValue(null, SCORE2)) >= this.puntuacion || datoIntroducido){
							new Puntuaciones(parser.getAttributeValue(null, USERNAME), Integer.parseInt(parser.getAttributeValue(null, SCORE2)),parser.getAttributeValue(null, CHAMPION));
						}else{
							new Puntuaciones(recuperarNombreUsuario(), this.puntuacion,"no");
							new Puntuaciones(parser.getAttributeValue(null, USERNAME), Integer.parseInt(parser.getAttributeValue(null, SCORE2)),parser.getAttributeValue(null, CHAMPION));
							datoIntroducido=true;
						}	
					}
				}
				eventType = parser.next();
			}
			if(!datoIntroducido){
				new Puntuaciones(recuperarNombreUsuario(), this.puntuacion,"no");
			}
			inputStream.close();
			escribirScores();
		} catch (FileNotFoundException e) {
			primerScore();
			System.out.println("XML FileNotFound "+e);
		} catch (XmlPullParserException e) {
			System.out.println("XML PullParser "+e);
		} catch (IOException e) {
			System.out.println("XML IO "+e);
		}
	}

	private void escribirScores() {
		Puntuaciones [] array = Puntuaciones.getDatos();
		XmlSerializer serialiser = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serialiser.setOutput(writer);
			serialiser.startDocument(null, null);
			serialiser.startTag(null, SCORES);
			for(int cont=0;cont<25;cont++){
				if(array[cont]!= null){
					serialiser.startTag(null, SCORE2);
					serialiser.attribute(null, USERNAME, array[cont].getNombre());
					serialiser.attribute(null, SCORE2, String.valueOf(array[cont].getScore()));
					serialiser.attribute(null, RANKING, ""+(cont+1));
					if(cont==0){
						serialiser.attribute(null, CHAMPION, "si");
					}else{
						serialiser.attribute(null, CHAMPION, array[cont].getChampion());
					}
						
					serialiser.endTag(null, SCORE2);
				}	
			}
			serialiser.endTag(null, SCORES);
			serialiser.endDocument();
			FileOutputStream fos = openFileOutput ("scores.xml",Context.MODE_PRIVATE);
			fos.write(writer.toString().getBytes());
			fos.flush();
			fos.close();
		} catch (IllegalArgumentException e) {
			System.out.println("XML IllegalArgument "+e);
		} catch (IllegalStateException e) {
			System.out.println("XML IlegalState "+e);
		} catch (IOException e) {
			System.out.println("XML IO "+e);
		}
	}

	private void primerScore() {
		XmlSerializer serialiser = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serialiser.setOutput(writer);
			serialiser.startDocument(null, null);
			serialiser.startTag(null, SCORES);
			serialiser.startTag(null, SCORE2);
			serialiser.attribute(null, USERNAME, recuperarNombreUsuario());
			serialiser.attribute(null, SCORE2, String.valueOf(puntuacion));
			serialiser.attribute(null, RANKING, ""+1);
			serialiser.attribute(null, CHAMPION, "si");
			serialiser.endTag(null, SCORE2);
			serialiser.endTag(null, SCORES);
			serialiser.endDocument();
			FileOutputStream fos = openFileOutput ("scores.xml",Context.MODE_PRIVATE);
			fos.write(writer.toString().getBytes());
			fos.flush();
			fos.close();
		} catch (IllegalArgumentException e) {
			System.out.println("XML IllegalArgument "+e);
		} catch (IllegalStateException e) {
			System.out.println("XML IllegalState "+e);
		} catch (IOException e) {
			System.out.println("XML IO "+e);
		}
		
	}

	private String recuperarNombreUsuario() {
		SharedPreferences preferencias=getSharedPreferences("settings", MODE_PRIVATE);
		return preferencias.getString("nombre", "Usuario1");
	}

	@Override
	protected void onPause() {
		super.onPause();
		terminarMusicaPlay();
		SharedPreferences.Editor editor=preferencias.edit();
		if(!isFinishing()){
			editor.putBoolean("voltear", true);
			editor.putInt("puntuacion", this.puntuacion );
			editor.putInt("numeroPregunta", this.numPregunta-1);
			editor.putInt("tiempo", barraTiempo.getProgress());
		}else{
			editor.putBoolean("voltear", false);
		}
		editor.commit();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		terminarMusicaPlay();
		reanudarMusicaPrincipal();
		finish();
	}	
}
