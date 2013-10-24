package eu.ydp.empiria.player.client.module.progressbonus;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressAssetConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressConfig;
import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.util.geom.Size;

@RunWith(MockitoJUnitRunner.class)
public class ProgressConfigResolverTest {

	@InjectMocks
	private ProgressConfigResolver awardResolver;
	@Mock
	private EmpiriaPaths empiriaPaths;
	@Mock
	private ProgressBonusConfig progressBonusConfig;

	@Before
	public void setUp() throws Exception {

		mockEmpiriaPaths();
	}

	@Test
	public void shouldBuildAssetForEmptyAwards() {
		// given
		when(progressBonusConfig.getProgresses()).thenReturn(Lists.<ProgressConfig> newArrayList());

		// when
		Map<Integer, List<ShowImageDTO>> resolvedConfig = awardResolver.resolveProgressConfig();

		// then
		assertThat(resolvedConfig).hasSize(0);
	}

	@Test
	public void shouldBuildSimpleAssets() {
		// given
		ProgressAssetConfig assetConfig = createProgressAssetConfig("PATH", 0);
		ProgressConfig progressConfig = createProgessConfig(0, assetConfig);

		ProgressAssetConfig assetConfig100 = createProgressAssetConfig("PATH100", 0);
		ProgressAssetConfig assetConfig100_1 = createProgressAssetConfig("PATH100_1", 0);
		ProgressAssetConfig assetConfig100_2 = createProgressAssetConfig("PATH100_2", 0);
		ProgressConfig progressConfig100 = createProgessConfig(100, assetConfig100, assetConfig100_1, assetConfig100_2);

		when(progressBonusConfig.getProgresses()).thenReturn(Lists.newArrayList(progressConfig, progressConfig100));

		// when
		Map<Integer, List<ShowImageDTO>> resolvedConfig = awardResolver.resolveProgressConfig();

		// then
		assertThat(resolvedConfig).hasSize(2);
		
		final List<ShowImageDTO> progress0 = resolvedConfig.get(0);
		assertThat(progress0).hasSize(1);
		assertThat(progress0.get(0).path).isEqualTo("RESOLVED_PATH");
		
		final List<ShowImageDTO> progress100 = resolvedConfig.get(100);
		assertThat(progress100).hasSize(3);
		assertThat(progress100.get(0).path).isEqualTo("RESOLVED_PATH100");
		assertThat(progress100.get(1).path).isEqualTo("RESOLVED_PATH100_1");
		assertThat(progress100.get(2).path).isEqualTo("RESOLVED_PATH100_2");
		
		verify(empiriaPaths, times(4)).getCommonsFilePath(anyString());
	}

	@Test
	public void shouldBuildTemplatedAssets() {
		// given
		ProgressAssetConfig assetConfig = createProgressAssetConfig("TEMPLATE_%.png", 3);
		ProgressConfig progressConfig = createProgessConfig(0, assetConfig);

		ProgressAssetConfig assetConfig100 = createProgressAssetConfig("TEMPLATE100_%.png", 2);
		ProgressConfig progressConfig100 = createProgessConfig(100, assetConfig100);

		when(progressBonusConfig.getProgresses()).thenReturn(Lists.newArrayList(progressConfig, progressConfig100));


		// when
		Map<Integer, List<ShowImageDTO>> resolvedConfig = awardResolver.resolveProgressConfig();

		// then
		assertThat(resolvedConfig).hasSize(2);
		
		final List<ShowImageDTO> progress0 = resolvedConfig.get(0);
		assertThat(progress0).hasSize(3);
		assertThat(progress0.get(0).path).isEqualTo("RESOLVED_TEMPLATE_1.png");
		assertThat(progress0.get(1).path).isEqualTo("RESOLVED_TEMPLATE_2.png");
		assertThat(progress0.get(2).path).isEqualTo("RESOLVED_TEMPLATE_3.png");
		
		final List<ShowImageDTO> progress100 = resolvedConfig.get(100);
		assertThat(progress100).hasSize(2);
		assertThat(progress100.get(0).path).isEqualTo("RESOLVED_TEMPLATE100_1.png");
		assertThat(progress100.get(1).path).isEqualTo("RESOLVED_TEMPLATE100_2.png");

		verify(empiriaPaths, times(5)).getCommonsFilePath(anyString());
	}

	private void mockEmpiriaPaths() {
		when(empiriaPaths.getCommonsFilePath(anyString())).then(new Answer<String>() {

			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				return "RESOLVED_" + invocation.getArguments()[0];
			}
		});
	}

	private ProgressAssetConfig createProgressAssetConfig(String path, int count) {
		ProgressAssetConfig assetConfig = mock(ProgressAssetConfig.class);
		when(assetConfig.getCount()).thenReturn(count);
		when(assetConfig.getPath()).thenReturn(path);
		when(assetConfig.getSize()).thenReturn(new Size(50, 100));
		return assetConfig;
	}

	private ProgressConfig createProgessConfig(int from, ProgressAssetConfig... assetsConfigs) {
		ProgressConfig progressConfig = mock(ProgressConfig.class);
		when(progressConfig.getFrom()).thenReturn(from);
		when(progressConfig.getAssets()).thenReturn(Lists.newArrayList(assetsConfigs));
		return progressConfig;
	}
}
