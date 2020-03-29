package semantic_models;

public class Conditional {
	private String first_term;
	private String second_term;
	private String first_type;
	private String second_type;
	
	
	public Conditional(String first_term, String second_term, String first_type, String second_type) {
		super();
		this.first_term = first_term;
		this.second_term = second_term;
		this.first_type = first_type;
		this.second_type = second_type;
	}


	public String getFirst_term() {
		return first_term;
	}


	public void setFirst_term(String first_term) {
		this.first_term = first_term;
	}


	public String getSecond_term() {
		return second_term;
	}


	public void setSecond_term(String second_term) {
		this.second_term = second_term;
	}


	public String getFirst_type() {
		return first_type;
	}


	public void setFirst_type(String first_type) {
		this.first_type = first_type;
	}


	public String getSecond_type() {
		return second_type;
	}


	public void setSecond_type(String second_type) {
		this.second_type = second_type;
	}
	
	
}
