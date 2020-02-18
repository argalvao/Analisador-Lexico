    import java.util.ArrayList;
    import java.util.List;
    import java.util.Queue;
    
    import model.SynthaticNode;
    import model.Token;
    import model.TokenTypes;
    import util.ChainedCall;
    import util.FirstFollow;
    
    public class SynthaticAnalyzer extends ChainedCall {
    
        private static SynthaticAnalyzer instance;
        private List<String> errors;
    
        private SynthaticAnalyzer() {
            super();
            this.errors = new ArrayList<>();

			this.functions.put("<ExpressaoLR>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<ArgumentoLR2>", tokens.peek())) {
                    tokenMap.add(this.call("<ArgumentoLR2>", tokens).getTokenNode());
					tokenMap.add(this.call("<ExpressaoLR2>", tokens).getTokenNode());
					} else if(this.predict("<ArgumentoLR3>", tokens.peek())) {
                        tokenMap.add(this.call("<ArgumentoLR3>", tokens).getTokenNode());
					tokenMap.add(this.call("<OperadorRelacional>", tokens).getTokenNode());
					tokenMap.add(this.call("<ArgumentoLR>", tokens).getTokenNode());
					tokenMap.add(this.call("<ExpressaoLR3>", tokens).getTokenNode());
					}return tokenMap;
                    });

				this.functions.put("<Var3>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "}".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("<TipoVar>", tokens.peek())) {tokenMap.add(this.call("<TipoVar>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Start>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "start".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "(".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ ")".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "{".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Corpo>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ "}".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<ComandosReturn>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "return".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<CodigosRetornos>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Condicional>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "if".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "(".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<ExpressaoLogicaRelacional>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ ")".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "then".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "{".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Corpo>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ "}".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<CondEnd>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<IdentificadorAritmetico>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<Escopo>", tokens.peek())) {tokenMap.add(this.call("<Escopo>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Identificador2>", tokens).getTokenNode());
					tokenMap.add(this.call("<ExpressaoAritmetica2>", tokens).getTokenNode());
					} else if(this.predict("Id", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<IdentificadorAritmetico3>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Read1>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<IdentificadorSemFuncao>", tokens.peek())) {tokenMap.add(this.call("<IdentificadorSemFuncao>", tokens).getTokenNode());
					tokenMap.add(this.call("<AuxRead>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<ArgumentoLR2_1>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("true", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "true".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("false", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "false".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<IdStruct>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Struct2>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Para1>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ ",".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Parametro>", tokens).getTokenNode());
					} else if(this.predict("')'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ ")".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<F2>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Comandos>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<IdentificadorComandos>", tokens.peek())) {tokenMap.add(this.call("<IdentificadorComandos>", tokens).getTokenNode());
					} else if(this.predict("<Laco>", tokens.peek())) {tokenMap.add(this.call("<Laco>", tokens).getTokenNode());
					} else if(this.predict("<Read>", tokens.peek())) {tokenMap.add(this.call("<Read>", tokens).getTokenNode());
					} else if(this.predict("<Condicional>", tokens.peek())) {tokenMap.add(this.call("<Condicional>", tokens).getTokenNode());
					} else if(this.predict("<Print>", tokens.peek())) {tokenMap.add(this.call("<Print>", tokens).getTokenNode());
					} else if(this.predict("<ComandosReturn>", tokens.peek())) {tokenMap.add(this.call("<ComandosReturn>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<ReadFim>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ ")".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ ";".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<T>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<F>", tokens.peek())) {tokenMap.add(this.call("<F>", tokens).getTokenNode());
					tokenMap.add(this.call("<T2>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<ExpressaoLogicaRelacional>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<ExpressaoLR>", tokens.peek())) {tokenMap.add(this.call("<ExpressaoLR>", tokens).getTokenNode());
					} else if(this.predict("'('", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "(".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<ExpressaoLR>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ ")".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<ExpressaoLR3>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<VetorDeclaracao>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "[".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<ValorVetor>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ "]".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Matriz>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<OperadorRelacional>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ ">=".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("'=='", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "==".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("'<'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "<".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("'!='", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "!=".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("'>'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ ">".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("'<='", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "<=".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<ArgumentoLR2>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "!".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<ArgumentoLR2_1>", tokens).getTokenNode());
					} else if(this.predict("true", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "true".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("false", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "false".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<F2>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "{".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Corpo>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Inicio>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<Const>", tokens.peek())) {tokenMap.add(this.call("<Const>", tokens).getTokenNode());
					tokenMap.add(this.call("<Struct>", tokens).getTokenNode());
					tokenMap.add(this.call("<Var>", tokens).getTokenNode());
					tokenMap.add(this.call("<GeraFuncaoeProcedure>", tokens).getTokenNode());
					tokenMap.add(this.call("<Start>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<TipoConst>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<Tipo>", tokens.peek())) {tokenMap.add(this.call("<Tipo>", tokens).getTokenNode());
					tokenMap.add(this.call("<IdConst>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<ArgumentoLR3>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "String".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("<ExpressaoAritmetica>", tokens.peek())) {tokenMap.add(this.call("<ExpressaoAritmetica>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<ContListaParametros>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ ",".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<ListaParametros>", tokens).getTokenNode());
					} else if(this.predict("", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<Read>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "read".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "(".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Read1>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<IdentificadorComandos>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<IdentificadorSemFuncao>", tokens.peek())) {tokenMap.add(this.call("<IdentificadorSemFuncao>", tokens).getTokenNode());
					tokenMap.add(this.call("<IdentificadorComandos2>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ ";".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<ExpressaoAritmetica2>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "++".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<T2>", tokens).getTokenNode());
					tokenMap.add(this.call("<E2>", tokens).getTokenNode());
					} else if(this.predict("'--'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "--".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<T2>", tokens).getTokenNode());
					tokenMap.add(this.call("<E2>", tokens).getTokenNode());
					} else if(this.predict("<T2>", tokens.peek())) {tokenMap.add(this.call("<T2>", tokens).getTokenNode());
					tokenMap.add(this.call("<E2>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Const>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "const".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ " { ".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<TipoConst>", tokens).getTokenNode());
					} else if(this.predict("", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<Procedimento>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "procedure".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "(".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Parametro>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<IdentificadorAritmetico3>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<Identificador2>", tokens.peek())) {tokenMap.add(this.call("<Identificador2>", tokens).getTokenNode());
					tokenMap.add(this.call("<ExpressaoAritmetica2>", tokens).getTokenNode());
					} else if(this.predict("'('", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "(".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<ListaParametros>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ ")".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<T2>", tokens).getTokenNode());
					tokenMap.add(this.call("<E2>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<ListaParametros2>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<Identificador>", tokens.peek())) {tokenMap.add(this.call("<Identificador>", tokens).getTokenNode());
					} else if(this.predict("Numero", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "Numero".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("String", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "String".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<OperadorLogico>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "&&".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("'||'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "||".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<Identificador3>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<Identificador2>", tokens.peek())) {tokenMap.add(this.call("<Identificador2>", tokens).getTokenNode());
					} else if(this.predict("'('", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "(".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<ListaParametros>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ ")".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<Corpo>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<Var>", tokens.peek())) {tokenMap.add(this.call("<Var>", tokens).getTokenNode());
					tokenMap.add(this.call("<Corpo2>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ "}".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("'}'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "}".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<ExpressaoAritmetica>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<T>", tokens.peek())) {tokenMap.add(this.call("<T>", tokens).getTokenNode());
					tokenMap.add(this.call("<E2>", tokens).getTokenNode());
					} else if(this.predict("<IdentificadorAritmetico>", tokens.peek())) {tokenMap.add(this.call("<IdentificadorAritmetico>", tokens).getTokenNode());
					} else if(this.predict("'--'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "--".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<IdentificadorSemFuncao>", tokens).getTokenNode());
					tokenMap.add(this.call("<T2>", tokens).getTokenNode());
					tokenMap.add(this.call("<E2>", tokens).getTokenNode());
					} else if(this.predict("'++'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "++".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<IdentificadorSemFuncao>", tokens).getTokenNode());
					tokenMap.add(this.call("<T2>", tokens).getTokenNode());
					tokenMap.add(this.call("<E2>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<PrintFim>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ ")".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ ";".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<Funcao>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "function".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Tipo>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "(".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Parametro>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Print>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "print".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "(".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Print1>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Var2>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ ",".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<IdVar>", tokens).getTokenNode());
					} else if(this.predict("';'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ ";".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Var3>", tokens).getTokenNode());
					} else if(this.predict("<VetorDeclaracao>", tokens.peek())) {tokenMap.add(this.call("<VetorDeclaracao>", tokens).getTokenNode());
					tokenMap.add(this.call("<Var4>", tokens).getTokenNode());
					} else if(this.predict("'='", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "=".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Valor>", tokens).getTokenNode());
					tokenMap.add(this.call("<Var4>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Vetor2>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "[".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<IndiceVetor>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ "]".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<Corpo2>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<Comandos>", tokens.peek())) {tokenMap.add(this.call("<Comandos>", tokens).getTokenNode());
					tokenMap.add(this.call("<Corpo2>", tokens).getTokenNode());
					} else if(this.predict("", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<TipoVar>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<Tipo>", tokens.peek())) {tokenMap.add(this.call("<Tipo>", tokens).getTokenNode());
					tokenMap.add(this.call("<IdVar>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Print1>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "String".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<AuxPrint>", tokens).getTokenNode());
					} else if(this.predict("<Identificador>", tokens.peek())) {tokenMap.add(this.call("<Identificador>", tokens).getTokenNode());
					tokenMap.add(this.call("<AuxPrint>", tokens).getTokenNode());
					} else if(this.predict("Numero", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "Numero".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<AuxPrint>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<AuxPrint>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ ",".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Print1>", tokens).getTokenNode());
					} else if(this.predict("<PrintFim>", tokens.peek())) {tokenMap.add(this.call("<PrintFim>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<GeraFuncaoeProcedure>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<Funcao>", tokens.peek())) {tokenMap.add(this.call("<Funcao>", tokens).getTokenNode());
					tokenMap.add(this.call("<GeraFuncaoeProcedure>", tokens).getTokenNode());
					} else if(this.predict("<Procedimento>", tokens.peek())) {tokenMap.add(this.call("<Procedimento>", tokens).getTokenNode());
					tokenMap.add(this.call("<GeraFuncaoeProcedure>", tokens).getTokenNode());
					} else if(this.predict("", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<Parametro>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<Tipo>", tokens.peek())) {tokenMap.add(this.call("<Tipo>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Para2>", tokens).getTokenNode());
					tokenMap.add(this.call("<Para1>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Laco>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "while".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "(".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<ExpressaoLogicaRelacional>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ ")".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "{".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Corpo>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ "}".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<E2>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "+".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<ExpressaoAritmetica>", tokens).getTokenNode());
					} else if(this.predict("'-'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "-".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<ExpressaoAritmetica>", tokens).getTokenNode());
					} else if(this.predict("", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<Const2>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ " , ".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<IdConst>", tokens).getTokenNode());
					} else if(this.predict("' ; '", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ " ; ".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Const3>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Const3>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ " } ".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("<TipoConst>", tokens.peek())) {tokenMap.add(this.call("<TipoConst>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<IdConst>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Valor>", tokens).getTokenNode());
					tokenMap.add(this.call("<Const2>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<IdentificadorComandos2_1>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<ExpressaoAritmetica>", tokens.peek())) {tokenMap.add(this.call("<ExpressaoAritmetica>", tokens).getTokenNode());
					} else if(this.predict("String", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "String".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("Boolean", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "Boolean".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<Para3>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "[".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "]".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<CodigosRetornos>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ ";".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("<ExpressaoAritmetica>", tokens.peek())) {tokenMap.add(this.call("<ExpressaoAritmetica>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ ";".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<TipoStruct>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<Tipo>", tokens.peek())) {tokenMap.add(this.call("<Tipo>", tokens).getTokenNode());
					tokenMap.add(this.call("<IdStruct>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Vetor>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "[".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<IndiceVetor>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ "]".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Vetor2>", tokens).getTokenNode());
					tokenMap.add(this.call("<Identificador4>", tokens).getTokenNode());
					} else if(this.predict("<Identificador4>", tokens.peek())) {tokenMap.add(this.call("<Identificador4>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Struct>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "typedef".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "struct".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Extends>", tokens).getTokenNode());
					tokenMap.add(this.call("<TipoStruct>", tokens).getTokenNode());
					tokenMap.add(this.call("<Struct>", tokens).getTokenNode());
					} else if(this.predict("", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<IndiceVetor>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "IntPos".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("<Identificador>", tokens.peek())) {tokenMap.add(this.call("<Identificador>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Identificador>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<Escopo>", tokens.peek())) {tokenMap.add(this.call("<Escopo>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Identificador2>", tokens).getTokenNode());
					} else if(this.predict("Id", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Identificador3>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Struct2>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ ",".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<IdStruct>", tokens).getTokenNode());
					} else if(this.predict("';'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ ";".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Struct3>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Matriz>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "[".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<ValorVetor>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ "]".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Var4>", tokens).getTokenNode());
					} else if(this.predict("<Var4>", tokens.peek())) {tokenMap.add(this.call("<Var4>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<IdentificadorComandos2>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "=".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<IdentificadorComandos2_1>", tokens).getTokenNode());
					} else if(this.predict("'('", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "(".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<ListaParametros>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ ")".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<ListaParametros>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<ListaParametros2>", tokens.peek())) {tokenMap.add(this.call("<ListaParametros2>", tokens).getTokenNode());
					tokenMap.add(this.call("<ContListaParametros>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Escopo>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "local".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ ".".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("'global'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "global".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ ".".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<AuxRead>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ ",".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Read1>", tokens).getTokenNode());
					} else if(this.predict("<ReadFim>", tokens.peek())) {tokenMap.add(this.call("<ReadFim>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<ValorVetor>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "IntPos".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("Id", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<Var4>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ ",".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<IdVar>", tokens).getTokenNode());
					} else if(this.predict("';'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ ";".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Var3>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<F>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "(".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<ExpressaoAritmetica>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ ")".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("Numero", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "Numero".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<ExpressaoLR3>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<OperadorLogico>", tokens.peek())) {tokenMap.add(this.call("<OperadorLogico>", tokens).getTokenNode());
					tokenMap.add(this.call("<ExpressaoLogicaRelacional>", tokens).getTokenNode());
					} else if(this.predict("", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<Var>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "var".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "{".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<TipoVar>", tokens).getTokenNode());
					} else if(this.predict("", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<Extends>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "extends".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "{".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("'{'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "{".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<Para2>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "[".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "]".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Para3>", tokens).getTokenNode());
					} else if(this.predict("", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<T2>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "*".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<ExpressaoAritmetica>", tokens).getTokenNode());
					} else if(this.predict("'/'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "/".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<ExpressaoAritmetica>", tokens).getTokenNode());
					} else if(this.predict("", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<IdVar>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Var2>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Identificador2>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ ".".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Vetor>", tokens).getTokenNode());
					} else if(this.predict("<Vetor>", tokens.peek())) {tokenMap.add(this.call("<Vetor>", tokens).getTokenNode());
					} else if(this.predict("", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<CondEnd>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "else".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "{".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Corpo>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ "}".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<Identificador4>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ ".".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Vetor>", tokens).getTokenNode());
					} else if(this.predict("", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<ExpressaoLR2>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<OperadorRelacional>", tokens.peek())) {tokenMap.add(this.call("<OperadorRelacional>", tokens).getTokenNode());
					tokenMap.add(this.call("<ArgumentoLR>", tokens).getTokenNode());
					tokenMap.add(this.call("<ExpressaoLR3>", tokens).getTokenNode());
					} else if(this.predict("<ExpressaoLR3>", tokens.peek())) {tokenMap.add(this.call("<ExpressaoLR3>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<ArgumentoLR>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<ArgumentoLR2>", tokens.peek())) {tokenMap.add(this.call("<ArgumentoLR2>", tokens).getTokenNode());
					} else if(this.predict("<ArgumentoLR3>", tokens.peek())) {tokenMap.add(this.call("<ArgumentoLR3>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<IdentificadorSemFuncao>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if(this.predict("<Escopo>", tokens.peek())) {tokenMap.add(this.call("<Escopo>", tokens).getTokenNode());
					if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Identificador2>", tokens).getTokenNode());
					} else if(this.predict("Id", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}tokenMap.add(this.call("<Identificador2>", tokens).getTokenNode());
					}return tokenMap;});

				this.functions.put("<Tipo>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "boolean".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("'string'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "string".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("'int'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "int".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("'real'", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "real".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("Id", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<Valor>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "Numero".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("String", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "String".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("Id", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "Id".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("Boolean", tokens.peek())) {if (token != null && /*token.getType() == TokenTypes. &&*/ "Boolean".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}}return tokenMap;});

				this.functions.put("<Struct3>", tokens -> {
				SynthaticNode tokenMap = new SynthaticNode();
				Token token = tokens.peek();
				if (token != null && /*token.getType() == TokenTypes. &&*/ "}".equals(token.getLexeme())) {tokenMap.add(new SynthaticNode(tokens.remove()));}} else if(this.predict("<TipoStruct>", tokens.peek())) {tokenMap.add(this.call("<TipoStruct>", tokens).getTokenNode());
					}return tokenMap;});

	        }
    
        public static SynthaticAnalyzer getInstance() {
            if (instance == null) {
                instance = new SynthaticAnalyzer();
            }
            return instance;
        }
    
        public SynthaticNode start(Queue<Token> queue) {
            return this.functions.get(FirstFollow.getInstance().StartSymbol.replace("<", "").replace(">", "")).run(queue);
        }
    
        public void showDerivation(SynthaticNode node) {
            if (node != null) {
                if (node.getNodeList().isEmpty()) {
                    System.out.println(node.getToken() != null ? node.getToken() : "Empty");
                } else {
                    for (SynthaticNode synthaticNode : node.getNodeList()) {
                        this.showDerivation(synthaticNode);
                    }
                }
            }
        }
    }
