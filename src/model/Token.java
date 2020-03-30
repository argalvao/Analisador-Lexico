/*
 *
 Abel Ramalho Galvão
 Ramon de Cerqueira Silva
 *
 */
package model;

public class Token {
	private final TokenTypes type;
	private final String lexeme;
	private final int line;
	private String tipoId;
	
	public Token(TokenTypes type, String lexeme, int line, String tipoId) {
		this.lexeme = lexeme;
		this.type = type;
		this.line = line;
		this.tipoId = tipoId;
	}

	public String getTipoId() {
		return tipoId;
	}
	
	public void setTipoId(String tipoId) {
		this.tipoId =  tipoId;
	}
	
	public TokenTypes getType() {
		return type;
	}

	public String getLexeme() {
		return lexeme;
	}

	public int getLine() {
		return line;
	}

	@Override
	public String toString() {
		// if (tipo == String.ERRO_SINT)
		// return (this.linha + " " + this.lexema + " " + this.tipo);
		// else
		return ("Linha:\t" + (this.line + 1) + "\t" + "| Lexema:\t" + this.lexeme + "\t\t\t" + "|\t"
				+ this.type.getName());
	}

}
