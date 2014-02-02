package org.pmm.supertrivialgame;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		Button botonPlay =(Button)findViewById(R.id.button1);
		Button botonCreditos =(Button)findViewById(R.id.button3);
		Button botonScore =(Button)findViewById(R.id.button2);
		
		botonPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(Main.this, Play.class);
				startActivity(i);
			}
		});
		
		botonCreditos.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent i=new Intent(Main.this, Creditos.class);
						startActivity(i);
					}
		});
		
		botonScore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(Main.this, Scores.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.menu_setting:
			Intent i= new Intent(this, Settings.class);
			startActivity(i);
			return true;
		case R.id.action_help:
			Builder ventana =new AlertDialog.Builder(this);
			ventana.setTitle("Ayuda");
			ventana.setMessage("La ayuda esta disponible en www.noexiste.com.");
			ventana.setIcon(android.R.drawable.ic_dialog_info);
			ventana.setPositiveButton("OK", null);
			ventana.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


}
