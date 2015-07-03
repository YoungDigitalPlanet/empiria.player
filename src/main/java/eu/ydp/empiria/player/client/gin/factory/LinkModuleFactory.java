package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.module.link.LinkModule;

public interface LinkModuleFactory {
    LinkModule getLinkModule(FlowRequestInvoker requestInvoker);
}
