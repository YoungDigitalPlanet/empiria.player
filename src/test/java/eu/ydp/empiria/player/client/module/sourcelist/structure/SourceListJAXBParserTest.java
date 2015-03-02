package eu.ydp.empiria.player.client.module.sourcelist.structure;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;

public class SourceListJAXBParserTest extends AbstractEmpiriaPlayerGWTTestCase {

	public void testTextOptions() {
		// when
		SourceListBean bean = parse(SourceListJAXBParserMock.XML_TEXTS);

		// then
		List<SimpleSourceListItemBean> items = bean.getSimpleSourceListItemBeans();

		List<SourcelistItemType> types = extractTypes(items);
		List<String> values = extractContents(items);

		assertEquals(values, Lists.newArrayList("psa", "kota", "tygrysa"));
		assertEquals(types, Lists.newArrayList(SourcelistItemType.TEXT, SourcelistItemType.TEXT, SourcelistItemType.TEXT));
	}

	public void testImageOptions() {
		// when
		SourceListBean bean = parse(SourceListJAXBParserMock.XML_IMAGES);

		// then
		List<SimpleSourceListItemBean> items = bean.getSimpleSourceListItemBeans();

		List<SourcelistItemType> types = extractTypes(items);
		List<String> values = extractContents(items);

		assertEquals(values, Lists.newArrayList("psa.png", "kota.png", "tygrysa.png"));
		assertEquals(types, Lists.newArrayList(SourcelistItemType.IMAGE, SourcelistItemType.IMAGE, SourcelistItemType.IMAGE));
		assertEquals(0, bean.getImagesWidth());
		assertEquals(0, bean.getImagesHeight());
	}

	public void testImageOptionsWithSize() {
		// when
		SourceListBean bean = parse(SourceListJAXBParserMock.XML_IMAGES_WITH_DIMENSION);

		// then
		List<SimpleSourceListItemBean> items = bean.getSimpleSourceListItemBeans();

		List<SourcelistItemType> types = extractTypes(items);
		List<String> values = extractContents(items);

		assertEquals(values, Lists.newArrayList("psa.png", "kota.png", "tygrysa.png"));
		assertEquals(types, Lists.newArrayList(SourcelistItemType.IMAGE, SourcelistItemType.IMAGE, SourcelistItemType.IMAGE));
		assertEquals(600, bean.getImagesWidth());
		assertEquals(602, bean.getImagesHeight());

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

	private SourceListBean parse(String xml) {
		SourceListJAXBParser jaxbParserFactory = GWT.create(SourceListJAXBParser.class);
		JAXBParser<SourceListBean> jaxbParser = jaxbParserFactory.create();
		SourceListBean slBean = jaxbParser.parse(xml);
		return slBean;
	}
}
