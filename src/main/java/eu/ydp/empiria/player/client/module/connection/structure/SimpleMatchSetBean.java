package eu.ydp.empiria.player.client.module.connection.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "simpleMatchSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleMatchSetBean {

    @XmlElement(name = "simpleAssociableChoice")
    private List<SimpleAssociableChoiceBean> simpleAssociableChoices;

    public SimpleMatchSetBean() {
        simpleAssociableChoices = new ArrayList<SimpleAssociableChoiceBean>();
    }

    public List<SimpleAssociableChoiceBean> getSimpleAssociableChoices() {
        return simpleAssociableChoices;
    }

    public void setSimpleAssociableChoices(List<SimpleAssociableChoiceBean> simpleAssociableChoices) {
        this.simpleAssociableChoices = simpleAssociableChoices;
    }
}
