package eu.ydp.empiria.player.client.module;

import com.google.gwt.core.client.GWT;

/**
 * Prosta implementacja ModuleCreator<br/>
 * createModule poprzez {@link GWT#create(Class)}
 *
 */
public class SimpleModuleCreator<T extends IModule> implements ModuleCreator {

	protected boolean isInlineModule;
	protected boolean isMultiViewModule;
	private Factory<? extends IModule> factory;

	/**
	 * @param factory
	 *            klasa modulu
	 * @param isMultiViewModule
	 *            czy modul jest multiView
	 * @param isInlineModule
	 */
	public SimpleModuleCreator(Factory<? extends IModule> factory, boolean isMultiViewModule, boolean isInlineModule) {
		this.isInlineModule = isInlineModule;
		this.isMultiViewModule = isMultiViewModule;
		this.factory = factory;
	}

	@Override
	public boolean isMultiViewModule() {
		return isMultiViewModule;
	}

	@Override
	public boolean isInlineModule() {
		return isInlineModule;
	}

	@Override
	public IModule createModule() {
		return factory.getNewInstance();
	}

}
