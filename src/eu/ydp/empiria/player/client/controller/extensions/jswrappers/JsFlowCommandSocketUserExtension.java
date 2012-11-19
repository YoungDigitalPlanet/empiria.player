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
	
	public void setFlowCommandsExecutor(FlowCommandsExecutor fce){
		flowCommandsExecutor = fce;
	}
	
	private void executeFlowCommand(JavaScriptObject commandJsObject){
		IFlowCommand command = FlowCommand.fromJsObject(commandJsObject);
		flowCommandsExecutor.executeCommand(command);
	}
	
	private native JavaScriptObject createFlowCommandsSocketJs()/*-{
		var instance = this;
		var socket = [];
		socket.executeFlowCommand = function(command){
			instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsFlowCommandSocketUserExtension::executeFlowCommand(Lcom/google/gwt/core/client/JavaScriptObject;)(command);
		}
		return socket;
	}-*/;
	
	private native void setFlowCommandsSocketJs(JavaScriptObject extension, JavaScriptObject socket)/*-{
		if (typeof extension.setFlowCommandsSocket == 'function'){
			extension.setFlowCommandsSocket(socket);
		}
	}-*/;

}
