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
		this.errorList = new ArrayList<>();
	}

	public static LexicalAnalyzer getInstance() {
		if (instance == null) {
			instance = new LexicalAnalyzer();
		}
		return instance;
	}

	private void classifyLexeme(int lineNumber) {
		if (TokenInformation.getInstance().getReservedWords().contains(this.lexeme.toString())) {
			this.wordList.add(new Token(TokenTypes.RESERVED, this.lexeme.toString(), lineNumber));
		} else if (this.lexeme.toString().matches("(-)?\\s*[0-9]([0-9]*\\.?[0-9]+)?")) {
			this.wordList.add(new Token(TokenTypes.NUMBER, this.lexeme.toString(), lineNumber));
		} else if (this.lexeme.toString().matches("[_]?(([a-z]|[A-Z]|_)+[0-9]*)+(([a-z]|[A-Z]|[0-9]|_)*)*")) {
			this.wordList.add(new Token(TokenTypes.IDENTIFIER, this.lexeme.toString(), lineNumber));
		} else {
			this.errorList.add("Error bad-formed Identifier");
		}

	}

	private char lookAhead(String line, int currentIndex) {
		if (currentIndex + 1 < line.length()) {
			return line.charAt(currentIndex + 1);
		}
		return 0;
	}

	private void parseLine(String line, int lineNumber) {
		char previousWord = 0;
		for (int index = 0; index < line.length(); index += 1) {
			char word = line.charAt(index);
			boolean isDelimiter = TokenInformation.getInstance().getDelimiters().contains("" + word);
			boolean isSplit = TokenInformation.getInstance().getSplitWords().contains(word);
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
			} else if (word == ' ' || word == '\n' || word == '\t' || isDelimiter || isSplit) {
				if (isDelimiter) {
					if (lexeme.length() > 0) {
						char temporaryCharacter = lexeme.charAt(0);
						if (!(word == '.' && temporaryCharacter >= '0' && temporaryCharacter <= '9')) {
							this.classifyLexeme(lineNumber);
							this.lexeme = new StringBuilder();
							this.wordList.add(new Token(TokenTypes.DELIMITER, "" + word, lineNumber));
						} else {
							this.lexeme.append(word);
						}
					} else {
						this.wordList.add(new Token(TokenTypes.DELIMITER, "" + word, lineNumber));
					}
				} else if (isSplit) {
					if (TokenInformation.getInstance().getTogetherWords().containsKey(previousWord) && this.lexeme.length() == 1) {
						if (TokenInformation.getInstance().getTogetherWords().get(previousWord) == word) {
							lexeme.append(word);
							if (TokenInformation.getInstance().getArithmeticOperators().contains(this.lexeme.toString())) {
								this.wordList.add(new Token(TokenTypes.ARITHMETIC, this.lexeme.toString(), lineNumber));
							} else if (TokenInformation.getInstance().getLogicalOperators().contains(this.lexeme.toString())) {
								this.wordList.add(new Token(TokenTypes.LOGIC, this.lexeme.toString(), lineNumber));
							} else if (TokenInformation.getInstance().getRelationalOperators().contains(this.lexeme.toString())) {
								this.wordList.add(new Token(TokenTypes.RELATIONAL, this.lexeme.toString(), lineNumber));
							}
							this.lexeme = new StringBuilder();
						} else {
							if (TokenInformation.getInstance().getLogicalOperators().contains("" + previousWord + TokenInformation.getInstance().getTogetherWords().get(previousWord))) {
								this.errorList.add("Error " + lineNumber + " Logical Operator bad-formed");
							}
						}
					} else {
						this.classifyLexeme(lineNumber);
						this.lexeme = new StringBuilder();
						if (word == '/' && this.lookAhead(line, index) == '/') {
							this.lexeme.append(word);
						} else if (TokenInformation.getInstance().getTogetherWords().containsKey(word)) {
							lexeme.append(word);
						} else if (TokenInformation.getInstance().getArithmeticOperators().contains("" + word)) {
							this.wordList.add(new Token(TokenTypes.ARITHMETIC, "" + word, lineNumber));
						} else if (TokenInformation.getInstance().getLogicalOperators().contains("" + word)) {
							this.wordList.add(new Token(TokenTypes.LOGIC, "" + word, lineNumber));
						} else if (TokenInformation.getInstance().getRelationalOperators().contains("" + word)) {
							this.wordList.add(new Token(TokenTypes.RELATIONAL, "" + word, lineNumber));
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
					this.openString = (byte) (word == '"' ? 1 : 0);
				} else if (word == '/' && previousWord == '/') {
					this.openComment = 1;
				} else if (word == '*' && previousWord == '/') {
					this.openComment = 2;
				}
				this.lexeme.append(word);
			}
			previousWord = word;
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

	public ArrayList<String> getErrorList() {
		return  this.errorList;
	}

}

// tratar comentarios, guardar lista de tokens, tratar erros.