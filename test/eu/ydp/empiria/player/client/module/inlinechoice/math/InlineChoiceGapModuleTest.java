package eu.ydp.empiria.player.client.module.inlinechoice.math;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.xml.client.Element;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ResponseSocket;
import eu.ydp.empiria.player.client.module.gap.GapBinder;
import eu.ydp.empiria.player.client.module.math.MathGapModel;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.EventScope;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBox;
import eu.ydp.gwtutil.client.components.exlistbox.IsExListBox;
import eu.ydp.gwtutil.test.mock.ReturnsJavaBeanAnswers;

@SuppressWarnings("PMD")
public class InlineChoiceGapModuleTest extends AbstractTestBaseWithoutAutoInjectorInit {
	InlineChoiceMathGapModule instance;
	ExListBox listBox;
	EventsBus eventsBus;
	
	GapBinder gapBinder;

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	private static class CustomGuiceModule implements Module {
		private final InlineChoiceMathGapModulePresenter presenter;
		private final MathGapModel mathGapModel;

		public CustomGuiceModule(InlineChoiceMathGapModulePresenter presenter, MathGapModel mathGapModel) {
			this.presenter = presenter;
			this.mathGapModel = mathGapModel;
		}

		@Override
		public void configure(Binder binder) {
			binder.bind(InlineChoiceMathGapModulePresenter.class).toInstance(presenter);
			binder.bind(MathGapModel.class).annotatedWith(ModuleScoped.class).toInstance(mathGapModel);
		}
	}

	@Before
	public void before() {
		InlineChoiceMathGapModulePresenter presenter = mock(InlineChoiceMathGapModulePresenter.class);
		listBox = mock(ExListBox.class);
		doReturn(listBox).when(presenter).getListBox();
		MathGapModel mathGapModel = new MathGapModel();
		setUp(new Class<?>[] {}, new Class<?>[] {}, new Class<?>[] { EventsBus.class }, new CustomGuiceModule(presenter, mathGapModel ));
		eventsBus = injector.getInstance(EventsBus.class);
		instance = injector.getInstance(InlineChoiceMathGapModule.class);
//		gapBinder = mock(GapBinder.class);
//		instance.gapBinder = gapBinder;
	}

	@Test
	public void testPostConstruct() {
		verify(eventsBus).addHandler(Mockito.eq(PlayerEvent.getType(PlayerEventTypes.PAGE_SWIPE_STARTED)), Mockito.eq(instance), Mockito.any(EventScope.class));
	}

	@Test
	public void testOnPlayerEvent() {
		PlayerEvent event = mock(PlayerEvent.class);
		when(event.getType()).thenReturn(PlayerEventTypes.PAGE_SWIPE_STARTED);
		instance.onPlayerEvent(event);

		verify(listBox).hidePopup();
		Set<PlayerEventTypes> types = new HashSet<PlayerEventTypes>(Arrays.asList(PlayerEventTypes.values()));
		types.remove(PlayerEventTypes.PAGE_SWIPE_STARTED);
		for (PlayerEventTypes type : types) {
			when(event.getType()).thenReturn(type);
			instance.onPlayerEvent(event);
		}
		verify(listBox).hidePopup();
	}
	
	
	
	@Test
	public void resetSetsNoItemsWhenChoiceGapWithoutEmptyOption() {
		Map<String, String> mathStyles = mockMathStyles(false);
		MathGapModel mathGapModel = injector.getInstance(Key.get(MathGapModel.class, ModuleScoped.class));
		mathGapModel.setMathStyles(mathStyles); 
		InlineChoiceMathGapModule choiceGap =  new InlineChoiceGapModuleMock();

		choiceGap.reset();

		assertThat(choiceGap.getListBox().getSelectedIndex(), equalTo(-1));
	}

	@Test
	public void resetSetsNoItemsWhenChoiceGapWithEmptyOption() {
		Map<String, String> mathStyles = mockMathStyles(true);
		MathGapModel mathGapModel = injector.getInstance(Key.get(MathGapModel.class, ModuleScoped.class));
		mathGapModel.setMathStyles(mathStyles); 
		InlineChoiceMathGapModule choiceGap =  new InlineChoiceGapModuleMock();
		
		choiceGap.reset();

		assertThat(choiceGap.getListBox().getSelectedIndex(), equalTo(0));
	}
	
	
	public Map<String, String> mockMathStyles(boolean hasEmptyOption) {
		Map<String, String> mathStyles = new HashMap<String, String>();

		String styleValue = (hasEmptyOption)?
								EmpiriaStyleNameConstants.VALUE_SHOW:
								EmpiriaStyleNameConstants.VALUE_HIDE;

		mathStyles.put(EmpiriaStyleNameConstants.EMPIRIA_MATH_INLINECHOICE_EMPTY_OPTION, styleValue);

		return mathStyles;
	}
	
	private class InlineChoiceGapModuleMock extends InlineChoiceMathGapModule {

    	private IsExListBox mockedListBox;

		public InlineChoiceGapModuleMock() {
			super(mock(InlineChoiceMathGapModulePresenter.class), mock(ResponseSocket.class));
			mathGapModel = injector.getInstance(Key.get(MathGapModel.class, ModuleScoped.class));
			initStyles();
			options = createOptions(getModuleElement(), getModuleSocket());
		}

		@Override
		protected List<String> createOptions(Element moduleElement, ModuleSocket moduleSocket) {
			ArrayList<String> mockedListBoxIdentifiers = new ArrayList<String>();

			mockedListBoxIdentifiers.add("MATH_RESPONSE_7_3");
			mockedListBoxIdentifiers.add("MATH_RESPONSE_7_4");
			mockedListBoxIdentifiers.add("MATH_RESPONSE_7_5");

			return mockedListBoxIdentifiers;
		}

		@Override
		public IsExListBox getListBox() {
			if (mockedListBox == null) {
				mockedListBox = mock(IsExListBox.class, new ReturnsJavaBeanAnswers());
			}
			return mockedListBox;
		}
    }
	

	@After
	public void after() {

	}

}
