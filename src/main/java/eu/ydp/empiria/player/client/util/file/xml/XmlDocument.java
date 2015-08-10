package eu.ydp.empiria.player.client.util.file.xml;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.impl.DOMParseException;
import eu.ydp.empiria.player.client.util.file.DocumentLoadCallback;
import eu.ydp.empiria.player.client.util.file.TextDocument;

public class XmlDocument {

    private DocumentLoadCallback<Document> callback;
    private String url;

    public XmlDocument(String url, DocumentLoadCallback<Document> callback) {

        this.callback = callback;
        this.url = url;

        new TextDocument(this.url, new DocumentLoadCallback<String>() {

            @Override
            public void loadingError(String message) {
                XmlDocument.this.callback.loadingError(message);
            }

            @Override
            public void finishedLoading(String text, String baseUrl) {
                try {
                    Document dom = XMLParser.parse(text);
                    XmlDocument.this.callback.finishedLoading(dom, baseUrl);
                } catch (DOMParseException e) {
                    e.printStackTrace();
                    XmlDocument.this.callback.loadingError("Could not parse file: " + XmlDocument.this.url);
                }
            }
        });

    }
}
