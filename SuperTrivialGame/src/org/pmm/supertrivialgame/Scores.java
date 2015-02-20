package org.pmm.supertrivialgame;

import java.util.List;
import modelo.XmlScores;
import constants.Contants;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Scores extends Activity {
	private TableLayout tabla;
	private ProgressBar esperando;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scores);
		this.tabla =(TableLayout)findViewById(R.id.table);
		this.esperando = (ProgressBar)findViewById(R.id.progressBar1);
		createTab();
		
		Leer hilo= new Leer();
		hilo.execute();
	}

	private void createTab() {
		TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
		tabs.setup();
		
		TabHost.TabSpec spec=tabs.newTabSpec(Contants.TITLE_TAB1);
		spec.setContent(R.id.tab1);
		spec.setIndicator(Contants.TITLE_TAB1, getResources().getDrawable(R.drawable.usericon));
		tabs.addTab(spec);
		
		spec=tabs.newTabSpec(Contants.TITLE_TAB2);
		spec.setContent(R.id.tab2);
		spec.setIndicator(Contants.TITLE_TAB2, getResources().getDrawable(R.drawable.friends));
		
		tabs.addTab(spec);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.scores, menu);
		return true;
	}
	
	public class Leer extends AsyncTask<Void,TableRow,Void>{
		int progress;
		private List<Puntuaciones> listScores;
		@Override
		protected void onPreExecute() {
			listScores = XmlScores.ReadFileScore(getApplicationContext());
			progress =0;
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try{
				for(int cont = 0;cont<listScores.size();cont++){
					Puntuaciones scoreUser = listScores.get(cont);
					progress++;					
					TableRow fila = new TableRow(getApplicationContext());
					
					TextView user=new TextView(getApplicationContext());
					user.setText(scoreUser.getNombre());
					configurationTextView(user);
					
					TextView score=new TextView(getApplicationContext());
					score.setText(scoreUser.getScore()+"");
					configurationTextView(score);
					
					TextView ranking=new TextView(getApplicationContext());
					ranking.setText(""+(cont+1));
					configurationTextView(ranking);
					
					TextView cham=new TextView(getApplicationContext());
					cham.setText(scoreUser.getChampion());
					configurationTextView(cham);
					
					fila.addView(user);
					fila.addView(score);
					fila.addView(ranking);
					fila.addView(cham);
					
					publishProgress(fila);
				}
				esperando.setVisibility(View.INVISIBLE);
			}catch (Exception e){
				System.out.println("Error mostrar tabla score "+ e);
			}
			return null;
		}

		private void configurationTextView(TextView textView) {
			textView.setGravity(17);
			textView.setTextColor(Color.BLUE);
			textView.setTypeface(null, 1);
		}
		
		@Override
		protected void onProgressUpdate(TableRow... values) {
			if(values[0]!=null)
				esperando.setProgress(progress);
				tabla.addView(values[0]);
		}	
	}
}
