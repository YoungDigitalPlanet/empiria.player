package eu.ydp.empiria.player.client.module.registry;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import eu.ydp.empiria.player.client.module.core.base.IModule;

public interface ModulesRegistrySocket {

    public boolean isModuleSupported(String nodeName);

    public boolean isMultiViewModule(Element element);

    public boolean isInlineModule(String nodeName);

    public IModule createModule(Element node);
}
