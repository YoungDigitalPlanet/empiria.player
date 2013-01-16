package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.internal.modules.AudioMuteButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.CheckButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.ResetButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.ShowAnswersButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.DefaultMediaProcessorExtension;
import eu.ydp.empiria.player.client.module.ImageActionProcessor;
import eu.ydp.empiria.player.client.module.InlineContainerModule;
import eu.ydp.empiria.player.client.module.TextActionProcessor;
import eu.ydp.empiria.player.client.module.connection.ConnectionModule;
import eu.ydp.empiria.player.client.module.identification.IdentificationModule;
import eu.ydp.empiria.player.client.module.img.ImgModule;
import eu.ydp.empiria.player.client.module.inlinechoice.InlineChoiceModule;
import eu.ydp.empiria.player.client.module.math.MathModule;
import eu.ydp.empiria.player.client.module.object.ObjectModule;
import eu.ydp.empiria.player.client.module.pageinpage.PageInPageModule;
import eu.ydp.empiria.player.client.module.selection.SelectionModule;
import eu.ydp.empiria.player.client.module.sourcelist.SourceListModule;

public class ModuleProviderFactory {
	@Inject
	protected Provider<ConnectionModule> connectionModule;

	@Inject
	protected Provider<SourceListModule> sourceListModule;

	@Inject
	protected Provider<ObjectModule> objectModule;

	@Inject
	protected Provider<PageInPageModule> pageInPageModule;

	@Inject
	protected Provider<TextActionProcessor> textActionProcessor;

	@Inject
	protected Provider<ImageActionProcessor> imageActionProcessor;

	@Inject
	protected Provider<ImgModule> imgModule;

	@Inject
	protected Provider<SelectionModule> selectionModule;

	@Inject
	private Provider<InlineContainerModule> inlineContainerModule;

	@Inject
	protected Provider<DefaultMediaProcessorExtension> mediaProcessor;

	@Inject
	protected Provider<MathModule> mathModule;

	@Inject
	protected Provider<CheckButtonModuleConnectorExtension> checkButtonModuleConnectorExtension;

	@Inject
	protected Provider<ShowAnswersButtonModuleConnectorExtension> showAnswersButtonModuleConnectorExtension;

	@Inject
	protected Provider<AudioMuteButtonModuleConnectorExtension> audioMuteButtonModuleConnectorExtension;

	@Inject
	protected Provider<ResetButtonModuleConnectorExtension> resetButtonModuleConnectorExtension;

	@Inject
	protected Provider<InlineChoiceModule> inlineChoiceModule;

	@Inject
	protected Provider<IdentificationModule> identificationModule;

	public Provider<ConnectionModule> getConnectionModule() {
		return connectionModule;
	}

	public Provider<SourceListModule> getSourceListModule() {
		return sourceListModule;
	}

	public Provider<ObjectModule> getObjectModule() {
		return objectModule;
	}

	public Provider<PageInPageModule> getPageInPageModule() {
		return pageInPageModule;
	}

	public Provider<TextActionProcessor> getTextActionProcessor() {
		return textActionProcessor;
	}

	public Provider<ImageActionProcessor> getImageActionProcessor() {
		return imageActionProcessor;
	}

	public Provider<ImgModule> getImgModule() {
		return imgModule;
	}
	
	public Provider<InlineContainerModule> getInlineContainerModule() {
		return inlineContainerModule;
	}	

	public Provider<SelectionModule> getSelectionModule() {
		return selectionModule;
	}

	public Provider<DefaultMediaProcessorExtension> getMediaProcessor() {
		return mediaProcessor;
	}

	public Provider<MathModule> getMathModule() {
		return mathModule;
	}

	public Provider<InlineChoiceModule> getInlineChoiceModule() {
		return inlineChoiceModule;
	}

	public Provider<CheckButtonModuleConnectorExtension> getCheckButtonModuleConnectorExtension() {
		return checkButtonModuleConnectorExtension;
	}

	public Provider<ShowAnswersButtonModuleConnectorExtension> getShowAnswersButtonModuleConnectorExtension() {
		return showAnswersButtonModuleConnectorExtension;
	}

	public Provider<AudioMuteButtonModuleConnectorExtension> getAudioMuteButtonModuleConnectorExtension() {
		return audioMuteButtonModuleConnectorExtension;
	}

	public Provider<ResetButtonModuleConnectorExtension> getResetButtonModuleConnectorExtension() {
		return resetButtonModuleConnectorExtension;
	}

	public Provider<IdentificationModule> getIdentificationModule() {
		return identificationModule;
	}

}
