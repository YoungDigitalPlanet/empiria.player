package eu.ydp.empiria.player.client.controller.flow.execution;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.FlowCommandsListener;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.IFlowCommand;

public class MainFlowCommandsExecutor implements FlowCommandsExecutor {

    protected FlowCommandsListener flowCommandsListener;

    @Inject
    public MainFlowCommandsExecutor(FlowCommandsListener fcl) {
        flowCommandsListener = fcl;
    }

    @Override
    public void executeCommand(IFlowCommand command) {
        if (command != null)
            command.execute(flowCommandsListener);
    }
}
