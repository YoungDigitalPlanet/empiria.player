package eu.ydp.empiria.player.client.module.slideshow.slides;

import static org.fest.assertions.api.Assertions.*;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import java.util.List;
import org.junit.Test;

public class SlidesSorterTest {

	private final SlidesSorter testObj = new SlidesSorter();

	@Test
	public void listShouldBeSortedByTime() {
		// given
		List<SlideBean> slides = Lists.newArrayList();

		SlideBean firstSlide = new SlideBean();
		firstSlide.setStartTime(100);

		SlideBean secondSlide = new SlideBean();
		secondSlide.setStartTime(0);

		slides.add(firstSlide);
		slides.add(secondSlide);

		// when
		testObj.sortByTime(slides);

		// then
		assertThat(slides.get(0)).isSameAs(secondSlide);
		assertThat(slides.get(1)).isSameAs(firstSlide);
	}

	@Test
	public void shouldBeSortedInAdditionOrder() {
		// given
		List<SlideBean> slides = Lists.newArrayList();

		SlideBean firstSlide = new SlideBean();
		firstSlide.setStartTime(0);

		SlideBean secondSlide = new SlideBean();
		secondSlide.setStartTime(0);

		slides.add(firstSlide);
		slides.add(secondSlide);

		// when
		testObj.sortByTime(slides);

		// then
		assertThat(slides.get(0)).isSameAs(firstSlide);
		assertThat(slides.get(1)).isSameAs(secondSlide);
	}
}
