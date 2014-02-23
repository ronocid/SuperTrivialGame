package org.pmm.supertrivialgame;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.TabActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Xml;
import android.view.Menu;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TableRow;
import android.widget.TabHost.TabSpec;
import android.widget.TableLayout;

public class Scores extends TabActivity {
	private static final String SCORES = "scores";
	private static final String RANKING = "ranking";
	private static final String SCORE2 = "score";
	private static final String USERNAME = "username";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scores);
		
		TableLayout tabla= (TableLayout)findViewById(R.id.table);
		
		TableRow user = new TableRow(this);
		TableRow score = new TableRow(this);
		TableRow ranking = new TableRow(this);
		
		tabla.addView(user);
		tabla.addView(score);
		tabla.addView(ranking);
		
		TabHost tabHost=getTabHost();
		
		tabHost.setup();
		TabSpec specs = tabHost.newTabSpec("tab1");
		specs.setContent(R.id.local);
		specs.setIndicator("Local",getResources().getDrawable(R.drawable.userlocal));
		tabHost.addTab(specs);
		specs = tabHost.newTabSpec("tab2");
		specs.setContent(R.id.friend);
		specs.setIndicator("User Friend",getResources().getDrawable(R.drawable.usergroup));
		tabHost.addTab(specs);
		
		Button boton = new Button (this);
		boton.setText("1");
		Button boton2 = new Button (this);
		boton2.setText("2");
		
		user.addView(boton);
		score.addView(boton2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scores, menu);
		return true;
	}

	
	public class LeerXml extends AsyncTask<Void,Integer,Void>{
		private FileInputStream inputStream;
		private XmlPullParser parser;

		@Override
		protected void onPreExecute() {
			try {
				inputStream = openFileInput("scores.xml");
				XmlPullParser parser = Xml.newPullParser();
				parser.setInput(inputStream, "UTF-8");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			try{
				int eventType = XmlPullParser.START_DOCUMENT;
				
				while(eventType != XmlPullParser.END_DOCUMENT){
					if(eventType == XmlPullParser.START_TAG){
						if(parser.getAttributeValue(null,USERNAME)!=null){
							new Puntuaciones(parser.getAttributeValue(null, USERNAME), Integer.parseInt(parser.getAttributeValue(null, SCORE2)));
						}
					}
					eventType = parser.next();
				}
			}catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
