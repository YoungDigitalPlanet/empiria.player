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

package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.wrapper;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.executor.ExternalMediaExecutor;

public class ExternalMediaProxy implements MediaProxy {

    private ExternalMediaWrapper externalMediaWrapper;
    private ExternalMediaExecutor externalMediaExecutor;

    @Inject
    public ExternalMediaProxy(ExternalMediaWrapper externalMediaWrapper, ExternalMediaExecutor externalMediaExecutor) {
        this.externalMediaWrapper = externalMediaWrapper;
        this.externalMediaExecutor = externalMediaExecutor;
        this.externalMediaExecutor.setMediaWrapper(this.externalMediaWrapper);
    }

    @Override
    public ExternalMediaWrapper getMediaWrapper() {
        return externalMediaWrapper;
    }

    @Override
    public ExternalMediaExecutor getMediaExecutor() {
        return externalMediaExecutor;
    }

    public void setDuration(double durationSeconds) {
        externalMediaWrapper.setDuration(durationSeconds);
    }

    public void setCurrentTime(double currentTimeSeconds) {
        externalMediaWrapper.setCurrentTime(currentTimeSeconds);
    }

}
