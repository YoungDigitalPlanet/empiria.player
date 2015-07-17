package eu.ydp.empiria.player.client.scripts;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.debug.log.Logger;

public class RequestWrapper {

    @Inject
    private Logger logger;

    public void get(String url, RequestCallback callback) {
        try {
            new RequestBuilder(RequestBuilder.GET, url).sendRequest("", callback);
        } catch (RequestException e) {
            logger.error(e);
        }
    }
}
