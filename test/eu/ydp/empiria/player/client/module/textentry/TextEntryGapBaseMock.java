package eu.ydp.empiria.player.client.module.textentry;

import eu.ydp.empiria.player.client.module.gap.GapModulePresenter;

public class TextEntryGapBaseMock extends TextEntryGapBase {
	@Override
	public void addPlayerEventHandlers() {
		super.addPlayerEventHandlers();
	}

	public void setPresenter(GapModulePresenter presenter) {
		this.presenter = presenter;
	}
}
