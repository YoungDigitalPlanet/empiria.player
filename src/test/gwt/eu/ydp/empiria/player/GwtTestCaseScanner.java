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

package eu.ydp.empiria.player;

import com.google.gwt.junit.client.GWTTestCase;
import org.reflections.scanners.AbstractScanner;

import java.lang.reflect.Modifier;

public class GwtTestCaseScanner extends AbstractScanner {

    @Override
    public void scan(Object classMetadata) {
        String className = getMetadataAdapter().getClassName(classMetadata);
        String superclassName = getMetadataAdapter().getSuperclassName(classMetadata);

        try {
            Class<?> clazz = loadClass(className);
            if (isGwtTestCase(clazz) && !isAbstract(clazz)) {
                if (acceptResult(superclassName)) {
                    getStore().put(superclassName, className);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Class<?> loadClass(String className) throws ClassNotFoundException {
        return this.getClass().getClassLoader().loadClass(className);
    }

    private boolean isAbstract(Class<?> clazz) {
        int modifiers = clazz.getModifiers();
        return Modifier.isAbstract(modifiers);
    }

    private boolean isGwtTestCase(Class<?> clazz) {
        return GWTTestCase.class.isAssignableFrom(clazz);
    }
}
