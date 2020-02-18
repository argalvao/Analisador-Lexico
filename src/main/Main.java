/*
 *
 Abel Ramalho Galvão
 Ramon de Cerqueira Silva
 *
 */
package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import controller.LexicalAnalyzer;
import model.Token;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		HashMap<String, ArrayList<Token>> fileTokens = new HashMap<>();
		try {
			File directory = new File("input"); // Identifica o diretório de entrada de arquivos
			if (directory.isDirectory()) {
				File outputDirectory = new File("output");
				// noinspection ResultOfMethodCallIgnored
				outputDirectory.mkdir();
				File fileList[] = directory.listFiles(); // Lista todos os arquivos do diretório e armazena em um array
				for (int counter = 0; counter < fileList.length; counter++) {
					if (fileList[counter].getName().contains("entrada")) {
						fileTokens.put(fileList[counter].getName(), LexicalAnalyzer.getInstance().parseFile("input/" + fileList[counter].getName()));
						BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output/" + fileList[counter].getName().replace("entrada", "saida")));
						PrintWriter printWriter = new PrintWriter(bufferedWriter);
						printWriter.printf("LISTA DE TOKENS:\n\n");
						for (Token token : fileTokens.get(fileList[counter].getName())) {
							printWriter.println(token);
						}
						printWriter.printf("\n\nLISTA DE ERROS:\n\n");
						for (String errorMessage : LexicalAnalyzer.getInstance().getErrorList()) {
							printWriter.println(errorMessage);
						}
						printWriter.close();
					}
				}
			}
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
	}

}
