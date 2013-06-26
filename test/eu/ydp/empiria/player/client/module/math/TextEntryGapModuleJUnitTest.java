package eu.ydp.empiria.player.client.module.math;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_MAXLENGTH;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.common.collect.Lists;
import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.xml.client.Element;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import eu.ydp.empiria.player.client.gin.factory.TextEntryModuleFactory;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.gap.GapModulePresenter;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.xml.XMLParser;

public class TextEntryGapModuleJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private static class CustomGuiceModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(TextEntryGapModulePresenter.class).toInstance(mock(TextEntryGapModulePresenter.class));
			TextEntryModuleFactory factory = mock(TextEntryModuleFactory.class);
			binder.bind(TextEntryModuleFactory.class).toInstance(factory);

			when(factory.getTextEntryGapModulePresenter(Mockito.any(IModule.class))).thenAnswer(new Answer<TextEntryGapModulePresenter>() {
				@Override
				public TextEntryGapModulePresenter answer(InvocationOnMock invocation) throws Throwable {
					return mock(TextEntryGapModulePresenter.class);
				}
			});
		}
	}

	@Before
	public void before() {
		setUp(new Class<?>[] {}, new CustomGuiceModule());
	}

	@Test
	public void testIfSubOrSupIsCorrectlyDetected() {
		TextEntryGapModule textGap = mockTextGap();

		Element node = XMLParser.parse("<gap type=\"text-entry\" uid=\"uid_0000\" />").getDocumentElement();
		Element parentNode = XMLParser.parse(
				"<mmultiscripts>" + "<gap type=\"text-entry\" uid=\"uid_0000\" />" + "<none/>" + "<mprescripts/>" + "<none/>" + "<none/>" + "</mmultiscripts>")
				.getDocumentElement();

		Assert.assertTrue(textGap.isSubOrSup(node, parentNode));
	}

	@Test
	public void testIfCorrectAnswerIsFound() {
		TextEntryGapModule textGap = mockTextGap();
		Assert.assertTrue(textGap.getCorrectAnswer().equals("13"));
	}

	@Test
	public void testLongestAnswerLength() {
		TextEntryGapModule textGap = mockTextGap();
		int answerLength = textGap.getLongestAnswerLength();
		assertThat(answerLength, is(4));
	}

	@Test
	public void testMaxlengthBindingValue() {
		Map<String, String> styles = new HashMap<String, String>();
		styles.put(EMPIRIA_TEXTENTRY_GAP_MAXLENGTH, "3");
		TextEntryGapModuleMock textGap = mockTextGap();
		textGap.invokeSetMaxlengthBinding(styles);
		verify(textGap.getPresenter()).setMaxLength(3);
	}

	@Test
	public void testWidthBindingValue() {
		Map<String, String> styles = new HashMap<String, String>();
		styles.put(EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN, "gap");
		TextEntryGapModuleMock textGap = mockTextGap();
		textGap.invokeSetWidthBinding(styles);
		int width = textGap.invokeCalculateTextBoxWidth();
		assertThat(width, is(80));
	}

	@Test
	public void testIfMarkAnswersWorksCorrectly() {
		TextEntryGapModuleMock gap1 = mockTextGap();
		TextEntryGapModuleMock gap2 = mockTextGap();
		TextEntryGapModuleMock gap3 = mockTextGap();

		gap1.setMockedResponse("13");
		gap2.setMockedResponse("4");
		gap3.setMockedResponse("");

		gap1.setEvaluatedResponse(true);
		gap2.setEvaluatedResponse(false);
		gap3.setEvaluatedResponse(false);

		gap1.markAnswers(true);
		gap2.markAnswers(true);
		gap3.markAnswers(true);

		verify(gap1.getPresenter()).setMarkMode(GapModulePresenter.CORRECT);
		verify(gap2.getPresenter()).setMarkMode(GapModulePresenter.WRONG);
		verify(gap3.getPresenter()).setMarkMode(GapModulePresenter.NONE);
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

	public TextEntryGapModuleMock mockTextGap(Map<String, String> styles) {
		return new TextEntryGapModuleMock(styles);
	}

	public TextEntryGapModuleMock mockTextGap() {
		return new TextEntryGapModuleMock(new HashMap<String, String>());
	}

	private class TextEntryGapModuleMock extends TextEntryGapModule {

		private boolean evaluatedResponse;

		public TextEntryGapModuleMock(Map<String, String> styles) {
			super(injector.getInstance(TextEntryModuleFactory.class), injector.getInstance(StyleSocket.class),injector.getInstance(SourcelistManager.class));
			this.mathStyles = styles;
		}

		protected String mockedResponse;

		@Override
		protected Response getResponse() {
			Response response = mock(Response.class);
			response.groups = Lists.newArrayList();
			response.correctAnswers = new CorrectAnswers();
			response.correctAnswers.add(new ResponseValue("13"));
			response.correctAnswers.add(new ResponseValue("1322"));
			response.correctAnswers.add(new ResponseValue("1"));

			return response;
		}

		@Override
		public int getFontSize() {
			return 20;
		}

		public void invokeSetMaxlengthBinding(Map<String, String> styles) {
			setMaxlengthBinding(styles, XMLParser.parse("<gap type=\"text-entry\" uid=\"uid_0000\" />").getDocumentElement());
		}

		public void invokeSetWidthBinding(Map<String, String> styles) {
			setWidthBinding(styles, XMLParser.parse("<gap type=\"text-entry\" uid=\"uid_0000\" />").getDocumentElement());
		}

		public int invokeCalculateTextBoxWidth() {
			return getLongestAnswerLength() * getFontSize();
		}

		public void setMockedResponse(String response) {
			mockedResponse = response;
		}

		@Override
		protected String getCurrentResponseValue() {
			return mockedResponse;
		}

		public GapModulePresenter getPresenter() {
			return presenter;
		}

		public void setEvaluatedResponse(boolean evaluatedResponse) {
			this.evaluatedResponse = evaluatedResponse;
		}

		@Override
		protected List<Boolean> getEvaluatedResponse() {
			List<Boolean> evaluations = Lists.newArrayList();
			evaluations.add(evaluatedResponse);
			return evaluations;
		}

	}
}
