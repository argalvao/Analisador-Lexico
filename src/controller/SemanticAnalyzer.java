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
	public Token aux;
	public HashMap<String, HashMap<String, HashMap<String, Token>>> procedimentos;
	public HashMap<String, HashMap<String, HashMap<String, Token>>> funcoes;
	public HashMap<String, HashMap<String, HashMap<String, Token>>> blocos;
	public HashMap<String, Token> varGlobal;
	public HashMap<String, Token> varConst;
	
	SemanticAnalyzer() {
		//super();
		this.errors = new ArrayList<>();
		this.id = new ArrayList<>();
		this.aux = new Token(null, null, 0, null, null);
		this.procedimentos = new HashMap<>();
		this.funcoes = new HashMap<>();
		this.blocos = new HashMap<>();
		this.varGlobal = new HashMap<>();
		this.varConst = new HashMap<>();
		
	}
	
	// Adiciona parametros e verifica replica de variaveis, em funcoes e procedimentos
	public void fuctionsProcedureAddPara(Token tokens, String bloco, String nomeBloco, int size) {
		int line = tokens.getLine() + 1;
		String i = Integer.toString(size);
		//System.out.println(i+"    "+nomeBloco);
		if(tokens != null && bloco.equals("function") && !this.funcoes.get(nomeBloco).get("parametros").containsKey(i)){
			this.funcoes.get(nomeBloco).get("parametros").put(i, tokens);
		} else if(tokens != null && bloco.equals("procedure") && !this.procedimentos.get(nomeBloco).get("parametros").containsKey(i)){
			this.procedimentos.get(nomeBloco).get("parametros").put(i, tokens);
		} else {
			errors.add("Linha: " + line +"	|	Ja houve parametro em "+bloco+" com o nome: " + tokens.getLexeme());
		}
	}
	
	// Verificacao Semantica de nomes iguais de funcoes
	public void funtionsEqualNames(Token tokens) {
		int line = tokens.getLine() + 1;
		if (tokens != null &&  !this.funcoes.containsKey(tokens.getLexeme()) && !this.procedimentos.containsKey(tokens.getLexeme())) {
			HashMap <String, HashMap<String, Token>> variaveis = new HashMap<>();
			HashMap <String, Token> varLocal = new HashMap<>();
			HashMap <String, Token> varEscopo = new HashMap<>();
			HashMap <String, Token> parametros = new HashMap<>();
			HashMap <String, Token> tipo = new HashMap<>();
			this.funcoes.put(tokens.getLexeme(), variaveis);
			this.funcoes.get(tokens.getLexeme()).put("varLocal", varLocal);
			this.funcoes.get(tokens.getLexeme()).put("varEscopo", varEscopo);
			this.funcoes.get(tokens.getLexeme()).put("parametros", parametros);	
			this.funcoes.get(tokens.getLexeme()).put("tipo", tipo);	
			this.funcoes.get(tokens.getLexeme()).get("tipo").put(tokens.getTipoId(),tokens);
			// Verifica se adicionou hashMaps corretamente
			// System.out.println(this.funcoes.get(tokens.getLexeme()).containsKey("varEscopo"));
		} else {
			errors.add("Linha: " + line +"	|	Ja houve um declaracao de funcao com o nome: " + tokens.getLexeme());
		}
	}
	
	// Verificacao Semantica de multipla declaracao do start
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
	
	// Verificacao Semantica da declaracao do struct com nomes iguais
	public void structOnly(Token tokens) {
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
			errors.add("Linha: " + line +"	|	Ja houve um declaracao de struct com o nome: " + tokens.getLexeme());
		}
	}
	
	// Verificacao Semantica da declaracao do struct exteds com nomes iguais a struct pai
	public void structExtendsOnly(Token tokens, String extend) {
		int line = tokens.getLine() + 1;
		if(tokens != null &&  this.blocos.containsKey(extend)) {
			if (tokens != null && !this.blocos.get(extend).get("varLocal").containsKey(tokens.getLexeme())) {
				this.blocos.get(extend).get("varLocal").put(tokens.getLexeme(), tokens);
			} else {
				errors.add("Linha: " + line +"	|	Não pode sobrescrita de variavel na struct extends com o nome: " + tokens.getLexeme());
			}	
		} else {
			errors.add("Linha: " + line +"	|	Não houve um declaracao de struct com o nome: " + tokens.getLexeme());
		}
	}
		
	// Verifica se existe struct a ser usada pelo extends
	public boolean verificStructExtends(Token tokens) {
		int line = tokens.getLine() + 1;
		if (tokens != null &&  this.blocos.containsKey(tokens.getLexeme())) {
			return true;
		} else {
			errors.add("Linha: " + line +"	|	Não existe struct com o nome : " + tokens.getLexeme());
		}
		return false;
	}
		
		
	// Verifica se o tipo ID, existe apenas se a struct foi declarada
	public boolean verificStructExtendsType(Token tokens) {
		int line = tokens.getLine() + 1;
		if (tokens != null &&  this.blocos.containsKey(tokens.getLexeme())) {
			return true;
		} else {
			errors.add("Linha: " + line +"	|	Não existe struct com o nome : " + tokens.getLexeme());
		}
		return false;
	}
	// OK
	// Verificacaoo Semantica de nomes iguais de procedimentos
	public void procedureEqualNames(Token tokens) {
		int line = tokens.getLine() + 1;
		if (tokens != null &&  !this.procedimentos.containsKey(tokens.getLexeme()) && !this.funcoes.containsKey(tokens.getLexeme())) {
			HashMap <String, HashMap<String, Token>> variaveis = new HashMap<>();
			HashMap <String, Token> varLocal = new HashMap<>();
			HashMap <String, Token> varEscopo = new HashMap<>();
			HashMap <String, Token> parametros = new HashMap<>();
			this.procedimentos.put(tokens.getLexeme(), variaveis);
			this.procedimentos.get(tokens.getLexeme()).put("varLocal", varLocal);
			this.procedimentos.get(tokens.getLexeme()).put("varEscopo", varEscopo);
			this.procedimentos.get(tokens.getLexeme()).put("parametros", parametros);	
			// Verifica se adicionou hashMaps corretamente
			// System.out.println(this.procedimentos.get(tokens.getLexeme()).containsKey("varEscopo"));
		} else {
			errors.add("Linha: " + line +"	|	Ja houve um declaracao de procedimento com o nome: " + tokens.getLexeme());
		}
	}
	// Verificacaoo Semantica da declaracaoo de variaveis iguais no bloco struct
	public void structVarEqualNames(Token tokens, String nomeBloco) {
		int line = tokens.getLine() + 1;
		if (tokens != null && !this.blocos.get(nomeBloco).get("varLocal").containsKey(tokens.getLexeme())) {
			this.blocos.get(nomeBloco).get("varLocal").put(tokens.getLexeme(), tokens);
			// Verifica se adicionou hashMaps corretamente
			// System.out.println(this.varConst.containsKey(tokens.getLexeme()));
		} else {
			errors.add("Linha: " + line +"	|	Ja houve declaracao de variavel na struct com o nome: " + tokens.getLexeme());
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

	// Verificacaoo Semantica da declaracaoo de variaveis globais
	public boolean verificGlobalVar(Token tokens, boolean global) {
		int line = tokens.getLine() + 1;
		if (tokens != null &&  global && this.varGlobal.containsKey(tokens.getLexeme())) {
			return true;
		} else {
			errors.add("Linha: " + line +"	|	Ja houve declaracao de variavel global com o nome: " + tokens.getLexeme());
		}
		return false;
	}
		
	// Verifica se existe variaveis com nomes iguais no bloco const
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
	
	// olhar, usar de 0 a size como keys dos parametros das funcoes
	// Verifica se existem funcoes para serem acessada, comparar tambem o tamanho dos parametros,e compara mesma variavel e tipos no parametro
	public boolean verificFuncProcDeclaration(Token tokens, String nomeFuncao, String bloco, String nomeBloco, int sizeParam, List<Token> list) {
		int line = tokens.getLine() + 1;

		//System.out.println(this.funcoes.get(nomeFuncao).get("parametros").size() +"  "+ sizeParam +"  "+nomeFuncao);
		if (tokens != null && this.funcoes.containsKey(nomeFuncao) 
			&& this.funcoes.get(nomeFuncao).containsKey("tipo") 
			&& this.funcoes.get(nomeFuncao).get("parametros").size() == sizeParam) {
			int i = 0;
			
			while(sizeParam != i) {
				Token k = list.get(i);
				String x = Integer.toString(i);
				if(k != null && this.blocos.get("start").get("varLocal").containsKey(k.getLexeme())) {
					k = this.blocos.get("start").get("varLocal").get(k.getLexeme());
					// Verifica parametros com mesmo tipo e nome
					if( this.funcoes.get(nomeFuncao).get("parametros").containsKey(x)
						&& this.funcoes.get(nomeFuncao).get("parametros").get(x).getTipoId().equals(k.getTipoId())) {
						//System.out.println(nomeFuncao);
					}else {
						errors.add("Linha: " + line +"	|	A variavel : " + k.getLexeme() + ", nao tem o mesmo tipo da variavel do parametro da funcao: "+nomeFuncao);
					}
				} else {
					errors.add("Linha: " + line +"	|	A variavel : " + k.getLexeme() + ", nao foi declarada localmente.");
				}
				i++;
			}
			return true;
		} else if (tokens != null && this.procedimentos.containsKey(nomeFuncao) 
				   && this.procedimentos.get(nomeFuncao).get("parametros").size() == sizeParam) {
			int i = 0;
			
			while(sizeParam != i) {
				Token k = list.get(i);
				String x = Integer.toString(i);
				if(k != null && this.blocos.get("start").get("varLocal").containsKey(k.getLexeme())) {
					k = this.blocos.get("start").get("varLocal").get(k.getLexeme());
					// Verifica parametros com mesmo tipo e nome
					if(this.procedimentos.get(nomeFuncao).get("parametros").containsKey(x) 
						&& this.procedimentos.get(nomeFuncao).get("parametros").get(x).getTipoId().equals(k.getTipoId())) {
						//System.out.println(nomeFuncao);
					}else {
						errors.add("Linha: " + line +"	|	A variavel : " + k.getLexeme() + ", nao tem o mesmo tipo da variavel do parametro da procedimento: "+nomeFuncao);
					}
				} else {
					errors.add("Linha: " + line +"	|	A variavel : " + k.getLexeme() + ", nao foi declarada localmente.");
				}
				i++;
			}
			
			return true;
		} else {
			errors.add("Linha: " + line +"	|	Essa funcao/procedimento com nome: " + nomeFuncao + ", nao foi declarada ou não apresenta numero igual de parametros.");
		}
		return false;
	}
	
	// Verifica se variaveis ja foram declaradas em fuction, start, procedure, globais e constantes.
	public boolean verificVarDeclaration(Token tokens, String bloco, String nomeBloco) {
		int line = tokens.getLine() + 1;
		if (tokens != null && bloco.equals("start") && this.blocos.get("start").get("varLocal").containsKey(tokens.getLexeme())) {
			//System.out.println(tokens.getLexeme());
			return true;
		}else if (tokens != null && bloco.equals("function") && this.funcoes.get(nomeBloco).get("varLocal").containsKey(tokens.getLexeme())) {
			return true;
		} else if (tokens != null && bloco.equals("procedure") && this.procedimentos.get(nomeBloco).get("varLocal").containsKey(tokens.getLexeme())) {
			return true;
		} else if(tokens != null &&  this.varGlobal.containsKey(tokens.getLexeme())) {
			return true;
		} else if (tokens != null &&  this.varConst.containsKey(tokens.getLexeme())){
			return true;
		} else {
			errors.add("Linha: " + line +"	|	Essa variavel com nome: " + tokens.getLexeme() + ", nao foi declarada no bloco var.");
		}
		return false;
	}
	
	public boolean verificTipoVarConstValor(Token tokens, String bloco, String tipoBloco, String nomeBloco) {
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
	
	// Verifica se o tipo da variavel corresponde ao tipo do valor atribuido
	public boolean verificConstVarTypeValor(Token tokens, String tipoValor,String bloco, String nomeBloco) {
		int line = tokens.getLine() + 1;
		if (tokens != null && bloco.equals("procedure") && this.procedimentos.get(nomeBloco).get("varLocal").containsKey(tokens.getLexeme())) {
			Token k = this.procedimentos.get(nomeBloco).get("varLocal").get(tokens.getLexeme());
			if(k.getTipoId().equals(tipoValor)) {
				return true;
			} else {
				errors.add("Linha: " + line +"	|	A variavel com nome: " + tokens.getLexeme() + ", nao tem o mesmo tipo do valor atribuido");
			}
		} else if (tokens != null && bloco.equals("function") && this.funcoes.get(nomeBloco).get("varLocal").containsKey(tokens.getLexeme())) {
			Token k = this.funcoes.get(nomeBloco).get("varLocal").get(tokens.getLexeme());
			if(k.getTipoId().equals(tipoValor)) {
				return true;
			} else {
				errors.add("Linha: " + line +"	|	A variavel com nome: " + tokens.getLexeme() + ", nao tem o mesmo tipo do valor atribuido");
			}
		} else if(tokens != null && bloco.equals("start") && this.blocos.get("start").get("varLocal").containsKey(tokens.getLexeme())) {
			Token k = this.blocos.get("start").get("varLocal").get(tokens.getLexeme());
			if(k.getTipoId().equals(tipoValor)) {
				return true;
			} else {
				errors.add("Linha: " + line +"	|	A variavel com nome: " + tokens.getLexeme() + ", nao tem o mesmo tipo do valor atribuido");
			}
		} else if (tokens != null && this.varGlobal.containsKey(tokens.getLexeme())){
			Token k = this.varGlobal.get(tokens.getLexeme());
			if(k.getTipoId().equals(tipoValor)) {
				return true;
			} else {
				errors.add("Linha: " + line +"	|	A variavel com nome: " + tokens.getLexeme() + ", nao tem o mesmo tipo do valor atribuido");
			}
		} else if (tokens != null &&  this.varConst.containsKey(tokens.getLexeme())) {
			Token k = this.varConst.get(tokens.getLexeme());
			if(k.getTipoId().equals(tipoValor)) {
				return true;
			} else {
				errors.add("Linha: " + line +"	|	A variavel com nome: " + tokens.getLexeme() + ", nao tem o mesmo tipo do valor atribuido");
			}
		}else {
			errors.add("Linha: " + line +"	|	Não existe constante ou variavel global/local com o nome : " + tokens.getLexeme());
		}
		return false;
	}

	// Verifica se o tipo da variavel e valor são positivos para ser valido como valor do vetor
	public boolean verificTypeVetor(Token tokens, String bloco, String nomeBloco) {
		int line = tokens.getLine() + 1;
		if (tokens != null && bloco.equals("procedure") && this.procedimentos.get(nomeBloco).get("varLocal").containsKey(tokens.getLexeme())) {
			Token k = this.procedimentos.get(nomeBloco).get("varLocal").get(tokens.getLexeme());
			if(k.getTipoId().equals("int") && k.getValorId().matches("[+]?[0-9][0-9]*")) {
				return true;
			}else {
				errors.add("Linha: " + line +"	|	O valor da variavel com nome: " + tokens.getLexeme() + ", do vetor nao tem valor positivo atribuido");
			}
		}else if (tokens != null && bloco.equals("function") && this.funcoes.get(nomeBloco).get("varLocal").containsKey(tokens.getLexeme())) {
			Token k = this.funcoes.get(nomeBloco).get("varLocal").get(tokens.getLexeme());
			if(k.getTipoId().equals("int") && k.getValorId().matches("[+]?[0-9][0-9]*")) {
				return true;
			}else {
				errors.add("Linha: " + line +"	|	O valor da variavel com nome: " + tokens.getLexeme() + ", do vetor nao tem valor positivo atribuido");
			}
		}else if(tokens != null && bloco.equals("start") && this.blocos.get("start").get("varLocal").containsKey(tokens.getLexeme())) {
			Token k = this.blocos.get("start").get("varLocal").get(tokens.getLexeme());
			if(k.getTipoId().equals("int") && k.getValorId().matches("[+]?[0-9][0-9]*")) {
				return true;
			}else {
				errors.add("Linha: " + line +"	|	O valor da variavel com nome: " + tokens.getLexeme() + ", do vetor nao tem valor positivo atribuido");
			}
		}else if (tokens != null && this.varGlobal.containsKey(tokens.getLexeme())){
			Token k = this.varGlobal.get(tokens.getLexeme());
			if(k.getTipoId().equals("int") && k.getValorId().matches("[+]?[0-9][0-9]*")) {
				return true;
			}else {
				errors.add("Linha: " + line +"	|	O valor da variavel com nome: " + tokens.getLexeme() + ", do vetor nao tem valor positivo atribuido");
			}
		}else if (tokens != null &&  this.varConst.containsKey(tokens.getLexeme())) {
			Token k = this.varConst.get(tokens.getLexeme());
			if(k.getTipoId().equals("int") && k.getValorId().matches("[+]?[0-9][0-9]*")) {
				return true;
			}else {
				errors.add("Linha: " + line +"	|	O valor da variavel com nome: " + tokens.getLexeme() + ", do vetor nao tem valor positivo atribuido");
			}
		} else {
			errors.add("Linha: " + line +"	|	Não existe constante ou variavel global, ou local com o nome : " + tokens.getLexeme());
		}
		return false;
	}
	
	// Verifica se o retorno da funcao tem mesmo tipo que a variavel atribuida.
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
	
	// Verifica se o retorno da funcao tem mesmo tipo que a variavel atribuida no corpo;
	public boolean verificTipoVarAtriFunction(Token tokens, String bloco, String tipoBloco, String nomeFuncao) {
		int line = tokens.getLine() + 1;
		
		if (tokens != null && bloco.equals("start") && this.blocos.get("start").get("varLocal").containsKey(tokens.getLexeme())) {
			Token k = this.blocos.get("start").get("varLocal").get(tokens.getLexeme());		
			//System.out.println(k.getTipoId());
		
			if (k != null && this.funcoes.get(nomeFuncao).get("tipo").containsKey(k.getTipoId())) {
				return true;
			}else {
				errors.add("Linha: " + line +"	|	Esse variavel de retorno com nome: " + tokens.getLexeme() + ", nao tem mesmo tipo que o retorno da funcao: "+ nomeFuncao);
			}
			return true;
		} else if (tokens != null && this.varGlobal.containsKey(tokens.getLexeme())){
			Token k = this.varGlobal.get(tokens.getLexeme());
			//System.out.println(nomeFuncao);
			if (tokens != null && this.funcoes.get(nomeFuncao).get("tipo").containsKey(k.getTipoId())) {
				return true;
			}else {
				errors.add("Linha: " + line +"	|	Essa variavel global de retorno com nome: " + tokens.getLexeme() + ", nao tem mesmo tipo que o retorno da funcao: "+ nomeFuncao);
			}
		} else {
			errors.add("Linha: " + line +"	|	Esse variavel de retorno com nome: " + tokens.getLexeme() + ", nao foi declarada, ou não eh funcao");
		}
		return false;
	}
	// Verifica se o retorno da funcao tem mesmo tipo que a variavel atribuida no corpo;
	public boolean verificFunction(Token tokens, String nomeFuncao) {
		int line = tokens.getLine() + 1;
		if(tokens != null && this.funcoes.containsKey(nomeFuncao)) {
			return true;
		}
		return false;
	}
	// Verificando tipo de variaveis com o operador de negacao apenas quando for booleana.
	public boolean verificTipoVarNegative(Token tokens, String bloco, String tipoBloco, String nomeBloco) {
		int line = tokens.getLine() + 1;
		if (tokens != null && bloco.equals("procedure") && this.procedimentos.get(nomeBloco).get("varLocal").containsKey(tokens.getLexeme())) {
			Token k = this.procedimentos.get(nomeBloco).get("varLocal").get(tokens.getLexeme());
			if(k.getTipoId().equals("boolean")) {
				return true;
			}else {
				errors.add("Linha: " + line +"	|	Esse variavel com nome: " + tokens.getLexeme() + ", nao é booleana.");
			}
		}else if (tokens != null && bloco.equals("function") && this.funcoes.get(nomeBloco).get("varLocal").containsKey(tokens.getLexeme())) {
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
	
	//Verifica se existe variaveis locais ja declaradas
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
	
		
	// Verifica a existencia da variavel utilizada pelo read
	public boolean readVarExists(Token tokens, String bloco, String nomeStruct) {
		int line = tokens.getLine() + 1;
		// Pode ser dentro de funcao/procedimento
		if (tokens != null && bloco.equals("start") && (this.blocos.containsKey(tokens.getLexeme()) || this.blocos.get(bloco).get("varLocal").containsKey(tokens.getLexeme()))) {
			if(!this.blocos.containsKey(tokens.getLexeme()) && !this.blocos.get(bloco).get("varLocal").containsKey(tokens.getLexeme())) {
				errors.add("Linha: " + line +"	|	Variavel nao declarada com o nome: " + tokens.getLexeme());	
			}else {
				return true;
			}
			return false;	
		} else {
			errors.add("Linha: " + line +"	|	Variavel/Struct nao declarada com o nome: " + tokens.getLexeme());
		}
		return false;
	}
	
	public boolean readVarStructExists(Token tokens, String bloco, String nomeStruct) {
		int line = tokens.getLine() + 1;
		// Pode ser dentro de funcao/procedimento
		
		if (tokens != null && this.blocos.get(bloco).get("varLocal").containsKey(nomeStruct) && this.blocos.containsKey(nomeStruct) 
				&& this.blocos.get(nomeStruct).get("varLocal").containsKey(tokens.getLexeme())){
			return true;
		} else {
			errors.add("Linha: " + line +"	|	Variavel/Struct nao declarada com o nome: " + tokens.getLexeme());
		}
		return false;
	}
	
	public boolean verificLocalVar(Token tokens, String bloco, String nomeBloco) {
		int line = tokens.getLine() + 1;
		if (tokens != null && bloco.equals("start") && this.blocos.get("start").get("varLocal").containsKey(tokens.getLexeme())) {
			return true;
		}else if (tokens != null && bloco.equals("function") && this.funcoes.get(nomeBloco).get("varLocal").containsKey(tokens.getLexeme())) {
			return true;
		} else if (tokens != null && bloco.equals("procedure") && this.procedimentos.get(nomeBloco).get("varLocal").containsKey(tokens.getLexeme())) {
			return true;
		} else {
			errors.add("Linha: " + line +"	|	Essa variavel com nome: " + tokens.getLexeme() + ", nao foi declarada no bloco var.");
		}
		return false;
	}
	
	
	public static List<String> getErros() {
		return errors;
	}
	
	public List<Token> getId() {
		return id;
	}
}