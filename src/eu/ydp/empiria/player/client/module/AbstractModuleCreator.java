package eu.ydp.empiria.player.client.module;

/**
 * Klasa bedca prost implementacja ModuleCreator. Nalezy zaimplementowac medode tworzaca modul
 * 
 */
public abstract class AbstractModuleCreator extends SimpleModuleCreator<IModule> {
	/**
	 * taki sam efekt jak {@link AbstractModuleCreator(false,false)}
	 */
	public AbstractModuleCreator() {
		super(false, false);
	}

	/**
	 * @param isMultiViewModule
	 *            czy modul jest multiView
	 * @param isInlineModule
	 *            czy modul jest inline
	 */
	public AbstractModuleCreator(boolean isMultiViewModule, boolean isInlineModule) {
		super(isMultiViewModule, isInlineModule);
	}

	@Override
	public abstract IModule createModule();
}
