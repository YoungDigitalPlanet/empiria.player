package eu.ydp.empiria.player.client.module.slideshow;

import static org.fest.assertions.api.Assertions.*;

import eu.ydp.empiria.player.client.module.slideshow.structure.*;
import org.junit.Test;

public class SlideshowTemplateInterpreterTest {

	private final SlideshowTemplateInterpreter testObj = new SlideshowTemplateInterpreter();

	@Test
	public void shouldReturnTrue_whenPagerTemplateIsNotNull() {
		// given
		SlideshowPlayerBean slideshowPlayer = new SlideshowPlayerBean();
		SlideshowTemplate template = new SlideshowTemplate();
		SlideshowPagerBean pager = new SlideshowPagerBean();
		template.setSlideshowPager(pager);
		slideshowPlayer.setTemplate(template);

		// when
		boolean result = testObj.isPagerTemplateActivate(slideshowPlayer);

		// then
		assertThat(result).isTrue();
	}

	@Test
	public void shouldReturnFalse_whenPagerTemplateIsNull() {
		// given
		SlideshowPlayerBean slideshowPlayer = new SlideshowPlayerBean();
		SlideshowTemplate template = new SlideshowTemplate();
		template.setSlideshowPager(null);
		slideshowPlayer.setTemplate(template);

		// when
		boolean result = testObj.isPagerTemplateActivate(slideshowPlayer);

		// then
		assertThat(result).isFalse();
	}

	@Test
	public void shouldReturnFalse_whenTemplateIsNull() {
		// given
		SlideshowPlayerBean slideshowPlayer = new SlideshowPlayerBean();
		slideshowPlayer.setTemplate(null);

		// when
		boolean result = testObj.isPagerTemplateActivate(slideshowPlayer);

		// then
		assertThat(result).isFalse();
	}
}
