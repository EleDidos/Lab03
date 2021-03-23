package it.polito.tdp.spellchecker.model;

/**
 * Ogni classe di questo tipo corrisponde a una parola
 * Associo il fatto che sia corretta o meno
 * @author elena
 *
 */

public class RichWord {
	
	private String word;
	private boolean correct;
	
	
	public RichWord(String word) {
		
		this.word = word;
		
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public boolean isCorrect() {
		return correct;
	}
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

}
