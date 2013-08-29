package eu.ydp.empiria.player.client.gin.scopes.module.providers;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Map;

import org.fest.assertions.data.MapEntry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gwt.xml.client.Element;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.data.ElementStyleSelectorBuilder;
import eu.ydp.empiria.player.client.style.ModuleStyle;
import eu.ydp.empiria.player.client.style.StyleSocket;


@RunWith(MockitoJUnitRunner.class)
public class WithCacheCssStylesModuleScopedProviderTest {
	@Mock private ElementStyleSelectorBuilder elementStyleSelectorBuilder;
	@Mock private StyleSocket styleSocket;
	@Mock private Provider<Element> xmlProvider;
	@Mock Element element;

	@InjectMocks private WithCacheCssStylesModuleScopedProvider instance;

	private final List<String> selectors = Lists.newArrayList("3 3");
	private final Map<String, String> styleMap = Maps.newHashMap();


	@Before
	public void before() {
		doReturn(element).when(xmlProvider).get();
		doReturn(selectors).when(elementStyleSelectorBuilder).getElementSelectors(eq(element));
		doReturn(styleMap).when(styleSocket).getStyles(eq(element));
		styleMap.put("1", "1");
	}

	@Test
	public void get_NoDataInCache() throws Exception {
		ModuleStyle moduleStyle = instance.get();
		assertThat(moduleStyle).isNotNull();
		assertThat(moduleStyle).hasSize(1);
		assertThat(moduleStyle).contains(MapEntry.entry("1", "1"));

		verify(styleSocket).getStyles(eq(element));
	}

	@Test
	public void get_FromCache() throws Exception {
		instance.get();
		ModuleStyle moduleStyle = instance.get();

		assertThat(moduleStyle).isNotNull();
		assertThat(moduleStyle).hasSize(1);
		assertThat(moduleStyle).contains(MapEntry.entry("1", "1"));
		//only once
		verify(styleSocket).getStyles(eq(element));
	}

}
