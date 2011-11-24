package eu.ydp.empiria.player.client.controller.flow.processing.commands;

public interface FlowCommandsListener {

	public void nextPage();
	public void previousPage();
	public void gotoPage(int index);
	public void gotoFirstPage();
	public void gotoLastPage();
	public void gotoToc();
	public void gotoSummary();
	public void gotoTest();
	public void checkPage();
	public void showAnswers();
	// REMOVE HIDE_ANSWERS
	//public void hideAnswers();
	public void continuePage();
	public void resetPage();
	public void lockPage();
	public void unlockPage();
	public void previewPage(int index);
}
