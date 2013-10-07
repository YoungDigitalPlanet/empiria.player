package eu.ydp.empiria.player.client.module.progressbonus;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;

import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.gwtutil.client.util.RandomWrapper;
import eu.ydp.gwtutil.client.util.geom.Size;

@RunWith(MockitoJUnitRunner.class)
public class ProgressAssetTest {

	@InjectMocks
	private ProgressAsset asset;
	@Mock
	private RandomWrapper random;

	@Test
	public void shouldCreateImageForRange() {
		// given
		ShowImageDTO dto1 = createShowImageDTO("PATH1");
		ShowImageDTO dto2 = createShowImageDTO("PATH2");
		ShowImageDTO dto3 = createShowImageDTO("PATH3");
		List<ShowImageDTO> dtos0 = Lists.newArrayList(dto1, dto2, dto3);
		asset.add(Range.closedOpen(0, 100), dtos0);

		ShowImageDTO dto100 = createShowImageDTO("PATH100");
		List<ShowImageDTO> dtos100 = Lists.newArrayList(dto100);
		asset.add(Range.atLeast(100), dtos100);

		when(random.nextInt(dtos0.size() - 1)).thenReturn(1);
		when(random.nextInt(dtos100.size() - 1)).thenReturn(0);

		// when
		ShowImageDTO imageFor0 = asset.getImageForProgress(0);
		ShowImageDTO imageFor100 = asset.getImageForProgress(100);

		// then
		assertThat(imageFor0.path).isEqualTo("PATH2");
		assertThat(imageFor100.path).isEqualTo("PATH100");
	}

	@Test
	public void shouldSetIdOfReturnedImage() {
		// given
		ShowImageDTO dto1 = createShowImageDTO("PATH1");
		ShowImageDTO dto2 = createShowImageDTO("PATH2");
		ShowImageDTO dto3 = createShowImageDTO("PATH3");
		List<ShowImageDTO> dtos0 = Lists.newArrayList(dto1, dto2, dto3);
		asset.add(Range.closedOpen(0, 100), dtos0);

		when(random.nextInt(dtos0.size() - 1)).thenReturn(1);

		// when
		asset.getImageForProgress(0);

		// then
		assertThat(asset.getId()).isEqualTo(1);
	}

	private ShowImageDTO createShowImageDTO(String path) {
		return new ShowImageDTO(path, new Size(300, 400));
	}

}
