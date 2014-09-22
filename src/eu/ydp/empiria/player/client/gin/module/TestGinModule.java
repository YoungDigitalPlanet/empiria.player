package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;

import eu.ydp.empiria.player.client.module.test.reset.view.TestResetButtonView;
import eu.ydp.empiria.player.client.module.test.reset.view.TestResetButtonViewImpl;
import eu.ydp.empiria.player.client.module.test.submit.view.TestPageSubmitView;
import eu.ydp.empiria.player.client.module.test.submit.view.TestPageSubmitViewImpl;

public class TestGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(TestPageSubmitView.class).to(TestPageSubmitViewImpl.class);
		bind(TestResetButtonView.class).to(TestResetButtonViewImpl.class);
	}
}
