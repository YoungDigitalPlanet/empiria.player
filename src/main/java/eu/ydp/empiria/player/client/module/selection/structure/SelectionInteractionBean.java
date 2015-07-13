package eu.ydp.empiria.player.client.module.selection.structure;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.HasShuffle;
import eu.ydp.empiria.player.client.structure.InteractionModuleBean;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "selectionInteraction")
@XmlAccessorType(XmlAccessType.NONE)
public class SelectionInteractionBean extends InteractionModuleBean implements HasShuffle {

    @XmlAttribute
    private int matchMax;

    @XmlAttribute
    private int maxAssociations;

    @XmlAttribute
    private boolean multi;

    @XmlAttribute
    private boolean shuffle;

    @XmlAttribute
    private boolean ignored;

    @XmlElement(name = "simpleChoice")
    private List<SelectionSimpleChoiceBean> simpleChoices;

    @XmlElement(name = "item")
    private List<SelectionItemBean> items;

    public int getMatchMax() {
        return matchMax;
    }

    public void setMatchMax(int matchMax) {
        this.matchMax = matchMax;
    }

    public List<SelectionSimpleChoiceBean> getSimpleChoices() {
        return simpleChoices;
    }

    public void setSimpleChoices(List<SelectionSimpleChoiceBean> simpleChoices) {
        this.simpleChoices = simpleChoices;
    }

    public int getMaxAssociations() {
        return maxAssociations;
    }

    public void setMaxAssociations(int maxAssociations) {
        this.maxAssociations = maxAssociations;
    }

    public boolean isMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }

    public List<SelectionItemBean> getItems() {
        return items;
    }

    public void setItems(List<SelectionItemBean> items) {
        this.items = items;
    }

    @Override
    public boolean isShuffle() {
        return shuffle;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public boolean isIgnored() {
        return ignored;
    }

    public void setIgnored(boolean ignored) {
        this.ignored = ignored;
    }
}
