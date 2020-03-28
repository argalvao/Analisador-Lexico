/*
 *
 Abel Ramalho Galvï¿½o
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
import java.util.LinkedList;
import java.util.Queue;

import controller.LexicalAnalyzer;
import controller.SynthaticAnalyzer;
import model.SynthaticNode;
import model.Token;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		HashMap<String, ArrayList<Token>> fileTokens = new HashMap<>();
		try {
			File directory = new File("input"); // Identifica o diretÃ³rio de entrada de arquivos
			if (directory.isDirectory()) {
				File outputDirectory = new File("output");
				// noinspection ResultOfMethodCallIgnored
				outputDirectory.mkdir();
				File fileList[] = directory.listFiles(); // Lista todos os arquivos do diretÃ³rio e armazena em um array
				for (int counter = 0; counter < fileList.length; counter++) {
					if (fileList[counter].getName().contains("entrada")) {
						fileTokens.put(fileList[counter].getName(), LexicalAnalyzer.getInstance().parseFile("input/" + fileList[counter].getName()));
						BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output/" + fileList[counter].getName().replace("entrada", "saida")));
						PrintWriter printWriter = new PrintWriter(bufferedWriter);
						printWriter.printf("LISTA DE TOKENS:\n\n");
						Queue<Token> tempQueue = new LinkedList<Token>(fileTokens.get(fileList[counter].getName()));				
						SynthaticNode synthaticNode = SynthaticAnalyzer.getInstance().start(tempQueue);
						SynthaticAnalyzer.getInstance().showDerivation(synthaticNode);
						for (Token token : fileTokens.get(fileList[counter].getName())) {
							printWriter.println(token);
						}
						printWriter.printf("\n\nLISTA DE ERROS LEXICOS:\n\n");
						if(LexicalAnalyzer.getInstance().getErrorList().isEmpty()) {
							System.out.println("Código não apresenta erros léxicos, parabéns!");
						}else {
							for (String errorMessage : LexicalAnalyzer.getInstance().getErrorList()) {
								printWriter.println(errorMessage);
								System.out.println("ATENÇÃO - Erros léxicos foram encontrados!");
							}
						}
						//System.out.println(SynthaticAnalyzer.getInstance().getId());
						printWriter.printf("\n\n\nLISTA DE ERROS SINTATICOS:\n\n");
						if(SynthaticAnalyzer.getInstance().getErros().isEmpty()) {
							System.out.println("Código não apresenta erros sintáticos, parabéns!");
						}else {
							for(String synthaticError : SynthaticAnalyzer.getInstance().getErros()) {
								printWriter.println(synthaticError);
							}
							System.out.println("ATENÇÃO - Erros sintáticos foram encontrados!");
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
