package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;

import eu.ydp.empiria.player.client.module.test.reset.view.TestResetButtonView;
import eu.ydp.empiria.player.client.module.test.reset.view.TestResetButtonViewImpl;

public class TestResetGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(TestResetButtonView.class).to(TestResetButtonViewImpl.class);
	}

}
