package eu.ydp.empiria.player.client.module.dictionary.external.util;

public interface DocumentLoadingListener {

	public void onDocumentLoaded(String text);

	public void onDocumentLoadError(String error);
}