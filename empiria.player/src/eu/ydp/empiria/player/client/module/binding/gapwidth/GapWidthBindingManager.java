package eu.ydp.empiria.player.client.module.binding.gapwidth;

import eu.ydp.empiria.player.client.module.binding.BindingContext;
import eu.ydp.empiria.player.client.module.binding.BindingManagerBase;
import eu.ydp.empiria.player.client.module.binding.BindingType;

public class GapWidthBindingManager extends BindingManagerBase {

	public GapWidthBindingManager(boolean acceptOnlyEmptyGroupIdentifier) {
		super(acceptOnlyEmptyGroupIdentifier);
	}

	@Override
	protected BindingContext createNewBindingContext() {
		return new GapWidthBindingContext(BindingType.GAP_WIDTHS);
	}

	
}
