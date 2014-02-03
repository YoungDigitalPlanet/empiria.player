package com.mathplayer.player.model;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import gwt.g2d.client.graphics.Surface;
import junit.framework.TestCase;

import org.junit.Test;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Size;
import com.mathplayer.player.interaction.InteractionSocket;

public class TokenTest extends TestCase {
	@Test
	public void testCanvasForTextWidthMeasureAddedAndRemoved() {
		GWTMockUtilities.disarm();

		Token token = new Token() {
			@Override
			public String toString() {
				return null;
			}

			@Override
			public String toMathML() {
				return null;
			}

			@Override
			public Size measure(InteractionSocket socket) {
				return null;
			}

			@Override
			public Surface getCanvas() {
				return mock(Surface.class);
			}

			@Override
			public Boolean isStackAndroidBrowser() {
				return false;
			}
		};

		AbsolutePanel mockPanel = mock(AbsolutePanel.class);

		String content = "";
		Font font = new Font(10, "Arial", false, false);
		double margin = 0;

		token.getTextWidth(content, font, margin, mockPanel);
		verify(mockPanel).add(any(Surface.class));
		verify(mockPanel).remove(any(Surface.class));

		GWTMockUtilities.restore();
	}
}