package org.pmm.supertrivialgame;

import constants.Contants;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings extends Musica {
	private EditText nombre;
	private EditText email;
	private String[] listQuestionThemes;
	private String[] listServers;
	private Spinner servers;
	boolean listQuestionThemesSelection[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		this.nombre=(EditText) findViewById(R.id.editText1);
		this.email=(EditText) findViewById(R.id.editText2);
		this.servers = (Spinner)findViewById(R.id.spinner1);
		Button botonSelectPreferences = (Button)findViewById(R.id.button1);	
		
		initializationData();
		retrievingPreferences();	
		createAdapterSpinnerServer();

		final AlertDialog.Builder ventanaPreferences = alertDialogSelectedQuestionThemes();	
		botonSelectPreferences.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//questionPrefrences.show();
				ventanaPreferences.show();
			}
		});

	}

	private AlertDialog.Builder alertDialogSelectedQuestionThemes() {
		final AlertDialog.Builder ventanaPreferences =new AlertDialog.Builder(this);
		
		ventanaPreferences.setTitle(Contants.TITLE_DIALOG_THEMES);
		ventanaPreferences.setMultiChoiceItems(listQuestionThemes, listQuestionThemesSelection, new DialogInterface.OnMultiChoiceClickListener() {
						
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if(isChecked){
					listQuestionThemesSelection[which]=true;
				}else {
					listQuestionThemesSelection[which]=false;
				}				
			}
		});

		ventanaPreferences.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getApplicationContext(), "Saved preferences...", Toast.LENGTH_SHORT).show();				
			}
		});
		return ventanaPreferences;
	}

	private void createAdapterSpinnerServer() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listServers);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		servers.setAdapter(adapter);
	}

	private void retrievingPreferences() {
		SharedPreferences preferencias=getSharedPreferences(Contants.FILE_NAME_SETTINGS, MODE_PRIVATE);
		this.nombre.setText(preferencias.getString(Contants.NOMBRE, Contants.DEFAULT_NAME));
		this.email.setText(preferencias.getString(Contants.EMAIL, Contants.DEFAULT_EMAIL));
		for(int cont=0;cont<listQuestionThemes.length;cont++){
			this.listQuestionThemesSelection[cont]=preferencias.getBoolean(listQuestionThemes[cont], Contants.DEFAULT_QUESTION_THEME);
		}
		this.servers.setSelection(preferencias.getInt(Contants.SERVER, 0));
	}

	private void initializationData() {
		this.listQuestionThemes = new String[]{Contants.SPORTS, Contants.LITERATURE, Contants.SCIENCE, Contants.MOVIES, Contants.HISTORY};
		this.listServers = new String[]{Contants.NONE,Contants.DAKKON_ISCA_UPV_ES,Contants.TEST_SERVER_NOT_WORKING};
		this.listQuestionThemesSelection = new boolean[listQuestionThemes.length];
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.action_help:
				AlertDialog.Builder ventana =new AlertDialog.Builder(this);
				ventana.setTitle("Ayuda");
				ventana.setMessage("La ayuda esta disponible en www.noexiste.com.");
				ventana.setIcon(android.R.drawable.ic_dialog_info);
				ventana.setPositiveButton("OK", null);
				ventana.show();
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		SharedPreferences preferencias= getSharedPreferences(Contants.FILE_NAME_SETTINGS, MODE_PRIVATE);
		
		SharedPreferences.Editor editor=preferencias.edit();
		editor.putString(Contants.NOMBRE, this.nombre.getText().toString());
		editor.putString(Contants.EMAIL, this.email.getText().toString());
		
		for(int cont=0;cont<listQuestionThemes.length;cont++){
			editor.putBoolean(listQuestionThemes[cont], listQuestionThemesSelection[cont]);
		}
		editor.putInt(Contants.SERVER, servers.getSelectedItemPosition());
		editor.commit();
	}
}