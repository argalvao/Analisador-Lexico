package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;

//import javax.swing.plaf.synth.SynthSeparatorUI;
import controller.SynthaticAnalyzer;
import model.SynthaticNode;
import model.Token;
import model.TokenTypes;
import util.RecursiveCall;
import util.FirstFollow;

public class SemanticAnalyser extends RecursiveCall {

	private static SemanticAnalyser instance;
	public static List<String> errors;
	public List<Token> id;
	public HashMap<String, Token> procedimentos;
	public HashMap<String, Token> funcoes;
	SemanticAnalyser() {
		
		//super();
		this.errors = new ArrayList<>();
		this.id = new ArrayList<>();
		this.procedimentos = new HashMap<>();
		this.funcoes = new HashMap<>();
		

	}
	
	// Verifica��o Semantica de nomes iguais de fun��es
	public void funtionsEqualNames(Token tokens) {
		int line = tokens.getLine() + 1;
		if (tokens != null &&  !this.funcoes.containsKey(((Token) tokens).getLexeme())) {
			this.funcoes.put(tokens.getLexeme(), tokens);
		} else {
			errors.add("Linha: " + line +"	|	J� houve um declara��o de funcao com o nome: " + tokens.getLexeme());
		}
	}
	
	// Verifica��o Semantica de nomes iguais de procedimentos
	public void procedureEqualNames(Token tokens) {
		int line = tokens.getLine() + 1;
		if (tokens != null &&  !this.procedimentos.containsKey(tokens.getLexeme())) {
			this.procedimentos.put(tokens.getLexeme(), tokens);
		} else {
			errors.add("Linha: " + line +"	|	J� houve um declara��o de procedimento com o nome: " + tokens.getLexeme());
		}
	}
	


	public static List<String> getErros() {
		return errors;
	}
	
	public List<Token> getId() {
		return id;
	}
}