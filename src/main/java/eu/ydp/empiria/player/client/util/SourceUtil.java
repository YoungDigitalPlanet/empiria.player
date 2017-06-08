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

package eu.ydp.empiria.player.client.util;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import eu.ydp.gwtutil.client.xml.XMLUtils;

import java.util.HashMap;
import java.util.Map;

public final class SourceUtil {
    private SourceUtil() {
    }

    public static String getMpegSource(Map<String, String> sources) {
        for (Map.Entry<String, String> src : sources.entrySet()) {
            if (src.getValue().matches(".*mpeg|.*mp4|.*mp3")) {
                return src.getKey(); // NOPMD
            }
        }
        return null;
    }

    public static boolean containsOgg(Map<String, String> sources) {
        for (Map.Entry<String, String> src : sources.entrySet()) {
            if (src.getValue().matches(".*ogv|.*ogg")) {
                return true;
            }
        }
        return false;
    }

    public static String getOggSource(Map<String, String> sources) {
        for (Map.Entry<String, String> src : sources.entrySet()) {
            if (src.getValue().matches(".*ogv|.*ogg")) {
                return src.getKey(); // NOPMD
            }
        }
        return null;
    }

    /**
     * Zwraca kolekcje zrodel powiazanych z tym elementem key=url,value=type
     *
     * @param element
     * @return
     */
    public static Map<String, String> getSource(Element element, String mediaType) {
        NodeList sources = element.getElementsByTagName("source");
        String src = XMLUtils.getAttributeAsString(element, "data");
        Map<String, String> retValue = new HashMap<String, String>();
        if (sources.getLength() > 0) {
            for (int x = 0; x < sources.getLength(); ++x) {
                src = XMLUtils.getAttributeAsString((Element) sources.item(x), "src");
                String type = XMLUtils.getAttributeAsString((Element) sources.item(x), "type");
                retValue.put(src, type);
            }
        } else {
            String[] type = src.split("[.]");
            retValue.put(src, mediaType + "/" + type[type.length - 1]);
        }
        return retValue;
    }
}
