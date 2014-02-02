package org.pmm.supertrivialgame;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends Activity {
	private ImageView iz;
	private ImageView der;
	
	protected void onStop() {
		Splash.this.finish();
		super.onStop();
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		ImageView azul=(ImageView)findViewById(R.id.azul);
		Animation animAzul=AnimationUtils.loadAnimation(this, R.anim.animacionazul);
		azul.setAnimation(animAzul);
		
		ImageView amarillo=(ImageView)findViewById(R.id.amarillo);
		Animation animAmarillo=AnimationUtils.loadAnimation(this, R.anim.animacionamarillo);
		amarillo.setAnimation(animAmarillo);
		
		ImageView morado=(ImageView)findViewById(R.id.morado);
		Animation animMorado=AnimationUtils.loadAnimation(this, R.anim.animacionmorado);
		morado.setAnimation(animMorado);
		
		ImageView naranja=(ImageView)findViewById(R.id.naranja);
		Animation animNaranja=AnimationUtils.loadAnimation(this, R.anim.animacionnaranja);
		naranja.setAnimation(animNaranja);
		
		ImageView rojo=(ImageView)findViewById(R.id.rojo);
		Animation animRojo=AnimationUtils.loadAnimation(this, R.anim.animacionrojo);
		rojo.setAnimation(animRojo);
		
		ImageView verde=(ImageView)findViewById(R.id.verde);
		Animation animVerde=AnimationUtils.loadAnimation(this, R.anim.animacionverde);
		verde.setAnimation(animVerde);
		
		iz=(ImageView)findViewById(R.id.iz);
		Animation animIz=AnimationUtils.loadAnimation(this, R.anim.animintroizq);
		iz.setAnimation(animIz);
		
		der=(ImageView)findViewById(R.id.de);
		Animation animDer=AnimationUtils.loadAnimation(this, R.anim.animintroder);
		der.setAnimation(animDer);
		
		TextView texto1=(TextView)findViewById(R.id.textViewTextoTitulo1);
		Animation animtexto= AnimationUtils.loadAnimation(this, R.anim.animletras);
		texto1.setAnimation(animtexto);
		
		TextView texto2=(TextView)findViewById(R.id.textViewTextoTitulo2);
		texto2.setAnimation(animtexto);
		
		
		animtexto.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation arg0) {
				iz.setVisibility(0);
				der.setVisibility(0);
				Intent i= new Intent(Splash.this, Main.class);
				startActivity(i);
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				
			}

			@Override
			public void onAnimationStart(Animation arg0) {
				
			}});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
