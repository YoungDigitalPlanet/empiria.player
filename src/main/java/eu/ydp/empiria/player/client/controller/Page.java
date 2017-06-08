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

package eu.ydp.empiria.player.client.controller;

import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;

@Singleton
public class Page extends InternalExtension implements FlowDataSocketUserExtension {

    private static FlowDataSupplier supplier;
    protected boolean isInitialized = false;

    public int getCurrentPageNumber() {
        return supplier == null ? 0 : supplier.getCurrentPageIndex();
    }

    public int getPageCount() {
        return supplier == null ? 0 : supplier.getPageCount();
    }

    @Override
    public void setFlowDataSupplier(FlowDataSupplier supplier) {
        Page.supplier = supplier;
        if (supplier != null) {
            isInitialized = true;
        }
    }

    public boolean isNotLastPage(int pageIndex) {
        return pageIndex < getPageCount() - 1;
    }

    @Override
    public void init() {// NOPMD

    }
}
