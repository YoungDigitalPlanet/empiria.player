package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;

import eu.ydp.empiria.player.client.MocksCollector;
import eu.ydp.empiria.player.client.gin.factory.DictionaryModuleFactory;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;

@RunWith(MockitoJUnitRunner.class)
public class ExplanationControllerTest {

	@Mock
	private ExplanationDescriptionSoundController explanationDescriptionSoundController;

	private ExplanationController testObj;
	@Mock
	private ExplanationView explanationView;

	@Mock
	private DictionaryModuleFactory dictionaryModuleFactory;

	@Mock
	private ExplanationEntrySoundController explanationEntrySoundController;

	private final MocksCollector mocksCollector = new MocksCollector();

	private ClickHandler clickHandler;
	private MouseUpHandler mouseUpHandler;

	@Before
	public void setUp() {
		when(dictionaryModuleFactory.getExplanationDescriptionSoundController(explanationView)).thenReturn(explanationDescriptionSoundController);
		testObj = new ExplanationController(explanationView, explanationEntrySoundController, dictionaryModuleFactory);
	}

	@Test
	public void shouldProcessNotNullEntry() {
		// given
		Entry entry = mock(Entry.class);

		// when
		testObj.processEntry(entry);

		// then
		verify(explanationView).processEntry(entry);
	}

	@Test
	public void shouldProcessAndPlayNotNullEntry() {
		// given
		Entry entry = mock(Entry.class);

		// when
		testObj.processEntryAndPlaySound(entry);

		// then
		verify(explanationEntrySoundController).playEntrySound(entry.getEntrySound());
	}

	@Test
	public void shouldShow() {
		// when
		testObj.show();

		// then
		verify(explanationView).show();
	}

	@Test
	public void shouldHide() {
		// when
		testObj.hide();

		// then
		verify(explanationDescriptionSoundController).stop();
		verify(explanationView).hide();
	}

	@Test
	public void shouldInit() {
		// given
		String fileName = "test.mp3";

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				mouseUpHandler = (MouseUpHandler) invocation.getArguments()[0];
				return null;
			}
		}).when(explanationView).addEntryExamplePanelHandler(any(MouseUpHandler.class));

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				clickHandler = (ClickHandler) invocation.getArguments()[0];
				return null;
			}
		}).when(explanationView).addPlayButtonHandler(any(ClickHandler.class));

		// when
		testObj.init();

		// then
		verify(explanationView).addPlayButtonHandler(clickHandler);
		verify(explanationView).addEntryExamplePanelHandler(mouseUpHandler);
	}

	@Test
	public void shouldCallPlayOrStopDescriptionOnPanelMouseUp() {
		// given
		String file = "test.mp3";
		Entry entry = mock(Entry.class);
		when(entry.getEntryExampleSound()).thenReturn(file);

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				mouseUpHandler = (MouseUpHandler) invocation.getArguments()[0];
				return null;
			}
		}).when(explanationView).addEntryExamplePanelHandler(any(MouseUpHandler.class));

		// when
		testObj.init();
		testObj.processEntry(entry);
		mouseUpHandler.onMouseUp(null);

		// then
		verify(explanationDescriptionSoundController).playOrStopDescriptionSound(file);
	}

	@Test
	public void shouldCallPlayOrStopDescriptionOnPlayButtonClick() {
		// given
		String file = "test.mp3";
		Entry entry = mock(Entry.class);
		when(entry.getEntryExampleSound()).thenReturn(file);

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				clickHandler = (ClickHandler) invocation.getArguments()[0];
				return null;
			}
		}).when(explanationView).addPlayButtonHandler(any(ClickHandler.class));

		// when
		testObj.init();
		testObj.processEntry(entry);
		clickHandler.onClick(null);

		// then
		verify(explanationDescriptionSoundController).playOrStopDescriptionSound(file);
	}
}
