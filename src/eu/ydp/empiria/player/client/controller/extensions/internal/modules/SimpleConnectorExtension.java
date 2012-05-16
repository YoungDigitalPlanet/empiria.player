package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import eu.ydp.empiria.player.client.controller.extensions.types.ModuleConnectorExtension;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleCreator;

/**
 * Klasa bedaca podstawowowa implementacja ModuleConnectorExtension.<br/>
 *
 */
public class SimpleConnectorExtension extends ModuleExtension implements ModuleConnectorExtension {

	private Factory<? extends IModule> clazz;
	private String tagName;
	private boolean isMultiViewModule = false;
	private boolean isInlineModule = false;

	/**
	 * domyslnie isMultiViewModule=false,isInlineModule=false
	 *
	 * @param clazz
	 *            klasa modulu
	 * @param tagName
	 *            nazwa taga który ma by obslugiwany
	 */
	public SimpleConnectorExtension(Factory< ? extends IModule> clazz, ModuleTagName tagName) {
		this(clazz, tagName, false);
	}

	/**
	 * domyslnie isInlineModule=false
	 *
	 * @param clazz
	 *            klasa modulu
	 * @param tagName
	 *            nazwa taga który ma by obslugiwany
	 * @param isMultiViewModule
	 *            czy modul jest multiView
	 */
	public SimpleConnectorExtension(Factory< ? extends IModule> clazz, ModuleTagName tagName, boolean isMultiViewModule) {
		this(clazz, tagName, isMultiViewModule, false);
	}

	/**
	 * @param clazz
	 *            klasa modulu
	 * @param tagName
	 *            nazwa taga który ma by obslugiwany
	 * @param isMultiViewModule
	 *            czy modul jest multiView
	 * @param isInlineModule
	 */
	public SimpleConnectorExtension(Factory< ? extends IModule> clazz, ModuleTagName tagName, boolean isMultiViewModule, boolean isInlineModule) {
		this.clazz = clazz;
		this.tagName = tagName.tagName();
		this.isMultiViewModule = isMultiViewModule;
		this.isInlineModule = isInlineModule;
	}

	@Override
	public ModuleCreator getModuleCreator() {
		return new SimpleModuleCreator(clazz, isMultiViewModule, isInlineModule);
	}

	@Override
	public String getModuleNodeName() {
		return tagName;
	}

}
