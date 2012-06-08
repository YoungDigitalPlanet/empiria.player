package eu.ydp.empiria.player.client.util.file;

public interface TextDocumentLoadCallback {

	public void finishedLoading(String text, String baseUrl);
	public void loadingError(String message);
}
