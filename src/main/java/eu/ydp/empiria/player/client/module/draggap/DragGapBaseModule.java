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

package eu.ydp.empiria.player.client.module.draggap;

import com.google.gwt.json.client.JSONArray;
import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;
import eu.ydp.empiria.player.client.module.core.base.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.dragdrop.ResizableSourcelistClient;
import eu.ydp.empiria.player.client.module.draggap.dragging.DragDropController;
import eu.ydp.empiria.player.client.module.draggap.presenter.DragGapPresenter;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapBaseBean;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapBaseStructure;
import eu.ydp.gwtutil.client.StringUtils;
import eu.ydp.gwtutil.client.Wrapper;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

import java.util.List;

public abstract class DragGapBaseModule<T extends DragGapBaseBean, U extends JAXBParserFactory<T>> extends AbstractInteractionModule<DragGapModuleModel, T> implements ResizableSourcelistClient {

    @Inject
    @ModuleScoped
    private DragGapModuleModel dragGapModuleModel;
    @Inject
    @ModuleScoped
    private SourceListManagerAdapter sourceListManagerAdapter;
    @Inject
    private DragDropController dragDropController;
    @Inject
    private DragGapPresenter<T> presenter;
    @Inject
    private DragGapBaseStructure<T, U> structure;

    private final Wrapper<String> itemIdWrapper = Wrapper.of(StringUtils.EMPTY_STRING);

    private HasDimensions size;

    @Override
    protected void initalizeModule() {
        sourceListManagerAdapter.initialize(getIdentifier());
        dragGapModuleModel.initialize(this);
        dragDropController.initializeDrop(getIdentifier());
        dragDropController.initializeDrag(getIdentifier(), itemIdWrapper);
        sourceListManagerAdapter.registerModule(this);
    }

    @Override
    public void reset() {
        super.reset();
        itemIdWrapper.setInstance(StringUtils.EMPTY_STRING);
        sourceListManagerAdapter.onUserValueChanged();
    }

    @Override
    protected DragGapModuleModel getResponseModel() {
        return dragGapModuleModel;
    }

    @Override
    public String getDragItemId() {
        return itemIdWrapper.getInstance();
    }

    @Override
    protected DragGapPresenter<T> getPresenter(){
        return presenter;
    }

    @Override
    protected DragGapBaseStructure<T, U> getStructure(){
        return structure;
    };

    @Override
    public void setDragItem(String itemId) {
        itemIdWrapper.setInstance(itemId);
        getPresenter().setContent(itemId);
    }

    @Override
    public void removeDragItem() {
        getPresenter().removeContent();
        itemIdWrapper.setInstance(StringUtils.EMPTY_STRING);
    }

    @Override
    public void lockDropZone() {
        dragDropController.lockDropZone();
    }

    @Override
    public void unlockDropZone() {
        dragDropController.unlockDropZone();
    }

    @Override
    public void setSize(HasDimensions size) {
        this.size = size;
        getPresenter().setGapDimensions(size);

    }

    @Override
    public HasDimensions getSize() {
        return size;
    }

    @Override
    public void lock(boolean lock) {
        super.lock(lock);
        if (lock) {
            sourceListManagerAdapter.lockGroup();
        } else {
            sourceListManagerAdapter.unlockGroup();
            dragDropController.unlockDropZone();
        }
    }

    @Override
    public String getSourcelistId() {
        T dragGapBean = getStructure().getBean();
        return dragGapBean.getSourcelistId();
    }

    @Override
    public void setState(JSONArray stateAndStructure) {
        super.setState(stateAndStructure);
        DragGapModuleModel responseModel = getResponseModel();
        List<String> currentAnswers = responseModel.getCurrentAnswers();
        if (currentAnswers.size() > 0) {
            String answer = currentAnswers.get(0);
            setDragItem(answer);
        }
    }
}
