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
	            		gravarArq.println("Linha: " + (line+1) + " | " + word[i] + " | Numeral"); //Escreve o numeral
	            	} catch (NumberFormatException nfex) {
	            		value = false;
	            	}
					if(!word[i].equals("") && value == false) { // Espaco entre os parenteses, guardava espaços em branco.
	            		wordList.add(word[i]);
	            		if(ReservedWords.aRWords.contains(word[i])) { // verifica é uma palavra reservada	        			 
	            			 gravarArq.println("Linha: " + (line+1) + " | " + word[i] + " | Palavra Reservada"); // escreve a palavra reservada no arquivo
		        		 }
		        		 else {
		        			 gravarArq.println("Linha: " + line + " | " + word[i] + " | Identificador"); // escreve o identificador no arquivo
		        		 }
	            	}
	            }
	            line++; // armazena as linhas para impressão no arquivo
		      }
	         inputArq.close();        
	         outputArq.close();
	      }catch(IOException ioe){
	         ioe.printStackTrace();
	      }
	}

}
// tratar comentarios, guardar lista de tokens, tratar erros.