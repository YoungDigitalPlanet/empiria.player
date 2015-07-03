package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusService;
import eu.ydp.empiria.player.client.module.progressbonus.ProgressCalculator;
import eu.ydp.empiria.player.client.module.progressbonus.view.ProgressBonusView;
import eu.ydp.empiria.player.client.module.progressbonus.view.ProgressBonusViewImpl;

public class ProgressBonusGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(ProgressBonusView.class).to(ProgressBonusViewImpl.class);
        bind(ProgressCalculator.class).in(Singleton.class);
        bind(ProgressBonusService.class).in(Singleton.class);
    }
}
