package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.external.ExternalStateUtil;
import eu.ydp.empiria.player.client.module.external.view.ExternalInteractionView;
import eu.ydp.empiria.player.client.module.external.view.ExternalInteractionViewImpl;

public class ExternalInteractionGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(ExternalInteractionView.class).to(ExternalInteractionViewImpl.class);
		bind(ExternalStateUtil.class).in(Singleton.class);
	}
}
