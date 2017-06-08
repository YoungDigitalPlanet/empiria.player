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

package eu.ydp.empiria.player.client;

import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import junit.framework.Assert;
import org.apache.tools.ant.filters.StringInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.lang.reflect.ParameterizedType;

public abstract class TestJAXBParser<T> implements JAXBParser<T> {

    private Unmarshaller unmarshaller;

    @Override
    public T parse(String xml) {
        ensureUnmarshaller();
        try {
            return (T) unmarshaller.unmarshal(new StringInputStream(xml));
        } catch (JAXBException e) {
            Assert.fail(e.getMessage());
            return null;
        }
    }

    private void ensureUnmarshaller() {
        if (unmarshaller == null) {
            unmarshaller = getUnmarshaller();
        }
    }

    @SuppressWarnings("unchecked")
    private Class<T> getBeanClass() throws Exception {
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) superclass.getActualTypeArguments()[0];
    }

    private Unmarshaller getUnmarshaller() {
        Unmarshaller unmarshaller = null;

        try {
            JAXBContext context = JAXBContext.newInstance(getBeanClass());
            unmarshaller = context.createUnmarshaller();
            return unmarshaller;
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
            return unmarshaller;
        }
    }
}
