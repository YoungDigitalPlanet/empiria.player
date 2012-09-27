package eu.ydp.empiria.player.client.module.math;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.FlowPanel;

import eu.ydp.empiria.player.client.components.ExListBox;
import eu.ydp.empiria.player.client.components.ExListBox.ExListBoxUnitTestAccess;
import eu.ydp.empiria.player.client.components.ExListBoxOption;

public class InlineChoiceGapJUnitTest {

	@Test
	public void properValueAccessWithEmptyOption() {
		InlineChoiceGap choiceGap = mockChoiceGap(true);
		
		choiceGap.setValue("MATH_RESPONSE_7_5");
		String received = choiceGap.getValue();		
		
		assertThat(received, equalTo("MATH_RESPONSE_7_5"));		
	}

	@Test
	public void properValueAccessWithoutEmptyOption() {
		InlineChoiceGap choiceGap = mockChoiceGap(false);
		
		choiceGap.setValue("MATH_RESPONSE_7_5");
		String received = choiceGap.getValue();
				
		assertThat(received, equalTo("MATH_RESPONSE_7_5"));		
	}
	
	@Test
	public void resetSetsNoItemsWhenChoiceGapWithoutEmptyOption() {
		InlineChoiceGap choiceGap = mockChoiceGap(false);
		
		choiceGap.reset();
		
		assertThat(choiceGap.getListBox().getSelectedIndex(), equalTo(-1));
	}
	
	@Test
	public void resetSetsNoItemsWhenChoiceGapWithEmptyOption() {
		InlineChoiceGap choiceGap = mockChoiceGap(true);
		
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
    
	public InlineChoiceGap mockChoiceGap(boolean hasEmptyOption) {
		ArrayList<String> mockedListBoxIdentifiers = new ArrayList<String>();
		mockedListBoxIdentifiers.add("MATH_RESPONSE_7_3");			
		mockedListBoxIdentifiers.add("MATH_RESPONSE_7_4");
		mockedListBoxIdentifiers.add("MATH_RESPONSE_7_5");
		
		ExListBox mockedListBox = mock(ExListBox.class);
		
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
		
		return new InlineChoiceGapMock(mockedListBox, mockedListBoxIdentifiers, hasEmptyOption);
	}    
    
    private class InlineChoiceGapMock extends InlineChoiceGap {
    	
		public InlineChoiceGapMock(ExListBox listBox, List<String> options, boolean hasEmptyOption) {
			super(listBox, options, hasEmptyOption);
		}

		@Override
		public FlowPanel createFlowPanel() {		
			return mock(FlowPanel.class);
		}
    }
}
