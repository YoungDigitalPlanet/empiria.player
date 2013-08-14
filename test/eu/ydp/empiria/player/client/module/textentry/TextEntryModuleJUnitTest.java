package eu.ydp.empiria.player.client.module.textentry;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_MAXLENGTH;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.ResponseSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.expression.PipedReplacementsParser;
import eu.ydp.empiria.player.client.module.gap.GapBinder;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.xml.XMLParser;

@SuppressWarnings("PMD")
public class TextEntryModuleJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private static class CustomGuiceModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(TextEntryModulePresenter.class).toInstance(mock(TextEntryModulePresenter.class));
			binder.bind(ResponseSocket.class).annotatedWith(PageScoped.class).toInstance(mock(ResponseSocket.class));
			binder.bind(PipedReplacementsParser.class).toInstance(mock(PipedReplacementsParser.class));
		}
	}

	@Before
	public void before() {
		setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGuiceModule());
	}

	@Test
	public void setFontSize() {
		Map<String, String> styles = new HashMap<String, String>();
		styles.put(EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_FONT_SIZE, "12");
		TextEntryModuleMock textEntryModule = mockTextGap(styles);
		doNothing().when(textEntryModule.getPresenter()).setFontSize(anyDouble(), (Style.Unit) any());
		textEntryModule.invokeSetFontSize();
		textEntryModule.invokeSetDimensions();
		verify(textEntryModule.getPresenter(), times(1)).setFontSize(Double.parseDouble("12"), Unit.PX);
	}

	@Test
	public void setMaxlength() {
		Map<String, String> styles = new HashMap<String, String>();
		styles.put(EMPIRIA_TEXTENTRY_GAP_MAXLENGTH, "3");
		TextEntryModuleMock textEntryModule = mockTextGap();
		textEntryModule.invokeSetMaxlengthBinding(styles);
		verify(textEntryModule.getPresenter()).setMaxLength(3);
	}

	@Test
	public void setDimensionsPixelWidth() {
		Map<String, String> styles = new HashMap<String, String>();
		styles.put(EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_WIDTH, "60");
		TextEntryModuleMock textEntryModule = mockTextGap(styles);
		doNothing().when(textEntryModule.getPresenter()).setWidth(anyDouble(), (Style.Unit) any());
		textEntryModule.invokeSetFontSize();
		textEntryModule.invokeSetDimensions();
		verify(textEntryModule.getPresenter(), times(1)).setWidth(Double.parseDouble("60"), Unit.PX);
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

	public TextEntryModuleMock mockTextGap(Map<String, String> styles) {
		TextEntryModuleMock instance = new TextEntryModuleMock();
		instance.setStyles(styles);
		return instance;
	}

	public TextEntryModuleMock mockTextGap() {
		TextEntryModuleMock instance = new TextEntryModuleMock();
		instance.setStyles(new HashMap<String, String>());
		return instance;
	}

	private class TextEntryModuleMock extends TextEntryGapModule {

		public TextEntryModuleMock() {
			gapBinder = new GapBinder();
			textEntryPresenter = injector.getInstance(TextEntryModulePresenter.class);
			postConstruct();
		}

		public void setStyles(Map<String, String> styles) {
			this.styles = styles;
		}

		public TextEntryModulePresenter getPresenter() {
			return (TextEntryModulePresenter) presenter;
		}

		public void invokeSetFontSize() {
			setFontSize(styles);
		}
		
		public void invokeSetDimensions() {
			setDimensions(styles);
		}

		public void invokeSetMaxlengthBinding(Map<String, String> styles) {
			setMaxlengthBinding(styles, XMLParser.parse("<gap type=\"text-entry\" uid=\"uid_0000\" />").getDocumentElement());
		}

		@Override
		public void initReplacements(Map<String, String> styles) {
			super.initReplacements(styles);
		}
	}
}
