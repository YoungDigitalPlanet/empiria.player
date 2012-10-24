package eu.ydp.empiria.player.client.module.math;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.components.ExListBox;
import eu.ydp.empiria.player.client.components.ExListBox.ExListBoxUnitTestAccess;
import eu.ydp.empiria.player.client.components.ExListBoxOption;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.gwtutil.junit.mock.GWTConstantsMock;

public class InlineChoiceGapModuleJUnitTest {

	@Test
	public void properValueAccessWithEmptyOption() {
		InlineChoiceGapModule choiceGap = mockChoiceGap(true);
		
		choiceGap.setValue("MATH_RESPONSE_7_5");
		String received = choiceGap.getValue();
		
		assertThat(received, equalTo("MATH_RESPONSE_7_5"));
	}

	@Test
	public void properValueAccessWithoutEmptyOption() {
		InlineChoiceGapModule choiceGap = mockChoiceGap(false);
		
		choiceGap.setValue("MATH_RESPONSE_7_5");
		String received = choiceGap.getValue();
				
		assertThat(received, equalTo("MATH_RESPONSE_7_5"));
	}

	@Test
	public void resetSetsNoItemsWhenChoiceGapWithoutEmptyOption() {
		InlineChoiceGapModule choiceGap = mockChoiceGap(false);
		
		choiceGap.reset();
		
		assertThat(choiceGap.getListBox().getSelectedIndex(), equalTo(-1));
	}
	
	@Test
	public void resetSetsNoItemsWhenChoiceGapWithEmptyOption() {
		InlineChoiceGapModule choiceGap = mockChoiceGap(true);
		
		choiceGap.reset();
		
		assertThat(choiceGap.getListBox().getSelectedIndex(), equalTo(0));
	}	
	
    @BeforeClass
    public static void prepareTestEnviroment() {
    	/**
    	 * disable GWT.create() behavior for pure JUnit testing
    	 */
    	GWTMockUtilities.disarm();    	
    }
    
    @AfterClass
    public static void restoreEnviroment() {
    	/**
    	 * restore GWT.create() behavior
    	 */
    	GWTMockUtilities.restore();
    }	
    
	public InlineChoiceGapModule mockChoiceGap(boolean hasEmptyOption) {		
		Map<String, String> mathStyles = new HashMap<String, String>();		
	
		String styleValue = (hasEmptyOption)? 
								EmpiriaStyleNameConstants.VALUE_SHOW:
								EmpiriaStyleNameConstants.VALUE_HIDE;
		
		mathStyles.put(EmpiriaStyleNameConstants.EMPIRIA_MATH_INLINECHOICE_EMPTY_OPTION, styleValue);
		
		return new InlineChoiceGapModuleMock(mathStyles);
	}    
    
    private class InlineChoiceGapModuleMock extends InlineChoiceGapModule {
    	
    	private ExListBox mockedListBox;
    	
		public InlineChoiceGapModuleMock(Map<String, String> mathStyles) {
			setPresenter();
			styles = new HashMap<String, String>();
			this.mathStyles = mathStyles;	
			initStyles();
			options = createOptions(getModuleElement(), getModuleSocket());
			initListBox();
		}
		
		@Override
		protected void setPresenter() {
			presenter = mock(InlineChoiceGapModulePresenter.class);
		}
		
		@Override
		List<String> createOptions(Element moduleElement, ModuleSocket moduleSocket) {
			ArrayList<String> mockedListBoxIdentifiers = new ArrayList<String>();

			mockedListBoxIdentifiers.add("MATH_RESPONSE_7_3");			
			mockedListBoxIdentifiers.add("MATH_RESPONSE_7_4");
			mockedListBoxIdentifiers.add("MATH_RESPONSE_7_5");
			
			return mockedListBoxIdentifiers;
		}
		
		protected void initListBox() {
			ExListBoxUnitTestAccess listBoxPrivateAccess = mockedListBox.new ExListBoxUnitTestAccess();
			
			ArrayList<ExListBoxOption> options = new ArrayList<ExListBoxOption>();
			
			if (hasEmptyOption) {
				options.add(mock(ExListBoxOption.class));
			}
			options.add(mock(ExListBoxOption.class));
			options.add(mock(ExListBoxOption.class));
			options.add(mock(ExListBoxOption.class));
			
			listBoxPrivateAccess.setOptions(options);
			
			doCallRealMethod().when(mockedListBox).setSelectedIndex(anyInt());
			doCallRealMethod().when(mockedListBox).getSelectedIndex();
		}
		
		@Override
		public ExListBox getListBox() {
			if (mockedListBox == null) {
				mockedListBox = mock(ExListBox.class);
			}
			
			return mockedListBox;
		}
		
		@Override
		protected EventsBus getEventsBus() {
			return mock(EventsBus.class);
		}
		
		@Override
		protected StyleNameConstants getStyleNameConstants() {
			return GWTConstantsMock.mockAllStringMethods(mock(StyleNameConstants.class), StyleNameConstants.class);
		}
    }
}
