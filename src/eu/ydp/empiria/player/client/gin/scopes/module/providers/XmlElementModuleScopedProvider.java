package eu.ydp.empiria.player.client.gin.scopes.module.providers;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.gwtutil.client.gin.scopes.module.ModuleCreationContext;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopeStack;

public class XmlElementModuleScopedProvider implements Provider<Element>{

	@Inject
	private ModuleScopeStack moduleScopeStack;

	@Override
	public Element get() {
		ModuleCreationContext context = moduleScopeStack.getCurrentTopContext();
		return context.getXmlElement();
	}
}
