package eu.ydp.empiria.player.client.scripts;

import com.google.gwt.core.client.Callback;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.inject.ScriptInjectorWrapper;
import eu.ydp.gwtutil.client.util.paths.UrlConverter;

import java.util.*;

/**
 * Created by ldomzalski on 2015-06-24.
 */
public class SynchronousScriptsLoader {

    @Inject
    private ScriptInjectorWrapper scriptInjectorWrapper;
    @Inject
    private UrlConverter urlConverter;


    public void injectScripts(Callback<Void, Exception> callback) {

        List<ScriptsSyncLoading> scriptsList = new ArrayList<ScriptsSyncLoading>(Arrays.asList(ScriptsSyncLoading.values()));
        Collections.reverse(scriptsList);

        Stack<ScriptsSyncLoading> scriptsStack = new Stack<ScriptsSyncLoading>();
        scriptsStack.addAll(scriptsList);
        injectScript(scriptsStack, callback);

    }

    private void injectScript(final Stack<ScriptsSyncLoading> scriptsStack, final Callback<Void, Exception> callback) {
        if (scriptsStack.empty()) {
            return;
        }

        String correctUrl = urlConverter.getModuleRelativeUrl(scriptsStack.pop().getUrl());

        scriptInjectorWrapper.fromUrl(correctUrl, new Callback<Void, Exception>() {
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
