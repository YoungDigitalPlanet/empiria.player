package eu.ydp.empiria.player.client.module.textentry;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_MAXLENGTH;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.gin.factory.TextEntryModuleFactory;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.binding.BindingType;
import eu.ydp.empiria.player.client.module.binding.BindingValue;
import eu.ydp.empiria.player.client.module.binding.gapmaxlength.GapMaxlengthBindingValue;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.gwtutil.xml.XMLParser;

public class TextEntryModuleJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private static class CustomGuiceModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(TextEntryModulePresenter.class).toInstance(mock(TextEntryModulePresenter.class));
			TextEntryModuleFactory factory = mock(TextEntryModuleFactory.class);
			when(factory.getTextEntryModulePresenter(Mockito.any(IModule.class))).thenReturn(mock(TextEntryModulePresenter.class));
			binder.bind(TextEntryModuleFactory.class).toInstance(factory);
		}
	}

	@Before
	public void before() {
		setUp(new Class<?>[] {}, new CustomGuiceModule());
	}

	@Test
	public void setDimensionsPixelWidth() {
		Map<String, String> styles = new HashMap<String, String>();
		styles.put(EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_WIDTH, "60");
		TextEntryModuleMock textEntryModule = mockTextGap(styles);
		doCallRealMethod().when(textEntryModule.getPresenter()).setWidth(anyDouble(), (Style.Unit) any());
		textEntryModule.invokeSetDimensions();
		Mockito.verify(textEntryModule.getPresenter(), Mockito.times(1)).setWidth(Double.parseDouble("60"), Unit.PX);
	}

	@Test
	public void setMaxlength() {
		Map<String, String> styles = new HashMap<String, String>();
		styles.put(EMPIRIA_TEXTENTRY_GAP_MAXLENGTH, "3");
		TextEntryModuleMock textEntryModule = mockTextGap();
		textEntryModule.invokeSetMaxlengthBinding(styles);
		BindingValue bindingValue = textEntryModule.getBindingValue(BindingType.GAP_MAXLENGHTS);
		int maxlength = ((GapMaxlengthBindingValue) bindingValue).getGapCharactersCount();
		assertThat(maxlength, is(3));
	}

	@Test
	public void setFontSize() {
		Map<String, String> styles = new HashMap<String, String>();
		styles.put(EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_FONT_SIZE, "12");
		TextEntryModuleMock textEntryModule = mockTextGap(styles);
		doCallRealMethod().when(textEntryModule.getPresenter()).setFontSize(anyDouble(), (Style.Unit) any());
		textEntryModule.invokeSetDimensions();
		Mockito.verify(textEntryModule.getPresenter(), Mockito.times(1)).setFontSize(Double.parseDouble("12"), Unit.PX);
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

	private class TextEntryModuleMock extends TextEntryModule {

		public TextEntryModuleMock() {
			super(injector.getInstance(TextEntryModuleFactory.class));
		}

		public void setStyles(Map<String, String> styles) {
			this.styles = styles;
		}

		public TextEntryModulePresenter getPresenter() {
			return (TextEntryModulePresenter) presenter;
		}

		@Override
		protected EventsBus getEventsBus() {
			return mock(EventsBus.class);
		}

		public void invokeSetDimensions() {
			setDimensions(styles);
		}

		public void invokeSetMaxlengthBinding(Map<String, String> styles) {
			setMaxlengthBinding(styles, XMLParser.parse("<gap type=\"text-entry\" uid=\"uid_0000\" />").getDocumentElement());
		}
	}
}
