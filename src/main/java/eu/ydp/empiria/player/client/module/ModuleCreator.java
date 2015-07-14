package eu.ydp.empiria.player.client.module;

public interface ModuleCreator {

    /**
     * Zwraca instancje modulu
     *
     * @return
     */
    public IModule createModule();

    /**
     * Czy modul jest multiview
     *
     * @return
     */
    public boolean isMultiViewModule();

    public boolean isInlineModule();
}
