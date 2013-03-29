package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import static junitparams.JUnitParamsRunner.$;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class ExternalMediaProcessorVideoSupportTest extends ExternalMediaProcessorTestBase {

	@SuppressWarnings("unused")
	private Object[] media_video(){
		return $((Object[])TestMediaVideo.values());
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Parameters(method = "media_video")
	public void onReady(TestMediaVideo testMedias) {
		// when
		container.createMediaWrapper(testMedias);
	}
	
}
