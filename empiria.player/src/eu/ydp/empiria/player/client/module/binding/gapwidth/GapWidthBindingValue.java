package eu.ydp.empiria.player.client.module.binding.gapwidth;

import eu.ydp.empiria.player.client.module.binding.BindingOutcomeValue;
import eu.ydp.empiria.player.client.module.binding.BindingValue;

public class GapWidthBindingValue implements BindingValue, BindingOutcomeValue {
	
	private int charsCount;

	public GapWidthBindingValue(int charsCount){
		this.charsCount = charsCount;
	}

	public int getGapCharactersCount(){
		return charsCount;
	}
	
	void merge(GapWidthBindingValue v){
		if (v.getGapCharactersCount() > getGapCharactersCount()){
			charsCount = v.getGapCharactersCount();
		}
	}
}
