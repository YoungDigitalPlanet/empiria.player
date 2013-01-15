package eu.ydp.empiria.player.client.module.math;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.gwtutil.client.components.exlistbox.IsExListBox;
import eu.ydp.gwtutil.test.mock.ReturnsJavaBeanAnswers;

public class InlineChoiceGapModuleJUnitTest extends AbstractTestBase{

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

		InlineChoiceGapModuleMock inlineChoiceGapModuleMock = injector.getInstance(InlineChoiceGapModuleMock.class);
		inlineChoiceGapModuleMock.initMock(mathStyles);
		return inlineChoiceGapModuleMock;
	}

    public static class InlineChoiceGapModuleMock extends InlineChoiceGapModule {

    	private IsExListBox mockedListBox;

		public InlineChoiceGapModuleMock() {
			super(mock(InlineChoiceGapModulePresenter.class));

		}

		public void initMock(Map<String, String> mathStyles){
			this.mathStyles = mathStyles;
			initStyles();
			options = createOptions(getModuleElement(), getModuleSocket());
		}

		@Override
		List<String> createOptions(Element moduleElement, ModuleSocket moduleSocket) {
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

		@Override
		protected boolean isResponseInMathModule() {
			return false;
		}
    }
}
