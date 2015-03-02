package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;

public abstract class ModuleExtension extends InternalExtension {

	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_MODULE;
	}

	@Override
	public void init() {

	}

}
