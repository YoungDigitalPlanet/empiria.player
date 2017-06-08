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

package eu.ydp.empiria.player.client.module.media;

import com.google.gwt.user.client.ui.Widget;

public interface MediaWrapper<T extends Widget> {
    /**
     * zwraca konfiguracje dostpnych opcji na zasaobie
     *
     * @return
     */
    MediaAvailableOptions getMediaAvailableOptions();

    /**
     * zwraca piowiazany zasob
     *
     * @return
     */
    T getMediaObject();

    /**
     * Zwraca unikatowe id w obrebie calej aplikacji
     *
     * @return
     */
    String getMediaUniqId();

    /**
     * pozycja w strumieniu
     *
     * @return
     */
    double getCurrentTime();

    /**
     * dlugosc utworu jaka mozna odczytac z metadata.
     *
     * @return
     */
    double getDuration();

    /**
     * czy audio jest wyciszone przez metode mute
     *
     * @return
     */
    boolean isMuted();

    /**
     * naezenie dzwieku przedzial 0 do 1
     *
     * @return
     */
    double getVolume();

    /**
     * Czy audio jest gotowe do odtwarzania
     *
     * @return
     */
    boolean canPlay();

}
