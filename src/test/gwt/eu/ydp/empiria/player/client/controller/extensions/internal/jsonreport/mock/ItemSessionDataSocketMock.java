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

package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.mock;

import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;

public class ItemSessionDataSocketMock implements ItemSessionDataSocket {

    private VariableProviderSocket variableProvider;

    public void setVariableProvider(VariableProviderSocket variableProvider) {
        this.variableProvider = variableProvider;
    }

    @Override
    public JavaScriptObject getJsSocket() {
        return null;
    }

    @Override
    public VariableProviderSocket getVariableProviderSocket() {
        return variableProvider;
    }

    @Override
    public int getActualTime() {
        return 0;
    }

}
