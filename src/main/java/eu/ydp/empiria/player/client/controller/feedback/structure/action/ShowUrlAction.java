package eu.ydp.empiria.player.client.controller.feedback.structure.action;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "showUrl")
public class ShowUrlAction implements FeedbackUrlAction {

    @XmlElement(name = "source")
    private List<ShowUrlActionSource> sources = Lists.newArrayList();

    @XmlAttribute(name = "href")
    private String href;

    @XmlAttribute(name = "type")
    private String type;

    public List<ShowUrlActionSource> getSources() {
        return sources;
    }

    public void setSources(List<ShowUrlActionSource> sources) {
        this.sources = sources;
    }

    @Override
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getSourcesWithTypes() {
        Map<String, String> sourcesWithTypes = new HashMap<String, String>();

        for (ShowUrlActionSource actionSource : getSources()) {
            sourcesWithTypes.put(actionSource.getSrc(), actionSource.getType());
        }

        return sourcesWithTypes;
    }

    @Override
    public String toString() {
        return "ShowUrlAction [href=" + href + ", type=" + type + "]";
    }
}
