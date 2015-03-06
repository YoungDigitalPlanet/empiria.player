package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.feedback.ModuleFeedbackProcessor;

public interface FeedbackModuleFactory {
	ModuleFeedbackProcessor getModuleFeedbackProcessor(InlineBodyGeneratorSocket inlineBodyGeneratorSocket);
}
