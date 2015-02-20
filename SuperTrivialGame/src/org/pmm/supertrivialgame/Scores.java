package org.pmm.supertrivialgame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Scores extends Activity {
	private TableLayout tabla;
	private static final String SCORE2 = "score";
	private static final String USERNAME = "username";
	private static final String RANKING = "ranking";
	private ProgressBar esperando;
	private static final String CHAMPION= "champion";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scores);
		this.tabla =(TableLayout)findViewById(R.id.table);
		this.esperando = (ProgressBar)findViewById(R.id.progressBar1);
		
		TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
		tabs.setup();
		
		TabHost.TabSpec spec=tabs.newTabSpec("Usuario");
		spec.setContent(R.id.tab1);
		spec.setIndicator("Usuario", getResources().getDrawable(R.drawable.usericon));
		tabs.addTab(spec);
		
		spec=tabs.newTabSpec("Friends");
		spec.setContent(R.id.tab2);
		spec.setIndicator("Amigos", getResources().getDrawable(R.drawable.friends));
		
		tabs.addTab(spec);
		
		Leer hilo= new Leer();
		hilo.execute();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.scores, menu);
		return true;
	}
	
	public class Leer extends AsyncTask<Void,TableRow,Void>{
		
		private FileInputStream inputStream;
		private XmlPullParser parser;
		private int eventType; 
		int progress;
		@Override
		protected void onPreExecute() {
			try {
				inputStream = openFileInput("scores.xml");
				parser = Xml.newPullParser();
				parser.setInput(inputStream, "UTF-8");
				eventType = XmlPullParser.START_DOCUMENT;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			progress =0;
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try{
				while(eventType != XmlPullParser.END_DOCUMENT){
					if(eventType == XmlPullParser.START_TAG){
						if(parser.getAttributeValue(null,USERNAME)!=null){
							progress++;
							String username=parser.getAttributeValue(null, USERNAME);
							String puntuacion =parser.getAttributeValue(null, SCORE2);
							String posicion =parser.getAttributeValue(null, RANKING);
							String champion =parser.getAttributeValue(null, CHAMPION);
							
							TableRow fila = new TableRow(Scores.this);
							
							TextView user=new TextView(Scores.this);
							user.setText(username);
							user.setGravity(17);
							user.setTextColor(Color.BLUE);
							user.setTypeface(null, 1);
							
							TextView score=new TextView(Scores.this);
							score.setText(puntuacion);
							score.setGravity(17);
							score.setTextColor(Color.BLUE);
							score.setTypeface(null, 1);
							
							TextView ranking=new TextView(Scores.this);
							ranking.setText(posicion);
							ranking.setGravity(17);
							ranking.setTextColor(Color.BLUE);
							ranking.setTypeface(null, 1);
							
							TextView cham=new TextView(Scores.this);
							cham.setText(champion);
							cham.setGravity(17);
							cham.setTextColor(Color.BLUE);
							cham.setTypeface(null, 1);
							
							fila.addView(user);
							fila.addView(score);
							fila.addView(ranking);
							fila.addView(cham);
							
							publishProgress(fila);
						}
					}
					eventType = parser.next();
				}
				esperando.setVisibility(View.INVISIBLE);
			}catch (Exception e){
				
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(TableRow... values) {
			if(values[0]!=null)
				esperando.setProgress(progress);
				tabla.addView(values[0]);
		}	
	}
}
