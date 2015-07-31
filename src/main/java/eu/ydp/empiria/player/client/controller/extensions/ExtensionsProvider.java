package eu.ydp.empiria.player.client.controller.extensions;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.controller.extensions.internal.SoundProcessorManagerExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.SimpleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension;
import eu.ydp.empiria.player.client.controller.multiview.MultiPageController;
import eu.ydp.empiria.player.client.gin.factory.ModuleConnectorExtensionProvider;
import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.ModuleProviderFactory;
import eu.ydp.empiria.player.client.gin.factory.SingleModuleInstanceProvider;
import eu.ydp.empiria.player.client.module.ModuleTagName;

import javax.inject.Provider;
import java.util.List;

public class ExtensionsProvider {

    private ModuleProviderFactory moduleProviderFactory;
    private ModuleFactory moduleFactory;
    private SoundProcessorManagerExtension soundProcessorManagerExtension;
    private SingleModuleInstanceProvider singleModuleInstanceProvider;
    private ModuleConnectorExtensionProvider moduleConnectorExtensionProvider;

    private final Provider<MultiPageController> multiPageProvider;
    private final Provider<Page> pageProvider;
    private final Provider<BookmarkProcessorExtension> bookmarkProcessorExtensionProvider;
    private final Provider<StickiesProcessorExtension> stickiesProcessorExtensionProvider;

    private List<? extends Extension> extensions;

    @Inject
    public ExtensionsProvider(ModuleProviderFactory moduleProviderFactory, ModuleFactory moduleFactory,
                              SoundProcessorManagerExtension soundProcessorManagerExtension, SingleModuleInstanceProvider singleModuleInstanceProvider,
                              ModuleConnectorExtensionProvider moduleConnectorExtensionProvider, Provider<MultiPageController> multiPageProvider,
                              Provider<Page> pageProvider, Provider<BookmarkProcessorExtension> bookmarkProcessorExtensionProvider,
                              Provider<StickiesProcessorExtension> stickiesProcessorExtensionProvider) {
        this.moduleProviderFactory = moduleProviderFactory;
        this.moduleFactory = moduleFactory;
        this.soundProcessorManagerExtension = soundProcessorManagerExtension;
        this.singleModuleInstanceProvider = singleModuleInstanceProvider;
        this.moduleConnectorExtensionProvider = moduleConnectorExtensionProvider;
        this.multiPageProvider = multiPageProvider;
        this.pageProvider = pageProvider;
        this.bookmarkProcessorExtensionProvider = bookmarkProcessorExtensionProvider;
        this.stickiesProcessorExtensionProvider = stickiesProcessorExtensionProvider;

        initExtensions();
    }

