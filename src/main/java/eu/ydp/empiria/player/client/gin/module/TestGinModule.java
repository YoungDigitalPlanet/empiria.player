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

package eu.ydp.empiria.player.client.gin.module;

import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.module.test.reset.TestResetButtonPresenter;
import eu.ydp.empiria.player.client.module.test.reset.view.TestResetButtonView;
import eu.ydp.empiria.player.client.module.test.reset.view.TestResetButtonViewImpl;
import eu.ydp.empiria.player.client.module.test.submit.view.TestPageSubmitButtonView;
import eu.ydp.empiria.player.client.module.test.submit.view.TestPageSubmitButtonViewImpl;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class TestGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bind(TestPageSubmitButtonView.class).to(TestPageSubmitButtonViewImpl.class);
        bind(TestResetButtonView.class).to(TestResetButtonViewImpl.class);

        bindModuleScoped(TestResetButtonPresenter.class, new TypeLiteral<ModuleScopedProvider<TestResetButtonPresenter>>() {
        });
        bindModuleScoped(TestResetButtonView.class, new TypeLiteral<ModuleScopedProvider<TestResetButtonView>>() {
        });
    }
}
