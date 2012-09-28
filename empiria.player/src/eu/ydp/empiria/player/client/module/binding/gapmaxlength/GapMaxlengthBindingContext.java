package eu.ydp.empiria.player.client.module.binding.gapmaxlength;

import java.util.ArrayList;
import java.util.List;

import eu.ydp.empiria.player.client.module.binding.Bindable;
import eu.ydp.empiria.player.client.module.binding.BindingContext;
import eu.ydp.empiria.player.client.module.binding.BindingType;

public class GapMaxlengthBindingContext implements BindingContext {

	List<Bindable> bindables;
	private BindingType type;
	
	public GapMaxlengthBindingContext(BindingType type){
		this.type = type;
		bindables = new ArrayList<Bindable>();
	}
	
	@Override
	public boolean add(Bindable bindable) {
		bindables.add(bindable);
		return true;
	}

	public GapMaxlengthBindingValue getGapMaxlengthBindingOutcomeValue() {
		GapMaxlengthBindingValue value = new GapMaxlengthBindingValue(0);
		for (Bindable b : bindables){
			value.merge((GapMaxlengthBindingValue) b.getBindingValue(type));
		}
		return value;
	}

}
