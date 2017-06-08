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

package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.common.collect.Lists;

import java.util.List;

public class WordsResult {

    private final List<String> list;
    private final Integer index;

    public WordsResult() {
        this.list = Lists.newArrayList();
        this.index = -1;
    }

    public WordsResult(List<String> list, Integer index) {
        this.list = list;
        this.index = index;
    }

    public List<String> getList() {
        return list;
    }

    public Integer getIndex() {
        return index;
    }

}
