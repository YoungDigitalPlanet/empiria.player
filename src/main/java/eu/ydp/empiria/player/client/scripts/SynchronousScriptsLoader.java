/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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

        RequestCallback nextScriptLoaderCallback = createNextScriptLoaderCallback(scriptsStack, callback);

        injectScript(scriptsStack, nextScriptLoaderCallback);
    }

    private RequestCallback createNextScriptLoaderCallback(final Stack<ScriptUrl> scriptsStack, final SynchronousScriptsCallback finalCallback) {
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
