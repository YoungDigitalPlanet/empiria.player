package eu.ydp.empiria.player.client.module.progressbonus;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressAssetConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressAward;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressConfig;
import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.util.geom.Size;

@RunWith(MockitoJUnitRunner.class)
public class ProgressAwardResolverTest {

	@InjectMocks
	private ProgressAwardResolver awardResolver;
	@Mock
	private ProgressAssetBuilder assetBuilder;
	@Mock
	private EmpiriaPaths empiriaPaths;

	@Before
	public void setUp() throws Exception {
		mockEmpiriaPaths();
	}

	@Test
	public void shouldBuildAssetForEmptyAwards() {
		// given
		ProgressAward progressAward = mock(ProgressAward.class);
		when(progressAward.getProgresses()).thenReturn(Lists.<ProgressConfig> newArrayList());

		// when
		awardResolver.createProgressAsset(progressAward);

		// then
		verify(assetBuilder, never()).add(anyInt(), anyListOf(ShowImageDTO.class));
		verify(assetBuilder).build();
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldBuildSimpleAssets() {
		// given
		ProgressAssetConfig assetConfig = createProgressAssetConfig("PATH", 0);
		ProgressConfig progressConfig = createProgessConfig(0, assetConfig);

		ProgressAssetConfig assetConfig100 = createProgressAssetConfig("PATH100", 0);
		ProgressConfig progressConfig100 = createProgessConfig(100, assetConfig100);

		ProgressAward progressAward = mockPorgressAward(progressConfig, progressConfig100);

		ArgumentCaptor<List> argCaptor = ArgumentCaptor.forClass(List.class);
		ArgumentCaptor<List> argCaptor100 = ArgumentCaptor.forClass(List.class);

		// when
		awardResolver.createProgressAsset(progressAward);

		// then
		verify(assetBuilder).add(eq(0), argCaptor.capture());
		List<ShowImageDTO> dtos = argCaptor.getValue();
		assertThat(dtos).hasSize(1);
		assertThat(dtos.get(0).path).isEqualTo("RESOLVED_PATH");

		verify(assetBuilder).add(eq(100), argCaptor100.capture());
		List<ShowImageDTO> dtos100 = argCaptor100.getValue();
		assertThat(dtos100).hasSize(1);
		assertThat(dtos100.get(0).path).isEqualTo("RESOLVED_PATH100");
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldBuildTemplatedAssets() {
		// given
		ProgressAssetConfig assetConfig = createProgressAssetConfig("TEMPLATE_%.png", 3);
		ProgressConfig progressConfig = createProgessConfig(0, assetConfig);

		ProgressAssetConfig assetConfig100 = createProgressAssetConfig("TEMPLATE100_%.png", 2);
		ProgressConfig progressConfig100 = createProgessConfig(100, assetConfig100);

		ProgressAward progressAward = mockPorgressAward(progressConfig, progressConfig100);

		ArgumentCaptor<List> argCaptor = ArgumentCaptor.forClass(List.class);
		ArgumentCaptor<List> argCaptor100 = ArgumentCaptor.forClass(List.class);

		// when
		awardResolver.createProgressAsset(progressAward);

		// then
		verify(assetBuilder).add(eq(0), argCaptor.capture());
		List<ShowImageDTO> dtos = argCaptor.getValue();
		assertThat(dtos).hasSize(3);
		assertThat(dtos.get(0).path).isEqualTo("RESOLVED_TEMPLATE_1.png");
		assertThat(dtos.get(1).path).isEqualTo("RESOLVED_TEMPLATE_2.png");
		assertThat(dtos.get(2).path).isEqualTo("RESOLVED_TEMPLATE_3.png");

		verify(assetBuilder).add(eq(100), argCaptor100.capture());
		List<ShowImageDTO> dtos100 = argCaptor100.getValue();
		assertThat(dtos100).hasSize(2);
		assertThat(dtos100.get(0).path).isEqualTo("RESOLVED_TEMPLATE100_1.png");
		assertThat(dtos100.get(1).path).isEqualTo("RESOLVED_TEMPLATE100_2.png");
	}

	private void mockEmpiriaPaths() {
		when(empiriaPaths.getCommonsFilePath(anyString())).then(new Answer<String>() {

			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				return "RESOLVED_" + invocation.getArguments()[0];
			}
		});
	}

	private ProgressAward mockPorgressAward(ProgressConfig... progressConfigs) {
		ProgressAward progressAward = mock(ProgressAward.class);
		when(progressAward.getProgresses()).thenReturn(Lists.newArrayList(progressConfigs));
		return progressAward;
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
