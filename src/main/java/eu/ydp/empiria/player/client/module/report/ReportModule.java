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

package eu.ydp.empiria.player.client.module.report;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.report.table.ReportTable;
import eu.ydp.empiria.player.client.controller.report.table.ReportTableGenerator;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.gin.factory.ReportModuleFactory;
import eu.ydp.empiria.player.client.module.core.base.ContainerModuleBase;
import eu.ydp.empiria.player.client.module.core.flow.OnModuleShowHandler;
import eu.ydp.empiria.player.client.module.report.view.ReportView;

public class ReportModule extends ContainerModuleBase implements OnModuleShowHandler {

    private final ReportView view;
    private final PlayerWorkModeService playerWorkModeService;
    private final ReportModuleFactory reportModuleFactory;

    @Inject
    public ReportModule(ReportView view, PlayerWorkModeService playerWorkModeService,
                        ReportModuleFactory reportModuleFactory) {
        this.playerWorkModeService = playerWorkModeService;
        this.reportModuleFactory = reportModuleFactory;
        this.view = view;
    }

    @Override
    public void initModule(Element element) {
        ReportTableGenerator reportTableGenerator = reportModuleFactory.createReportTableGenerator(getBodyGenerator());
        ReportTable table = reportTableGenerator.generate(element);

        view.setTable(table.getFlexTable());
    }

    @Override
    public Widget getView() {
        return view.asWidget();
    }

    @Override
    public void onShow() {
        playerWorkModeService.tryToUpdateWorkMode(PlayerWorkMode.TEST_SUBMITTED);
    }
}
