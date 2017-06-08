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

package eu.ydp.empiria.player.client.module.expression;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExpressionCreatorJUnitTest {

    private ExpressionCreator expressionCreator;
    private ResponsesTestingHelper responsesHelper;

    @Before
    public void before() {
        expressionCreator = new ExpressionCreator();
        responsesHelper = new ResponsesTestingHelper();
    }

    @Test
    public void getExpressionTest() {
        ExpressionBean expressionBean = new ExpressionBean();
        List<Response> responses = Lists.newArrayList(responsesHelper.getResponse("a", "0"), responsesHelper.getResponse("b", "1"),
                responsesHelper.getResponse("c", "2"), responsesHelper.getResponse("d", "3"));
        expressionBean.getResponses().addAll(responses);
        expressionBean.setTemplate("'a'+5*'b'+3>='c'");

        String result = expressionCreator.getExpression(expressionBean);

        assertEquals("0+5*1+3>=2", result);
    }

}
