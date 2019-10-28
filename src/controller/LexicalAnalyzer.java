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
	
	public static void readArq() throws FileNotFoundException {
	     try{
	    	 BufferedReader arq = new BufferedReader(new FileReader("/home/abel/eclipse-workspace/Analisador-Lexico/input/entradaTeste.txt"));
	         while(arq.ready()){
	            linha = arq.readLine().split("[\\W]");
	            for (int i = 0; i < linha.length; i++) {
	            	if(!linha[i].equals(" ")) {
	            		lista.add(linha[i]);
	            	}
	            }
		      }
	         arq.close();
	         for (int j = 0; j < lista.size(); j++) {
	        	 for (int p = 0; p < ReservedWords.rWords.size(); p++) {
	        		 if(!lista.get(j).equals(ReservedWords.rWords.get(p))) {
	        			 System.out.println("Identificador: " + lista.get(j));
	        		 }
	        	 }
	         }
	      }catch(IOException ioe){
	         ioe.printStackTrace();
	      }
	}

}
