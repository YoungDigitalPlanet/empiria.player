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

package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowCommandsSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.execution.FlowCommandsExecutor;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.FlowCommand;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.IFlowCommand;

public class JsFlowCommandSocketUserExtension extends AbstractJsExtension implements FlowCommandsSocketUserExtension {

    protected FlowCommandsExecutor flowCommandsExecutor;
    protected JavaScriptObject flowCommandsSocketJs;

    @Override
    public ExtensionType getType() {
        return ExtensionType.EXTENSION_SOCKET_USER_FLOW_COMMAND;
    }

    @Override
    public void init() {
        flowCommandsSocketJs = createFlowCommandsSocketJs();
        setFlowCommandsSocketJs(extensionJsObject, flowCommandsSocketJs);
    }

    @Override
    public void setFlowCommandsExecutor(FlowCommandsExecutor fce) {
        flowCommandsExecutor = fce;
    }

    private void executeFlowCommand(JavaScriptObject commandJsObject) {
        IFlowCommand command = FlowCommand.fromJsObject(commandJsObject);
        flowCommandsExecutor.executeCommand(command);
    }

    private native JavaScriptObject createFlowCommandsSocketJs()/*-{
        var instance = this;
        var socket = [];
        socket.executeFlowCommand = function (command) {
            instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsFlowCommandSocketUserExtension::executeFlowCommand(Lcom/google/gwt/core/client/JavaScriptObject;)(command);
        }
        return socket;
    }-*/;

    private native void setFlowCommandsSocketJs(JavaScriptObject extension, JavaScriptObject socket)/*-{
        if (typeof extension.setFlowCommandsSocket == 'function') {
            extension.setFlowCommandsSocket(socket);
        }
    }-*/;

}
