package org.pmm.supertrivialgame;

import java.util.Scanner;
import dialog.DialogHelp;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Creditos extends Musica {
	private TextView descripcion;
	private TextView autores;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creditos);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		descripcion= (TextView)findViewById(R.id.textViewDescripcion);
		autores=(TextView)findViewById(R.id.textViewAutores);
		
		writeCredits();
	}

	private void writeCredits() {
		try{
			Scanner lectura=new Scanner(getResources().openRawResource(R.raw.creditos),"UTF-8");
			String lecturalinea="";
			while(lectura.hasNext()){
				lecturalinea=lectura.nextLine();
				lecturalinea+="\n"+lectura.nextLine();
				lecturalinea+="\n"+lectura.nextLine();
				descripcion.setText(lecturalinea);
				lecturalinea=lectura.nextLine();
				lecturalinea+="\n"+lectura.nextLine();
				autores.setText(lecturalinea);
			}
			lectura.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.creditos, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.action_help:
				DialogHelp.startDialogHelp(this);
				return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
