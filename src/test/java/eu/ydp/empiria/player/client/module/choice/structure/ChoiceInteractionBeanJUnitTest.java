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

public class ChoiceInteractionBeanJUnitTest extends AbstractJAXBTestBase<ChoiceInteractionBean> {

    @Test
    public void shouldReturnChoiceInteraction() {
        String xmlString = "<choiceInteraction id=\"dummy1_4\" maxChoices=\"1\" responseIdentifier=\"CHOICE_RESPONSE_1\" shuffle=\"false\" >"
                + "<prompt>This is prompt</prompt>"
                + "<simpleChoice identifier=\"CHOICE_RESPONSE_1_0\" >The Alps</simpleChoice>"
                + "<simpleChoice identifier=\"CHOICE_RESPONSE_1_1\" >The Carpathians</simpleChoice>"
                + "<simpleChoice identifier=\"CHOICE_RESPONSE_1_2\" >The Pyrenees</simpleChoice>"
                + "<simpleChoice identifier=\"CHOICE_RESPONSE_1_3\" >The Scandinavian Mountains</simpleChoice>"
                + "<simpleChoice identifier=\"CHOICE_RESPONSE_1_4\" >Dinaric Alps</simpleChoice>"
                + "<feedbackInline senderIdentifier=\"^CHOICE_RESPONSE_1$\" showHide=\"show\" sound=\"media/ok.mp3\" value=\"(\\+CHOICE_RESPONSE_1_3.*)|(-(CHOICE_RESPONSE_1_0|CHOICE_RESPONSE_1_1|CHOICE_RESPONSE_1_2|CHOICE_RESPONSE_1_4).*)\" variableIdentifier=\"CHOICE_RESPONSE_1-LASTCHANGE\" />"
                + "<feedbackInline senderIdentifier=\"^CHOICE_RESPONSE_1$\" showHide=\"show\" sound=\"media/wrong.mp3\" value=\"(-CHOICE_RESPONSE_1_3.*)|(\\+(CHOICE_RESPONSE_1_0|CHOICE_RESPONSE_1_1|CHOICE_RESPONSE_1_2|CHOICE_RESPONSE_1_4).*)\" variableIdentifier=\"CHOICE_RESPONSE_1-LASTCHANGE\" />"
                + "</choiceInteraction>";
        ChoiceInteractionBean choiceInteraction = createBeanFromXMLString(xmlString);

        assertThat(choiceInteraction.getId(), is(equalTo("dummy1_4")));
        assertThat(choiceInteraction.getMaxChoices(), is(equalTo(1)));
        assertThat(choiceInteraction.getPrompt(), is(equalTo("This is prompt")));
        assertThat(choiceInteraction.getResponseIdentifier(), is(equalTo("CHOICE_RESPONSE_1")));
        assertThat(choiceInteraction.isShuffle(), is(equalTo(false)));
        assertThat(choiceInteraction.getSimpleChoices().size(), is(equalTo(5)));
    }

    @Test
    public void shouldReturnChoiceInteractionWhen_isEmpty() {
        String xmlString = "<choiceInteraction/>";
        ChoiceInteractionBean choiceInteraction = createBeanFromXMLString(xmlString);

        assertThat(choiceInteraction.getId(), is(equalTo(StringUtils.EMPTY_STRING)));
        assertThat(choiceInteraction.getId(), is(equalTo(StringUtils.EMPTY_STRING)));
        assertThat(choiceInteraction.getMaxChoices(), is(equalTo(0)));
        assertThat(choiceInteraction.getPrompt(), is(equalTo(StringUtils.EMPTY_STRING)));
        assertThat(choiceInteraction.getResponseIdentifier(), is(equalTo(StringUtils.EMPTY_STRING)));
        assertThat(choiceInteraction.isShuffle(), is(equalTo(false)));
        assertThat(choiceInteraction.getSimpleChoices().size(), is(equalTo(0)));
    }

}
