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

import java.lang.annotation.Annotation;

public class BindDescriptor<T> {

    public enum BindType {
        SIMPLE, MOCK, SPY
    }

    private Class<? extends Annotation> in;
    private Class<? extends T> to;
    private Class<T> bind;

    public BindDescriptor() {

    }

    public Class<T> getBind() {
        return bind;
    }

    public Class<? extends Annotation> getIn() {
        return in;
    }

    public Class<? extends T> getTo() {
        return to;
    }

    public BindDescriptor<T> bind(Class<T> bind) {
        this.bind = bind;
        return this;
    }

    public BindDescriptor<T> to(Class<? extends T> to) {
        this.to = to;
        return this;
    }

    public BindDescriptor<T> in(Class<? extends Annotation> in) {
        this.in = in;
        return this;
    }

    public boolean isAllSet() {
        return in != null && to != null && bind != null;
    }
}
