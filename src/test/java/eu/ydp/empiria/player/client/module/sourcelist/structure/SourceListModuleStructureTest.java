package eu.ydp.empiria.player.client.module.sourcelist.structure;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.gwtutil.client.json.YJsonArray;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.spy;

@SuppressWarnings("PMD")
public class SourceListModuleStructureTest extends AbstractTestBase {

    private SourceListModuleStructure instance;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        instance = spy(injector.getInstance(SourceListModuleStructure.class));
    }

    @Test
    public void getFactoryTestNotNullTest() {
        assertNotNull(instance.getParserFactory());
    }

    @Test
    public void parseBeanTestNotNullCheck() {
        YJsonArray state = Mockito.mock(YJsonArray.class);

        instance.createFromXml(SourceListJAXBParserMock.XML, state);
        assertNotNull(instance.getBean());
    }
}
