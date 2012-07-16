package eu.ydp.empiria.player.client.module.binding.gapwidth;

import eu.ydp.empiria.player.client.module.binding.BindingContext;
import eu.ydp.empiria.player.client.module.binding.BindingManagerBase;
import eu.ydp.empiria.player.client.module.binding.BindingType;

public class GapWidthBindingManager extends BindingManagerBase {

	public GapWidthBindingManager(boolean acceptEmptyGroupIdentifier) {
		super(acceptEmptyGroupIdentifier);
	}

	@Override
	protected BindingContext createNewBindingContext() {
		return new GapWidthBindingContext(BindingType.GAP_WIDTHS);
	}

	
}
