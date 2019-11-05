package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import model.ReservedWords;


public class LexicalAnalyzer {
	
	public static final ArrayList wordList = new ArrayList();
	static String[] word = null;
	static String[] lineL = null;
	static int line = 0;
	static Boolean value = null;
	
	@SuppressWarnings("unused")
	public static void readArq() throws FileNotFoundException {
	     try{
	    	 BufferedReader inputArq = new BufferedReader(new FileReader("input//entradaTeste.txt"));
	    	 BufferedWriter outputArq = new BufferedWriter(new FileWriter("output//saidaTeste.txt"));
	    	 PrintWriter gravarArq = new PrintWriter(outputArq);
	    	 gravarArq.printf("LISTA DE TOKENS:\n\n");
	         while(inputArq.ready()){
	            word = inputArq.readLine().split("[\\W]");
	            for (int i = 0; i < word.length; i++) {
	            	try { // verifica se a String pode ser um valor Integer
	            		Integer.parseInt(word[i]);
	            		value = true;
	            		gravarArq.println("Linha: " + line + " | " + word[i] + " | Digito");
	            	} catch (NumberFormatException nfex) {
	            		value = false;
	            	}
					if(!word[i].equals("") && value == false) { // Espaco entre os parenteses, guardava espa�os em branco.
	            		wordList.add(word[i]);
	            		if(!ReservedWords.aRWords.contains(word[i])) { // Modifiquei para contais() e funcionou	        			 
	            			 gravarArq.println("Linha: " + line + " | " + word[i] + " | Identificador");
		        			 //break; // melhor um break ou um barramento?
		        		 }
		        		 else {
		        			 gravarArq.println("Linha: " + line + " | " + word[i] + " | Palavra Reservada");
		        			 //break;
		        		 }
	            	}
	            }
	            line++;
		      }
	         inputArq.close();        
	         outputArq.close();
	      }catch(IOException ioe){
	         ioe.printStackTrace();
	      }
	}

}
