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

package eu.ydp.empiria.player.client.module.accordion.structure;

import eu.ydp.empiria.player.client.module.accordion.Transition;
import eu.ydp.empiria.player.client.structure.ModuleBean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "accordion")
public class AccordionBean extends ModuleBean {

    @XmlAttribute
    private Transition transition;
    @XmlElement(name = "section")
    private List<AccordionSectionBean> sections;

    public AccordionBean() {
        sections = new ArrayList<>();
        transition = Transition.ALL;
    }

    public Transition getTransition() {
        return transition;
    }

    public void setTransition(Transition transition) {
        this.transition = transition;
    }

    public List<AccordionSectionBean> getSections() {
        return sections;
    }

    public void setSections(List<AccordionSectionBean> sections) {
        this.sections = sections;
    }
}
