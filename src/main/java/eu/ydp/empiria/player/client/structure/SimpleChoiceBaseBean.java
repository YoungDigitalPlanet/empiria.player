package eu.ydp.empiria.player.client.structure;

import eu.ydp.gwtutil.client.StringUtils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class SimpleChoiceBaseBean {

    @XmlAttribute
    protected String identifier = StringUtils.EMPTY_STRING;
    ;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
