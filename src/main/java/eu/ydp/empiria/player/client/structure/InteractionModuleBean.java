package eu.ydp.empiria.player.client.structure;

import eu.ydp.gwtutil.client.StringUtils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class InteractionModuleBean extends ModuleBean {

    @XmlAttribute
    protected String responseIdentifier = StringUtils.EMPTY_STRING;

    public String getResponseIdentifier() {
        return responseIdentifier;
    }

    public void setResponseIdentifier(String responseIdentifier) {
        this.responseIdentifier = responseIdentifier;
    }
}
