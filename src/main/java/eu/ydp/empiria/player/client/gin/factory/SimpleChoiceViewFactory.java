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

package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.name.Named;
import eu.ydp.empiria.player.client.module.choice.presenter.SimpleChoicePresenter;
import eu.ydp.empiria.player.client.module.choice.providers.SimpleChoiceStyleProvider;
import eu.ydp.empiria.player.client.module.choice.view.SimpleChoiceView;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceButtonBase;

public interface SimpleChoiceViewFactory {

    SimpleChoiceView getSimpleChoiceView(SimpleChoicePresenter simpleChoicePresenter, SimpleChoiceStyleProvider styleProvider);

    @Named("multi")
    ChoiceButtonBase getMultiChoiceButton(String styleName);

    @Named("single")
    ChoiceButtonBase getSingleChoiceButton(String styleName);

    @Named("multi")
    SimpleChoiceStyleProvider getMultiChoiceStyleProvider();

    @Named("single")
    SimpleChoiceStyleProvider getSingleChoiceStyleProvider();
}
