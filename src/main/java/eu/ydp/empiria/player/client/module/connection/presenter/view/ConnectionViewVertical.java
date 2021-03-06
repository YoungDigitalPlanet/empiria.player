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

package eu.ydp.empiria.player.client.module.connection.presenter.view;

import com.google.common.collect.ObjectArrays;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveEndEvent;
import eu.ydp.empiria.player.client.module.connection.view.event.ConnectionMoveStartEvent;
import eu.ydp.empiria.player.client.util.events.internal.EventType;
import eu.ydp.empiria.player.client.util.events.internal.emulate.TouchEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.TouchTypes;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.HasTouchHandlers;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.TouchHandler;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

import javax.annotation.PostConstruct;
import java.util.Map;

public class ConnectionViewVertical extends AbstractConnectionView {

    private static ConnectionViewUiBinder uiBinder = GWT.create(ConnectionViewUiBinder.class);

    interface ConnectionViewUiBinder extends UiBinder<Widget, ConnectionViewVertical> {
    }

    @UiField
    protected FlowPanel leftColumn;

    @UiField
    protected FlowPanel centerColumn;

    @UiField
    protected FlowPanel rightColumn;

    @UiField
    protected FlowPanel view;

    Map<String, String> errorStyles = null, correctStyles = null;

    protected HasTouchHandlers touchRecognition;

    @PostConstruct
    public void createAndBindUi() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    private void initTouchRecognition() {
        if (touchRecognition == null) {
            touchRecognition = touchRecognitionFactory.getTouchRecognition(this, !UserAgentChecker.isMobileUserAgent(), true);
            touchRecognition.addTouchHandlers(this, getTouchTypes());
        }
    }

    private EventType<TouchHandler, TouchTypes>[] getTouchTypes() {
        EventType<TouchHandler, TouchTypes>[] types = TouchEvent.getTypes(TouchTypes.TOUCH_START, TouchTypes.TOUCH_END, TouchTypes.TOUCH_CANCEL);
        if (isDrawFollowTouch()) {
            types = ObjectArrays.concat(types, TouchEvent.getType(TouchTypes.TOUCH_MOVE));
        }
        return types;
    }

    @Override
    public void addFirstColumnItem(ConnectionItem item) {
        leftColumn.add(item);
    }

    @Override
    public void addSecondColumnItem(ConnectionItem item) {
        rightColumn.add(item);
    }

    @Override
    public void addElementToMainView(IsWidget widget) {
        view.insert(widget, 0);
    }

    @Override
    public void onTouchStart(NativeEvent event) {
        ConnectionMoveStartEvent connectionEvent = new ConnectionMoveStartEvent(getPositionX(event), getPositionY(event), event);
        callOnMoveStartHandlers(connectionEvent);

    }

    @Override
    public void onTouchEnd(NativeEvent event) {
        ConnectionMoveEndEvent connectionMoveEndEvent = new ConnectionMoveEndEvent(getPositionX(event), getPositionY(event), event);
        callOnMoveEndHandlers(connectionMoveEndEvent);
    }

    @Override
    public void onTouchCancel(NativeEvent event) {
        callOnMoveCancelHandlers();
    }

    @Override
    protected FlowPanel getView() {
        return view;
    }

    @Override
    public Widget asWidget() {
        initTouchRecognition();
        return this;
    }
}
