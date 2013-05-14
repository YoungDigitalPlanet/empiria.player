package eu.ydp.empiria.player.client.module.textentry;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.TextBox;

import eu.ydp.empiria.player.client.module.gap.GapBase.PresenterHandler;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;

@SuppressWarnings("PMD")
public class TextBoxChangeHandlerJUnitTest {


	private DroppableObject<TextBox> object;
	private TextBox textBox;
	private PresenterHandler presenterHandler;
	private final TextBoxChangeHandler instance = new TextBoxChangeHandler();

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	@Before
	public void before() {
		object = mock(DroppableObject.class);
		textBox = mock(TextBox.class);
		presenterHandler = mock(PresenterHandler.class);
		doReturn(textBox).when(object).getOriginalWidget();
	}

	@Test
	public void bindNoPresenterHandlerTest(){
		instance.bind(object, null);
		verifyZeroInteractions(object);
	}

	@Test
	public void bindTest(){
		instance.bind(object, presenterHandler);
		verify(textBox).addChangeHandler(Mockito.eq(instance));
		verify(textBox).addBlurHandler(Mockito.eq(instance));
		verify(object).addDropHandler(Mockito.eq(instance));
	}


	@Test
	public void onDropTest(){
		instance.bind(object, presenterHandler);
		DropEvent event = mock(DropEvent.class);
		instance.onDrop(event);
		verify(presenterHandler).onChange(Mockito.any(ChangeEvent.class));
	}

	@Test
	public void onChangeTest(){
		instance.bind(object, presenterHandler);
		ChangeEvent event = mock(ChangeEvent.class);
		instance.onChange(event);
		verify(presenterHandler).onChange(Mockito.eq(event));
		verifyNoMoreInteractions(presenterHandler);

	}

	@Test
	public void onBlurTest(){
		instance.bind(object, presenterHandler);
		BlurEvent blurEvent = mock(BlurEvent.class);
		instance.onBlur(blurEvent);
		verify(presenterHandler).onBlur(Mockito.eq(blurEvent));
		verifyNoMoreInteractions(presenterHandler);
	}

}
