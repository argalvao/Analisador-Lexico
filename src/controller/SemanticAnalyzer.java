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
import semantic_models.Symbol;
public class SemanticAnalyzer extends RecursiveCall {

	private static SemanticAnalyzer instance;
	public static List<String> errors;
	public List<Token> id;
	public HashMap<String, HashMap<String, HashMap<String, Token>>> procedimentos;
	public HashMap<String, HashMap<String, HashMap<String, Token>>> funcoes;
	public HashMap<String, HashMap<String, HashMap<String, Token>>> blocos;
	public HashMap<String, Token> varGlobal;
	public HashMap<String, Token> varConst;
	SemanticAnalyzer() {
		//super();
		this.errors = new ArrayList<>();
		this.id = new ArrayList<>();
		this.procedimentos = new HashMap<>();
		this.funcoes = new HashMap<>();
		this.blocos = new HashMap<>();
		this.varGlobal = new HashMap<>();
		this.varConst = new HashMap<>();
	}
	
	// Verificacao Semantica de nomes iguais de funcoes
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
	
	// Verificacao Semantica da declaracao do start
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
			errors.add("Linha: " + line +"	|	Ja houve declaracao do bloco start");
		}
	}
	
	// OK
	// Verificacaoo Semantica de nomes iguais de procedimentos
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
	
	// Verificacaoo Semantica da declaracaoo de variaveis globais
	public void globalVarEqualNames(Token tokens) {
		int line = tokens.getLine() + 1;
		if (tokens != null &&  !this.varGlobal.containsKey(tokens.getLexeme())) {
			this.varGlobal.put(tokens.getLexeme(), tokens);
			// Verifica se adicionou hashMaps corretamente
			// System.out.println(this.varGlobal.containsKey(tokens.getLexeme()));
		} else {
			errors.add("Linha: " + line +"	|	Ja houve declaracao de variavel global com o nome: " + tokens.getLexeme());
		}
	}

	
	public void constVarEqualNames(Token tokens) {
		int line = tokens.getLine() + 1;
		if (tokens != null &&  !this.varConst.containsKey(tokens.getLexeme())) {
			this.varConst.put(tokens.getLexeme(), tokens);
			// Verifica se adicionou hashMaps corretamente
			// System.out.println(this.varConst.containsKey(tokens.getLexeme()));
		} else {
			errors.add("Linha: " + line +"	|	Ja houve declaracao de constante com o nome: " + tokens.getLexeme());
		}
	}
	
	
	public boolean verificVarDeclaration(Token tokens, String bloco, String nomeBloco) {
		int line = tokens.getLine() + 1;
		if (tokens != null && bloco.equals("start") && this.blocos.get("start").get("varLocal").containsKey(tokens.getLexeme())) {
			return true;
		}else if (tokens != null && bloco.equals("function") && this.funcoes.get(nomeBloco).get("varLocal").containsKey(tokens.getLexeme())) {
			return true;
		} else if (tokens != null && bloco.equals("procedure") && this.procedimentos.get(nomeBloco).get("varLocal").containsKey(tokens.getLexeme())) {
			return true;
		} else if(tokens != null &&  !this.varGlobal.containsKey(tokens.getLexeme())) {
			return true;
		} else if (tokens != null &&  !this.varConst.containsKey(tokens.getLexeme())){
			return true;
		} else {
			errors.add("Linha: " + line +"	|	Essa variavel com nome: " + tokens.getLexeme() + ", nao foi declarada no bloco var.");
		}
		return false;
	}
	
	
	public boolean verificTipoVarReturn(Token tokens, String bloco, String tipoBloco, String nomeBloco) {
		int line = tokens.getLine() + 1;
		if (tokens != null && bloco.equals("function") && this.funcoes.get(nomeBloco).get("varLocal").containsKey(tokens.getLexeme())) {
			Token k = this.funcoes.get(nomeBloco).get("varLocal").get(tokens.getLexeme());
			if(k.getTipoId().equals(tipoBloco)) {
				return true;
			}else {
				errors.add("Linha: " + line +"	|	Esse variavel de retorno com nome: " + tokens.getLexeme() + ", nao tem o mesmo tipo da funcao de nome: "+ nomeBloco);
			}
		}else {
			errors.add("Linha: " + line +"	|	Esse variavel de retorno com nome: " + tokens.getLexeme() + ", nao foi declarada");
		}
		return false;
	}
	
	// Verificando tipo de variaveis com o operador de negacao apenas quando for booleana.
	public boolean verificTipoVarNegative(Token tokens, String bloco, String tipoBloco, String nomeBloco) {
		int line = tokens.getLine() + 1;
		if (tokens != null && bloco.equals("function") && this.funcoes.get(nomeBloco).get("varLocal").containsKey(tokens.getLexeme())) {
			Token k = this.funcoes.get(nomeBloco).get("varLocal").get(tokens.getLexeme());
			if(k.getTipoId().equals("boolean")) {
				return true;
			}else {
				errors.add("Linha: " + line +"	|	Esse variavel com nome: " + tokens.getLexeme() + ", nao é booleana.");
			}
		}else if(tokens != null && bloco.equals("start") && this.blocos.get("start").get("varLocal").containsKey(tokens.getLexeme())) {
			Token k = this.blocos.get("start").get("varLocal").get(tokens.getLexeme());
			if(k.getTipoId().equals("boolean")) {
				return true;
			}else {
				errors.add("Linha: " + line +"	|	Esse variavel com nome: " + tokens.getLexeme() + ", nao é booleana.");
			}
		}else{
			errors.add("Linha: " + line +"	|	Esse variavel com nome: " + tokens.getLexeme() + ", nao foi declarada");
		}
		return false;
	}
	
	public void localVarEqualNames(Token tokens, String bloco, String nomeBloco) {
		int line = tokens.getLine() + 1;
		if (tokens != null && bloco.equals("start") && !this.blocos.get("start").get("varLocal").containsKey(tokens.getLexeme())) {
			this.blocos.get("start").get("varLocal").put(tokens.getLexeme(), tokens);
		}else if (tokens != null && bloco.equals("function") && !this.funcoes.get(nomeBloco).get("varLocal").containsKey(tokens.getLexeme())) {
			this.funcoes.get(nomeBloco).get("varLocal").put(tokens.getLexeme(), tokens);
			// Verifica se adicionou hashMaps corretamente
			// System.out.println(this.varGlobal.containsKey(tokens.getLexeme()));
		} else if (tokens != null && bloco.equals("procedure") && !this.procedimentos.get(nomeBloco).get("varLocal").containsKey(tokens.getLexeme())) {
			this.procedimentos.get(nomeBloco).get("varLocal").put(tokens.getLexeme(), tokens);
		} else{ 
			errors.add("Linha: " + line +"	|	Ja houve declaracao de variavel local de nome: "+ tokens.getLexeme() +", no bloco de tipo: " + bloco);
		}
	}
	
	public static List<String> getErros() {
		return errors;
	}
	
	public List<Token> getId() {
		return id;
	}
}