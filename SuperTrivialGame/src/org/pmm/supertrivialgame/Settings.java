package org.pmm.supertrivialgame;

import android.os.Bundle;
import android.app.Activity;
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

public class Settings extends Activity {
	private EditText nombre;
	private EditText email;
	private String[] datosQuestionPreferences;
	private String[] data;
	private Spinner lista;
	boolean itemSelected[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		this.nombre=(EditText) findViewById(R.id.editText1);
		this.email=(EditText) findViewById(R.id.editText2);
		datosQuestionPreferences = new String[]{"Sports", "Literature", "Science", "Movies", "History"};
		this.itemSelected = new boolean[datosQuestionPreferences.length];
		
		SharedPreferences preferencias=getSharedPreferences("settings", MODE_PRIVATE);
		this.nombre.setText(preferencias.getString("nombre", "Usuario1"));
		this.email.setText(preferencias.getString("email", "User1@gmail.com"));
		for(int cont=0;cont<datosQuestionPreferences.length;cont++){
			this.itemSelected[cont]=preferencias.getBoolean(datosQuestionPreferences[cont], false);
		}
	
		
		
		//Radio button de Question Server
		data = new String[]{"None","dakkon.isca.upv.es","Test server (not working!)"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
		lista = (Spinner)findViewById(R.id.spinner1);
		////Indicamos el tipo de Spinner 
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		// Establecemos el adaptador en el Spinner
		lista.setAdapter(adapter);
		
		lista.setSelection(preferencias.getInt("server", 0));
		
		//CheckBox de Question Preferences
		final AlertDialog.Builder ventanaPreferences =new AlertDialog.Builder(this);
		
		ventanaPreferences.setTitle("Question Preferences");
		ventanaPreferences.setMultiChoiceItems(datosQuestionPreferences, itemSelected, new DialogInterface.OnMultiChoiceClickListener() {
						
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if(isChecked){
					itemSelected[which]=true;
				}else {
					itemSelected[which]=false;
				}				
			}
		});

		ventanaPreferences.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getApplicationContext(), "Saved preferences...", Toast.LENGTH_SHORT).show();				
			}
		});	
		//se recupera el boton
		Button botonSelectPreferences = (Button)findViewById(R.id.button1);				
		// se asigna el listener
		botonSelectPreferences.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//questionPrefrences.show();
				ventanaPreferences.show();
			}
		});

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
		SharedPreferences preferencias= getSharedPreferences("settings", MODE_PRIVATE);
		
		SharedPreferences.Editor editor=preferencias.edit();
		editor.putString("nombre", this.nombre.getText().toString());
		editor.putString("email", this.email.getText().toString());
		
		for(int cont=0;cont<datosQuestionPreferences.length;cont++){
			editor.putBoolean(datosQuestionPreferences[cont], itemSelected[cont]);
		}
		editor.putInt("server", lista.getSelectedItemPosition());
		editor.commit();
	}


}
