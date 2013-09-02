package eu.ydp.empiria.player.client.gin.scopes.module.providers;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.style.ModuleStyle;
import eu.ydp.empiria.player.client.style.StyleSocket;

public class CssStylesModuleScopedProvider implements Provider<ModuleStyle> {

	@Inject StyleSocket styleSocket;
	@Inject @ModuleScoped Provider<Element> xmlProvider;

	@Override
	public ModuleStyle get() {
		return new ModuleStyle(styleSocket.getStyles(xmlProvider.get()));
	}

}
