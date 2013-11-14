package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.internal.TutorApiExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.AudioMuteButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.CheckButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.ResetButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.ShowAnswersButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.DefaultMediaProcessorExtension;
import eu.ydp.empiria.player.client.module.ImageActionProcessor;
import eu.ydp.empiria.player.client.module.InlineContainerModule;
import eu.ydp.empiria.player.client.module.TextActionProcessor;
import eu.ydp.empiria.player.client.module.bonus.BonusModule;
import eu.ydp.empiria.player.client.module.button.download.ButtonModule;
import eu.ydp.empiria.player.client.module.choice.ChoiceModule;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillInteractionModule;
import eu.ydp.empiria.player.client.module.connection.ConnectionModule;
import eu.ydp.empiria.player.client.module.containers.DivModule;
import eu.ydp.empiria.player.client.module.containers.SubHtmlContainerModule;
import eu.ydp.empiria.player.client.module.containers.SupHtmlContainerModule;
import eu.ydp.empiria.player.client.module.containers.TextInteractionModule;
import eu.ydp.empiria.player.client.module.containers.group.GroupModule;
import eu.ydp.empiria.player.client.module.draggap.DragGapModule;
import eu.ydp.empiria.player.client.module.drawing.DrawingModule;
import eu.ydp.empiria.player.client.module.flash.FlashModule;
import eu.ydp.empiria.player.client.module.identification.IdentificationModule;
import eu.ydp.empiria.player.client.module.img.ImgModule;
import eu.ydp.empiria.player.client.module.inlinechoice.InlineChoiceModule;
import eu.ydp.empiria.player.client.module.inlinechoice.math.InlineChoiceMathGapModule;
import eu.ydp.empiria.player.client.module.labelling.LabellingModule;
import eu.ydp.empiria.player.client.module.math.MathModule;
import eu.ydp.empiria.player.client.module.mathtext.MathTextModule;
import eu.ydp.empiria.player.client.module.object.ObjectModule;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModule;
import eu.ydp.empiria.player.client.module.pageinpage.PageInPageModule;
import eu.ydp.empiria.player.client.module.progressbonus.ProgressBonusModule;
import eu.ydp.empiria.player.client.module.prompt.PromptModule;
import eu.ydp.empiria.player.client.module.selection.SelectionModule;
import eu.ydp.empiria.player.client.module.shape.ShapeModule;
import eu.ydp.empiria.player.client.module.simpletext.SimpleTextModule;
import eu.ydp.empiria.player.client.module.simulation.SimulationModule;
import eu.ydp.empiria.player.client.module.slideshow.SlideshowPlayerModule;
import eu.ydp.empiria.player.client.module.sourcelist.SourceListModule;
import eu.ydp.empiria.player.client.module.span.SpanModule;
import eu.ydp.empiria.player.client.module.table.TableModule;
import eu.ydp.empiria.player.client.module.textentry.TextEntryGapModule;
import eu.ydp.empiria.player.client.module.textentry.math.TextEntryMathGapModule;
import eu.ydp.empiria.player.client.module.tutor.TutorModule;
import eu.ydp.empiria.player.client.module.video.VideoModule;

