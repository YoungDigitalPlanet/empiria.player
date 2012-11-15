package eu.ydp.empiria.player.client.controller;

import java.util.Map;

import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.body.ModuleHandlerManager;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionEventListner;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsSocket;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackManager;
import eu.ydp.empiria.player.client.controller.flow.IFlowSocket;
import eu.ydp.empiria.player.client.controller.session.sockets.ItemSessionSocket;
import eu.ydp.empiria.player.client.controller.session.sockets.PageSessionSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.view.item.ItemViewSocket;
import eu.ydp.empiria.player.client.view.page.PageViewSocket;

public interface AssessmentControllerFactory {
	
	PageController getPageController(PageViewSocket pvs, IFlowSocket fs, 
										InteractionEventsSocket is, PageSessionSocket pss, 
										ModulesRegistrySocket mrs, ModuleHandlerManager moduleHandlerManager);

	ItemController getItemController(ItemViewSocket ivs, IFlowSocket fs, 
										InteractionEventsSocket is, ItemSessionSocket iss, 
										ModulesRegistrySocket mrs, ModuleHandlerManager moduleHandlerManager);

	Item getItem(XmlData data, DisplayContentOptions options, 
					InteractionEventsListener interactionEventsListener, StyleSocket ss, 
					ModulesRegistrySocket mrs, Map<String, Outcome> outcomeVariables, 
					ModuleHandlerManager moduleHandlerManager);

	FeedbackManager getFeedbackManager(NodeList feedbackNodes, String baseUrl, 
										ModuleSocket moduleSocket, FeedbackInteractionEventListner feedbackListener);
	
}
