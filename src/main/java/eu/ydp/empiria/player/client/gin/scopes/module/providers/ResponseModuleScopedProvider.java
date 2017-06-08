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

package eu.ydp.empiria.player.client.gin.scopes.module.providers;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleCreationContext;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopeStack;
import eu.ydp.gwtutil.client.xml.XMLUtils;

import java.util.Map;

public class ResponseModuleScopedProvider implements Provider<Response> {

    @Inject
    private ModuleScopeStack moduleScopeStack;

    @PageScoped
    @Inject
    private ItemResponseManager responseManager;

    @Override
    public Response get() {
        String responseId = findResponseIdentifier();
        return getResponseForId(responseId);
    }

    private Response getResponseForId(String responseId) {
        Response response = responseManager.getVariable(responseId);
        return response;
    }

    private String findResponseIdentifier() {
        ModuleCreationContext context = moduleScopeStack.getCurrentTopContext();
        Element element = context.getXmlElement();
        String responseId = findResponseIdByXml(element);
        return responseId;
    }

    private String findResponseIdByXml(Element element) {
        String responseId = XMLUtils.getAttributeAsString(element, "responseIdentifier");
        return responseId;
    }

}