@SuppressWarnings({ "PMD" })
public class ModuleProviderFactory {
	@Inject
	private Provider<ConnectionModule> connectionModule;
	@Inject
	private Provider<SourceListModule> sourceListModule;
	@Inject
	private Provider<ObjectModule> objectModule;
	@Inject
	private Provider<PageInPageModule> pageInPageModule;
	@Inject
	private Provider<TextActionProcessor> textActionProcessor;
	@Inject
	private Provider<ImageActionProcessor> imageActionProcessor;
	@Inject
	private Provider<ImgModule> imgModule;
	@Inject
	private Provider<SelectionModule> selectionModule;
	@Inject
	private Provider<InlineContainerModule> inlineContainerModule;
	@Inject
	private Provider<DefaultMediaProcessorExtension> mediaProcessor;
	@Inject
	private Provider<MathModule> mathModule;
	@Inject
	private Provider<CheckButtonModuleConnectorExtension> checkButtonModuleConnectorExtension;
	@Inject
	private Provider<ShowAnswersButtonModuleConnectorExtension> showAnswersButtonModuleConnectorExtension;
	@Inject
	private Provider<AudioMuteButtonModuleConnectorExtension> audioMuteButtonModuleConnectorExtension;
	@Inject
	private Provider<ResetButtonModuleConnectorExtension> resetButtonModuleConnectorExtension;
	@Inject
	private Provider<InlineChoiceModule> inlineChoiceModule;
	@Inject
	private Provider<IdentificationModule> identificationModule;
	@Inject
	private Provider<SimulationModule> simulationModule;
	@Inject
	private Provider<SlideshowPlayerModule> slideshowPlayerModule;
	@Inject
	private Provider<LabellingModule> labellingModule;
	@Inject
	private Provider<OrderInteractionModule> orderInteractionModule;
	@Inject
	private Provider<ChoiceModule> choiceModule;
	@Inject
	private Provider<InlineChoiceMathGapModule> inlineChoiceMathGapModule;
	@Inject
	private Provider<TextEntryGapModule> textEntryGapModule;
	@Inject
	private Provider<TextEntryMathGapModule> textEntryMathGapModule;
	@Inject
	private Provider<DragGapModule> dragGapModule;
	@Inject
	private Provider<DivModule> divModule;
	@Inject
	private Provider<GroupModule> groupModule;
	@Inject
	private Provider<SpanModule> spanModule;
	@Inject
	private Provider<TextInteractionModule> textInteractionModule;
	@Inject
	private Provider<ColorfillInteractionModule> colorfillInteractionModule;
	@Inject
	private Provider<SimpleTextModule> simpleTextModule;
	@Inject
	private Provider<MathTextModule> mathTextModule;
	@Inject
	private Provider<FlashModule> flashModule;
	@Inject
	private Provider<PromptModule> promptModule;
	@Inject
	private Provider<TableModule> tableModule;
	@Inject
	private Provider<ShapeModule> shapeModule;
	@Inject
	private Provider<SupHtmlContainerModule> supHtmlContainerModule;
	@Inject
	private Provider<SubHtmlContainerModule> subHtmlContainerModule;
	@Inject
	private Provider<TutorModule> tutor;
	@Inject
	private Provider<ButtonModule> buttonModule;
	@Inject
	private Provider<TutorApiExtension> tutorApiExtension;
	@Inject
	private Provider<DrawingModule> drawingModule;
	@Inject
	private Provider<BonusModule> bonusModule;
	@Inject
	private Provider<ProgressBonusModule> progressBonusModule;
	@Inject
	private Provider<VideoModule> videoModule;

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

	public Provider<SimulationModule> getSimulationModule() {
		return simulationModule;
	}

	public Provider<SlideshowPlayerModule> getSlideshowPlayerModule() {
		return slideshowPlayerModule;
	}

	public Provider<LabellingModule> getLabellingModule() {
		return labellingModule;
	}

	public Provider<OrderInteractionModule> getOrderInteractionModule() {
		return orderInteractionModule;
	}

	public Provider<ChoiceModule> getChoiceModule() {
		return choiceModule;
	}

	public Provider<InlineChoiceMathGapModule> getInlineChoiceMathGapModule() {
		return inlineChoiceMathGapModule;
	}

	public Provider<TextEntryGapModule> getTextEntryGapModule() {
		return textEntryGapModule;
	}

	public Provider<TextEntryMathGapModule> getTextEntryMathGapModule() {
		return textEntryMathGapModule;
	}

	public Provider<DragGapModule> getDragGapModule() {
		return dragGapModule;
	}

	public Provider<ColorfillInteractionModule> getColorfillInteractionModule() {
		return colorfillInteractionModule;
	}

	public Provider<DivModule> getDivModule() {
		return divModule;
	}

	public Provider<GroupModule> getGroupModule() {
		return groupModule;
	}

	public Provider<SpanModule> getSpanModule() {
		return spanModule;
	}

	public Provider<TextInteractionModule> getTextInteractionModule() {
		return textInteractionModule;
	}

	public Provider<SimpleTextModule> getSimpleTextModule() {
		return simpleTextModule;
	}

	public Provider<MathTextModule> getMathTextModule() {
		return mathTextModule;
	}

	public Provider<FlashModule> getFlashModule() {
		return flashModule;
	}

	public Provider<PromptModule> getPromptModule() {
		return promptModule;
	}

	public Provider<TableModule> getTableModule() {
		return tableModule;
	}

	public Provider<ShapeModule> getShapeModule() {
		return shapeModule;
	}

	public Provider<SupHtmlContainerModule> getSupHtmlContainerModule() {
		return supHtmlContainerModule;
	}

	public Provider<SubHtmlContainerModule> getSubHtmlContainerModule() {
		return subHtmlContainerModule;
	}

	public Provider<TutorModule> getTutorModule() {
		return tutor;
	}

	public Provider<ButtonModule> getButtonModule() {
		return buttonModule;
	}

	public Provider<TutorApiExtension> getTutorApiExtension() {
		return tutorApiExtension;
	}

	public Provider<DrawingModule> getDrawingModule() {
		return drawingModule;
	}

	public Provider<BonusModule> getBonusModule() {
		return bonusModule;
	}

	public Provider<ProgressBonusModule> getProgressBonusModule() {
		return progressBonusModule;
	}

	public Provider<VideoModule> getVideoModule() {
		return videoModule;
	}
}
