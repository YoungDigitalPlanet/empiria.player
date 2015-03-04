package eu.ydp.empiria.player.client.module.sourcelist.structure;

import static eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType.TEXT;
import static junit.framework.Assert.*;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;
import eu.ydp.gwtutil.client.json.YJsonArray;

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

	@Test
	public void parseBean() {
		// given
		YJsonArray state = Mockito.mock(YJsonArray.class);

		// when
		instance.createFromXml(SourceListJAXBParserMock.XML_WITHOUT_SHUFFLE, state);

		// then
		SourceListBean bean = instance.getBean();
		assertTrue(bean.isMoveElements());
		assertTrue(!bean.isShuffle());
		assertEquals("dummy2", bean.getId());

		List<SimpleSourceListItemBean> items = bean.getSimpleSourceListItemBeans();

		List<SourcelistItemType> types = extractTypes(items);
		List<String> contents = extractContents(items);
		List<String> alts = extractAlts(items);

		assertThat(types).containsSequence(TEXT, TEXT, TEXT);
		assertThat(contents).containsSequence("psa", "kota", "tygrysa");
		assertThat(alts).containsSequence("psa", "kota", "tygrysa");
	}

	@Test
	public void noShuffleTest() {
		// given
		YJsonArray state = Mockito.mock(YJsonArray.class);

		// when
		instance.createFromXml(SourceListJAXBParserMock.XML_WITHOUT_SHUFFLE, state);

		// then
		List<String> contents = extractContents(instance.getBean().getSimpleSourceListItemBeans());
		assertThat(contents).containsSequence("psa", "kota", "tygrysa");
	}

	@Test
	public void shuffleTest() {
		YJsonArray state = Mockito.mock(YJsonArray.class);

		// when
		instance.createFromXml(SourceListJAXBParserMock.XML_WITH_MORE_ITEMS, state);

		// then
		SourceListBean bean = instance.getBean();
		List<String> list = new ArrayList<String>(Arrays.asList("psa", "kota", "tygrysa", "psa1", "kota1", "tygrysa1", "psa2", "kota2", "tygrysa2"));
		List<SimpleSourceListItemBean> items = bean.getSimpleSourceListItemBeans();
		List<String> contents = extractContents(items);

		assertThat(contents).isNotEqualTo(list);
	}

	private List<String> extractContents(List<SimpleSourceListItemBean> items) {
		List<String> values = Lists.newArrayList();
		for (SimpleSourceListItemBean beanItem : items) {
			values.add(beanItem.getItemValue().getContent());
		}
		return values;
	}

	private List<SourcelistItemType> extractTypes(List<SimpleSourceListItemBean> items) {
		List<SourcelistItemType> types = Lists.newArrayList();
		for (SimpleSourceListItemBean beanItem : items) {
			types.add(beanItem.getItemValue().getType());
		}
		return types;
	}

	private List<String> extractAlts(List<SimpleSourceListItemBean> items) {
		List<String> alts = Lists.newArrayList();
		for (SimpleSourceListItemBean beanItem : items) {
			alts.add(beanItem.getAlt());
		}
		return alts;
	}

}
