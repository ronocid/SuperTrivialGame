package org.pmm.supertrivialgame;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Musica extends Activity{
	private static MediaPlayer mpPrincipal = null;
	private MediaPlayer mpPlay = null;
	private MediaPlayer mpAcierto = null;
	private MediaPlayer mpFallo= null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	
	public void comenzarMusicaPrincipal(){
		try{
			mpPrincipal = MediaPlayer.create(this,R.raw.secuencia);
			mpPrincipal.prepareAsync();
			mpPrincipal.start();
			mpPrincipal.setLooping(true);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void pausarMusicaPrincipal(){
		mpPrincipal.pause();
	}
	
	public void reanudarMusicaPrincipal(){
		mpPrincipal.start();
	}
	
	public void comenzarMusicaPlay(){
		try{
			mpPlay = MediaPlayer.create(this,R.raw.secuencia);
			//mpPlay.prepareAsync();
			mpPlay.start();
			mpPlay.setLooping(true);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void comenzarMusicaAcierto(){
		try{
			mpAcierto = MediaPlayer.create(this,R.raw.secuencia);
			//mpAcierto.prepareAsync();
			mpAcierto.start();
			mpAcierto.setLooping(true);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void comenzarMusicaFallo(){
		try{
			mpFallo = MediaPlayer.create(this,R.raw.secuencia);
			//mpFallo.prepareAsync();
			mpFallo.start();
			mpFallo.setLooping(true);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
}
