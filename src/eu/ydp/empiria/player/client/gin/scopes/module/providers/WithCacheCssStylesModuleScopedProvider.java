package eu.ydp.empiria.player.client.gin.scopes.module.providers;

import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.data.ElementStyleSelectorBuilder;
import eu.ydp.empiria.player.client.style.ModuleStyle;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class WithCacheCssStylesModuleScopedProvider implements Provider<ModuleStyle> {

	@Inject private ElementStyleSelectorBuilder elementStyleSelectorBuilder;
	@Inject @ModuleScoped private Provider<Element> xmlProvider;
	@Inject @ModuleScoped private Provider<ModuleStyle> moduleStyleProvider;

	private final Map<String, ModuleStyle> cache = Maps.newHashMap();

	@Override
	public ModuleStyle get() {
		Element currentElement = xmlProvider.get();
		String cacheKey = getCacheKey(currentElement);
		if (cache.containsKey(cacheKey)) {
			return cache.get(cacheKey);
		}
		return createModuleStyleAndPutToCache(cacheKey);
	}

	private ModuleStyle createModuleStyleAndPutToCache(String cacheKey) {
		ModuleStyle moduleStyle = createModuleStyle();
		cache.put(cacheKey, moduleStyle);
		return moduleStyle;
	}

	private ModuleStyle createModuleStyle() {
		return moduleStyleProvider.get();
	}

	private String getCacheKey(Element currentElement) {
		List<String> elementSelectors = elementStyleSelectorBuilder.getElementSelectors(currentElement);
		return Joiner.on(" ").join(elementSelectors);
	}

}
