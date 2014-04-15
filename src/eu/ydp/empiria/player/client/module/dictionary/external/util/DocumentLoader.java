package eu.ydp.empiria.player.client.module.dictionary.external.util;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;

public abstract class DocumentLoader {

	public static void load(String url, final DocumentLoadingListener listener) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));

		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onError(Request request, Throwable exception) {
					listener.onDocumentLoadError(exception.toString());
				}

				@Override
				public void onResponseReceived(Request request, Response response) {
					// StatusCode == 0 when loading from local file
					try {
						if (response.getStatusCode() == 200 || response.getStatusCode() == 0) {

							listener.onDocumentLoaded(response.getText());

						} else {
							// Handle the error. Can get the status text from
							// response.getStatusText()
							String errorString = "Wrong status: " + response.getText();
							listener.onDocumentLoadError(errorString);
						}
					} catch (Exception e) {
						listener.onDocumentLoadError(e.getMessage());
					}
				}
			});

		} catch (RequestException e) {
			// Couldn't connect to server
			listener.onDocumentLoadError(e.getMessage());
		}

	}
}
