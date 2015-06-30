package eu.ydp.empiria.player.client.util.file;

import com.google.gwt.core.client.GWT;
import eu.ydp.jsfilerequest.client.FileRequest;
import eu.ydp.jsfilerequest.client.FileRequestCallback;
import eu.ydp.jsfilerequest.client.FileRequestException;
import eu.ydp.jsfilerequest.client.FileResponse;

public class TextDocument {

    private DocumentLoadCallback<String> callback;
    private String url;
    private String baseUrl;

    public TextDocument(String u, DocumentLoadCallback<String> c) {

        this.callback = c;
        this.url = u;
        this.baseUrl = url.substring(0, url.lastIndexOf("/") + 1);

        FileRequest request = GWT.create(FileRequest.class);

        request.setUrl(url);
        try {
            request.send("", new FileRequestCallback() {

                @Override
                public void onResponseReceived(FileRequest request, FileResponse response) {
                    callback.finishedLoading(response.getText(), baseUrl);
                }

                @Override
                public void onError(FileRequest request, Throwable exception) {
                    exception.printStackTrace();
                    callback.loadingError(exception.getMessage());
                }
            });
        } catch (FileRequestException e) {
            e.printStackTrace();
            callback.loadingError("Can't connect to the server: " + e.toString());
        }

    }

}
