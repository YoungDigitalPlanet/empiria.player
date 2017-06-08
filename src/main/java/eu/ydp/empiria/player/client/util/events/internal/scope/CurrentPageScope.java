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

package eu.ydp.empiria.player.client.util.events.internal.scope;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.Page;

public class CurrentPageScope implements EventScope<CurrentPageScope> {
    private int pageIndex = -1;
    protected Scope scope = Scope.PAGE;

    public CurrentPageScope() {
        pageIndex = PlayerGinjectorFactory.getPlayerGinjector().getPage().getCurrentPageNumber();
    }

    @Inject
    public CurrentPageScope(Page page) {
        pageIndex = page.getCurrentPageNumber();
    }

    public CurrentPageScope(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    @Override
    public Scope getScope() {
        return scope;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CurrentPageScope [pageIndex=");
        builder.append(pageIndex);
        builder.append(", scope=");
        builder.append(getScope());
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int compareTo(CurrentPageScope object) {
        if (pageIndex > object.pageIndex) {
            return 1;
        }
        if (pageIndex < object.pageIndex) {
            return -1;
        }
        return 0;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        return result;
    }

    @SuppressWarnings("PMD")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CurrentPageScope)) {
            return false;
        }
        CurrentPageScope other = (CurrentPageScope) obj;
        if (pageIndex != other.pageIndex) {
            return false;
        }
        if (scope != other.scope) {
            return false;
        }
        return true;
    }

}
