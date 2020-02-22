package model;

public enum TokenTypes {
	RESERVED(1, "reserved"), DELIMITER(2, "delimiter"), RELATIONAL(3, "relational"), LOGIC(4, "logic"),
	ARITHMETIC(5, "arithmetic"), NUMBER(6, "number"), IDENTIFIER(7, "identifier"), STRING(8, "string"),
	COMMENT(9, "comment");

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
