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

/**
 * Obiekt opisujacy dostepne opcje mediow.
 */
public interface MediaAvailableOptions {
    /**
     * Czy mozemy wywolac akce play na kontrolerze
     *
     * @return true jezeli tak
     */
    boolean isPlaySupported();

    /**
     * Czy mozemy wykonac akcje pause na kontrelerze
     *
     * @return true jezeli tak
     */
    boolean isPauseSupported();

    /**
     * Czy mozemy wykonac akcje mute na kontrelerze
     *
     * @return true jezeli tak
     */
    boolean isMuteSupported();

    /**
     * Czy mozemy zmieniac natezenie dzwieku
     *
     * @return true jezeli tak
     */
    boolean isVolumeChangeSupported();

    /**
     * Czy mozemy wykonac akcje stop na kontrelerze
     *
     * @return true jezeli tak
     */
    boolean isStopSupported();

    /**
     * Czy mozemy dowlonie przemieszczac pozycje w strumieniu
     *
     * @return true jezeli tak
     */
    boolean isSeekSupported();

    /**
     * Czy mozemy wykonac akcje peny ekran na kontrolerze
     *
     * @return true jezeli tak
     */
    boolean isFullScreenSupported();

    /**
     * zwraca true jezeli mamy mozliwosc odczytania danych meta z zasobu takich jak dlugosc, pozycja w strumieniu itd..
     *
     * @return
     */
    boolean isMediaMetaAvailable();

    /**
     * Czy z danym odtwarzaczem mozliwe jest wykorzystanie szablonow
     *
     * @return
     */
    boolean isTemplateSupported();
}
