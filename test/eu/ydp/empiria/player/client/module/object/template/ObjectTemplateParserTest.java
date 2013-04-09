package eu.ydp.empiria.player.client.module.object.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactory;
import eu.ydp.empiria.player.client.module.media.button.MediaController;


public class ObjectTemplateParserTest extends AbstractTestBase {
	private ObjectTemplateParser<?> parser = null;
	private Set<String> controllers = null;

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	@Before
	public void init() {
		parser = injector.getInstance(ObjectTemplateParser.class);
		controllers = new HashSet<String>();
		controllers.add(ModuleTagName.MEDIA_PLAY_PAUSE_BUTTON.tagName());
		controllers.add(ModuleTagName.MEDIA_PLAY_STOP_BUTTON.tagName());
		controllers.add(ModuleTagName.MEDIA_STOP_BUTTON.tagName());
		controllers.add(ModuleTagName.MEDIA_MUTE_BUTTON.tagName());
		controllers.add(ModuleTagName.MEDIA_PROGRESS_BAR.tagName());
		controllers.add(ModuleTagName.MEDIA_FULL_SCREEN_BUTTON.tagName());
		controllers.add(ModuleTagName.MEDIA_POSITION_IN_STREAM.tagName());
		controllers.add(ModuleTagName.MEDIA_VOLUME_BAR.tagName());
		controllers.add(ModuleTagName.MEDIA_CURRENT_TIME.tagName());
		controllers.add(ModuleTagName.MEDIA_TOTAL_TIME.tagName());
		controllers.add(ModuleTagName.MEDIA_TEXT_TRACK.tagName());
		controllers.add(ModuleTagName.MEDIA_SCREEN.tagName());
	}

	@Test
	public void getMediaControllerNewInstanceTest() {
		MediaControllerFactory factory = parser.factory;
		when(factory.get(Mockito.any(ModuleTagName.class))).then(new Answer<MediaController<String>>() {
			@Override
			public MediaController<String> answer(InvocationOnMock invocation) throws Throwable {
				String param = String.valueOf(invocation.getArguments()[0]);
				MediaController<String> mediaController = mock(MediaController.class);
				when(mediaController.getNewInstance()).thenReturn(param);
				return mediaController;
			}
		});
		// test
		controllers.remove(ModuleTagName.MEDIA_TEXT_TRACK.tagName());
		controllers.remove(ModuleTagName.MEDIA_SCREEN.tagName());
		for (String tagName : controllers) {
			MediaController<?> mediaController = parser.getMediaControllerNewInstance(tagName, null);
			assertNotNull("media controller is null", mediaController);
			assertEquals("wrong return value", tagName, mediaController.getNewInstance());
		}
	}

	@Test
	public void getMediaControllerNewInstanceWithXMLTest() {
		MediaControllerFactory factory = parser.factory;
		when(factory.get(Mockito.any(ModuleTagName.class), Mockito.any(TextTrackKind.class))).then(new Answer<MediaController<String>>() {
			@Override
			public MediaController<String> answer(InvocationOnMock invocation) throws Throwable {
				String param = String.valueOf(invocation.getArguments()[1]);
				MediaController<String> mediaController = mock(MediaController.class);
				when(mediaController.getNewInstance()).thenReturn(param);
				return mediaController;
			}
		});

		Element node = mock(Element.class);
		when(node.getAttribute("kind")).thenReturn(TextTrackKind.SUBTITLES.name());
		// test

		MediaController<?> mediaController = parser.getMediaControllerNewInstance(ModuleTagName.MEDIA_TEXT_TRACK.tagName(), node);
		assertNotNull("media controller is null", mediaController);
		assertEquals("wrong return value", TextTrackKind.SUBTITLES.name(), mediaController.getNewInstance());

	}

	@Test
	public void isModuleSupportedTest() {
		for (String tagName : controllers) {
			assertTrue("missing suport for " + tagName, parser.isModuleSupported(tagName));
		}
	}

	@Test
	public void isModuleNotSupportedTest() {
		for (ModuleTagName tagName : ModuleTagName.values()) {
			if (!controllers.contains(tagName.tagName())) {
				assertFalse("no tests for " + tagName, parser.isModuleSupported(tagName.tagName()));
			}
		}
	}

}
