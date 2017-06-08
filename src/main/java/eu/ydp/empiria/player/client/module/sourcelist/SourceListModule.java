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

package eu.ydp.empiria.player.client.module.sourcelist;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.dragdrop.Sourcelist;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListModuleStructure;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

import java.util.List;

public class SourceListModule extends SimpleModuleBase implements Sourcelist {

    @Inject
    private SourceListModuleStructure moduleStructure;
    @Inject
    private SourceListPresenter presenter;
    @Inject
    private IJSONService ijsonService;
    @Inject
    @PageScoped
    private SourcelistManager sourcelistManager;
    private String sourcelistId;

    @Override
    public Widget getView() {
        return presenter.asWidget();
    }

    @Override
    protected void initModule(Element element) {
        moduleStructure.createFromXml(element.toString(), ijsonService.createArray());
        SourceListBean bean = moduleStructure.getBean();
        sourcelistId = bean.getSourcelistId();
        presenter.setBean(bean);
        presenter.setModuleId(sourcelistId);
        presenter.createAndBindUi(getModuleSocket().getInlineBodyGeneratorSocket());
        sourcelistManager.registerSourcelist(this);
    }

    @Override
    public SourcelistItemValue getItemValue(String itemId) {
        return presenter.getItemValue(itemId);
    }

    @Override
    public void useItem(String itemId) {
        presenter.useItem(itemId);

    }

    @Override
    public void restockItem(String itemId) {
        presenter.restockItem(itemId);

    }

    @Override
    public void useAndRestockItems(List<String> itemsIds) {
        presenter.useAndRestockItems(itemsIds);

    }

    @Override
    public String getIdentifier() {
        return sourcelistId;
    }

    @Override
    public void lockSourceList() {
        presenter.lockSourceList();

    }

    @Override
    public void unlockSourceList() {
        presenter.unlockSourceList();

    }

    @Override
    public HasDimensions getItemSize() {
        return presenter.getMaxItemSize();
    }

}
