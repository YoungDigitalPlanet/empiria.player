package eu.ydp.empiria.player.client.module.binding.gapmaxlength;

import eu.ydp.empiria.player.client.module.binding.BindingOutcomeValue;
import eu.ydp.empiria.player.client.module.binding.BindingValue;

public class GapMaxlengthBindingValue implements BindingValue, BindingOutcomeValue {

    private int charsCount;

    public GapMaxlengthBindingValue(int charsCount) {
        this.charsCount = charsCount;
    }

    public int getGapCharactersCount() {
        return charsCount;
    }

    void merge(GapMaxlengthBindingValue v) {
        if (v.getGapCharactersCount() > getGapCharactersCount()) {
            charsCount = v.getGapCharactersCount();
        }
    }
}
