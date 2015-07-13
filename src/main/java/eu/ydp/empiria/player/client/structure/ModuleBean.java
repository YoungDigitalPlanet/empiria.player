package eu.ydp.empiria.player.client.structure;

import eu.ydp.gwtutil.client.StringUtils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class ModuleBean {

    @XmlAttribute
    protected String id = StringUtils.EMPTY_STRING;

    @XmlAttribute(name = "class")
    protected String type = StringUtils.EMPTY_STRING;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
