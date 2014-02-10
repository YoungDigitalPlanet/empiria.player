package eu.ydp.empiria.player.client.controller.extensions.internal;

import eu.ydp.empiria.player.client.controller.extensions.Extension;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;

public abstract class InternalExtension implements Extension {

	@Override
	public ExtensionType getType() {
		return ExtensionType.MULTITYPE;
	}
}
