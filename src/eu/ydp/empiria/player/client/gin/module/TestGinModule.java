package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;

import eu.ydp.empiria.player.client.module.test.reset.view.TestResetButtonView;
import eu.ydp.empiria.player.client.module.test.reset.view.TestResetButtonViewImpl;
import eu.ydp.empiria.player.client.module.test.submit.view.TestPageSubmitButtonView;
import eu.ydp.empiria.player.client.module.test.submit.view.TestPageSubmitButtonViewImpl;

public class TestGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(TestPageSubmitButtonView.class).to(TestPageSubmitButtonViewImpl.class);
		bind(TestResetButtonView.class).to(TestResetButtonViewImpl.class);
	}
}
