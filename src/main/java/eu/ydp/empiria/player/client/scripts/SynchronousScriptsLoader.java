package eu.ydp.empiria.player.client.scripts;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.inject.ScriptInjectorWrapper;
import eu.ydp.gwtutil.client.scripts.ScriptUrl;
import eu.ydp.gwtutil.client.util.paths.UrlConverter;

import java.util.Collections;
import java.util.Stack;

public class SynchronousScriptsLoader {

    @Inject
    private ScriptInjectorWrapper scriptInjectorWrapper;
    @Inject
    private UrlConverter urlConverter;
    @Inject
    private RequestWrapper requestWrapper;

    public void injectScripts(ScriptUrl[] scripts, SynchronousScriptsCallback callback) {
        Stack<ScriptUrl> scriptsStack = new Stack<>();
        Collections.addAll(scriptsStack, scripts);
        Collections.reverse(scriptsStack);

        RequestCallback requestCallback = createRequestCallback(scriptsStack, callback);

        injectScript(scriptsStack, requestCallback);
    }

    private RequestCallback createRequestCallback(final Stack<ScriptUrl> scriptsStack, final SynchronousScriptsCallback finalCallback) {
        return new RequestCallback() {
            @Override
            public void onResponseReceived(Request request, Response response) {
                scriptInjectorWrapper.fromString(response.getText());
                if (scriptsStack.empty()) {
                    finalCallback.onLoad();
                } else {
                    injectScript(scriptsStack, this);
                }
            }

            @Override
            public void onError(Request request, Throwable exception) {

            }
        };
    }

    private void injectScript(final Stack<ScriptUrl> scriptsStack, final RequestCallback callback) {
        ScriptUrl script = scriptsStack.pop();
        String correctUrl = urlConverter.getModuleRelativeUrl(script);
        requestWrapper.get(correctUrl, callback);
    }
}
