package semantic_models;

public class List {
	private String name;
	private String type;
	
	public List(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}
	private String size;

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
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	
}
