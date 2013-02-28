package eu.ydp.empiria.player.client.controller.variables.processor.results;


public class UserInteractionVariables {

	private LastAnswersChanges lastAnswerChanges;
	private boolean lastmistaken;
	private int mistakes;

	public UserInteractionVariables() {
		lastAnswerChanges = new LastAnswersChanges();
		lastmistaken = false;
		mistakes = 0;
	}

	public UserInteractionVariables(LastAnswersChanges lastAnswerChanges, boolean lastmistaken, int mistakes) {
		this.lastAnswerChanges = lastAnswerChanges;
		this.lastmistaken = lastmistaken;
		this.mistakes = mistakes;
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
	public LastAnswersChanges getLastAnswerChanges() {
		return lastAnswerChanges;
	}
	public void setLastAnswerChanges(LastAnswersChanges lastAnswerChanges) {
		this.lastAnswerChanges = lastAnswerChanges;
	}
}
