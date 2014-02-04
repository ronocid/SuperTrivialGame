package org.pmm.supertrivialgame;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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

public class Play extends Activity {

	private static final String SCORES = "scores";
	private static final String RANKING = "ranking";
	private static final String SCORE2 = "score";
	private static final String USERNAME = "username";
	private static final String RESPUESTA_ERRONEA = "Respuesta Erronea. Siguiente pregunta";
	private static final String ERRONEA_UTLIMA_PREGUNTA = "Respuesta Erronea. Era la utlima pregunta. Tu puntuación es: ";
	private static final String RESPUESTA_CORRECTA = "Respuesta Correcta. Siguiente pregunta";
	private static final String CORRECTA_UTLIMA_PREGUNTA = "Respuesta Correcta. Era la utlima pregunta. Tu puntuación es: ";
	private static final String RESPUESTA = "Respuesta";

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

	protected void onStop() {
		hiloTiempo.cancel(true);//Esto evita un error al volver atras
		super.onStop();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		pregunta =(TextView)findViewById(R.id.pregunta);
		respuesta1=(Button)findViewById(R.id.respuesta1);
		respuesta2=(Button)findViewById(R.id.respuesta2);
		respuesta3=(Button)findViewById(R.id.respuesta3);
		respuesta4=(Button)findViewById(R.id.respuesta4);
		score=(TextView)findViewById(R.id.score);
		barraTiempo=(ProgressBar)findViewById(R.id.progressBar1);
		puntuacion=0;
		
		inicializarPreguntas();
		Collections.shuffle(preguntas);//ordena aleatoriamente el arraylist
		realizarPregunta();
		
		respuesta1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hiloTiempo.cancel(true);
				if(question.getRightAnswer()==0){
					puntuacion+=(100-barraTiempo.getProgress())*10;
					score.setText("Score: "+puntuacion);
					respuesta1Correcta();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,CORRECTA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA, RESPUESTA_CORRECTA);
					}
				}else if(question.getRightAnswer()==1){
					respuesta2Correcta();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}else if(question.getRightAnswer()==2){
					respuesta3Correcta();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}else if(question.getRightAnswer()==3){
					respuesta4Correcta();
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
				if(question.getRightAnswer()==1){
					puntuacion+=(100-barraTiempo.getProgress())*10;
					score.setText("Score: "+puntuacion);
					respuesta2Correcta();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,CORRECTA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA, RESPUESTA_CORRECTA);
					}
				}else if(question.getRightAnswer()==0){
					respuesta1Correcta();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}else if(question.getRightAnswer()==2){
					respuesta3Correcta();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}else if(question.getRightAnswer()==3){
					respuesta4Correcta();
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
				if(question.getRightAnswer()==2){
					puntuacion+=(100-barraTiempo.getProgress())*10;
					score.setText("Score: "+puntuacion);
					respuesta3Correcta();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,CORRECTA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA, RESPUESTA_CORRECTA);
					}
				}else if(question.getRightAnswer()==1){
					respuesta2Correcta();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}else if(question.getRightAnswer()==0){
					respuesta1Correcta();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}else if(question.getRightAnswer()==3){
					respuesta4Correcta();
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
				if(question.getRightAnswer()==3){
					puntuacion+=(100-barraTiempo.getProgress())*10;
					score.setText("Score: "+puntuacion);
					respuesta4Correcta();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,CORRECTA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA, RESPUESTA_CORRECTA);
					}
				}else if(question.getRightAnswer()==1){
					respuesta2Correcta();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}else if(question.getRightAnswer()==2){
					respuesta3Correcta();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}else if(question.getRightAnswer()==0){
					respuesta1Correcta();
					if(preguntas.size()==numPregunta){
						dialogRespuesta(RESPUESTA,ERRONEA_UTLIMA_PREGUNTA+puntuacion);
					}else{
						dialogRespuesta(RESPUESTA,RESPUESTA_ERRONEA);
					}
				}
			}
		});
	}
	
	private void realizarPregunta() {
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
		preguntas.add(new Question("Geografía","¿Qué línea imaginaria de la tierra está marcada por el monolito Jujuy?",new String[]{"El Trópico de Sagitario","El Trópico de Aries","El Trópico de Virgo","El Trópico de Capricornio"},3,1));
		preguntas.add(new Question("Espectáculos","¿En qué película aparecían The Beatles en dibujos animados?",new String[]{"Yellow submarine","A Hard Day’s Night","Magical Mystery Tour","Help!"},0,3));
		preguntas.add(new Question("Historia","¿Por qué cruz pasó a la historia Henri Dunant?",new String[]{"Cruz de Occitania","Cruz Roja","Cruz de Borgoña","Cruz del calvario"},1,0));
		preguntas.add(new Question("Geografía","¿Cómo se llama el mar que separa Argentina de las islas Malvinas?",new String[]{"Mar Argentino","Mar de la Plata","Mar Malvino","Mar del Tucumán"},0,3));
		/*preguntas.add(new Question("Arte y Literatura","¿A qué poeta debemos el Llanto por Ignacio Sánchez Mejías?",new String[]{"Miguel Hernández","García Lorca","Pablo Neruda","Rafael Alberti"},1,2));
		preguntas.add(new Question("Ciencias y Naturaleza","¿Qué miembros de una colmena se alimentan con jalea real?",new String[]{"Las larvas","Los zánganos","La reina","Las obreras"},0,2));
		preguntas.add(new Question("Historia","¿Quién fue el legislador ateniense más famoso por la severidad de sus penas?",new String[]{"Platón","Salaminia","Dracón","Aristóteles"},2,1));
		preguntas.add(new Question("Arte y Literatura","¿En qué café dirigió una famosa tertulia literaria Ramón Gómez de la Serna?",new String[]{"Café Madrid","Café Pombo","Café Gijón","Café Cibeles"},1,3));
		preguntas.add(new Question("Ocio y Deportes","¿Qué equipo de fútbol español fue el primero en llevarse a sus vitrinas la Supercopa de Europa?",new String[]{"Real Madrid","Valencia","Fc Barcelona","Atlético de Madrid"},1,2));
		preguntas.add(new Question("Historia","¿En qué país 100.000 personas protagonizaron La Larga Marcha?",new String[]{"India","Rusia","China","Indonesia"},2,0));
		preguntas.add(new Question("Espectáculos","¿Quién dirigió primero Tesis y después Abre los ojos?",new String[]{"Álex de la Iglesia","Alejandro Amenábar","Eduardo Noriega","Mateo Gil"},1,0));
		preguntas.add(new Question("Arte y Literatura","¿Quién escribió un Viaje a la Luna y la Historia cómica de los estados e imperios del Sol?",new String[]{"Cyrano de Bergerac","Julio Verne","Victor Hugo","H. G. Wells"},0,1));*/
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
		private int progress;
		private int cont;
		
		@Override
		protected void onPreExecute() {
			progress=0;	
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
								System.out.println("Error "+e);
							}
						}
						comodin=false;
					}else{
					progress++;
					try{
						Thread.sleep(100);
					}catch(Exception e){
						System.out.println("Error "+e);
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
			                    		escribirXMLScore();
			                    		Intent i= new Intent(Play.this, Main.class);
			            				startActivity(i);
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
	
	private void escribirXMLScore() {
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer= new StringWriter();
		FileInputStream inputStream = null;
		FileOutputStream fos =null;
		int eventType = XmlPullParser.START_DOCUMENT;
		boolean puntuacionIntroducida=false;
		
		try {
			serializer.setOutput(writer);
			serializer.startDocument(null, null);
			serializer.startTag(null, SCORES);

			inputStream=openFileInput("scores.xml");
			/*XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
			parser.setInput(inputStream, null);*/
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(inputStream, "UTF-8");
			
			for(int cont=0;cont<25 && eventType != XmlPullParser.END_DOCUMENT;cont++){
				if(eventType == XmlPullParser.START_TAG){
					String puntuacion=parser.getAttributeValue(null, SCORE2);
					System.out.println(parser.getAttributeValue(null, SCORE2)+" "+ parser.getAttributeValue(null, USERNAME)+" "+ parser.getAttributeValue(null, RANKING));
					serializer.startTag(null, SCORE2);
					if(Integer.parseInt(puntuacion)>this.puntuacion || puntuacionIntroducida){
						escribirXmlPuntuacionAntigua(serializer, parser, cont,puntuacion);
					}else{
						puntuacionIntroducida = escribirXmlPuntuacionNueva(serializer, cont);
					}
				}
				serializer.endTag(null,SCORE2);
				eventType =parser.next();
			}
			serializer.endTag(null, SCORES);
			serializer.endDocument();
			
			fos = openFileOutput("scores.xml", Context.MODE_PRIVATE);
			fos.write(writer.toString().getBytes());
			fos.flush();
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		} catch (IllegalStateException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
			primerScore();
		} catch (XmlPullParserException e) {
			System.out.println(e);
		}finally{
			try {
				if(fos != null)
					fos.close();
				if(inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			serialiser.endTag(null, SCORE2);
			serialiser.endTag(null, SCORES);
			serialiser.endDocument();
			FileOutputStream fos = openFileOutput ("scores.xml",Context.MODE_PRIVATE);
			fos.write(writer.toString().getBytes());
			fos.flush();
			fos.close();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void escribirXmlPuntuacionAntigua(XmlSerializer serializer,
			XmlPullParser parser, int cont, String puntuacion)
			throws IOException {
		serializer.attribute(null, USERNAME, parser.getAttributeValue(null, USERNAME));
		serializer.attribute(null, SCORE2, String.valueOf(puntuacion));
		serializer.attribute(null, RANKING, String.valueOf(cont+1));
	}

	private boolean escribirXmlPuntuacionNueva(XmlSerializer serializer,
			int cont) throws IOException {
		boolean puntuacionIntroducida;
		serializer.attribute(null, USERNAME, recuperarNombreUsuario());
		serializer.attribute(null, SCORE2, String.valueOf(this.puntuacion));
		serializer.attribute(null, RANKING, String.valueOf(cont+1));
		puntuacionIntroducida=true;
		return puntuacionIntroducida;
	}

	private String recuperarNombreUsuario() {
		SharedPreferences preferencias=getSharedPreferences("settings", MODE_PRIVATE);
		return preferencias.getString("nombre", "Usuario1");
	}


}
