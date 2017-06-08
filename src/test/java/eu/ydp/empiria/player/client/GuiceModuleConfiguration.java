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

import com.google.common.collect.Lists;
import com.google.gwt.thirdparty.guava.common.collect.Sets;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class GuiceModuleConfiguration {
    private final List<Class<?>> classToOmmit = Lists.newArrayList();
    private final List<Class<?>> classToMock = Lists.newArrayList();
    private final List<Class<?>> classToSpy = Lists.newArrayList();
    private final Set<Class<?>> classWithDisabledPostConstruct = Sets.newHashSet();

    public void addClassToOmmit(Class<?> clazz) {
        classToOmmit.add(clazz);
    }

    public void addClassToMock(Class<?> clazz) {
        classToMock.add(clazz);
    }

    public void addClassToSpy(Class<?> clazz) {
        classToSpy.add(clazz);
    }

    public void addAllClassToOmit(Class<?>... clazz) {
        classToOmmit.addAll(Arrays.asList(clazz));
    }

    public void addAllClassToMock(Class<?>... clazz) {
        classToMock.addAll(Arrays.asList(clazz));
    }

    public void addAllClassToSpy(Class<?>... clazz) {
        classToSpy.addAll(Arrays.asList(clazz));
    }

    public void addClassWithDisabledPostConstruct(Class<?> clazz) {
        classWithDisabledPostConstruct.add(clazz);
    }

    public List<Class<?>> getClassToMock() {
        return classToMock;
    }

    public List<Class<?>> getClassToOmmit() {
        return classToOmmit;
    }

    public List<Class<?>> getClassToSpy() {
        return classToSpy;
    }

    public Set<Class<?>> getClassWithDisabledPostConstruct() {
        return classWithDisabledPostConstruct;
    }

}
