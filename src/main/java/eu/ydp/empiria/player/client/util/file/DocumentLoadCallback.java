package eu.ydp.empiria.player.client.util.file;

public interface DocumentLoadCallback<T> {

    public void finishedLoading(T value, String baseUrl);

    public void loadingError(String message);
}
