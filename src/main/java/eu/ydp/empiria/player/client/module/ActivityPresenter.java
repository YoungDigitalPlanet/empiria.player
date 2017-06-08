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

package eu.ydp.empiria.player.client.module;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersType;
import eu.ydp.empiria.player.client.module.core.answer.ShowAnswersType;
import eu.ydp.empiria.player.client.structure.ModuleBean;

/**
 * Interfejs prezentera
 *
 * @param <H> typ odpowiedzi
 * @param <T> typ beana przyjmowanego do utworzenia struktury widoku
 */
public interface ActivityPresenter<H extends AbstractResponseModel<?>, T extends ModuleBean> extends IsWidget {

    /**
     * Wiąże widok z prezenterem, w tym momencie prezenter powinien mieć
     * pełną informację o strukturze według, której będzie tworzył widoki
     */
    void bindView();

    /**
     * Czyści wszystkie powiazania w module
     */
    void reset();

    /**
     * Ustawia response model
     *
     * @param model
     */
    void setModel(H model);

    /**
     * @param socket
     */
    void setModuleSocket(ModuleSocket socket);

    /**
     * Ustawia beana reprezentującego strukturę widoku
     *
     * @param bean
     */
    void setBean(T bean);

    /**
     * Blokowanie widoku
     *
     * @param locked
     */
    void setLocked(boolean locked);

    /**
     * Zaznaczanie/odznaczanie poprawnych/błędnych odpowiedzi
     */
    void markAnswers(MarkAnswersType type, MarkAnswersMode mode);

    /**
     * Pokazywanie/ukrywanie poprawnych odpowiedzi
     */
    void showAnswers(ShowAnswersType mode);

}
