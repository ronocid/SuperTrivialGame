package controlador.play;

import constants.Contants;
import android.graphics.Color;
import android.widget.Button;

public class ControladorButton {
	private Button answer1;
	private Button answer2;
	private Button answer3;
	private Button answer4;
	
	public ControladorButton (Button answer1, Button answer2, Button answer3, Button answer4){
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
		this.answer4 = answer4;
	}
	
	public void bloquearBotones() {
		answer1.setEnabled(false);
		answer2.setEnabled(false);
		answer3.setEnabled(false);
		answer4.setEnabled(false);
	}
	
	public void changeColorTextButton(int colorButton1, int colorButton2, int colorButton3,int colorButton4){
		answer1.setTextColor(colorButton1);
		answer2.setTextColor(colorButton2);
		answer3.setTextColor(colorButton3);
		answer4.setTextColor(colorButton4);
	}
	
	public void inicializarRespuesta(String[] aswers) {
		answer1.setText(aswers[0]);
		answer2.setText(aswers[1]);
		answer3.setText(aswers[2]);
		answer4.setText(aswers[3]);
	}
	
	public void botonesVisible() {
		answer1.setEnabled(true);
		answer2.setEnabled(true);
		answer3.setEnabled(true);
		answer4.setEnabled(true);
	}

	public void removeQuestionHelp(int ayuda) {
		switch(ayuda){
		case 0:
			answer1.setEnabled(false);
			break;
		case 1:
			answer2.setEnabled(false);
			break;
		case 2:
			answer3.setEnabled(false);
			break;
		case 3:
			answer4.setEnabled(false);
			break;
		}
	}
	
	public void showCorrectAnswer(int answerQuestion) {
		if(answerQuestion == Contants.ANSWER_ONE){
			changeColorTextButton(Color.GREEN, Color.RED, Color.RED, Color.RED);
		}else if(answerQuestion == Contants.ANSWER_TWO){
			changeColorTextButton(Color.RED, Color.GREEN, Color.RED, Color.RED);
		}else if(answerQuestion == Contants.ANSWER_THREE){
			changeColorTextButton(Color.RED, Color.RED, Color.GREEN, Color.RED);
		}else if(answerQuestion == Contants.ANSWER_FOUR){
			changeColorTextButton(Color.RED, Color.RED, Color.RED, Color.GREEN);
		}
	}
}
