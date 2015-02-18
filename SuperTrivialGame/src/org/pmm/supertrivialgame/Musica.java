package org.pmm.supertrivialgame;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;

public class Musica extends Activity{
	private static MediaPlayer mpPrincipal = null;
	private static MediaPlayer mpPlay = null;
	private static MediaPlayer mpAcierto = null;
	private static MediaPlayer mpFallo= null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	
	public void comenzarMusicaPrincipal(){
		try{
			mpPrincipal = MediaPlayer.create(this,R.raw.secuencia);
			mpPrincipal.setOnPreparedListener(new OnPreparedListener() {
				
				@Override
				public void onPrepared(MediaPlayer arg0) {
					mpPrincipal.start();
					mpPrincipal.setLooping(true);
				}
			});
			
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void terminarMusicaPrincipal() {
		mpPrincipal.release();		
	}
	
	public void pausarMusicaPrincipal(){
		mpPrincipal.pause();
	}
	
	public void reanudarMusicaPrincipal(){
		mpPrincipal.start();
	}
	
	public void comenzarMusicaPlay(){
		try{
			mpPlay = MediaPlayer.create(this,R.raw.clock);
			mpPlay.setOnPreparedListener(new OnPreparedListener() {
				
				@Override
				public void onPrepared(MediaPlayer arg0) {
					mpPlay.start();
					mpPlay.setLooping(true);
				}
			});
			
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void pausarMusicaPlay() {
		mpPlay.pause();		
	}
	
	public void reanudarMusicaPlay(){
		mpPlay.start();
	}
	
	public void terminarMusicaPlay() {
		mpPlay.release();		
	}
	
	public void comenzarMusicaAcierto(){
		try{
			mpAcierto = MediaPlayer.create(this,R.raw.acierto);
			//mpAcierto.prepareAsync();
			mpAcierto.start();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void comenzarMusicaFallo(){
		try{
			mpFallo = MediaPlayer.create(this,R.raw.fallo);
			//mpFallo.prepareAsync();
			mpFallo.start();
		}catch(Exception e){
			System.out.println(e);
		}
	}

}
