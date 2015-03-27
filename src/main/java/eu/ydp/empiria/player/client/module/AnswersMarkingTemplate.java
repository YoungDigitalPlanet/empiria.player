package eu.ydp.empiria.player.client.module;

public abstract class AnswersMarkingTemplate {

	public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
		if (type == MarkAnswersType.CORRECT) {
			if (mode == MarkAnswersMode.MARK) {
				markCorrect();
			} else {
				unmarkCorrect();
			}
		} else {
			if (mode == MarkAnswersMode.MARK) {
				markWrong();
			} else {
				unmarkWrong();
			}
		}
	}

	public abstract void unmarkWrong();

	public abstract void markWrong();

	public abstract void unmarkCorrect();

	public abstract void markCorrect();
}
