package eu.ydp.empiria.player.client.controller.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.ydp.empiria.player.client.style.StyleDocument;
import eu.ydp.empiria.player.client.util.PathUtil;
import eu.ydp.empiria.player.client.util.file.DocumentLoadCallback;
import eu.ydp.empiria.player.client.util.file.TextDocument;

public class StyleDataSourceLoader {

	private Map<String, StyleDocument> documents;
	private Map<String, String> errorMessages;
	private Map<String, List<DocumentLoadCallback<StyleDocument>>> pending;
	
	public StyleDataSourceLoader(){
		documents = new HashMap<String, StyleDocument>();
		errorMessages = new HashMap<String, String>();
		pending = new HashMap<String, List<DocumentLoadCallback<StyleDocument>>>();
	}
	
	
	public synchronized void load(String url, final DocumentLoadCallback<StyleDocument> callback){
		final String urlNormalized  = PathUtil.normalizePath(url);
		if (errorMessages.containsKey(urlNormalized)){
			callback.loadingError(errorMessages.get(urlNormalized));
		} else if (documents.containsKey(urlNormalized)){
			callback.finishedLoading(documents.get(urlNormalized), documents.get(urlNormalized).getBasePath());			
		} else if(pending.containsKey(urlNormalized)){
			pending.get(urlNormalized).add(callback);
		} else {
			pending.put(urlNormalized, new ArrayList<DocumentLoadCallback<StyleDocument>>());
			pending.get(urlNormalized).add(callback);
			new TextDocument(urlNormalized, new DocumentLoadCallback<String>() {

				@Override
				public void finishedLoading(String value, String baseUrl) {
					StyleDocument sd = new StyleDocument( CssParser.parseCss(value), baseUrl);
					documents.put(urlNormalized, sd);
					for (DocumentLoadCallback<StyleDocument> currCallback : pending.get(urlNormalized)){
						currCallback.finishedLoading(sd, baseUrl);
					}
					pending.remove(urlNormalized);
				}

				@Override
				public void loadingError(String message) {
					errorMessages.put(urlNormalized, message);
					for (DocumentLoadCallback<StyleDocument> currCallback : pending.get(urlNormalized)){
						currCallback.loadingError(message);
					}
					pending.remove(urlNormalized);
				}
			});
		}
	}
}
