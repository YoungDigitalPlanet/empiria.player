package eu.ydp.empiria.player.client.controller.variables.processor.results.model;


public class GlobalVariables implements ResultVariables {

	private int todo;
	private int done;
	private int mistakes;
	private int errors;
	private LastMistaken lastMistaken;

	public GlobalVariables() {
	}

	public GlobalVariables(int todo, int done, int errors, int mistakes, LastMistaken lastMistaken) {
		this.todo = todo;
		this.done = done;
		this.mistakes = mistakes;
		this.errors = errors;
		this.lastMistaken = lastMistaken;
	}

	@Override
	public int getTodo() {
		return todo;
	}
	public void setTodo(int todo) {
		this.todo = todo;
	}

	@Override
	public int getDone() {
		return done;
	}
	public void setDone(int done) {
		this.done = done;
	}

	@Override
	public int getMistakes() {
		return mistakes;
	}
	public void setMistakes(int mistakes) {
		this.mistakes = mistakes;
	}

	@Override
	public int getErrors() {
		return errors;
	}

	public void setErrors(int errors) {
		this.errors = errors;
	}

	@Override
	public LastMistaken getLastMistaken() {
		return lastMistaken;
	}

	public void setLastMistaken(LastMistaken lastMistaken) {
		this.lastMistaken = lastMistaken;
	}

	

	@Override
	public String toString() {
		return "GlobalVariables [todo=" + todo + ", done=" + done + ", mistakes=" + mistakes + ", errors=" + errors + ", lastMistaken=" + lastMistaken
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + done;
		result = prime * result + errors;
		result = prime * result + ((lastMistaken == null) ? 0 : lastMistaken.hashCode());
		result = prime * result + mistakes;
		result = prime * result + todo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ResultVariables)) {
			return false;
		}
		ResultVariables other = (ResultVariables) obj;
		if (done != other.getDone()) {
			return false;
		}
		if (errors != other.getErrors()) {
			return false;
		}
		if (lastMistaken != other.getLastMistaken()) {
			return false;
		}
		if (mistakes != other.getMistakes()) {
			return false;
		}
		if (todo != other.getTodo()) {
			return false;
		}
		return true;
	}
}
