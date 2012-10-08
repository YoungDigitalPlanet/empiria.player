package eu.ydp.empiria.player.client.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("PMD")
public class SourceUtilJUnitTest {

	@Test
	public void getMpegSourceTest() {
		Map<String, String> sources = new HashMap<String, String>();
		sources.put("video.mpeg","video/mpeg");
		sources.put("video.ogg","video/ogg");
		Assert.assertTrue(SourceUtil.getMpegSource(sources).equals("video.mpeg"));
		sources.put("video.mpeg","video/mp3");
		Assert.assertTrue(SourceUtil.getMpegSource(sources).equals("video.mpeg"));
		sources.put( "video.mpeg","video/mp4");
		Assert.assertTrue(SourceUtil.getMpegSource(sources).equals("video.mpeg"));
		sources.clear();
		sources.put("video.ogg","video/ogg");
		Assert.assertNull(SourceUtil.getMpegSource(sources));
	}

}
