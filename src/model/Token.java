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
	private String valorId;
	
	public Token(TokenTypes type, String lexeme, int line, String tipoId, String valorId) {
		this.lexeme = lexeme;
		this.type = type;
		this.line = line;
		this.tipoId = tipoId;
		this.valorId = valorId;
	}

	public String getTipoId() {
		return tipoId;
	}
	
	public void setTipoId(String tipoId) {
		this.tipoId =  tipoId;
	}
	public String getValorId() {
		return valorId;
	}
	
	public void setValorId(String valorId) {
		this.valorId =  valorId;
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
