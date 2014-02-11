package org.pmm.supertrivialgame;

import java.util.Scanner;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Creditos extends Musica {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creditos);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		TextView descripcion= (TextView)findViewById(R.id.textViewDescripcion);
		TextView autores=(TextView)findViewById(R.id.textViewAutores);
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.creditos, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
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
