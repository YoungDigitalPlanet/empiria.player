package eu.ydp.empiria.player.client.module.binding.gapwidth;

import java.util.ArrayList;
import java.util.List;

import eu.ydp.empiria.player.client.module.binding.Bindable;
import eu.ydp.empiria.player.client.module.binding.BindingContext;
import eu.ydp.empiria.player.client.module.binding.BindingType;

public class GapWidthBindingContext implements BindingContext {

	List<Bindable> bindables;
	private BindingType type;

	public GapWidthBindingContext(BindingType type) {
		this.type = type;
		bindables = new ArrayList<Bindable>();
	}

	@Override
	public boolean add(Bindable bindable) {
		bindables.add(bindable);
		return true;
	}

	public GapWidthBindingValue getGapWidthBindingOutcomeValue() {
		GapWidthBindingValue value = new GapWidthBindingValue(0);
		for (Bindable b : bindables) {
			value.merge((GapWidthBindingValue) b.getBindingValue(type));
		}
		return value;
	}

}
