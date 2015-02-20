package modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.pmm.supertrivialgame.Puntuaciones;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Xml;
import constants.Contants;

public class XmlScores {
	
	public static void writeScoreGame(Context c, int scoreGame){
		List<Puntuaciones> listScores = ReadFileScore(c);
		
		String nameUser = recuperarNombreUsuario(c);
		Puntuaciones currentScore = new Puntuaciones(nameUser, scoreGame, Contants.DEFAULT_CHAMPION);
		orderScores(listScores, currentScore);
		
		writeXml(c,listScores);
	}

	private static void writeXml(Context c, List<Puntuaciones> listScores) {
		XmlSerializer serialiser = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serialiser.setOutput(writer);
			serialiser.startDocument(null, null);
			serialiser.startTag(null, Contants.LIST_SCORES);
			for(int cont=0;cont<listScores.size();cont++){
				Puntuaciones score = listScores.get(cont);
				serialiser.startTag(null, Contants.SCORE);
				serialiser.attribute(null, Contants.USERNAME, score.getNombre());
				serialiser.attribute(null, Contants.SCORE, String.valueOf(score.getScore()));
				serialiser.attribute(null, Contants.RANKING, ""+(cont+1));	
				serialiser.attribute(null, Contants.CHAMPION,score.getChampion());	
				serialiser.endTag(null, Contants.SCORE);
			}
			serialiser.endTag(null, Contants.LIST_SCORES);
			serialiser.endDocument();
			FileOutputStream fos = c.openFileOutput (Contants.XML_NAME_SCORE,Context.MODE_PRIVATE);
			fos.write(writer.toString().getBytes());
			fos.flush();
			fos.close();
		} catch (IllegalArgumentException e) {
			System.out.println("XML IllegalArgument "+e);
		} catch (IllegalStateException e) {
			System.out.println("XML IlegalState "+e);
		} catch (IOException e) {
			System.out.println("XML IO "+e);
		}
	}

	private static void orderScores(List<Puntuaciones> listScores,
			Puntuaciones currentScore) {
		boolean insertCurrentUser = false;
		if(listScores.size()>0){
			for(int cont =0;cont<listScores.size() && insertCurrentUser==false;cont++){
				Puntuaciones score = listScores.get(cont);
				if(currentScore.getScore() >= score.getScore()){
					if(cont == 0){
						currentScore.setChampion(Contants.CHAMPION_YES);
					}
					listScores.add(cont, currentScore);
					insertCurrentUser=true;
				}
			}
			if(!insertCurrentUser){
				listScores.add(currentScore);
			}
		}else{
			currentScore.setChampion(Contants.CHAMPION_YES);
			listScores.add(currentScore);
		}
	}

	public static List<Puntuaciones> ReadFileScore(Context c) {
		List<Puntuaciones> listScores = new ArrayList<Puntuaciones>();
		try {
			FileInputStream inputStream = c.openFileInput(Contants.XML_NAME_SCORE);
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(inputStream, "UTF-8");
			int eventType = XmlPullParser.START_DOCUMENT;
			
			while(eventType != XmlPullParser.END_DOCUMENT){
				if(eventType == XmlPullParser.START_TAG){
					if(Contants.SCORE.equals(parser.getName())){
						String name = parser.getAttributeValue(null, Contants.USERNAME);
						int score = Integer.parseInt(parser.getAttributeValue(null, Contants.SCORE));
						String champion = parser.getAttributeValue(null, Contants.CHAMPION);
						listScores.add(new Puntuaciones(name, score, champion));
					}
				}
				eventType = parser.next();
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			System.out.println("XML FileNotFound "+e);
		} catch (XmlPullParserException e) {
			System.out.println("XML PullParser "+e);
		} catch (IOException e) {
			System.out.println("XML IO "+e);
		}
		return listScores;
	}
	
	private static String recuperarNombreUsuario(Context c) {
		SharedPreferences preferencias=c.getSharedPreferences(Contants.FILE_NAME_SETTINGS, Context.MODE_PRIVATE);
		return preferencias.getString(Contants.NOMBRE, Contants.DEFAULT_NAME);
	}
}
