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
import model.*;

import static model.ReservedWords.*;


public class LexicalAnalyzer {
	
	public static final ArrayList wordList = new ArrayList();
	public static final ArrayList reservedList = new ArrayList();
	static int line = 0;
	public static String[] word = null;

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
								wordList.add(inputArq.readLine());
								for (int i = 0; i < wordList.size(); i++) {
									word = wordList.get(i).toString().split("[\\W]");
									for (int g = 0; g < word.length; g++){
										if(!word[g].equals("")) {
											try { // verifica se a String pode ser um valor Integer
												Integer.parseInt(word[g]);
												gravarArq.println("Linha:\t" + (line+1) + "\t" + "|\t" + word[g]+ "\t\t" + "|\tNúmero Inteiro");
											} catch (NumberFormatException nfex) {
											}
										}
									}
									word = null;
									for (int g = 0; g < ArithmeticOperators.aAOperators.size(); g++){
										if(wordList.get(i).toString().contains(ArithmeticOperators.aAOperators.get(g).toString())){
											gravarArq.println("Linha:\t" + (line+1) + "\t" + "|\t" + ArithmeticOperators.aAOperators.get(g).toString() + "\t" + "\t| Operador Lógico");
										}
									}
									for (int k = 0; k < CommentDelimiters.aCDelimiters.size(); k++){
										if(wordList.get(i).toString().contains(CommentDelimiters.aCDelimiters.get(k).toString())){
											gravarArq.println("Linha:\t" + (line+1) + "\t" + "|\t" + CommentDelimiters.aCDelimiters.get(k).toString() + "\t\t" + "|\tDelimitador de Comentário");
										}
									}
									for (int k = 0; k < Delimiters.aDelimiters.size(); k++){
										if(wordList.get(i).toString().contains(Delimiters.aDelimiters.get(k).toString())){
											gravarArq.println("Linha:\t" + (line+1) + "\t" + "|\t" + Delimiters.aDelimiters.get(k).toString() + "\t\t" + "|\tDelimitador");
										}
									}
									for (int k = 0; k < LogicalOperators.aLOperators.size(); k++){
										if(wordList.get(i).toString().contains(LogicalOperators.aLOperators.get(k).toString())){
											gravarArq.println("Linha:\t" + (line+1) + "\t" + "|\t" + LogicalOperators.aLOperators.get(k).toString()+ "\t\t" + "|\tOperador Lógico");
										}
									}
									for (int k = 0; k < RelationalOperators.aROperators.size(); k++){
										if (wordList.get(i).toString().contains(RelationalOperators.aROperators.get(k).toString())){
											gravarArq.println("Linha:\t" + (line+1) + "\t" + "|\t" + RelationalOperators.aROperators.get(k).toString() + "\t\t" + "|\tOperador Relacional");
										}
									}
									for (int k = 0; k < aRWords.size(); k++){
										if (wordList.get(i).toString().contains(aRWords.get(k).toString())){
											gravarArq.println("Linha:\t" + (line+1) + "\t" + "|\t" + aRWords.get(k).toString() + "\t" + "|\tPalavra Reservada");
										}
									}
								}
								wordList.clear();
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