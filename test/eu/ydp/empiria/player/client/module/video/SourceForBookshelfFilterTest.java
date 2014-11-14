package eu.ydp.empiria.player.client.module.video;

import static org.fest.assertions.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class SourceForBookshelfFilterTest {

	private final SourceForBookshelfFilter testObj = new SourceForBookshelfFilter();

	@Test
	public void shouldGetFilteredSources() {
		// given
		String sourceAVI = "source.avi";
		String sourceMP4 = "source.mp4";
		List<String> sources = new ArrayList<>();
		sources.add(sourceAVI);
		sources.add(sourceMP4);

		// when
		List<String> resultList = testObj.getFilteredSources(sources);

		// then
		assertThat(resultList.size()).isEqualTo(1);
		assertThat(resultList.contains(sourceMP4)).isTrue();
		assertThat(resultList.contains(sourceAVI)).isFalse();
	}

}
