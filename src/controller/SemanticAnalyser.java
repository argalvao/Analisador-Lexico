package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import model.SynthaticNode;
import model.Token;
import model.TokenTypes;

public class SemanticAnalyser {
	private static SemanticAnalyser instance;
	private HashSet<SynthaticNode> postAnalysis;
	private HashSet<SynthaticNode> alreadyAnalyzed;
	private HashSet<SynthaticNode> successAnalyzed;
	private List<String> errors;
	private HashMap<String, String> inheritance;
	private HashMap<String, HashSet<String>> classMethods;
	private HashSet<String> validTypes;
	private HashSet<String> classNames;
	private HashMap<String, String> globalConstants;
	private HashMap<String, HashMap<String, String>> scopeElements;

	public static SemanticAnalyser getInstance() {
		if (instance == null) {
			instance = new SemanticAnalyser();
		}
		return instance;
	}

	private SemanticAnalyser() {
		this.inheritance = new HashMap<>();
		this.classMethods = new HashMap<>();
		this.validTypes = new HashSet<>(Arrays.asList("string", "float", "int", "bool"));
		this.classNames = new HashSet<>();
		this.errors = new ArrayList<>();
		this.postAnalysis = new HashSet<>();
		this.alreadyAnalyzed = new HashSet<>();
		this.successAnalyzed = new HashSet<>();
		this.globalConstants = new HashMap<>();
		this.scopeElements = new HashMap<>();
	}

	private void toAnalyse(SynthaticNode synthaticNode, HashSet<String> error, String lexeme) {
		if (this.postAnalysis.contains(synthaticNode)) {
			if (!this.alreadyAnalyzed.contains(synthaticNode)) {
				this.errors.add(lexeme);
			} else {
				this.alreadyAnalyzed.add(synthaticNode);
			}
			this.postAnalysis.remove(synthaticNode);
		} else {
			this.postAnalysis.add(synthaticNode);
		}
	}

	private boolean compareTypes(String first, String second) {
		if (("int".equals(first) || "int".equals(second)) && ("float".equals(first) || "float".equals(second))) {
			return true;
		}
		return first.equals(second);
	}

	private String getExpressionValue(SynthaticNode synthaticNode) {
		String firstValue;
		String secondValue = null;

		if (synthaticNode.getProduction().equals("<Expr Arit>")) {
			firstValue = this.getValue(synthaticNode.getNodeList().get(0));
			if (synthaticNode.getNodeList().get(1).getNodeList().size() > 1) {
				secondValue = this.getValue(synthaticNode.getNodeList().get(1).getNodeList().get(1));
			}
		} else {
			return this.getExpressionValue(synthaticNode.getNodeList().get(0));
		}

		if (secondValue != null) {
			if (secondValue.equals(firstValue)) {
				return secondValue;
			} else {
				return null;
			}
		} else {
			return firstValue;
		}
	}

	private String getValue(SynthaticNode synthaticNode) {
		if (synthaticNode.getProduction() != null) {
			switch (synthaticNode.getProduction()) {
			case "<Value>":
				Token token = synthaticNode.getNodeList().get(0).getToken();
				if (token.getType() == TokenTypes.STRING) {
					return "string";
				} else if (token.getLexeme().getValue().equals("true")
						|| token.getLexeme().getValue().equals("false")) {
					return "bool";
				} else {
					if (token.getLexeme().getValue().contains(".")) {
						return "float";
					} else {
						return "int";
					}
				}
			case "Identifier":
				return "string";
			case "<Init Array>":
				return "array";
			default:
				return this.getValue(synthaticNode.getNodeList().get(0));
			}
		}
		return null;
	}

	private String getParametersString(SynthaticNode synthaticNode) {
		return synthaticNode.toString();
	}

	private String getMethodSignature(SynthaticNode synthaticNode) {
		String returnType = synthaticNode.getNodeList().get(1).getNodeList().get(0).getNodeList().get(0).getToken()
				.getLexeme().getValue();
		String methodName = synthaticNode.getNodeList().get(1).getNodeList().get(1).getNodeList().get(0).getToken()
				.getLexeme().getValue();
		String parameters = this.getParametersString(synthaticNode.getNodeList().get(3));
		return returnType + "_" + methodName + "__" + parameters;
	}

	private void analyseMethod(SynthaticNode synthaticNode, String className) {
		String lexeme = synthaticNode.getNodeList().get(1).getNodeList().get(1).getNodeList().get(0).getToken()
				.getLexeme();
		if (this.classMethods.get(className).contains(this.getMethodSignature(synthaticNode))) {
			this.errors.add(new ClassMethodError("Override", lexeme));
		}
		this.classMethods.get(className).add(this.getMethodSignature(synthaticNode));
	}

	private void analyseClassCode(SynthaticNode synthaticNode, String className) {
		if ("<Methods>".equals(synthaticNode.getNodeList().get(0).getProduction())) {
			this.analyseMethod(synthaticNode.getNodeList().get(0), className);
		} else if ("<Variables>".equals(synthaticNode.getNodeList().get(0).getProduction())) {
			this.analyseVariableAssignment(synthaticNode.getNodeList().get(0).getNodeList().get(2), className);
		}
		if (synthaticNode.getNodeList().size() > 1 && !synthaticNode.getNodeList().get(1).isEmpty()) {
			this.analyseClassCode(synthaticNode.getNodeList().get(1), className);
		}
	}

