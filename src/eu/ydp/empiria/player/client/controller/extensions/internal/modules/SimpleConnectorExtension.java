package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.types.ModuleConnectorExtension;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.SimpleModuleCreator;

/**
 * Klasa bedaca podstawowowa implementacja ModuleConnectorExtension.<br/>
 *
 */
public class SimpleConnectorExtension extends ModuleExtension implements ModuleConnectorExtension {

	private final Factory<? extends IModule> clazz;
	private final Provider<? extends IModule> clazzProvider;
	private final String tagName;
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

	public SimpleConnectorExtension(Provider<? extends IModule> clazz,ModuleTagName tagName) {
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

	public SimpleConnectorExtension(Provider<? extends IModule> clazz, ModuleTagName tagName, boolean isMultiViewModule) {
		this(null, clazz, tagName, isMultiViewModule, false);
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
		this(clazz,null, tagName, isMultiViewModule, false);
	}
	public SimpleConnectorExtension(Provider< ? extends IModule> clazz, ModuleTagName tagName, boolean isMultiViewModule, boolean isInlineModule) {
		this(null,clazz, tagName, isMultiViewModule, false);
	}
	private SimpleConnectorExtension(Factory< ? extends IModule> clazz,Provider<? extends IModule> clazzProvider, ModuleTagName tagName, boolean isMultiViewModule, boolean isInlineModule) {
		this.clazz = clazz;
		this.tagName = tagName.tagName();
		this.isMultiViewModule = isMultiViewModule;
		this.isInlineModule = isInlineModule;
		this.clazzProvider = clazzProvider;
	}


	@Override
	public ModuleCreator getModuleCreator() {
		ModuleCreator creator;
		if(clazz==null){
			creator = new SimpleModuleCreator<IModule>(clazzProvider,isMultiViewModule,isInlineModule);
		}else{
			creator = new SimpleModuleCreator<IModule>(clazz, isMultiViewModule, isInlineModule);
		}
		return creator;
	}

	@Override
	public String getModuleNodeName() {
		return tagName;
	}

}
