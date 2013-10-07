package eu.ydp.empiria.player.client.module.progressbonus;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;

import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.gwtutil.client.util.geom.Size;

@RunWith(MockitoJUnitRunner.class)
public class ProgressAssetBuilderTest {

	private static final ArrayList<ShowImageDTO> EMPTY_DTOS = Lists.newArrayList();
	@InjectMocks
	private ProgressAssetBuilder assetBuilder;
	@Mock
	private ProgressAsset progressAsset;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void shouldBuildEmpyAsset() {
		// when
		ProgressAsset returnedAsset = assetBuilder.build();

		// then
		verify(returnedAsset).add(eq(Range.atLeast(Integer.MIN_VALUE)), eq(EMPTY_DTOS));
	}

	@Test
	public void shouldBuildAsset() {
		// given
		int from0 = 0;
		ShowImageDTO dtoFor0 = new ShowImageDTO("PATH", new Size(300, 400));
		List<ShowImageDTO> dtosFrom0 = Lists.newArrayList(dtoFor0);
		int from100 = 100;
		ShowImageDTO dtoFor100_1 = new ShowImageDTO("PATH1", new Size(320, 400));
		ShowImageDTO dtoFor100_2 = new ShowImageDTO("PATH2", new Size(340, 400));
		List<ShowImageDTO> dtosFrom100 = Lists.newArrayList(dtoFor100_1, dtoFor100_2);

		assetBuilder.add(from0, dtosFrom0);
		assetBuilder.add(from100, dtosFrom100);

		// when
		ProgressAsset returnedAsset = assetBuilder.build();

		// then
		verify(returnedAsset).add(eq(Range.closedOpen(Integer.MIN_VALUE, 0)), eq(EMPTY_DTOS));
		verify(returnedAsset).add(eq(Range.closedOpen(from0, from100)), eq(dtosFrom0));
		verify(returnedAsset).add(eq(Range.atLeast(from100)), eq(dtosFrom100));
	}
}
