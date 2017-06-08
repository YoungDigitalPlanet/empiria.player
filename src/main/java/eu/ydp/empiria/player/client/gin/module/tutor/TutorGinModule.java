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

package eu.ydp.empiria.player.client.gin.module.tutor;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.PersonaService;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.gin.factory.TutorCommandsModuleFactory;
import eu.ydp.empiria.player.client.gin.module.BaseGinModule;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.PersonaServiceModuleScopedProvider;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.TutorConfigModuleScopedProvider;
import eu.ydp.empiria.player.client.module.selection.model.GroupAnswersControllerModel;
import eu.ydp.empiria.player.client.module.tutor.*;
import eu.ydp.empiria.player.client.module.tutor.actions.*;
import eu.ydp.empiria.player.client.module.tutor.actions.popup.*;
import eu.ydp.empiria.player.client.module.tutor.commands.AnimationCommand;
import eu.ydp.empiria.player.client.module.tutor.commands.ShowImageCommand;
import eu.ydp.empiria.player.client.module.tutor.presenter.TutorPresenter;
import eu.ydp.empiria.player.client.module.tutor.presenter.TutorPresenterImpl;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.empiria.player.client.module.tutor.view.TutorViewImpl;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class TutorGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bind(TutorView.class).to(TutorViewImpl.class);
        bind(TutorPresenter.class).to(TutorPresenterImpl.class);
        bind(ActionExecutorService.class).to(ActionExecutorServiceImpl.class);
        bind(TutorPopupPresenter.class).to(TutorPopupPresenterImpl.class);
        bind(TutorPopupView.class).to(TutorPopupViewImpl.class);
        bind(TutorPopupViewWidget.class).to(TutorPopupViewWidgetImpl.class);
        bind(TutorPopupViewPersonaView.class).to(TutorPopupViewPersonaViewImpl.class);
        bind(TutorEndHandler.class).to(SingleRunTutorEndHandler.class);
        install(new GinFactoryModuleBuilder().implement(TutorCommand.class, Names.named("image"), ShowImageCommand.class)
                .implement(TutorCommand.class, Names.named("animation"), AnimationCommand.class).build(TutorCommandsModuleFactory.class));

        bindModuleScoped(ActionEventGenerator.class, new TypeLiteral<ModuleScopedProvider<ActionEventGenerator>>() {
        });
        bindModuleScoped(TutorPresenter.class, new TypeLiteral<ModuleScopedProvider<TutorPresenterImpl>>() {
        });
        bindModuleScoped(TutorView.class, new TypeLiteral<ModuleScopedProvider<TutorViewImpl>>() {
        });
        bindModuleScoped(ActionExecutorService.class, new TypeLiteral<ModuleScopedProvider<ActionExecutorService>>() {
        });
        bindModuleScoped(CommandFactory.class, new TypeLiteral<ModuleScopedProvider<CommandFactory>>() {
        });
        bindModuleScoped(OutcomeDrivenActionTypeGenerator.class, new TypeLiteral<ModuleScopedProvider<OutcomeDrivenActionTypeGenerator>>() {
        });
        bindModuleScoped(OnPageAllOkAction.class, new TypeLiteral<ModuleScopedProvider<OnPageAllOkAction>>() {
        });
        bind(String.class).annotatedWith(TutorId.class).toProvider(TutorIdProvider.class);
        bindModuleScoped(GroupAnswersControllerModel.class, new TypeLiteral<ModuleScopedProvider<GroupAnswersControllerModel>>() {
        });
        bindModuleScoped(OnOkAction.class, new TypeLiteral<ModuleScopedProvider<OnOkAction>>() {
        });
        bindModuleScoped(OnWrongAction.class, new TypeLiteral<ModuleScopedProvider<OnWrongAction>>() {
        });
        bindModuleScoped(OutcomeDrivenActionTypeProvider.class, new TypeLiteral<ModuleScopedProvider<OutcomeDrivenActionTypeProvider>>() {
        });
        bind(TutorConfig.class).annotatedWith(ModuleScoped.class).toProvider(TutorConfigModuleScopedProvider.class);
        bind(PersonaService.class).annotatedWith(ModuleScoped.class).toProvider(PersonaServiceModuleScopedProvider.class);
        bindModuleScoped(TutorEndHandler.class, new TypeLiteral<ModuleScopedProvider<TutorEndHandler>>() {
        });
    }
}
