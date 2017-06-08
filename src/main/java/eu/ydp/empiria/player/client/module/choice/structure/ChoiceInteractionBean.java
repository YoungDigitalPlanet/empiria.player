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

import eu.ydp.empiria.player.client.module.abstractmodule.structure.HasShuffle;
import eu.ydp.empiria.player.client.structure.InteractionModuleBean;
import eu.ydp.gwtutil.client.StringUtils;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "choiceInteraction")
public class ChoiceInteractionBean extends InteractionModuleBean implements HasShuffle {

    @XmlAttribute
    private int maxChoices;
    @XmlAttribute
    private boolean shuffle;
    @XmlAttribute
    private boolean ignored;
    @XmlElement(name = "simpleChoice")
    private List<SimpleChoiceBean> simpleChoices;
    @XmlElement
    private String prompt;

    public ChoiceInteractionBean() {
        simpleChoices = new ArrayList<SimpleChoiceBean>();
        prompt = StringUtils.EMPTY_STRING;
    }

    public List<SimpleChoiceBean> getSimpleChoices() {
        return simpleChoices;
    }

    public void setSimpleChoices(List<SimpleChoiceBean> simpleChoices) {
        this.simpleChoices = simpleChoices;
    }

    public int getMaxChoices() {
        return maxChoices;
    }

    public void setMaxChoices(int maxChoices) {
        this.maxChoices = maxChoices;
    }

    @Override
    public boolean isShuffle() {
        return shuffle;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public boolean isIgnored() {
        return ignored;
    }

    public void setIgnored(boolean ignored) {
        this.ignored = ignored;
    }
}
