package eu.ydp.empiria.player.client.module;


/**
 * Prosta implementacja ModuleCreator<br/>
 *
 */
public class SimpleModuleCreator<T extends IModule> implements ModuleCreator {

	private final boolean inlineModule;
	private final boolean multiViewModule;
	private final Factory<? extends IModule> factory;

	/**
	 * @param factory
	 *            klasa modulu
	 * @param isMultiViewModule
	 *            czy modul jest multiView
	 * @param isInlineModule
	 */
	public SimpleModuleCreator(Factory<? extends IModule> factory, boolean isMultiViewModule, boolean isInlineModule) {
		this.inlineModule = isInlineModule;
		this.multiViewModule = isMultiViewModule;
		this.factory = factory;
	}

	@Override
	public boolean isMultiViewModule() {
		return multiViewModule;
	}

	@Override
	public boolean isInlineModule() {
		return inlineModule;
	}

	@Override
	public IModule createModule() {
		return factory.getNewInstance();
	}

}
