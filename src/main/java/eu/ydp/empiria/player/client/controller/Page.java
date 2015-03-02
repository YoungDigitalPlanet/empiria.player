package eu.ydp.empiria.player.client.controller;

import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;

public class Page extends InternalExtension implements FlowDataSocketUserExtension {

	private static FlowDataSupplier supplier;
	protected boolean isInitialized = false;

	public int getCurrentPageNumber() {
		return supplier == null ? 0 : supplier.getCurrentPageIndex();
	}

	public int getPageCount() {
		return supplier == null ? 0 : supplier.getPageCount();
	}

	@Override
	public void setFlowDataSupplier(FlowDataSupplier supplier) {
		Page.supplier = supplier;
		if (supplier != null) {
			isInitialized = true;
		}
	}

	public boolean isNotLastPage(int pageIndex) {
		return pageIndex < getPageCount() - 1;
	}

	@Override
	public void init() {// NOPMD

	}
}