package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import static junitparams.JUnitParamsRunner.$;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class ExternalMediaProcessorVideoTest extends ExternalMediaProcessorTestBase {


	@SuppressWarnings("unused")
	private Object[] media_params(){
		return $((Object[])TestMediaVideo.values());
	}
	
	@Test(expected = UnsupportedOperationException.class)
	@Parameters(method = "media_params")
	public void init(TestMediaVideo testMedia) {
		// when
		container.createMediaWrapper(testMedia);
		
		// then
		verify(connector).init(anyString(), eq(testMedia.getSources()));
	}
}
