package semantic_models;

public class Variable {
	private String name;
	private String type;
	private boolean global;
	
	public Variable(String name, String type, boolean global) {
		super();
		this.name = name;
		this.type = type;
		this.global = global;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isGlobal() {
		return global;
	}
	public void setGlobal(boolean global) {
		this.global = global;
	}
	
		
}
