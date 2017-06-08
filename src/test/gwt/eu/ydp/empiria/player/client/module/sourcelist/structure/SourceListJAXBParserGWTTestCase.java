/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.module.sourcelist.structure;

import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;
import eu.ydp.empiria.player.client.module.sourcelist.predicates.ComplexTextPredicate;
import eu.ydp.empiria.player.client.module.sourcelist.predicates.FormattedTextPredicate;
import eu.ydp.empiria.player.client.module.sourcelist.predicates.SpecialCharacterPredicate;

import java.util.List;

import static eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType.*;
import static eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListJAXBParserMock.*;

public class SourceListJAXBParserGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private SpecialCharacterPredicate specialCharacterPredicate = new SpecialCharacterPredicate();
    private FormattedTextPredicate formattedTextPredicate = new FormattedTextPredicate();
    private ComplexTextPredicate complexTextChecker = new ComplexTextPredicate(specialCharacterPredicate, formattedTextPredicate);

    public void testTextOptions() {
        // when
        SourceListBean bean = parse(XML_TEXTS);

        // then
        List<SimpleSourceListItemBean> items = bean.getSimpleSourceListItemBeans();

        List<SourcelistItemType> types = extractTypes(items);
        List<String> values = extractContents(items);

        assertEquals(values, Lists.newArrayList("psa", "kota", "tygrysa"));
        assertEquals(types, Lists.newArrayList(TEXT, TEXT, TEXT));
    }

    public void testParseBeanWithNoShuffle() {
        // when
        SourceListBean bean = parse(XML_WITHOUT_SHUFFLE);

        // then
        assertFalse(bean.isShuffle());
        assertTrue(bean.isMoveElements());
        assertEquals("dummy2", bean.getId());

        List<SimpleSourceListItemBean> items = bean.getSimpleSourceListItemBeans();
        List<SourcelistItemType> types = extractTypes(items);
        List<String> values = extractContents(items);
        List<String> alts = extractAlts(items);

        assertEquals(values, Lists.newArrayList("psa", "kota", "tygrysa"));
        assertEquals(types, Lists.newArrayList(TEXT, TEXT, TEXT));
        assertEquals(alts, Lists.newArrayList("psa", "kota", "tygrysa"));
    }

    public void testParseBeanWithShuffle() {
        // when
        SourceListBean bean = parse(XML_WITH_MORE_ITEMS);

        // then
        assertTrue(bean.isShuffle());
        assertEquals("dummy2", bean.getId());

        List<SimpleSourceListItemBean> items = bean.getSimpleSourceListItemBeans();
        List<String> values = extractContents(items);

        assertEquals(values, Lists.newArrayList("psa", "kota", "tygrysa", "psa1", "kota1", "tygrysa1", "psa2", "kota2", "tygrysa2"));

    }

    public void testImageOptions() {
        // when
        SourceListBean bean = parse(XML_IMAGES);

        // then
        List<SimpleSourceListItemBean> items = bean.getSimpleSourceListItemBeans();

        List<SourcelistItemType> types = extractTypes(items);
        List<String> values = extractContents(items);

        assertEquals(values, Lists.newArrayList("psa.png", "kota.png", "tygrysa.png"));
        assertEquals(types, Lists.newArrayList(IMAGE, IMAGE, IMAGE));
        assertEquals(0, bean.getImagesWidth());
        assertEquals(0, bean.getImagesHeight());
    }

    public void testImageOptionsWithSize() {
        // when
        SourceListBean bean = parse(XML_IMAGES_WITH_DIMENSION);

        // then
        List<SimpleSourceListItemBean> items = bean.getSimpleSourceListItemBeans();

        List<SourcelistItemType> types = extractTypes(items);
        List<String> values = extractContents(items);

        assertEquals(values, Lists.newArrayList("psa.png", "kota.png", "tygrysa.png"));
        assertEquals(types, Lists.newArrayList(IMAGE, IMAGE, IMAGE));
        assertEquals(600, bean.getImagesWidth());
        assertEquals(602, bean.getImagesHeight());

    }

    public void testSourcelistShouldHasComplexText_whenContainsMathElement() {
        // given
        SourceListBean bean = parse(SOURCELIST_WITH_MATH);

        // when
        List<SimpleSourceListItemBean> items = bean.getSimpleSourceListItemBeans();
        List<SourcelistItemType> types = extractTypes(items);

        // then
        assertEquals(types, Lists.newArrayList(COMPLEX_TEXT, TEXT, TEXT));
    }

    public void testSourcelistShouldHasComplexText_whenContainsFormattedElement() {
        // given
        SourceListBean bean = parse(SOURCELIST_WITH_FORMATTED_TEXT);

        // when
        List<SimpleSourceListItemBean> items = bean.getSimpleSourceListItemBeans();
        List<SourcelistItemType> types = extractTypes(items);

        // then
        assertEquals(types, Lists.newArrayList(COMPLEX_TEXT, COMPLEX_TEXT));
    }

    private List<String> extractContents(List<SimpleSourceListItemBean> items) {
        List<String> values = Lists.newArrayList();
        for (SimpleSourceListItemBean beanItem : items) {
            values.add(beanItem.getItemValue(complexTextChecker).getContent());
        }
        return values;
    }

    private List<SourcelistItemType> extractTypes(List<SimpleSourceListItemBean> items) {
        List<SourcelistItemType> types = Lists.newArrayList();
        for (SimpleSourceListItemBean beanItem : items) {
            types.add(beanItem.getItemValue(complexTextChecker).getType());
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
