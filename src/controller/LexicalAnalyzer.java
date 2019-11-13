package controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.File;

import model.ArithmeticOperators;
import model.Delimiters;
import model.LogicalOperators;
import model.ReservedWords;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LexicalAnalyzer {
	
	public static final ArrayList wordList = new ArrayList();
	public static final ArrayList reservedList = new ArrayList();
	static String[] word = null;
	static int line = 0;
	static Boolean value = null;
	
	@SuppressWarnings("unused")
	public static void readArq() throws FileNotFoundException {
	     try{
			 File file = new File("input"); // Identifica o diretório de entrada de arquivos
			 File afile[] = file.listFiles(); // Lista todos os arquivos do diretório e armazena em um array
			 int l = 0;
			 for (int j = afile.length; l < j; l++) {
			 	if (afile[l].toString().contains("entrada")){ // Verifica de o arquivo inicia com o nome "entrada"
					File arquivos = afile[l]; // Armazena os nomes dos arquivos já verificados
					String name[] = afile[l].toString().split("entrada"); // Separa o restante do nome do arquivo
					for (int h = 0; h < name.length; h++){
						if (name[h].toString().contains(".txt")) { // Verifica se o arquivo possui a extensão .txt
							BufferedReader inputArq = new BufferedReader(new FileReader(afile[l])); // // Passa o caminho e o nome do arquivo
							BufferedWriter outputArq = new BufferedWriter(new FileWriter("output//saida" + name[h])); // Concatena a String "saida" com o restante do nome do arquivo de entrada
							PrintWriter gravarArq = new PrintWriter(outputArq); 
							gravarArq.printf("LISTA DE TOKENS:\n\n");
							while (inputArq.ready()){
								word = inputArq.readLine().split("[\\W][ ]");
								for (int i = 0; i < word.length; i++) {
									if(!word[i].equals("")) {
										wordList.add(word[i]);
										for (int k = 0; k < ReservedWords.aRWords.size(); k++){
											if (word[i].toString().contains(ReservedWords.aRWords.get(k).toString())){
												gravarArq.println("Linha: " + (line+1) + " | " + ReservedWords.aRWords.get(k).toString() + " | Palavra Reservada");
											}
										}
										for (int k = 0; k < Delimiters.aDelimiters.size(); k++){
											if(word[i].toString().contains(Delimiters.aDelimiters.get(k).toString())){
												gravarArq.println("Linha: " + (line+1) + " | " + Delimiters.aDelimiters.get(k).toString() + " | Delimitador");
											}
										}
										for (int k = 0; k < LogicalOperators.aLOperators.size(); k++){
											if(word[i].toString().contains(LogicalOperators.aLOperators.get(k).toString())){
												gravarArq.println("Linha: " + (line+1) + " | " + LogicalOperators.aLOperators.get(k).toString() + " | Operador Lógico");
											}
										}
										for (int k = 0; k < ArithmeticOperators.aAOperators.size(); k++){
											if(word[i].toString().contains(ArithmeticOperators.aAOperators.get(k).toString())){
												gravarArq.println("Linha: " + (line+1) + " | " + ArithmeticOperators.aAOperators.get(k).toString() + " | Operador Aritmético");
											}
										}
									}
								}
								line++;
							}
							inputArq.close();
							outputArq.close();
							line = 0;
						}
					}
				}
			 }
	      }catch(IOException ioe){
	         ioe.printStackTrace();
	      }
	}

}
// tratar comentarios, guardar lista de tokens, tratar erros.