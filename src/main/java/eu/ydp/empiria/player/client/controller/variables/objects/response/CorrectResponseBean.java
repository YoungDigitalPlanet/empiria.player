package eu.ydp.empiria.player.client.controller.variables.objects.response;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class CorrectResponseBean {

    @XmlElement(name = "value")
    private List<ValueBean> values;

    public List<ValueBean> getValues() {
        return values;
    }

    public void setValues(List<ValueBean> values) {
        this.values = values;
    }

}
