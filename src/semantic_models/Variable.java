package semantic_models;

import model.Token;

public class Variable {
	Token token;
	private String type;
	
	public Variable(Token token, String type) {
		super();
		this.token = token;
		this.type = type;
	}
	
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
		
}
