package eu.ydp.empiria.player.client.controller.variables.processor.results.model;

public enum VariableName {

	TODO("TODO"),
	ERRORS("ERRORS"),
	DONE("DONE"),
	MISTAKES("MISTAKES"),
	LASTMISTAKEN("LASTMISTAKEN"),
	LASTCHANGE("LASTCHANGE");
	
	
	
	private String name;

	VariableName(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
}
