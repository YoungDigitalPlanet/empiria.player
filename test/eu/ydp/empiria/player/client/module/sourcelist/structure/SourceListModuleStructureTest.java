package eu.ydp.empiria.player.client.module.sourcelist.structure;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.gwtutil.client.json.YJsonArray;

@SuppressWarnings("PMD")
public class SourceListModuleStructureTest extends AbstractTestBase {
	private SourceListModuleStructure instance;

	@Before
	public void before() {
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

	@Test
	public void parseBeanTestAttributeCheck() {
		YJsonArray state = Mockito.mock(YJsonArray.class);

		instance.createFromXml(SourceListJAXBParserMock.XML_WITHOUT_SHUFFLE, state);
		SourceListBean bean = instance.getBean();
		assertTrue(bean.isMoveElements());
		assertTrue(!bean.isShuffle());
		assertEquals("dummy2", bean.getId());
		assertNotNull(bean.getSimpleSourceListItemBeans());
		assertTrue(bean.getSimpleSourceListItemBeans().size() == 3);

		List<String> list = new ArrayList<String>(Arrays.asList("psa", "kota", "tygrysa"));
		for (SimpleSourceListItemBean benItem : bean.getSimpleSourceListItemBeans()) {
			assertNotNull(benItem.getAlt());
			assertNotNull(benItem.getValue());
			String value = list.remove(0);
			assertEquals(value, benItem.getAlt());
			assertEquals(value, benItem.getValue());
		}
	}

	@Test
	public void noShuffleTest() {
		YJsonArray state = Mockito.mock(YJsonArray.class);

		instance.createFromXml(SourceListJAXBParserMock.XML_WITHOUT_SHUFFLE, state);
		Mockito.verify(instance, times(0)).shuffle();
	}

	@Test
	public void shuffleTest() {
		YJsonArray state = Mockito.mock(YJsonArray.class);

		instance.createFromXml(SourceListJAXBParserMock.XML_WITH_MORE_ITEMS, state);
		Mockito.verify(instance, times(1)).shuffle();
		SourceListBean bean = instance.getBean();
		List<String> list = new ArrayList<String>(Arrays.asList("psa", "kota", "tygrysa", "psa1", "kota1", "tygrysa1", "psa2", "kota2", "tygrysa2"));
		List<String> listToCompare = new ArrayList<String>();
		List<SimpleSourceListItemBean> simpleSourceListItemBeans = bean.getSimpleSourceListItemBeans();
		for (int x = 0; x < simpleSourceListItemBeans.size(); ++x) {
			listToCompare.add(simpleSourceListItemBeans.get(x).getValue());
		}
		assertNotSame(list, listToCompare);
	}

}
