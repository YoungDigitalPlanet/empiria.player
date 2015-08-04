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
