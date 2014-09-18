package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;

import eu.ydp.empiria.player.client.module.testmode.submit.view.TestPageSubmitView;
import eu.ydp.empiria.player.client.module.testmode.submit.view.TestPageSubmitViewImpl;

public class TestPageGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(TestPageSubmitView.class).to(TestPageSubmitViewImpl.class);
	}
}
