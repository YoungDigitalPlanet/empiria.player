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

package eu.ydp.empiria.player.client.module.choice.structure;

import eu.ydp.empiria.player.client.AbstractJAXBTestBase;
import eu.ydp.gwtutil.client.StringUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SimpleChoiceBeanJUnitTest extends AbstractJAXBTestBase<SimpleChoiceBean> {

    @Test
    public void shouldReturnSimpleChoice() {
        String nodeString = "<simpleChoice identifier=\"CHOICE_RESPONSE_1_2\">The Pyrenees</simpleChoice>";
        SimpleChoiceBean simpleChoice = createBeanFromXMLString(nodeString);

        assertThat(simpleChoice.getIdentifier(), is(equalTo("CHOICE_RESPONSE_1_2")));
    }

    @Test
    public void shouldReturnSimpleChoiceWhen_emptyValues() {
        String nodeString = "<simpleChoice/>";
        SimpleChoiceBean simpleChoice = createBeanFromXMLString(nodeString);

        assertThat(simpleChoice.getIdentifier(), is(equalTo(StringUtils.EMPTY_STRING)));
    }
}
