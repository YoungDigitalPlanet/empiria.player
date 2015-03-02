package eu.ydp.empiria.player.client.controller.variables.processor.results.model;

public class UserInteractionVariables {

	private LastAnswersChanges lastAnswerChanges;
	private LastMistaken lastmistaken;
	private int mistakes;

	public UserInteractionVariables() {
		lastAnswerChanges = new LastAnswersChanges();
		lastmistaken = LastMistaken.NONE;
		mistakes = 0;
	}

	public UserInteractionVariables(LastAnswersChanges lastAnswerChanges, LastMistaken lastmistaken, int mistakes) {
		this.lastAnswerChanges = lastAnswerChanges;
		this.lastmistaken = lastmistaken;
		this.mistakes = mistakes;
	}

	public int getMistakes() {
		return mistakes;
	}

	public void setMistakes(int mistakes) {
		this.mistakes = mistakes;
	}

	public LastAnswersChanges getLastAnswerChanges() {
		return lastAnswerChanges;
	}

	public void setLastAnswerChanges(LastAnswersChanges lastAnswerChanges) {
		this.lastAnswerChanges = lastAnswerChanges;
	}

	public LastMistaken getLastmistaken() {
		return lastmistaken;
	}

	public void setLastmistaken(LastMistaken lastmistaken) {
		this.lastmistaken = lastmistaken;
	}
}
