package eu.ydp.empiria.player.client.module.sourcelist.structure;

import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;

import java.util.List;

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

    public void testParseBeanWithNoShuffle() {
        // when
        SourceListBean bean = parse(SourceListJAXBParserMock.XML_WITHOUT_SHUFFLE);

        // then
        assertFalse(bean.isShuffle());
        assertTrue(bean.isMoveElements());
        assertEquals("dummy2", bean.getId());

        List<SimpleSourceListItemBean> items = bean.getSimpleSourceListItemBeans();
        List<SourcelistItemType> types = extractTypes(items);
        List<String> values = extractContents(items);
        List<String> alts = extractAlts(items);

        assertEquals(values, Lists.newArrayList("psa", "kota", "tygrysa"));
        assertEquals(types, Lists.newArrayList(SourcelistItemType.TEXT, SourcelistItemType.TEXT, SourcelistItemType.TEXT));
        assertEquals(alts, Lists.newArrayList("psa", "kota", "tygrysa"));
    }

    public void testParseBeanWithShuffle() {
        // when
        SourceListBean bean = parse(SourceListJAXBParserMock.XML_WITH_MORE_ITEMS);

        // then
        assertTrue(bean.isShuffle());
        assertEquals("dummy2", bean.getId());

        List<SimpleSourceListItemBean> items = bean.getSimpleSourceListItemBeans();
        List<String> values = extractContents(items);

        assertEquals(values, Lists.newArrayList("psa", "kota", "tygrysa", "psa1", "kota1", "tygrysa1", "psa2", "kota2", "tygrysa2"));

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

    private List<String> extractAlts(List<SimpleSourceListItemBean> items) {
        List<String> alts = Lists.newArrayList();
        for (SimpleSourceListItemBean beanItem : items) {
            alts.add(beanItem.getAlt());
        }
        return alts;
    }

    private SourceListBean parse(String xml) {
        SourceListJAXBParser jaxbParserFactory = GWT.create(SourceListJAXBParser.class);
        JAXBParser<SourceListBean> jaxbParser = jaxbParserFactory.create();
        SourceListBean slBean = jaxbParser.parse(xml);
        return slBean;
    }
}
