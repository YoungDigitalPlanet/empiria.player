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

package eu.ydp.empiria.player.client.util.style;

import com.google.gwt.dom.client.Element;
import eu.ydp.empiria.player.client.AbstractTestBase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("PMD")
public class StyleToPropertyMappingHelperTest extends AbstractTestBase {

    private StyleToPropertyMappingHelper testObject;
    private NativeStyleHelper nativeStyleHelper;

    @Before
    public void init() {
        testObject = injector.getInstance(StyleToPropertyMappingHelper.class);
        nativeStyleHelper = injector.getInstance(NativeStyleHelper.class);
    }

    @Test
    public void setPropertyTest() {
        // prepare
        Map<String, String> propertyStyle = new HashMap<String, String>();
        propertyStyle.put("lineWidth", "0");
        propertyStyle.put("lineCap", "miter");
        propertyStyle.put("lineJoin", "square");
        propertyStyle.put("miterLimit", "3");

        // test
        testObject.applyStyles(mock(Element.class), propertyStyle);
        verify(nativeStyleHelper, times(4)).applyProperty(any(Element.class), any(String.class), any(String.class));
        for (Map.Entry<String, String> entry : propertyStyle.entrySet()) {
            verify(nativeStyleHelper).applyProperty(any(Element.class), Matchers.eq(entry.getKey()), Matchers.eq(entry.getValue()));
        }
    }

    @Test
    public void callFunctionTest() {
        // prepare
        Map<String, String> propertyStyle = new HashMap<String, String>();
        propertyStyle.put("lineWidth", "0");
        propertyStyle.put("lineCap", "miter");
        propertyStyle.put("lineJoin", "square");
        propertyStyle.put("miterLimit", "3");
        propertyStyle.put("fn-lineCap", "miter");
        propertyStyle.put("fn-lineJoin", "square");
        propertyStyle.put("fn-miterLimit", "3");
        // test
        testObject.applyStyles(mock(Element.class), propertyStyle);
        verify(nativeStyleHelper, times(0)).callFunction(any(Element.class), any(String.class), any(String.class));
        testObject.addFunctionToWhiteList("lineCap");
        testObject.addFunctionToWhiteList("miterLimit");
        testObject.applyStyles(mock(Element.class), propertyStyle);
        verify(nativeStyleHelper, times(2)).callFunction(any(Element.class), any(String.class), any(String.class));
    }

}
