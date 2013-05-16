package eu.ydp.empiria.player.client;

import static org.mockito.Mockito.spy;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.google.gwt.junit.GWTMockUtilities;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.structure.ModuleBean;
import eu.ydp.gwtutil.client.json.YJsonArray;

public abstract class AbstractModuleStructureTestBase<M extends AbstractModuleStructure<B, P>, B extends ModuleBean, P extends JAXBParserFactory<B>> {

	private AbstractModuleStructure<B, P> moduleStructure;

	protected abstract M createModuleStructure();

	@Before
	public void init() {
		moduleStructure = spy(createModuleStructure());
	}

	public B createFromXML(String xmlString, YJsonArray state) {
		moduleStructure.createFromXml(xmlString, state);
		return moduleStructure.getBean();
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

}
