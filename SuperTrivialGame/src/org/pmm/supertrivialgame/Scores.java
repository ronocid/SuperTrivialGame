package org.pmm.supertrivialgame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.util.Xml;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Scores extends Activity {
	private TableLayout tabla;
	private static final String SCORE2 = "score";
	private static final String USERNAME = "username";
	private static final String RANKING = "ranking";

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scores);
		this.tabla =(TableLayout)findViewById(R.id.table);
		
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
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try{
				while(eventType != XmlPullParser.END_DOCUMENT){
					if(eventType == XmlPullParser.START_TAG){
						if(parser.getAttributeValue(null,USERNAME)!=null){
							String username=parser.getAttributeValue(null, USERNAME);
							String puntuacion =parser.getAttributeValue(null, SCORE2);
							String posicion =parser.getAttributeValue(null, RANKING);
							
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
							
							fila.addView(user);
							fila.addView(score);
							fila.addView(ranking);
							
							publishProgress(fila);
						}
					}
					eventType = parser.next();
				}
			}catch (Exception e){
				
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(TableRow... values) {
			if(values[0]!=null)
				tabla.addView(values[0]);
		}	
	}
}
