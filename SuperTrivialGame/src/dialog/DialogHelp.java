package dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;

public class DialogHelp {
	public static void startDialogHelp(Activity activity){
		Builder ventana =new AlertDialog.Builder(activity);
		ventana.setTitle("Ayuda");
		ventana.setMessage("La ayuda esta disponible en www.noexiste.com.");
		ventana.setIcon(android.R.drawable.ic_dialog_info);
		ventana.setPositiveButton("OK", null);
		ventana.show();
	}
}
