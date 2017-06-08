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

package eu.ydp.empiria.player.client.module.info;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.List;

public class ContentFieldRegistry {

    @Inject
    private ContentFieldInfoListProvider contentFieldInfoListProvider;
    @Inject
    private ContentFieldInfoSearcher contentFieldInfoSearcher;
    private final List<ContentFieldInfo> fieldInfos = Lists.newArrayList();

    public Optional<ContentFieldInfo> getFieldInfo(final String fieldName) {
        registerIfRequired();
        return contentFieldInfoSearcher.findByTagName(fieldName, fieldInfos);
    }

    private void register() {
        fieldInfos.addAll(contentFieldInfoListProvider.get());
    }

    private boolean isRegistered() {
        return !fieldInfos.isEmpty();
    }

    private void registerIfRequired() {
        if (!isRegistered()) {
            register();
        }
    }

}