	private void analyseClass(SynthaticNode hasConsumed) {
		if (!this.successAnalyzed.contains(hasConsumed)) {
			String newType = hasConsumed.getNodeList().get(0).getNodeList().get(1).getToken().getLexeme().getValue();
			this.validTypes.add(newType);
			this.classNames.add(newType);
			this.classMethods.put(newType, new HashSet<>());
			this.successAnalyzed.add(hasConsumed);
			String subProduction = hasConsumed.getNodeList().get(0).getNodeList().get(2).getProduction();
			if ("<Extends>".equals(subProduction)) {
				Token token = hasConsumed.getNodeList().get(0).getNodeList().get(2).getNodeList().get(0).getToken();
				if (token != null) {
					String lexeme = hasConsumed.getNodeList().get(0).getNodeList().get(2).getNodeList().get(1)
							.getToken().getLexeme();
					String className = lexeme;
					if (this.classNames.contains(className)) {
						this.successAnalyzed.add(hasConsumed.getNodeList().get(0).getNodeList().get(2));
						this.inheritance.put(newType, className);
					} else {
						this.toAnalyse(hasConsumed, this.classNames, lexeme);
					}
				}
			}
			this.analyseClassCode(hasConsumed.getNodeList().get(0).getNodeList().get(4), newType);
		}
	}

	private void analyseVariableAssignment(SynthaticNode hasConsumed, String scope) {
		if (!this.successAnalyzed.contains(hasConsumed)) {
			if (scope == null) {
				scope = hasConsumed.toString();
			}
			String subProduction = hasConsumed.getNodeList().get(0).getProduction();
			if (subProduction != null) {
				String lexeme = hasConsumed.getNodeList().get(0).getNodeList().get(0).getNodeList().get(0).getToken()
						.getLexeme();
				String type = lexeme;
				if (!this.validTypes.contains(type)) {
					this.toAnalyse(hasConsumed, this.validTypes, lexeme);
				} else {
					this.successAnalyzed.add(hasConsumed);
					if (!this.scopeElements.containsKey(scope)) {
						this.scopeElements.put(scope, new HashMap<>());
					}
					if (hasConsumed.getNodeList().get(1).getNodeList().size() > 1) {
						if (!this.compareTypes(type,
								this.getExpressionValue(hasConsumed.getNodeList().get(1).getNodeList().get(1)))) {
							this.toAnalyse(hasConsumed, this.validTypes, lexeme);
						}
					}
				}
			}
		}
	}

	private void analyseConstantAssignment(SynthaticNode hasConsumed) {
		if (!this.successAnalyzed.contains(hasConsumed)) {
			String subProduction = hasConsumed.getNodeList().get(0).getProduction();
			if (subProduction != null) {
				String lexeme = hasConsumed.getNodeList().get(0).getNodeList().get(0).getNodeList().get(0).getToken()
						.getLexeme();
				String type = lexeme;
				if (!this.validTypes.contains(type)) {
					this.toAnalyse(hasConsumed, this.validTypes, lexeme);
				} else {
					this.globalConstants.put(hasConsumed.getNodeList().get(0).getNodeList().get(1).getNodeList().get(0)
							.getToken().getLexeme().getValue(), type);
					if (!this.compareTypes(type, this.getExpressionValue(hasConsumed.getNodeList().get(2)))) {
						this.toAnalyse(hasConsumed, this.validTypes, lexeme);
					} else {
						this.successAnalyzed.add(hasConsumed);
					}
					if (hasConsumed.getNodeList().size() > 4) {
						this.analyseConstantAssignment(hasConsumed.getNodeList().get(4));
					}
				}
			}
		}
	}

	private void analyseProgram(SynthaticNode synthaticNode) {
		for (SynthaticNode hasConsumed : synthaticNode.getNodeList()) {
			if ("<Class>".equals(hasConsumed.getProduction())) {
				this.analyseClass(synthaticNode);
			} else if ("<Constants>".equals(hasConsumed.getProduction())) {
				this.analyseConstantAssignment(hasConsumed.getNodeList().get(2));
			}
		}
	}

	void analyse(SynthaticNode hasConsumed) {
		if ("<Program>".equals(hasConsumed.getProduction())) {
			this.analyseProgram(hasConsumed);
		} else if ("<Constant Assignment>".equals(hasConsumed.getProduction())) {
			this.analyseConstantAssignment(hasConsumed);
		} else if ("<Variable Assignment>".equals(hasConsumed.getProduction())) {
			this.analyseVariableAssignment(hasConsumed, hasConsumed.toString());
		}
	}

	void executePostAnalysis() {
		try {
			for (SynthaticNode synthaticNode : this.postAnalysis) {
				this.analyse(synthaticNode);
			}
		} catch (ConcurrentModificationException e) {
			this.executePostAnalysis();
		}
	}

	public List<String> getErrors() {
		return this.errors;
	}

	public void reset() {
		instance = new SemanticAnalyser();
	}
}