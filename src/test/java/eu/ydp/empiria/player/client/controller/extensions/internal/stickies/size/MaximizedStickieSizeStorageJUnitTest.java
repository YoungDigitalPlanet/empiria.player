package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.size;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

public class MaximizedStickieSizeStorageJUnitTest {

	private MaximizedStickieSizeStorage maximizedStickieSizeStorage;

	@Before
	public void setUp() throws Exception {
		maximizedStickieSizeStorage = new MaximizedStickieSizeStorage();
	}

	@Test
	public void shouldReturnNoValueWhenNothingPutIn() throws Exception {
		int colorIndex = 0;
		Optional<StickieSize> stickieSize = maximizedStickieSizeStorage.getSizeOfMaximizedStickie(colorIndex);

		assertThat(stickieSize.isPresent(), equalTo(false));
	}

	@Test
	public void shouldReturnCorrectSizesForColors() throws Exception {
		int firstColorIndex = 0;
		int secondColorIndex = 1;

		maximizedStickieSizeStorage.updateIfBiggerThanExisting(firstColorIndex, new StickieSize(100, 150));
		maximizedStickieSizeStorage.updateIfBiggerThanExisting(secondColorIndex, new StickieSize(200, 250));

		Optional<StickieSize> firstStickieSize = maximizedStickieSizeStorage.getSizeOfMaximizedStickie(firstColorIndex);
		Optional<StickieSize> secondStickieSize = maximizedStickieSizeStorage.getSizeOfMaximizedStickie(secondColorIndex);

		assertSizes(firstStickieSize, 100, 150);
		assertSizes(secondStickieSize, 200, 250);
	}

	@Test
	public void shouldUpdateIfWidthBiggerThanCurrentlyStored() throws Exception {
		int colorIndex = 0;
		maximizedStickieSizeStorage.updateIfBiggerThanExisting(colorIndex, new StickieSize(100, 111));
		maximizedStickieSizeStorage.updateIfBiggerThanExisting(colorIndex, new StickieSize(200, 111));

		Optional<StickieSize> optionalStickieSize = maximizedStickieSizeStorage.getSizeOfMaximizedStickie(colorIndex);
		assertSizes(optionalStickieSize, 200, 111);
	}

	@Test
	public void shouldUpdateIfHeightBiggerThanCurrentlyStored() throws Exception {
		int colorIndex = 0;
		maximizedStickieSizeStorage.updateIfBiggerThanExisting(colorIndex, new StickieSize(100, 111));
		maximizedStickieSizeStorage.updateIfBiggerThanExisting(colorIndex, new StickieSize(100, 222));

		Optional<StickieSize> optionalStickieSize = maximizedStickieSizeStorage.getSizeOfMaximizedStickie(colorIndex);
		assertSizes(optionalStickieSize, 100, 222);
	}

	@Test
	public void shouldUpdateIfDimensionsBiggerThanCurrentlyStored() throws Exception {
		int colorIndex = 0;
		maximizedStickieSizeStorage.updateIfBiggerThanExisting(colorIndex, new StickieSize(111, 222));
		maximizedStickieSizeStorage.updateIfBiggerThanExisting(colorIndex, new StickieSize(333, 444));

		Optional<StickieSize> optionalStickieSize = maximizedStickieSizeStorage.getSizeOfMaximizedStickie(colorIndex);
		assertSizes(optionalStickieSize, 333, 444);
	}

	private void assertSizes(Optional<StickieSize> optionalStickieSize, int width, int height) {
		assertThat(optionalStickieSize.isPresent(), equalTo(true));
		StickieSize stickieSize = optionalStickieSize.get();

		assertThat(stickieSize.getWidth(), equalTo(width));
		assertThat(stickieSize.getHeight(), equalTo(height));
	}

}
