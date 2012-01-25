package eu.ydp.empiria.player.client.controller.extensions.internal;

import eu.ydp.empiria.player.client.controller.extensions.Extension;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;

public abstract class InternalExtension extends Extension {

	public ExtensionType getType(){
		return ExtensionType.MULTITYPE;
	}
}
