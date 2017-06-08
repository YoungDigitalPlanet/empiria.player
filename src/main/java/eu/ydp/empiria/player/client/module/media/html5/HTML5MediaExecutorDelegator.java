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

package eu.ydp.empiria.player.client.module.media.html5;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.AbstractHTML5MediaExecutor;

/**
 * The delegator was applied because of the potential possibility of the change of the value while a program is run. Unfortunately it is necessary to protect
 * against it.
 */
public class HTML5MediaExecutorDelegator {

    private AbstractHTML5MediaExecutor executor;

    public AbstractHTML5MediaExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(AbstractHTML5MediaExecutor executor) {
        this.executor = executor;
    }
}
