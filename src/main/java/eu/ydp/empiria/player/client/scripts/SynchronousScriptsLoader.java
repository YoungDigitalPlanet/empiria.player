package eu.ydp.empiria.player.client.scripts;

import com.google.gwt.core.client.Callback;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.scripts.AsynchronousScriptsLoader;
import eu.ydp.gwtutil.client.scripts.ScriptUrl;

import java.util.*;

/**
 * Created by ldomzalski on 2015-06-24.
 */
public class SynchronousScriptsLoader {

    @Inject
    private AsynchronousScriptsLoader asynchronousScriptsLoader;

    public void injectScripts(ScriptUrl[] scripts, Callback<Void, Exception> callback) {

        List<ScriptUrl> scriptsList = new ArrayList<ScriptUrl>(Arrays.asList(scripts));
        Collections.reverse(scriptsList);

        Stack<ScriptUrl> scriptsStack = new Stack<ScriptUrl>();
        scriptsStack.addAll(scriptsList);
        injectScript(scriptsStack, callback);

    }

    private void injectScript(final Stack<ScriptUrl> scriptsStack, final Callback<Void, Exception> callback) {
        if (scriptsStack.empty()) {
            return;
        }

        asynchronousScriptsLoader.inject(scriptsStack.pop(), new Callback<Void, Exception>() {
            @Override
            public void onFailure(Exception reason) {

            }

            @Override
            public void onSuccess(Void result) {
                if (!scriptsStack.empty()) {
                    injectScript(scriptsStack, callback);
                } else {
                    callback.onSuccess(result);
                }
            }
        });

    }

}
