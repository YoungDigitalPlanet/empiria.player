package eu.ydp.empiria.player.client.module.dictionary.external.controller;

public class ComparationResult {

	private final int charsMatched;
	private final int lexiResult;

	public ComparationResult(int charsMatched, int lexiResult) {
		super();
		this.charsMatched = charsMatched;
		this.lexiResult = lexiResult;
	}

	public int getCharsMatched() {
		return charsMatched;
	}

	public int getLexiResult() {
		return lexiResult;
	}
}
