/*
 *
 Abel Ramalho Galvão
 Ramon de Cerqueira Silva
 *
 */

package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.Token;
import model.TokenInformation;
import model.TokenTypes;

public class LexicalAnalyzer {

	private static LexicalAnalyzer instance;
	private boolean status; /* true - Working | false - Finished */
	private StringBuilder lexeme;
	private byte openComment;
	private byte openString;
	private ArrayList<Token> wordList;
	private final ArrayList<String> errorList;

	private LexicalAnalyzer() {
		this.errorList = new ArrayList<String>();
	}

	public static LexicalAnalyzer getInstance() {
		if (instance == null) {
			instance = new LexicalAnalyzer();
		}
		return instance;
	}

	private void classifyLexeme(int lineNumber) {
		//verifying all symbols
		//if (this.lexeme.toString().matches([]))
	}

	private void parseLine(String line, int lineNumber) {
		for (int index = 0; index < line.length(); index += 1) {
			char word = line.charAt(index);
			boolean isDelimiter = TokenInformation.getInstance().getDelimiters().contains("" + word);
			if (this.openComment > 0) {
				this.lexeme.append(word);
				if(this.openComment == 1) {
					if(index == line.length() - 1) {
						this.wordList.add(new Token(TokenTypes.COMMENT, this.lexeme.toString(), lineNumber));
						this.lexeme = new StringBuilder();
						this.openComment = 0;
					}
				} else {
					if(TokenInformation.getInstance().closeComment.equals(this.lexeme.substring(this.lexeme.length() - 2))) {
						this.wordList.add(new Token(TokenTypes.COMMENT, this.lexeme.toString(), lineNumber));
						this.lexeme = new StringBuilder();
						this.openComment = 0;
					}
				}
			} else if (this.openString > 0) {
				this.lexeme.append(word);
				if (this.openString == 1 && word == '\'') {
					this.wordList.add(new Token(TokenTypes.STRING, this.lexeme.toString(), lineNumber));
					this.lexeme = new StringBuilder();
					this.openString = 0;
				} else if (this.openString == 2 && word == '"') {
					this.wordList.add(new Token(TokenTypes.STRING, this.lexeme.toString(), lineNumber));
					this.lexeme = new StringBuilder();
					this.openString = 0;
				}
			} else if (word == ' ' || word == '\n' || word == '\t' || isDelimiter) {
				if (isDelimiter) {
					if (lexeme.length() > 0) {
						char temporaryCharacter = lexeme.charAt(lexeme.length() - 1);
						if (!(temporaryCharacter >= '0' && temporaryCharacter <= '9')) {
							this.wordList.add(new Token(TokenTypes.DELIMITER, "" + word, lineNumber));
						}
					}
				} else {
					this.classifyLexeme(lineNumber);
					this.lexeme = new StringBuilder();
				}
			} else {
				if (word == '"' || word == '\'') {
					this.classifyLexeme(lineNumber);
					this.lexeme = new StringBuilder();
				}
				this.lexeme.append(word);
			}
		}
	}

	public ArrayList<Token> parseFile(String filename) throws IOException {
		if (this.status) {
			return null;
		}
		this.status = true;
		this.lexeme = new StringBuilder();
		this.openComment = 0;
		this.openString = 0;
		this.wordList = new ArrayList<>();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filename)); // // Passa o caminho e o nome do
																						// arquivo
		int counter = 1;
		while (bufferedReader.ready()) {
			this.parseLine(bufferedReader.readLine(), counter);
			counter++;
		}
		bufferedReader.close();
		this.status = false;
		return this.wordList;
	}

}
// tratar comentarios, guardar lista de tokens, tratar erros.