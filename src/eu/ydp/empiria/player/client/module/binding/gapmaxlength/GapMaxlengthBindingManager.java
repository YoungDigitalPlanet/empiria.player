package eu.ydp.empiria.player.client.module.binding.gapmaxlength;

import eu.ydp.empiria.player.client.module.binding.BindingContext;
import eu.ydp.empiria.player.client.module.binding.BindingManagerBase;
import eu.ydp.empiria.player.client.module.binding.BindingType;

public class GapMaxlengthBindingManager extends BindingManagerBase {

	public GapMaxlengthBindingManager(boolean acceptOnlyEmptyGroupIdentifier) {
		super(acceptOnlyEmptyGroupIdentifier);
	}

	@Override
	protected BindingContext createNewBindingContext() {
		return new GapMaxlengthBindingContext(BindingType.GAP_MAXLENGHTS);
	}

	
}
