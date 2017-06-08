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

package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.common.base.Objects;

public class ConnectionPairEntry<S, T> {
    S source;
    T target;

    public ConnectionPairEntry(S source, T target) {
        super();
        this.source = source;
        this.target = target;
    }

    public ConnectionPairEntry() {
    }

    public S getSource() {
        return source;
    }

    public T getTarget() {
        return target;
    }

    public void setSource(S source) {
        this.source = source;
    }

    public void setTarget(T target) {
        this.target = target;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (obj instanceof ConnectionPairEntry) {
            equals = Objects.equal(target, ((ConnectionPairEntry<?, ?>) obj).getTarget())
                    || Objects.equal(source, ((ConnectionPairEntry<?, ?>) obj).getTarget());
            if (equals) {
                equals = Objects.equal(target, ((ConnectionPairEntry<?, ?>) obj).getSource())
                        || Objects.equal(source, ((ConnectionPairEntry<?, ?>) obj).getSource());
            }
        }
        return equals;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(source) + Objects.hashCode(target);
    }
}
