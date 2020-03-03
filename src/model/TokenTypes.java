package model;

public enum TokenTypes {
	RESERVED(1, "palavra reservada"), DELIMITER(2, "delimitador"), RELATIONAL(3, "operador relacional"), LOGIC(4, "operador logico"),
	ARITHMETIC(5, "operador aritmetico"), NUMBER(6, "numero"), IDENTIFIER(7, "identificador"), STRING(8, "string"),
	COMMENT(9, "comentario");

	private final int id; // Can be used in comparing between values.
	private final String name; // Identify the Token, can be used on messagens.

	TokenTypes(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
