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

package eu.ydp.empiria.player.client.util.js;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayUtils;

import java.util.Collection;

public class JSArrayUtils {

    public static native JsArray<JavaScriptObject> createArray(int length)/*-{
        return new Array(length);
    }-*/;

    public static native JavaScriptObject fillArray(JavaScriptObject array, int index, int item)/*-{
        array[index] = item;
        return array;
    }-*/;

    public static native JavaScriptObject fillArray(JavaScriptObject array, int index, String item)/*-{
        array[index] = item;
        return array;
    }-*/;

    public static native JavaScriptObject fillArray(JavaScriptObject array, int index, JavaScriptObject item)/*-{
        array[index] = item;
        return array;
    }-*/;

    public static native JavaScriptObject fillArray(JavaScriptObject array, String index, String item)/*-{
        array[index] = item;
        return array;
    }-*/;

    public static native JavaScriptObject fillArray(JavaScriptObject array, String index, boolean item)/*-{
        array[index] = item;
        return array;
    }-*/;

    public static native JavaScriptObject fillArray(JavaScriptObject array, String index, JavaScriptObject item)/*-{
        array[index] = item;
        return array;
    }-*/;

    public static <T extends JavaScriptObject> JsArray<JavaScriptObject> convert(Collection<T> collection) {
        JavaScriptObject[] array = collection.toArray(new JavaScriptObject[0]);
        return JsArrayUtils.readOnlyJsArray(array);
    }
}
