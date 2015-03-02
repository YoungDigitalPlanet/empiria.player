package eu.ydp.empiria.player.client.module.progressbonus;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.gwtutil.client.util.RandomWrapper;
import eu.ydp.gwtutil.client.util.geom.Size;

@RunWith(MockitoJUnitRunner.class)
public class ProgressAssetProviderTest {

	@InjectMocks
	private ProgressAssetProvider assetProvider;
	@Mock
	private RandomWrapper random;
	@Mock
	private ProgressConfigResolver configResolver;

	@Test
	public void shouldResolveAwardsForGivenId() {
		// given
		final int ID = 1;
		Map<Integer, List<ShowImageDTO>> map = Maps.newHashMap();
		ShowImageDTO dto0_1 = createShowImageDTO("PATH0_1");
		ShowImageDTO dto100_1 = createShowImageDTO("PATH100_1");
		ShowImageDTO dto100_2 = createShowImageDTO("PATH100_2");
		ShowImageDTO dto100_3 = createShowImageDTO("PATH100_3");
		map.put(0, Lists.newArrayList(dto0_1));
		map.put(100, Lists.newArrayList(dto100_1, dto100_2, dto100_3));
		when(configResolver.resolveProgressConfig()).thenReturn(map);

		// when
		ProgressAsset asset = assetProvider.createFrom(ID);

		// then
		assertThat(asset.getId()).isEqualTo(ID);
		assertThat(asset.getImageForProgress(0)).isEqualTo(dto0_1);
		assertThat(asset.getImageForProgress(100)).isEqualTo(dto100_2);
	}

	@Test
	public void shouldResolveAwardsForRandomId() {
		// given
		final int ID = 1;
		Map<Integer, List<ShowImageDTO>> map = Maps.newHashMap();
		ShowImageDTO dto0_1 = createShowImageDTO("PATH0_1");
		ShowImageDTO dto100_1 = createShowImageDTO("PATH100_1");
		ShowImageDTO dto100_2 = createShowImageDTO("PATH100_2");
		ShowImageDTO dto100_3 = createShowImageDTO("PATH100_3");
		map.put(0, Lists.newArrayList(dto0_1));
		map.put(100, Lists.newArrayList(dto100_1, dto100_2, dto100_3));
		when(configResolver.resolveProgressConfig()).thenReturn(map);

		when(random.nextInt(3)).thenReturn(ID);

		// when
		ProgressAsset asset = assetProvider.createRandom();

		// then
		verify(random).nextInt(3);
		assertThat(asset.getId()).isEqualTo(ID);
		assertThat(asset.getImageForProgress(0)).isEqualTo(dto0_1);
		assertThat(asset.getImageForProgress(100)).isEqualTo(dto100_2);
	}

	private ShowImageDTO createShowImageDTO(String path) {
		return new ShowImageDTO(path, new Size(200, 300));
	}
}
