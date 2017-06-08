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

package eu.ydp.empiria.player.client.module.simulation.soundjs;

public interface SoundApiForJs {
    void preload(final String src);

    void play(final String src);

    void playLooped(final String src);

    void stop(String src);

    void pause(String src);

    void resume(String src);

    void setCurrentTime(String src, Double time);

    double getCurrentTime(String src);

}
