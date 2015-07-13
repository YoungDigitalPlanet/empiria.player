/*
  The MIT License
  
  Copyright (c) 2009 Krzysztof Langner
  
  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:
  
  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.
  
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
 */
package eu.ydp.empiria.player.client.util.file.xml;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.impl.DOMParseException;
import eu.ydp.empiria.player.client.util.file.DocumentLoadCallback;
import eu.ydp.empiria.player.client.util.file.TextDocument;

public class XmlDocument {

    private DocumentLoadCallback<Document> callback;
    private String url;

    /**
     * Loads an XML document.
     *
     * @param u Document URL
     * @param c Callback with on success and on error handlers
     */
    public XmlDocument(String u, DocumentLoadCallback<Document> c) {

        this.callback = c;
        this.url = u;

        new TextDocument(url, new DocumentLoadCallback<String>() {

            @Override
            public void loadingError(String message) {
                callback.loadingError(message);
            }

            @Override
            public void finishedLoading(String text, String baseUrl) {
                try {
                    Document dom = XMLParser.parse(text);
                    callback.finishedLoading(dom, baseUrl);
                } catch (DOMParseException e) {
                    e.printStackTrace();
                    callback.loadingError("Could not parse file: " + url);
                }
            }
        });

    }
}
