package semantic_models;

public class Return_function {
	private String name_funtion;
	private String name_return;
	private String type_return;
	private String type_function;
	
	public Return_function(String name_funtion, String name_return, String type_return, String type_function) {
		super();
		this.name_funtion = name_funtion;
		this.name_return = name_return;
		this.type_return = type_return;
		this.type_function = type_function;
	}

	public String getName_funtion() {
		return name_funtion;
	}

	public void setName_funtion(String name_funtion) {
		this.name_funtion = name_funtion;
	}

	public String getName_return() {
		return name_return;
	}

	public void setName_return(String name_return) {
		this.name_return = name_return;
	}

	public String getType_return() {
		return type_return;
	}

	public void setType_return(String type_return) {
		this.type_return = type_return;
	}

	public String getType_function() {
		return type_function;
	}

	public void setType_function(String type_function) {
		this.type_function = type_function;
	}
	
	
}
