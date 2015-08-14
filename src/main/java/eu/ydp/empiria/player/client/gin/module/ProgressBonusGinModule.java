package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import eu.ydp.empiria.player.client.module.progressbonus.view.ProgressBonusView;
import eu.ydp.empiria.player.client.module.progressbonus.view.ProgressBonusViewImpl;

public class ProgressBonusGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(ProgressBonusView.class).to(ProgressBonusViewImpl.class);
    }
}
