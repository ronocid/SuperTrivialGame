package modelo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import org.pmm.supertrivialgame.Question;
import org.pmm.supertrivialgame.R;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DataBaseQuestions {
	private static final String NAME_DATABASE = "/supertrivialgame.db";

	public static ArrayList<Question> getQuestions(Context c){
		ArrayList<Question> listQuestions = new ArrayList<Question>();
		SQLiteDatabase db=null;
		try{
			db= SQLiteDatabase.openDatabase(c.getFilesDir().getPath()+NAME_DATABASE, null, SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		}catch(SQLiteException e){
			try {
				db=null;
				OutputStream dataOS = new FileOutputStream (c.getFilesDir().getPath()+NAME_DATABASE);
				InputStream dataIS;
					
				byte[] buffer = new byte[1024];
				dataIS=c.getResources().openRawResource(R.raw.supertrivialgame);
				while(dataIS.read(buffer)>0){
					dataOS.write(buffer);
				}
				dataIS.close();
				dataOS.flush();
				dataOS.close();
						
				db= SQLiteDatabase.openDatabase(c.getFilesDir().getPath()+NAME_DATABASE, null, SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
			} catch (FileNotFoundException e1) {
				System.out.println("database FileNotFound "+e);					
			} catch (IOException e1) {
				System.out.println("database IOE "+e);
			}
		}
		if(db!=null){
			Cursor c1=db.rawQuery("Select * from questions", null);
			int cont=0;
			while(c1.moveToNext() && cont<5){
				listQuestions.add(new Question(c1.getString(1),c1.getString(2),new String[]{c1.getString(3),c1.getString(4),c1.getString(5),c1.getString(6)},c1.getInt(7),c1.getInt(8)));
				cont++;
			}
			c1.close();
		}
		db.close();
		return listQuestions;
	}
}
