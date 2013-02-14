package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.extensions.internal.PlayerCoreApiExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.ScormSupportExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.AssessmentJsonReportExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension;
import eu.ydp.empiria.player.client.module.choice.ChoiceModule;
import eu.ydp.empiria.player.client.module.connection.ConnectionModule;
import eu.ydp.empiria.player.client.module.dragdrop.DragDropManager;
import eu.ydp.empiria.player.client.module.math.InlineChoiceGapModule;
import eu.ydp.empiria.player.client.module.math.TextEntryGapModule;
import eu.ydp.empiria.player.client.module.object.ObjectModule;
import eu.ydp.empiria.player.client.module.sourcelist.SourceListModule;
import eu.ydp.empiria.player.client.module.textentry.TextEntryModule;

public interface ModuleFactory {
	ConnectionModule getConnectionModule();
	ChoiceModule getChoiceModule();
	ObjectModule getObjectModule();
	SourceListModule getSourceListModule();
	StickiesProcessorExtension getStickiesProcessorExtension();
	DragDropManager getDragDropManager();
	TextEntryGapModule getEntryGapModule();
	TextEntryModule getTextEntryModule();
	InlineChoiceGapModule getInlineChoiceGapModule();
	AssessmentJsonReportExtension getAssessmentJsonReportExtension();
	ScormSupportExtension getScormSupportExtension();
	PlayerCoreApiExtension getPlayerCoreApiExtension();
}
