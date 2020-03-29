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

public class SemanticAnalyzer extends RecursiveCall {

	private static SemanticAnalyzer instance;
	public static List<String> errors;
	public List<Token> id;
	public HashMap<String, HashMap<String, HashMap<String, Token>>> procedimentos;
	public HashMap<String, HashMap<String, HashMap<String, Token>>> funcoes;
	public HashMap<String, HashMap<String, HashMap<String, Token>>> blocos;
	public HashMap<String, Token> varGlobal;
	SemanticAnalyzer() {
		//super();
		this.errors = new ArrayList<>();
		this.id = new ArrayList<>();
		this.procedimentos = new HashMap<>();
		this.funcoes = new HashMap<>();
		this.blocos = new HashMap<>();
		this.varGlobal = new HashMap<>();
	}
	
	// Verificação Semantica de nomes iguais de funções
	public void funtionsEqualNames(Token tokens) {
		int line = tokens.getLine() + 1;
		if (tokens != null &&  !this.funcoes.containsKey(tokens.getLexeme())) {
			HashMap <String, HashMap<String, Token>> variaveis = new HashMap<>();
			HashMap <String, Token> varLocal = new HashMap<>();
			HashMap <String, Token> varEscopo = new HashMap<>();
			this.funcoes.put(tokens.getLexeme(), variaveis);
			this.funcoes.get(tokens.getLexeme()).put("varLocal", varLocal);
			this.funcoes.get(tokens.getLexeme()).put("varEscopo", varEscopo);	
			// Verifica se adicionou hashMaps corretamente
			// System.out.println(this.funcoes.get(tokens.getLexeme()).containsKey("varEscopo"));
		} else {
			errors.add("Linha: " + line +"	|	Ja houve um declaracao de funcao com o nome: " + tokens.getLexeme());
		}
	}
	
	public void startOnly(Token tokens) {
		int line = tokens.getLine() + 1;
		if (tokens != null &&  !this.blocos.containsKey(tokens.getLexeme())) {
			HashMap <String, HashMap<String, Token>> variaveis = new HashMap<>();
			HashMap <String, Token> varLocal = new HashMap<>();
			HashMap <String, Token> varEscopo = new HashMap<>();
			this.blocos.put(tokens.getLexeme(), variaveis);
			this.blocos.get(tokens.getLexeme()).put("varLocal", varLocal);
			this.blocos.get(tokens.getLexeme()).put("varEscopo", varEscopo);	
			// Verifica se adicionou hashMaps corretamente
			// System.out.println(this.blocos.get(tokens.getLexeme()).containsKey("varEscopo"));
		} else {
			errors.add("Linha: " + line +"	|	Já houve declaração do bloco start");
		}
	}
	
	// OK
	// Verificação Semantica de nomes iguais de procedimentos
	public void procedureEqualNames(Token tokens) {
		int line = tokens.getLine() + 1;
		if (tokens != null &&  !this.procedimentos.containsKey(tokens.getLexeme())) {
			HashMap <String, HashMap<String, Token>> variaveis = new HashMap<>();
			HashMap <String, Token> varLocal = new HashMap<>();
			HashMap <String, Token> varEscopo = new HashMap<>();
			this.procedimentos.put(tokens.getLexeme(), variaveis);
			this.procedimentos.get(tokens.getLexeme()).put("varLocal", varLocal);
			this.procedimentos.get(tokens.getLexeme()).put("varEscopo", varEscopo);	
			// Verifica se adicionou hashMaps corretamente
			// System.out.println(this.procedimentos.get(tokens.getLexeme()).containsKey("varEscopo"));
		} else {
			errors.add("Linha: " + line +"	|	Ja houve um declaracao de procedimento com o nome: " + tokens.getLexeme());
		}
	}
	
	public void globalVarEqualNames(Token tokens) {
		int line = tokens.getLine() + 1;
		if (tokens != null &&  !this.varGlobal.containsKey(tokens.getLexeme())) {
			this.varGlobal.put(tokens.getLexeme(), tokens);
			// Verifica se adicionou hashMaps corretamente
			// System.out.println(this.varGlobal.containsKey(tokens.getLexeme()));
		} else {
			errors.add("Linha: " + line +"	|	Já houve declaração de variavel global com o nome: " + tokens.getLexeme());
		}
	}

	public static List<String> getErros() {
		return errors;
	}
	
	public List<Token> getId() {
		return id;
	}
}