package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * Dizionario della lingua scelta
 * MODELLO del programma
 * @author elena
 *
 */

public class Dictionary {
	
	//***************** ARRAYLIST o LINKEDLIST ****************************************************
	//CON LA LISTA NON E' IN ORDINE ALFABETICO, dovrei sempre riordinarlo
	//List <String> dic=new LinkedList <String> (); //cambierò solo qui la lista
	
	TreeMap <String,RichWord> dic = new TreeMap <String,RichWord>();
	
	
	public void loadDictionary(String nomeFile, String language) {
		
		try {
			FileReader fr = new FileReader(nomeFile);
			BufferedReader br = new BufferedReader (fr);
			String word;
			while ((word=br.readLine())!=null) {
				dic.put(word,new RichWord(word));
			}
			br.close();
			fr.close();
		}catch(IOException ioe) {
			System.out.println("Errore nella lettura del file!");
		}
	}
	
	
	public List <RichWord> spellCheckTextLinear (List<String> inputTextList){
		
		List <RichWord> words = new LinkedList <RichWord>();
		
		for(String si: inputTextList) {
			if(dic.get(si)!=null) { //la contiene già
				dic.get(si).setCorrect(true); //corretta
			} else { //non la contiene
				dic.get(si).setCorrect(false); //sbagliata
			}
				
		}
		return words;	
	}
	
	
	/**
	 * lista in ordine alfabetico su cui effettuare la ricerca
	 * key il valore da cercare
	 * @return la posizione del valore trovato, -1 se non l'ha trovato
	 * 
	 * FUNZIONA SOLO CON UN ARRAY??????
	*/
	public List <RichWord> spellCheckTextDichotomic (List<String> lista){
		
			List <RichWord> words = new LinkedList <RichWord>(); //giuste o sbagliate
		
			//DIZIONARIO in ordine alfabetico IN ARRAY
			String [] arrayDIC = new String [dic.size()]; 
			
			int i = -1;
			for(String si: dic.keySet()) {
				arrayDIC[++i]=si;
			}
			
			for(String cerca : lista) {
				
				int low = 0;
				int high = lista.size()-1;
				int mid = (low+high)/2;
				boolean found=false;
				while (low<=high) {
					if(arrayDIC[mid].equals(cerca)) {
						found=true; //parola trovata nella posizione mid
						break;
				     }
					else if (arrayDIC[mid].compareTo(cerca)<0) {
						low = mid + 1; //taglio via la parte di dizionario troppo piccol lessicograficamente
						//guardo solo più la parte a dx delk "mid"
					}
					else {
						high = mid - 1; //guardo solo più la parte a sx del "mid"
					}
				} //while
				
				if(found==true) {
					//creo una parola da aggiungere alle mie "words" secondo il "mid" aggiornato
					//setto true=corretta alla parola trovata 
					RichWord rw = new RichWord (arrayDIC[mid]);
					rw.setCorrect(true); //corretta
					words.add(rw);
				} else {
					RichWord rw = new RichWord (arrayDIC[mid]);
					rw.setCorrect(false); //sbagliata
					words.add(rw);
				}
				
			} //for ogni parola da cercare passata da user
			
			return words; //lista di parole a cui ho anche assegnato GIUSTO o SBAGLIATO	
	}
	
	
	public String wrongWords (List <RichWord> list) {
		String wrong = "";
    	for(RichWord rwi : list)
    		if(!rwi.isCorrect()) //se è sbagliata
    			wrong+=rwi.getWord()+"\n";
    	return wrong;
	}
	
	
	public int numberErrors (List <RichWord> list) {
		int cnt=0;
    	for(RichWord rwi : list)
    		if(!rwi.isCorrect()) //se è sbagliata
    			cnt++;
    	return cnt;
	}
	

}
