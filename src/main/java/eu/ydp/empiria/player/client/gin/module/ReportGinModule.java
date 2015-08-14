package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import eu.ydp.empiria.player.client.gin.factory.ReportModuleFactory;
import eu.ydp.empiria.player.client.module.report.view.ReportView;
import eu.ydp.empiria.player.client.module.report.view.ReportViewImpl;

public class ReportGinModule extends AbstractGinModule{

    @Override
    protected void configure() {
        bind(ReportView.class).to(ReportViewImpl.class);
        install(new GinFactoryModuleBuilder().build(ReportModuleFactory.class));
    }
}
