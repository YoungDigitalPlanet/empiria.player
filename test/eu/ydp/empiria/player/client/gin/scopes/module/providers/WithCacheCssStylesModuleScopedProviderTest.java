package eu.ydp.empiria.player.client.gin.scopes.module.providers;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.google.gwt.xml.client.Element;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.data.ElementStyleSelectorBuilder;
import eu.ydp.empiria.player.client.style.ModuleStyle;

@RunWith(MockitoJUnitRunner.class)
public class WithCacheCssStylesModuleScopedProviderTest {
	@Mock
	private ElementStyleSelectorBuilder elementStyleSelectorBuilder;
	@Mock
	private Provider<Element> xmlProvider;
	@Mock
	private Provider<ModuleStyle> moduleStyleProvider;
	@Mock
	private ModuleStyle moduleStyle;
	@Mock
	Element element;

	@InjectMocks
	private WithCacheCssStylesModuleScopedProvider instance;

	private final List<String> selectors = Lists.newArrayList("3 3");

	@Before
	public void before() {
		doReturn(element).when(xmlProvider).get();
		doReturn(selectors).when(elementStyleSelectorBuilder).getElementSelectors(eq(element));
		doReturn(moduleStyle).when(moduleStyleProvider).get();
	}

	@Test
	public void get_NoDataInCache() throws Exception {
		ModuleStyle moduleStyle = instance.get();
		assertThat(moduleStyle).isNotNull();

		verify(moduleStyleProvider).get();
	}

	@Test
	public void get_FromCache() throws Exception {
		instance.get();
		ModuleStyle moduleStyle = instance.get();

		assertThat(moduleStyle).isNotNull();
		// only once
		verify(moduleStyleProvider).get();
	}

}
