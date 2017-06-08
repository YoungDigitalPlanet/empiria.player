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

package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.ResultExtractorsFactory;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableResult;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;

public class ResultValueHandler extends ProviderItemValueHandlerBase {

    private ResultExtractorsFactory resultExtractorsFactory;

    @Inject
    public ResultValueHandler(SessionDataSupplier sessionDataSupplier,
                              ResultExtractorsFactory resultExtractorsFactory) {
        super(sessionDataSupplier);
        this.resultExtractorsFactory = resultExtractorsFactory;
    }

    @Override
    protected String countValue(ContentFieldInfo info, VariableProviderSocket provider) {
        VariableResult result = resultExtractorsFactory.createVariableResult(provider);
        return String.valueOf(result.getResult());
    }
}
