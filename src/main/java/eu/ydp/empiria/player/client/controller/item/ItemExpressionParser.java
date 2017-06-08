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

package eu.ydp.empiria.player.client.controller.item;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.expression.ExpressionListBuilder;

import java.util.Map;

public class ItemExpressionParser {
    @Inject
    private ExpressionListBuilder expressionListBuilder;

    @Inject
    @PageScoped
    private ItemXMLWrapper xmlMapper;

    @Inject
    @PageScoped
    private ItemResponseManager responseManager;

    public void parseAndConnectExpressions() {
        NodeList expressionsNodes = xmlMapper.getExpressions();

        for (int i = 0; i < expressionsNodes.getLength(); i++) {
            Element expressionsElement = (Element) expressionsNodes.item(i);
            String expressionsXml = expressionsElement.toString();
            expressionListBuilder.parseAndConnectExpressions(expressionsXml, responseManager);
        }
    }
}
