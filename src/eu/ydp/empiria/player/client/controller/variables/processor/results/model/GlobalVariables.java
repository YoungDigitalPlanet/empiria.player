package eu.ydp.empiria.player.client.controller.variables.processor.results.model;

public class GlobalVariables {

	private int todo;
	private int errors;
	private int done;
	private boolean lastmistaken;
	private int mistakes;

	public GlobalVariables() {
	}
	
	public GlobalVariables(int todo, int errors, int done, boolean lastmistaken, int mistakes) {
		this.todo = todo;
		this.errors = errors;
		this.done = done;
		this.lastmistaken = lastmistaken;
		this.mistakes = mistakes;
	}
	public int getTodo() {
		return todo;
	}
	public void setTodo(int todo) {
		this.todo = todo;
	}
	public int getErrors() {
		return errors;
	}
	public void setErrors(int errors) {
		this.errors = errors;
	}
	public int getDone() {
		return done;
	}
	public void setDone(int done) {
		this.done = done;
	}
	public boolean isLastmistaken() {
		return lastmistaken;
	}
	public void setLastmistaken(boolean lastmistaken) {
		this.lastmistaken = lastmistaken;
	}
	public int getMistakes() {
		return mistakes;
	}
	public void setMistakes(int mistakes) {
		this.mistakes = mistakes;
	}
}
