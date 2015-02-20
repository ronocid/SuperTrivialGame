package org.pmm.supertrivialgame;

import java.util.ArrayList;
import java.util.Collections;
import modelo.DataBaseQuestions;
import modelo.GestionarVolteoPlay;
import constants.Contants;
import controlador.play.ControladorButton;
import dialog.DialogRespuesta;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Play extends Musica {
	private ArrayList <Question> preguntas;
	private int numPregunta=0;
	private TextView pregunta;
	private TextView score;
	private Question question;
	private ProgressBar barraTiempo;
	private Tiempo hiloTiempo;
	private int scoreGame;
	private boolean comodin=false;
	private int progress=0;
	private SharedPreferences preferencias;
	private ControladorButton controladorButton;
	private Play activity;

	protected void onStop() {
		hiloTiempo.cancel(true);
		super.onStop();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		activity = this;
	
		pregunta =(TextView)findViewById(R.id.pregunta);
		Button respuesta1=(Button)findViewById(R.id.respuesta1);
		Button respuesta2=(Button)findViewById(R.id.respuesta2);
		Button respuesta3=(Button)findViewById(R.id.respuesta3);
		Button respuesta4=(Button)findViewById(R.id.respuesta4);
		controladorButton = new ControladorButton(respuesta1, respuesta2, respuesta3, respuesta4);
		score=(TextView)findViewById(R.id.score);
		barraTiempo=(ProgressBar)findViewById(R.id.progressBar1);
		
		scoreGame=Contants.INITIAL_SCORE;
		
		preferencias= getSharedPreferences(Contants.FILE_NAME_XML_GAME, MODE_PRIVATE);
		if(preferencias !=null &&preferencias.getBoolean(Contants.VOLTEAR, false)){
			recargarActividad();
			preguntas= Question.getPreguntas();
			if(preguntas == null)
				inicializarPreguntas();
		}else{
			inicializarPreguntas();
			randomQuestion();
		}
		score.setText(Contants.TEXT_SCORE_PLAY+scoreGame);
		realizarPregunta();
		
		respuesta1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				checkAnswer(Contants.ANSWER_ONE, question.getRightAnswer());
			}
		});
		
		respuesta2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(Contants.ANSWER_TWO, question.getRightAnswer());
			}
		});

		respuesta3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(Contants.ANSWER_THREE, question.getRightAnswer());
			}
		});
		
		respuesta4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(Contants.ANSWER_FOUR, question.getRightAnswer());
			}
		});
	}
	
	private void checkAnswer(int answerCheck, int answerQuestion){
		hiloTiempo.cancel(true);
		pausarMusicaPlay();
		
		if(answerCheck==answerQuestion){
			correctAnswer(answerQuestion);
		}else{
			failureAnswer(answerQuestion);
		}
	}

	private void failureAnswer(int answerQuestion) {
		controladorButton.showCorrectAnswer(answerQuestion);
		comenzarMusicaFallo();
		if(preguntas.size()==numPregunta){
			prepareDialog();
			DialogRespuesta.dialogUltimaRespuesta(this, Contants.RESPUESTA, Contants.ERRONEA_UTLIMA_PREGUNTA+scoreGame, scoreGame);
		}else{
			prepareDialog();
			DialogRespuesta.dialogSiguienteRespuesta(this, Contants.RESPUESTA, Contants.RESPUESTA_ERRONEA);
		}
	}

	private void prepareDialog() {
		controladorButton.bloquearBotones();
		hiloTiempo.cancel(true);
	}

	private void correctAnswer(int answerQuestion) {
		scoreGame+=(100-barraTiempo.getProgress())*10;
		score.setText(Contants.TEXT_SCORE_PLAY+scoreGame);
		controladorButton.showCorrectAnswer(answerQuestion);
		comenzarMusicaAcierto();
		if(preguntas.size()==numPregunta){
			prepareDialog();
			DialogRespuesta.dialogUltimaRespuesta(this, Contants.RESPUESTA, Contants.CORRECTA_UTLIMA_PREGUNTA+scoreGame, scoreGame);
		}else{
			prepareDialog();
			DialogRespuesta.dialogSiguienteRespuesta(this, Contants.RESPUESTA, Contants.RESPUESTA_CORRECTA);
		}
	}

	private void randomQuestion() {
		Collections.shuffle(preguntas);
		Question.setPreguntas(preguntas);
	}
	
	private void recargarActividad() {
		scoreGame=preferencias.getInt("puntuacion", 0);
		numPregunta=preferencias.getInt("numeroPregunta", 0);
	}

	private void realizarPregunta() {
		comenzarMusicaPlay();
		controladorButton.botonesVisible();
		controladorButton.changeColorTextButton(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);
		question=preguntas.get(numPregunta);
		pregunta.setText(question.getQuestionText());
		String[]aswers=question.getAnswers();
		controladorButton.inicializarRespuesta(aswers);
		hiloTiempo=new Tiempo();
		hiloTiempo.execute();
		numPregunta++;
	}
	
	private void inicializarPreguntas() {
		preguntas=DataBaseQuestions.getQuestions(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.action_help:
				int ayuda= question.help;
				controladorButton.removeQuestionHelp(ayuda);
				return true;
			case R.id.action_comodin:
				comodin=true;
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}	
	}

	public class Tiempo extends AsyncTask<Void,Integer,Void>{
		private int cont;
		
		@Override
		protected void onPreExecute() {
			progress= GestionarVolteoPlay.getTimeProgressBar(getApplicationContext());
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
				prepareDialog();
				DialogRespuesta.dialogUltimaRespuesta(activity, Contants.RESPUESTA, Contants.ACABO_EL_TIEMPO_UTLIMA_PREGUNTA+scoreGame, scoreGame);
			}else{
				prepareDialog();
				DialogRespuesta.dialogSiguienteRespuesta(activity, Contants.RESPUESTA, Contants.SE_ACABO_EL_TIEMPO);
			}
		}	
	}

	private void cuenta(int cont) {
		Context contexto=getApplicationContext();
		CharSequence mensaje= cont+"...";
		int duracion =  (int) (1 * 0.001);
		Toast toast=Toast.makeText(contexto, mensaje, duracion);
		toast.show();
	}

	@Override
	protected void onPause() {
		super.onPause();
		terminarMusicaPlay();	
		if(!isFinishing()){
			GestionarVolteoPlay.saveData(getApplicationContext(), this.scoreGame, this.numPregunta, this.barraTiempo.getProgress());
		}else{
			GestionarVolteoPlay.saveNotTurn(getApplicationContext());
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		terminarMusicaPlay();
		reanudarMusicaPrincipal();
		finish();
	}

	public void launchQuestion() {
		realizarPregunta();
	}	
}
