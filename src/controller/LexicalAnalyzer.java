package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import model.ReservedWords;


public class LexicalAnalyzer {
	
	public static final ArrayList<String> lista = new ArrayList<String>();
	static String[] linha = null;
	static Boolean value = null;
	
	@SuppressWarnings("unused")
	public static void readArq() throws FileNotFoundException {
	     try{
	    	 BufferedReader arq = new BufferedReader(new FileReader("input//entradaTeste.txt"));
	         while(arq.ready()){
	            linha = arq.readLine().split("[\\W]");
	            for (int i = 0; i < linha.length; i++) {
	            	try { // verifica se a String pode ser um valor Integer
	            		Integer.parseInt(linha[i]);
	            		value = true;
	            	} catch (NumberFormatException nfex) {
	            		value = false;
	            	}
					if(!linha[i].equals("") && value == false) { // Espaco entre os parenteses, guardava espa�os em branco.
	            		lista.add(linha[i]);
	            	}
	            }
		      }
	         arq.close();
	         for (int j = 0; j < lista.size(); j++) {
	        	 for (int p = 0; p < ReservedWords.rWords.size(); p++) {
	        		 if(!ReservedWords.rWords.contains(lista.get(j))) { // Modifiquei para contais() e funcionou
	        			 System.out.println("Identificador: " + lista.get(j));
	        			 break; // melhor um break ou um barramento?
	        		 }
	        	 }
	         }
	      }catch(IOException ioe){
	         ioe.printStackTrace();
	      }
	}

}
