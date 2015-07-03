package eu.ydp.empiria.player.client.controller.variables.objects.response;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class ValueBean {
    @XmlValue
    private String value;
    @XmlAttribute
    private String forIndex;
    @XmlAttribute
    private String group;
    @XmlAttribute
    private String groupMode;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getForIndex() {
        return forIndex;
    }

    public void setForIndex(String forIndex) {
        this.forIndex = forIndex;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupMode() {
        return groupMode;
    }

    public void setGroupMode(String groupItem) {
        this.groupMode = groupItem;
    }

}
