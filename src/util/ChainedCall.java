package util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

import model.SynthaticNode;
import model.Token;
import model.TokenTypes;

public abstract class ChainedCall {

    private Queue<Token> tokenList;
    private SynthaticNode tokenNode;
    protected HashMap<String, HashSet<String>> first;
    protected HashMap<String, HashSet<String>> follow;
    protected HashMap<String, Production> functions;

    public ChainedCall() {
        this.tokenList = null;
        this.functions = new HashMap<>();
        this.first = FirstFollow.getInstance().getFirst();
        this.follow = FirstFollow.getInstance().getFollow();
    }

    protected boolean predict(String productionName, Token token) {   /*Something is wrong with predict*/
        if (token != null) {
            if (this.first.get(productionName) != null) {
                return this.first.get(productionName).contains(token.getLexeme())
                        || (TokenTypes.IDENTIFIER == token.getType() && this.first.get(productionName).contains("Identifier"))
                        || (TokenTypes.NUMBER == token.getType() && this.first.get(productionName).contains("NumberTerminal"))
                        || (TokenTypes.STRING == token.getType() && this.first.get(productionName).contains("StringLiteral"))
                        || (this.first.get(productionName).contains(""));
            } else if ("NumberTerminal".equals(productionName) && token.getType() == TokenTypes.NUMBER) {
                return true;
            } else if ("Identifier".equals(productionName) && token.getType() == TokenTypes.IDENTIFIER) {
                return true;
            } else return "StringLiteral".equals(productionName) && token.getType() == TokenTypes.STRING;
        }
        return false;
    }

    protected ChainedCall call(String productionName, Queue<Token> tokenList) {
        this.tokenList = tokenList;
        this.tokenNode = null;
        if (this.functions.containsKey(productionName) && this.predict(productionName, tokenList.peek())) {
            this.tokenNode = this.functions.get(productionName).run(tokenList);
        }
        return this;
    }

    public ChainedCall call(String productionName) {
        if (this.tokenNode == null && this.tokenList != null && this.functions.containsKey(productionName)) {
            if (this.predict(productionName, this.tokenList.peek())) {
                SynthaticNode map = this.functions.get(productionName).run(tokenList);
                if (map != null) {
                    this.tokenNode = map;
                }
            }
        }
        return this;
    }

    public SynthaticNode getTokenNode() {
        return tokenNode;
    }
}