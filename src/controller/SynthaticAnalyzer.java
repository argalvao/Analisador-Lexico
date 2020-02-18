package controller;

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

		this.functions.put("Inicio", tokens -> {
			SynthaticNode tokenMap = new SynthaticNode();
			if (this.predict("Const", tokens.peek())) {
				tokenMap.add(this.call("Const", tokens).getTokenNode());
			}
			if (this.predict("Struct", tokens.peek())) {
				tokenMap.add(this.call("Struct", tokens).getTokenNode());
			}
			if (this.predict("Var", tokens.peek())) {
				tokenMap.add(this.call("Var", tokens).getTokenNode());
			}
			if (this.predict("GeraFuncaoeProcedure", tokens.peek())) {
				tokenMap.add(this.call("GeraFuncaoeProcedure", tokens).getTokenNode());
			}
			if (this.predict("Start", tokens.peek())) {
				tokenMap.add(this.call("Start", tokens).getTokenNode());
			}
			return tokenMap;
		});

		/* Connections for terminals that derive from non-terminals */
		this.functions.put("Const", tokens -> {
			Token token = tokens.peek();
			if (token != null && token.getType() == TokenTypes.RESERVED && "const".equals(token.getLexeme())) {
				SynthaticNode tokenMap = new SynthaticNode();
				tokenMap.add(new SynthaticNode(tokens.remove()));
				token = tokens.peek();
				if (token != null && token.getType() == TokenTypes.DELIMITER && "{".equals(token.getLexeme())) {
					tokenMap.add(new SynthaticNode(tokens.remove()));
					SynthaticNode tempNode = this.call("TipoConst", tokens).getTokenNode();
					if (tempNode != null) {
						tokenMap.add(tempNode);
					} else {
						this.errors.add("Synthatic Error, on production TipoConst");
						return null;
					}
				}
			}
			return null;
		});

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