    private void initExtensions() {

        extensions = Lists.newArrayList(
                moduleFactory.getPlayerCoreApiExtension(),
                moduleFactory.getScormSupportExtension(),
                moduleFactory.getAssessmentJsonReportExtension(),
                soundProcessorManagerExtension,
                new SimpleConnectorExtension(moduleProviderFactory.getAccordionModule(), ModuleTagName.ACCORDION),
                new SimpleConnectorExtension(moduleProviderFactory.getDivModule(), ModuleTagName.DIV),
                new SimpleConnectorExtension(moduleProviderFactory.getGroupModule(), ModuleTagName.GROUP),
                new SimpleConnectorExtension(moduleProviderFactory.getSpanModule(), ModuleTagName.SPAN),
                new SimpleConnectorExtension(moduleProviderFactory.getTextInteractionModule(), ModuleTagName.TEXT_INTERACTION),
                new SimpleConnectorExtension(moduleProviderFactory.getImgModule(), ModuleTagName.IMG, false, true),
                new SimpleConnectorExtension(moduleProviderFactory.getChoiceModule(), ModuleTagName.CHOICE_INTERACTION, true),
                new SimpleConnectorExtension(moduleProviderFactory.getSelectionModule(), ModuleTagName.SELECTION_INTERACTION, true),
                new SimpleConnectorExtension(moduleProviderFactory.getIdentificationModule(), ModuleTagName.IDENTYFICATION_INTERACTION, true),
                new SimpleConnectorExtension(moduleProviderFactory.getTextEntryGapModule(), ModuleTagName.TEXT_ENTRY_INTERACTION, true),
                new SimpleConnectorExtension(moduleProviderFactory.getTextEntryMathGapModule(), ModuleTagName.MATH_GAP_TEXT_ENTRY_TYPE, true),
                new SimpleConnectorExtension(moduleProviderFactory.getDragGapModule(), ModuleTagName.DRAG_GAP, true),
                new SimpleConnectorExtension(moduleProviderFactory.getInlineChoiceMathGapModule(), ModuleTagName.MATH_GAP_INLINE_CHOICE_TYPE, true),
                new SimpleConnectorExtension(moduleProviderFactory.getInlineChoiceModule(), ModuleTagName.INLINE_CHOICE_INTERACTION, true),
                new SimpleConnectorExtension(moduleProviderFactory.getSimpleTextModule(), ModuleTagName.SIMPLE_TEXT),
                new SimpleConnectorExtension(moduleProviderFactory.getObjectModule(), ModuleTagName.AUDIO_PLAYER, false, true),
                new SimpleConnectorExtension(moduleProviderFactory.getInlineContainerModule(), ModuleTagName.INLINE_CONTAINER_STYLE_STRONG, false, true),
                new SimpleConnectorExtension(moduleProviderFactory.getMathTextModule(), ModuleTagName.MATH_TEXT, false, true),
                new SimpleConnectorExtension(moduleProviderFactory.getInlineMathJaxModule(), ModuleTagName.INLINE_MATH_JAX, false, true),
                new SimpleConnectorExtension(moduleProviderFactory.getMathModule(), ModuleTagName.MATH_INTERACTION),
                new SimpleConnectorExtension(moduleProviderFactory.getInteractionMathJaxModule(), ModuleTagName.INTERACTION_MATH_JAX),
                new SimpleConnectorExtension(moduleProviderFactory.getObjectModule(), ModuleTagName.OBJECT, false, true),
                new SimpleConnectorExtension(moduleProviderFactory.getSlideshowPlayerModule(), ModuleTagName.SLIDESHOW_PLAYER),
                new SimpleConnectorExtension(moduleProviderFactory.getFlashModule(), ModuleTagName.FLASH),
                new SimpleConnectorExtension(moduleProviderFactory.getSimulationModule(), ModuleTagName.SIMULATION_PLAYER),
                new SimpleConnectorExtension(moduleProviderFactory.getSourceListModule(), ModuleTagName.SOURCE_LIST),
                new SimpleConnectorExtension(moduleProviderFactory.getLabellingModule(), ModuleTagName.LABELLING_INTERACTION),
                new SimpleConnectorExtension(moduleProviderFactory.getOrderInteractionModule(), ModuleTagName.ORDER_INTERACTION, true),
                new SimpleConnectorExtension(moduleProviderFactory.getColorfillInteractionModule(), ModuleTagName.COLORFILL_INTERACTION, true),
                new SimpleConnectorExtension(moduleProviderFactory.getTutorModule(), ModuleTagName.TUTOR, false),
                new SimpleConnectorExtension(moduleProviderFactory.getButtonModule(), ModuleTagName.BUTTON, false),
                new SimpleConnectorExtension(moduleProviderFactory.getBonusModule(), ModuleTagName.BONUS, false),
                new SimpleConnectorExtension(moduleProviderFactory.getProgressBonusModule(), ModuleTagName.PROGRESS_BONUS, false),
                new SimpleConnectorExtension(moduleProviderFactory.getVideoModule(), ModuleTagName.VIDEO, false),
                new SimpleConnectorExtension(moduleProviderFactory.getDictionaryModule(), ModuleTagName.DICTIONARY, false),
                new SimpleConnectorExtension(moduleProviderFactory.getTextEditorModule(), ModuleTagName.OPEN_QUESTION, false),
                new SimpleConnectorExtension(moduleProviderFactory.getTestPageSubmitButtonModule(), ModuleTagName.TEST_PAGE_SUBMIT, false),
                new SimpleConnectorExtension(moduleProviderFactory.getSpeechScoreModule(), ModuleTagName.SPEECH_SCORE, false),
                singleModuleInstanceProvider.getInfoModuleConnectorExtension(),
                moduleConnectorExtensionProvider.getReportModuleConnectorExtension(),
                singleModuleInstanceProvider.getLinkModuleConnectorExtension(),
                new SimpleConnectorExtension(moduleProviderFactory.getPromptModule(), ModuleTagName.PROMPT),
                new SimpleConnectorExtension(moduleProviderFactory.getTableModule(), ModuleTagName.TABLE),
                moduleConnectorExtensionProvider.getNextPageButtonModuleConnectorExtension(),
                moduleConnectorExtensionProvider.getPrevPageButtonModuleConnectorExtension(),
                moduleConnectorExtensionProvider.getPageSwitchModuleConnectorExtension(),
                new SimpleConnectorExtension(moduleProviderFactory.getPageInPageModule(), ModuleTagName.PAGE_IN_PAGE),
                moduleProviderFactory.getCheckButtonModuleConnectorExtension().get(),
                moduleProviderFactory.getShowAnswersButtonModuleConnectorExtension().get(),
                moduleProviderFactory.getResetButtonModuleConnectorExtension().get(),
                new SimpleConnectorExtension(moduleProviderFactory.getShapeModule(), ModuleTagName.SHAPE),
                moduleProviderFactory.getAudioMuteButtonModuleConnectorExtension().get(),
                new SimpleConnectorExtension(moduleProviderFactory.getSubHtmlContainerModule(), ModuleTagName.SUB),
                new SimpleConnectorExtension(moduleProviderFactory.getSupHtmlContainerModule(), ModuleTagName.SUP),
                new SimpleConnectorExtension(moduleProviderFactory.getConnectionModule(), ModuleTagName.MATCH_INTERACTION, true),
                new SimpleConnectorExtension(moduleProviderFactory.getTextActionProcessor(), ModuleTagName.TEXT_FEEDBACK),
                new SimpleConnectorExtension(moduleProviderFactory.getImageActionProcessor(), ModuleTagName.IMAGE_FEEDBACK),
                new SimpleConnectorExtension(moduleProviderFactory.getDrawingModule(), ModuleTagName.DRAWING),
                new SimpleConnectorExtension(moduleProviderFactory.getTestResetButtonModule(), ModuleTagName.TEST_RESET, false),
                new SimpleConnectorExtension(moduleProviderFactory.getExternalInteractionModule(), ModuleTagName.EXTERNAL_INTERACTION, true),
                new SimpleConnectorExtension(moduleProviderFactory.getExternalPresentationModule(), ModuleTagName.EXTERNAL_PRESENTATION),
                moduleProviderFactory.getMediaProcessor().get(),
                multiPageProvider.get(),
                pageProvider.get(),
                bookmarkProcessorExtensionProvider.get(),
                stickiesProcessorExtensionProvider.get(),
                moduleProviderFactory.getTutorApiExtension().get()
        );
    }

    public List<? extends Extension> get() {
        return extensions;
    }
}
