package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TokenInformation {

	private static TokenInformation instance;
	private final HashSet<String> reservedWords;
	private final HashSet<String> arithmeticOperators;
	private final HashSet<String> relationalOperators;
	private final HashSet<String> logicalOperators;
	private final HashSet<String> delimiters;
	private final HashMap<String, Byte> commentDelimiters;
	public final String closeComment;
	public final String openCommnet;

	private TokenInformation() {
		this.reservedWords = new HashSet<>(Arrays.asList("var", "const", "typedef", "struct", "extends", "procedure",
				"function", "start", "return", "if", "else", "then", "while", "read", "print", "int", "real", "boolean",
				"string", "true", "false", "global", "local"));
		this.arithmeticOperators = new HashSet<>(Arrays.asList("+", "-", "*", "/", "++", "--"));
		this.relationalOperators = new HashSet<>(Arrays.asList("!=", "==", "<", "<=", ">", ">=", "="));
		this.logicalOperators = new HashSet<>(Arrays.asList("!", "&&", "||"));
		this.delimiters = new HashSet<>(Arrays.asList(";", ",", "(", ")", "[", "]", "{", "}", "."));
		this.commentDelimiters = new HashMap<String, Byte>();
		this.commentDelimiters.put("//", (byte) 1);
		this.commentDelimiters.put("/*", (byte) 2);
		this.commentDelimiters.put("*/", (byte) 3);
		this.closeComment = "*/";
		this.openCommnet = "/*";
	}

	public static TokenInformation getInstance() {
		if (instance == null) {
			instance = new TokenInformation();
		}
		return instance;
	}

	public HashSet<String> getReservedWords() {
		return this.reservedWords;
	}

	public HashSet<String> getArithmeticOperators() {
		return this.arithmeticOperators;
	}

	public HashSet<String> getRelationalOperators() {
		return this.relationalOperators;
	}

	public HashSet<String> getLogicalOperators() {
		return this.logicalOperators;
	}

	public HashSet<String> getDelimiters() {
		return this.delimiters;
	}

	public HashMap<String, Byte> getCommentDelimiters() {
		return this.commentDelimiters;
	}
	
	public HashMap<String, Set<String>> getSymbols() {
		HashMap<String, Set<String>> tempHash = new HashMap<String, Set<String>>();
		tempHash.put("reserved", this.reservedWords);
		tempHash.put("arithmeticOperators", this.arithmeticOperators);
		tempHash.put("relationalOperators", this.relationalOperators);
		tempHash.put("logicalOperators", this.logicalOperators);
		tempHash.put("delimiters", this.delimiters);
		tempHash.put("comment", new HashSet<>(Arrays.asList("//", "/*", "*/")));
		return tempHash;
	}

}
