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

package eu.ydp.empiria.player.client.module.menu;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.report.table.ReportTable;
import eu.ydp.empiria.player.client.controller.report.table.ReportTableGenerator;
import eu.ydp.empiria.player.client.gin.factory.ReportModuleFactory;
import eu.ydp.empiria.player.client.module.core.base.ContainerModuleBase;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;

import static eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes.BEFORE_FLOW;
import static eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes.PAGE_LOADED;

public class MenuModule extends ContainerModuleBase implements PlayerEventHandler {

    private final MenuPresenter presenter;
    private FlowDataSupplier flowDataSupplier;
    private final ReportModuleFactory reportModuleFactory;

    @Inject
    public MenuModule(MenuPresenter presenter, ReportModuleFactory reportModuleFactory, EventsBus eventsBus, FlowDataSupplier flowDataSupplier) {
        this.reportModuleFactory = reportModuleFactory;
        this.presenter = presenter;
        this.flowDataSupplier = flowDataSupplier;
        eventsBus.addHandler(PlayerEvent.getTypes(BEFORE_FLOW, PAGE_LOADED), this);
    }

    @Override
    public void initModule(Element element) {
        ReportTableGenerator reportTableGenerator = reportModuleFactory.createReportTableGenerator(getBodyGenerator());
        ReportTable table = reportTableGenerator.generate(element);
        presenter.setReportTable(table);
    }

    @Override
    public Widget getView() {
        return presenter.getView();
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        int page = flowDataSupplier.getCurrentPageIndex();
        switch (event.getType()) {
            case BEFORE_FLOW:
                presenter.hide();
                presenter.unmarkPage(page);
                break;
            case PAGE_LOADED:
                presenter.markPage(page);
        }
    }
}
