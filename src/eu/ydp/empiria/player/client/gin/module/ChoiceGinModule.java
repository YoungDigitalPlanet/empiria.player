package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;

import eu.ydp.empiria.player.client.gin.factory.ChoiceModuleFactory;
import eu.ydp.empiria.player.client.module.choice.presenter.ChoiceModulePresenter;
import eu.ydp.empiria.player.client.module.choice.presenter.ChoiceModulePresenterImpl;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceModuleStructure;
import eu.ydp.empiria.player.client.module.math.InlineChoiceGapModulePresenter;

public class ChoiceGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		install(new GinFactoryModuleBuilder().build(ChoiceModuleFactory.class));

		bind(ChoiceModulePresenter.class).to(ChoiceModulePresenterImpl.class);
		bind(ChoiceModuleStructure.class);
		bind(InlineChoiceGapModulePresenter.class);
	}

}
