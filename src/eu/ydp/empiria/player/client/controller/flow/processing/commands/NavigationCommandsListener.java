package eu.ydp.empiria.player.client.controller.flow.processing.commands;

public interface NavigationCommandsListener {

	public void nextPage();

	public void previousPage();

	public void gotoPage(int index);

	public void gotoFirstPage();

	public void gotoLastPage();

	public void gotoToc();

	public void gotoSummary();

	public void gotoTest();

	public void previewPage(int index);
}
