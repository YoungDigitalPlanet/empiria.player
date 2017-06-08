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

package eu.ydp.empiria.player.client.controller.variables.objects.response;

import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

/*Extracted from class eu.ydp.empiria.player.client.controller.variables.objects.response.Response
 * to separate object responsibilities from object creation (like xml parsing)
 * 
 * TODO: Refactor me please (I'm ugly!)
 */

public class ResponseNodeParser {

    private final ResponseNodeJAXBParserFactory responseNodeJAXBParserFactory;
    private final ResponseBeanConverter responseBeanConverter;

    @Inject
    public ResponseNodeParser(ResponseNodeJAXBParserFactory responseNodeJAXBParserFactory, ResponseBeanConverter responseBeanConverter) {
        this.responseNodeJAXBParserFactory = responseNodeJAXBParserFactory;
        this.responseBeanConverter = responseBeanConverter;
    }

    public Response parseResponseFromNode(String responseDeclaration) {
        JAXBParser<ResponseBean> jaxbParser = responseNodeJAXBParserFactory.create();
        ResponseBean responseJAXBModel = jaxbParser.parse(responseDeclaration);
        return responseBeanConverter.convert(responseJAXBModel);
    }

}
