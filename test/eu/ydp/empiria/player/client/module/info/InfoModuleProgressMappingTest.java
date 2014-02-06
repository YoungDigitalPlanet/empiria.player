package eu.ydp.empiria.player.client.module.info;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RunWith(MockitoJUnitRunner.class)
public class InfoModuleProgressMappingTest {

	@Mock
	private InfoModuleCssProgressMappingConfigurationParser cssMappingParser;
	@InjectMocks
	private InfoModuleProgressMapping instance;
	String styleName = "xxx";

	@Test
	public void getStyleNameForProgress_FullRange() throws Exception {
		Map<Integer, String> progressToStyleMapping = Maps.newHashMap();
		doReturn(progressToStyleMapping).when(cssMappingParser).getCssProgressToStyleMapping();
		progressToStyleMapping.put(0, styleName);
		instance.postConstruct();

		String styleNameFromCss = instance.getStyleNameForProgress(10);
		assertThat(styleNameFromCss).isEqualTo(styleName);
	}

	@Test
	public void getStyleNameForProgress_NumberOfRange() throws Exception {
		Map<Integer, String> progressToStyleMapping = Maps.newHashMap();
		doReturn(progressToStyleMapping).when(cssMappingParser).getCssProgressToStyleMapping();
		progressToStyleMapping.put(0, styleName + 0);
		progressToStyleMapping.put(2, styleName + 2);
		progressToStyleMapping.put(9, styleName + 9);

		instance.postConstruct();

		String styleNameFromCss = instance.getStyleNameForProgress(1);
		assertThat(styleNameFromCss).isEqualTo(styleName + 0);
		styleNameFromCss = instance.getStyleNameForProgress(2);
		assertThat(styleNameFromCss).isEqualTo(styleName + 2);
		styleNameFromCss = instance.getStyleNameForProgress(7);
		assertThat(styleNameFromCss).isEqualTo(styleName + 2);
		styleNameFromCss = instance.getStyleNameForProgress(9);
		assertThat(styleNameFromCss).isEqualTo(styleName + 9);
		styleNameFromCss = instance.getStyleNameForProgress(19);
		assertThat(styleNameFromCss).isEqualTo(styleName + 9);
		styleNameFromCss = instance.getStyleNameForProgress(100);
		assertThat(styleNameFromCss).isEqualTo(styleName + 9);
	}

	@Test
	public void getStyleNameForProgress_ZeroPercentNotDefined() throws Exception {
		Map<Integer, String> progressToStyleMapping = Maps.newHashMap();
		doReturn(progressToStyleMapping).when(cssMappingParser).getCssProgressToStyleMapping();
		String defaultStyleName = styleName + 10;
		progressToStyleMapping.put(10, defaultStyleName);

		instance.postConstruct();

		for (int x = 0; x < 10; ++x) {
			String styleNameFromCss = instance.getStyleNameForProgress(x);
			assertThat(styleNameFromCss).isNotNull();
			assertThat(styleNameFromCss).isEmpty();

		}

		for (int x = 10; x < 100; ++x) {
			String styleNameFromCss = instance.getStyleNameForProgress(x);
			assertThat(styleNameFromCss).isNotNull();
			assertThat(styleNameFromCss).isEqualTo(defaultStyleName);

		}
	}

	@Test
	public void getStyleNameForProgress_BoundaryValues() throws Exception {
		Map<Integer, String> progressToStyleMapping = Maps.newHashMap();
		doReturn(progressToStyleMapping).when(cssMappingParser).getCssProgressToStyleMapping();
		String defaultStyleName = styleName + 0;
		String maxPercentStyleName = styleName + 100;
		progressToStyleMapping.put(0, defaultStyleName);
		progressToStyleMapping.put(100, maxPercentStyleName);

		instance.postConstruct();

		for (int x = 0; x < 100; ++x) {
			String styleNameFromCss = instance.getStyleNameForProgress(x);
			assertThat(styleNameFromCss).isEqualTo(defaultStyleName);
		}
		String styleNameFromCss = instance.getStyleNameForProgress(100);
		assertThat(styleNameFromCss).isEqualTo(maxPercentStyleName);
	}

	@Test
	public void getStyleNameForProgress_NoDefinedStyles() throws Exception {
		Map<Integer, String> progressToStyleMapping = Maps.newHashMap();
		doReturn(progressToStyleMapping).when(cssMappingParser).getCssProgressToStyleMapping();

		instance.postConstruct();

		for (int x = 0; x < 100; ++x) {
			String styleNameFromCss = instance.getStyleNameForProgress(x);
			assertThat(styleNameFromCss).isNotNull();
			assertThat(styleNameFromCss).isEmpty();
		}
	}

	@Test
	public void getStyleNameForProgress_OutOfPercentScope() throws Exception {
		Map<Integer, String> progressToStyleMapping = Maps.newHashMap();
		doReturn(progressToStyleMapping).when(cssMappingParser).getCssProgressToStyleMapping();

		instance.postConstruct();
		List<Integer> wrongValues = Lists.newArrayList(Integer.MIN_VALUE, -897, -9, -1, 101, 987, Integer.MAX_VALUE);
		for (Integer percent : wrongValues) {
			String styleNameFromCss = instance.getStyleNameForProgress(percent);
			assertThat(styleNameFromCss).isNotNull();
			assertThat(styleNameFromCss).isEmpty();
		}
	}

	@Test
	public void getStyleNameForProgress_NullProgress() throws Exception {
		Map<Integer, String> progressToStyleMapping = Maps.newHashMap();
		doReturn(progressToStyleMapping).when(cssMappingParser).getCssProgressToStyleMapping();
		progressToStyleMapping.put(0, styleName);
		instance.postConstruct();

		String styleNameFromCss = instance.getStyleNameForProgress(null);
		assertThat(styleNameFromCss).isNotNull();
		assertThat(styleNameFromCss).isEmpty();
	}
}
