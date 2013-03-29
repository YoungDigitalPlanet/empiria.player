package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.MediaConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.MediaConnectorListener;

public class TestConnectorForwarder {

	public void forwardPlay(final MediaConnector connector, final MediaConnectorListener mediaConnectorListener) {
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				mediaConnectorListener.onPlay((String)invocation.getArguments()[0]);
				return null;
			}
		}).when(connector).play(anyString());
	}

	public void forwardPause(final MediaConnector connector, final MediaConnectorListener mediaConnectorListener) {
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				mediaConnectorListener.onPause((String)invocation.getArguments()[0]);
				return null;
			}
		}).when(connector).pause(anyString());
	}
}
