package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.xml.client.Element;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.PersonaService;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.binding.CachedModuleScoped;
import eu.ydp.empiria.player.client.gin.module.tutor.TutorId;
import eu.ydp.empiria.player.client.gin.module.tutor.TutorIdProvider;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.*;
import eu.ydp.empiria.player.client.module.bonus.BonusProvider;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleModel;
import eu.ydp.empiria.player.client.module.choice.presenter.ChoiceModulePresenter;
import eu.ydp.empiria.player.client.module.choice.providers.MultiChoiceStyleProvider;
import eu.ydp.empiria.player.client.module.choice.view.ChoiceModuleView;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillInteractionModuleModel;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillModelProxy;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ColorfillInteractionPresenter;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ColorfillInteractionViewColors;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ResponseAnswerByViewBuilder;
import eu.ydp.empiria.player.client.module.colorfill.presenter.UserToResponseAreaMapper;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillBeanProxy;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionStructure;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;
import eu.ydp.empiria.player.client.module.dictionary.DictionaryPresenter;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupView;
import eu.ydp.empiria.player.client.module.draggap.DragGapModuleModel;
import eu.ydp.empiria.player.client.module.draggap.SourceListManagerAdapter;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapView;
import eu.ydp.empiria.player.client.module.drawing.DrawingView;
import eu.ydp.empiria.player.client.module.drawing.model.DrawingBean;
import eu.ydp.empiria.player.client.module.drawing.model.DrawingModelProvider;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxButtonCreator;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxPresenter;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxView;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasPresenter;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasView;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasViewImpl;
import eu.ydp.empiria.player.client.module.drawing.view.DrawCanvas;
import eu.ydp.empiria.player.client.module.external.common.ExternalPaths;
import eu.ydp.empiria.player.client.module.external.common.sound.ExternalSoundInstanceCreator;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateSaver;
import eu.ydp.empiria.player.client.module.external.interaction.ExternalInteractionResponseModel;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.interaction.structure.ExternalInteractionModuleStructure;
import eu.ydp.empiria.player.client.module.math.MathGapModel;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.drag.DragController;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.presenter.ItemsResponseOrderController;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenter;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;
import eu.ydp.empiria.player.client.module.progressbonus.presenter.ProgressBonusPresenter;
import eu.ydp.empiria.player.client.module.progressbonus.view.ProgressBonusView;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.SelectionViewBuilder;
import eu.ydp.empiria.player.client.module.selection.model.GroupAnswersControllerModel;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlidePresenter;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlideshowButtonsPresenter;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlideshowPlayerPresenter;
import eu.ydp.empiria.player.client.module.slideshow.slides.SlidesSwitcher;
import eu.ydp.empiria.player.client.module.slideshow.slides.SlideshowController;
import eu.ydp.empiria.player.client.module.slideshow.sound.SlideshowSoundController;
import eu.ydp.empiria.player.client.module.slideshow.view.buttons.SlideshowButtonsView;
import eu.ydp.empiria.player.client.module.slideshow.view.player.SlideshowPlayerView;
import eu.ydp.empiria.player.client.module.slideshow.view.slide.SlideView;
import eu.ydp.empiria.player.client.module.speechscore.presenter.SpeechScorePresenter;
import eu.ydp.empiria.player.client.module.speechscore.view.SpeechScoreLinkView;
import eu.ydp.empiria.player.client.module.test.reset.TestResetButtonPresenter;
import eu.ydp.empiria.player.client.module.test.reset.view.TestResetButtonView;
import eu.ydp.empiria.player.client.module.texteditor.presenter.TextEditorPresenter;
import eu.ydp.empiria.player.client.module.texteditor.structure.TextEditorBean;
import eu.ydp.empiria.player.client.module.texteditor.view.TextEditorView;
import eu.ydp.empiria.player.client.module.tutor.ActionEventGenerator;
import eu.ydp.empiria.player.client.module.tutor.ActionExecutorService;
import eu.ydp.empiria.player.client.module.tutor.CommandFactory;
import eu.ydp.empiria.player.client.module.tutor.TutorEndHandler;
import eu.ydp.empiria.player.client.module.tutor.actions.*;
import eu.ydp.empiria.player.client.module.tutor.presenter.TutorPresenter;
import eu.ydp.empiria.player.client.module.tutor.presenter.TutorPresenterImpl;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.empiria.player.client.module.tutor.view.TutorViewImpl;
import eu.ydp.empiria.player.client.module.video.presenter.VideoPlayerBuilder;
import eu.ydp.empiria.player.client.module.video.presenter.VideoPlayerReattacher;
import eu.ydp.empiria.player.client.module.video.presenter.VideoPresenter;
import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.structure.VideoBeanProvider;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.empiria.player.client.style.ModuleStyle;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopeStack;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class ModuleScopedModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(ModuleScopeStack.class).in(Singleton.class);

        bind(Element.class).annotatedWith(ModuleScoped.class).toProvider(XmlElementModuleScopedProvider.class);
        bind(Response.class).annotatedWith(ModuleScoped.class).toProvider(ResponseModuleScopedProvider.class);
        bindCssStyle();
        bindOrdering();
        bindColorfill();
        bindDragGap();
        bindChoice();
        bindTutor();
        bindMathGap();
        bindSelection();
        bindDrawing();
        bindBonus();
        bindProgressBonus();
        bindVideoModule();
        bindDictionary();
        bindTextEditor();
        bindTestResetButton();
        bindSpeechScore();
        bindSlideshow();
        bindExternal();
        bindExternalInteraction();
    }

    private void bindSlideshow() {
        bindModuleScoped(SlideshowButtonsPresenter.class, new TypeLiteral<ModuleScopedProvider<SlideshowButtonsPresenter>>() {
        });
        bindModuleScoped(SlideshowPlayerPresenter.class, new TypeLiteral<ModuleScopedProvider<SlideshowPlayerPresenter>>() {
        });
        bindModuleScoped(SlideshowPlayerView.class, new TypeLiteral<ModuleScopedProvider<SlideshowPlayerView>>() {
        });
        bindModuleScoped(SlideshowButtonsView.class, new TypeLiteral<ModuleScopedProvider<SlideshowButtonsView>>() {
        });
        bindModuleScoped(SlideshowController.class, new TypeLiteral<ModuleScopedProvider<SlideshowController>>() {
        });
        bindModuleScoped(SlidesSwitcher.class, new TypeLiteral<ModuleScopedProvider<SlidesSwitcher>>() {
        });
        bindModuleScoped(SlidePresenter.class, new TypeLiteral<ModuleScopedProvider<SlidePresenter>>() {
        });
        bindModuleScoped(SlideView.class, new TypeLiteral<ModuleScopedProvider<SlideView>>() {
        });
        bindModuleScoped(SlideshowSoundController.class, new TypeLiteral<ModuleScopedProvider<SlideshowSoundController>>() {
        });
    }

    private void bindSpeechScore() {
        bindModuleScoped(SpeechScorePresenter.class, new TypeLiteral<ModuleScopedProvider<SpeechScorePresenter>>() {
        });
        bindModuleScoped(SpeechScoreLinkView.class, new TypeLiteral<ModuleScopedProvider<SpeechScoreLinkView>>() {
        });
    }

    private void bindTextEditor() {
        bindModuleScoped(TextEditorBean.class, new TypeLiteral<ModuleScopedProvider<TextEditorBean>>() {
        });
        bindModuleScoped(TextEditorPresenter.class, new TypeLiteral<ModuleScopedProvider<TextEditorPresenter>>() {
        });
        bindModuleScoped(TextEditorView.class, new TypeLiteral<ModuleScopedProvider<TextEditorView>>() {
        });
    }

    private void bindDictionary() {
        bindModuleScoped(DictionaryPresenter.class, new TypeLiteral<ModuleScopedProvider<DictionaryPresenter>>() {
        });
        bindModuleScoped(DictionaryButtonView.class, new TypeLiteral<ModuleScopedProvider<DictionaryButtonView>>() {
        });
        bindModuleScoped(DictionaryPopupView.class, new TypeLiteral<ModuleScopedProvider<DictionaryPopupView>>() {
        });
    }

    private void bindVideoModule() {
        bindModuleScoped(VideoBean.class, new TypeLiteral<ModuleScopedProvider<VideoBean>>() {
        });
        bind(VideoBean.class).toProvider(VideoBeanProvider.class);
        bindModuleScoped(VideoPresenter.class, new TypeLiteral<ModuleScopedProvider<VideoPresenter>>() {
        });
        bindModuleScoped(VideoView.class, new TypeLiteral<ModuleScopedProvider<VideoView>>() {
        });
        bindModuleScoped(VideoPlayerBuilder.class, new TypeLiteral<ModuleScopedProvider<VideoPlayerBuilder>>() {
        });
        bindModuleScoped(VideoPlayerReattacher.class, new TypeLiteral<ModuleScopedProvider<VideoPlayerReattacher>>() {
        });
    }

    private void bindDrawing() {
        bindModuleScoped(DrawingBean.class, new TypeLiteral<ModuleScopedProvider<DrawingBean>>() {
        });
        bind(DrawingBean.class).toProvider(DrawingModelProvider.class);
        bindModuleScoped(DrawingView.class, new TypeLiteral<ModuleScopedProvider<DrawingView>>() {
        });
        bindModuleScoped(CanvasView.class, new TypeLiteral<ModuleScopedProvider<CanvasViewImpl>>() {
        });
        bindModuleScoped(DrawCanvas.class, new TypeLiteral<ModuleScopedProvider<CanvasViewImpl>>() {
        });
        bindModuleScoped(CanvasViewImpl.class, new TypeLiteral<ModuleScopedProvider<CanvasViewImpl>>() {
        });
        bindModuleScoped(ToolboxPresenter.class, new TypeLiteral<ModuleScopedProvider<ToolboxPresenter>>() {
        });
        bindModuleScoped(ToolboxView.class, new TypeLiteral<ModuleScopedProvider<ToolboxView>>() {
        });
        bindModuleScoped(ToolboxButtonCreator.class, new TypeLiteral<ModuleScopedProvider<ToolboxButtonCreator>>() {
        });
        bindModuleScoped(CanvasPresenter.class, new TypeLiteral<ModuleScopedProvider<CanvasPresenter>>() {
        });
    }

    private void bindCssStyle() {
        bind(CssStylesModuleScopedProvider.class).in(Singleton.class);
        bind(WithCacheCssStylesModuleScopedProvider.class).in(Singleton.class);

        bind(ModuleStyle.class).annotatedWith(ModuleScoped.class).toProvider(CssStylesModuleScopedProvider.class);
        bind(ModuleStyle.class).annotatedWith(CachedModuleScoped.class).toProvider(WithCacheCssStylesModuleScopedProvider.class);
    }

    private void bindOrdering() {
        bindModuleScoped(OrderingItemsDao.class, new TypeLiteral<ModuleScopedProvider<OrderingItemsDao>>() {
        });
        bindModuleScoped(OrderInteractionModuleModel.class, new TypeLiteral<ModuleScopedProvider<OrderInteractionModuleModel>>() {
        });
        bindModuleScoped(OrderInteractionView.class, new TypeLiteral<ModuleScopedProvider<OrderInteractionView>>() {
        });
        bindModuleScoped(DragController.class, new TypeLiteral<ModuleScopedProvider<DragController>>() {
        });
        bindModuleScoped(ItemsResponseOrderController.class, new TypeLiteral<ModuleScopedProvider<ItemsResponseOrderController>>() {
        });
        bindModuleScoped(OrderInteractionPresenter.class, new TypeLiteral<ModuleScopedProvider<OrderInteractionPresenter>>() {
        });
    }

    private void bindColorfill() {
        bindModuleScoped(ColorfillInteractionView.class, new TypeLiteral<ModuleScopedProvider<ColorfillInteractionView>>() {
        });
        bindModuleScoped(ColorfillInteractionModuleModel.class, new TypeLiteral<ModuleScopedProvider<ColorfillInteractionModuleModel>>() {
        });
        bindModuleScoped(ColorfillInteractionPresenter.class, new TypeLiteral<ModuleScopedProvider<ColorfillInteractionPresenter>>() {
        });
        bindModuleScoped(ColorfillModelProxy.class, new TypeLiteral<ModuleScopedProvider<ColorfillModelProxy>>() {
        });
        bindModuleScoped(ColorfillInteractionViewColors.class, new TypeLiteral<ModuleScopedProvider<ColorfillInteractionViewColors>>() {
        });
        bindModuleScoped(UserToResponseAreaMapper.class, new TypeLiteral<ModuleScopedProvider<UserToResponseAreaMapper>>() {
        });
        bindModuleScoped(ColorfillBeanProxy.class, new TypeLiteral<ModuleScopedProvider<ColorfillBeanProxy>>() {
        });
        bindModuleScoped(ColorfillInteractionStructure.class, new TypeLiteral<ModuleScopedProvider<ColorfillInteractionStructure>>() {
        });
        bindModuleScoped(ResponseAnswerByViewBuilder.class, new TypeLiteral<ModuleScopedProvider<ResponseAnswerByViewBuilder>>() {
        });
    }

    private void bindDragGap() {
        bindModuleScoped(DragGapModuleModel.class, new TypeLiteral<ModuleScopedProvider<DragGapModuleModel>>() {
        });
        bindModuleScoped(DragGapView.class, new TypeLiteral<ModuleScopedProvider<DragGapView>>() {
        });
        bindModuleScoped(SourceListManagerAdapter.class, new TypeLiteral<ModuleScopedProvider<SourceListManagerAdapter>>() {
        });
    }

    private void bindChoice() {
        bindModuleScoped(ChoiceModuleView.class, new TypeLiteral<ModuleScopedProvider<ChoiceModuleView>>() {
        });
        bindModuleScoped(ChoiceModulePresenter.class, new TypeLiteral<ModuleScopedProvider<ChoiceModulePresenter>>() {
        });
        bindModuleScoped(ChoiceModuleModel.class, new TypeLiteral<ModuleScopedProvider<ChoiceModuleModel>>() {
        });
        bindModuleScoped(MultiChoiceStyleProvider.class, new TypeLiteral<ModuleScopedProvider<MultiChoiceStyleProvider>>() {
        });
    }

    private void bindTutor() {
        bindModuleScoped(ActionEventGenerator.class, new TypeLiteral<ModuleScopedProvider<ActionEventGenerator>>() {
        });
        bindModuleScoped(TutorPresenter.class, new TypeLiteral<ModuleScopedProvider<TutorPresenterImpl>>() {
        });
        bindModuleScoped(TutorView.class, new TypeLiteral<ModuleScopedProvider<TutorViewImpl>>() {
        });
        bindModuleScoped(ActionExecutorService.class, new TypeLiteral<ModuleScopedProvider<ActionExecutorService>>() {
        });
        bindModuleScoped(CommandFactory.class, new TypeLiteral<ModuleScopedProvider<CommandFactory>>() {
        });
        bindModuleScoped(OutcomeDrivenActionTypeGenerator.class, new TypeLiteral<ModuleScopedProvider<OutcomeDrivenActionTypeGenerator>>() {
        });
        bindModuleScoped(OnPageAllOkAction.class, new TypeLiteral<ModuleScopedProvider<OnPageAllOkAction>>() {
        });
        bind(String.class).annotatedWith(TutorId.class).toProvider(TutorIdProvider.class);
        bindModuleScoped(GroupAnswersControllerModel.class, new TypeLiteral<ModuleScopedProvider<GroupAnswersControllerModel>>() {
        });
        bindModuleScoped(OnOkAction.class, new TypeLiteral<ModuleScopedProvider<OnOkAction>>() {
        });
        bindModuleScoped(OnWrongAction.class, new TypeLiteral<ModuleScopedProvider<OnWrongAction>>() {
        });
        bindModuleScoped(OutcomeDrivenActionTypeProvider.class, new TypeLiteral<ModuleScopedProvider<OutcomeDrivenActionTypeProvider>>() {
        });
        bind(TutorConfig.class).annotatedWith(ModuleScoped.class).toProvider(TutorConfigModuleScopedProvider.class);
        bind(PersonaService.class).annotatedWith(ModuleScoped.class).toProvider(PersonaServiceModuleScopedProvider.class);
        bindModuleScoped(TutorEndHandler.class, new TypeLiteral<ModuleScopedProvider<TutorEndHandler>>() {
        });
    }

    private void bindSelection() {
        bindModuleScoped(SelectionModuleModel.class, new TypeLiteral<ModuleScopedProvider<SelectionModuleModel>>() {
        });
        bindModuleScoped(SelectionModuleView.class, new TypeLiteral<ModuleScopedProvider<SelectionModuleView>>() {
        });
        bindModuleScoped(SelectionViewBuilder.class, new TypeLiteral<ModuleScopedProvider<SelectionViewBuilder>>() {
        });
    }

    private void bindMathGap() {
        bindModuleScoped(MathGapModel.class, new TypeLiteral<ModuleScopedProvider<MathGapModel>>() {
        });
    }

    private void bindBonus() {
        bindModuleScoped(BonusProvider.class, new TypeLiteral<ModuleScopedProvider<BonusProvider>>() {
        });
        bind(BonusConfig.class).annotatedWith(ModuleScoped.class).toProvider(BonusConfigModuleScopeProvider.class);
    }

    private void bindProgressBonus() {
        bindModuleScoped(ProgressBonusPresenter.class, new TypeLiteral<ModuleScopedProvider<ProgressBonusPresenter>>() {
        });
        bindModuleScoped(ProgressBonusView.class, new TypeLiteral<ModuleScopedProvider<ProgressBonusView>>() {
        });
        bind(ProgressBonusConfig.class).annotatedWith(ModuleScoped.class).toProvider(ProgressBonusConfigModuleScopeProvider.class);
    }

    private void bindTestResetButton() {
        bindModuleScoped(TestResetButtonPresenter.class, new TypeLiteral<ModuleScopedProvider<TestResetButtonPresenter>>() {
        });
        bindModuleScoped(TestResetButtonView.class, new TypeLiteral<ModuleScopedProvider<TestResetButtonView>>() {
        });
    }

    private void bindExternal() {
        bindModuleScoped(ExternalPaths.class, new TypeLiteral<ModuleScopedProvider<ExternalPaths>>() {
        });
        bindModuleScoped(ExternalSoundInstanceCreator.class, new TypeLiteral<ModuleScopedProvider<ExternalSoundInstanceCreator>>() {
        });
        bindModuleScoped(ExternalInteractionEmpiriaApi.class, new TypeLiteral<ModuleScopedProvider<ExternalInteractionEmpiriaApi>>() {
        });
        bindModuleScoped(ExternalStateSaver.class, new TypeLiteral<ModuleScopedProvider<ExternalStateSaver>>() {
        });
    }

    private void bindExternalInteraction() {
        bindModuleScoped(ExternalInteractionResponseModel.class, new TypeLiteral<ModuleScopedProvider<ExternalInteractionResponseModel>>() {
        });
        bindModuleScoped(ExternalInteractionModuleStructure.class, new TypeLiteral<ModuleScopedProvider<ExternalInteractionModuleStructure>>() {
        });
    }

    private <F, T extends F> void bindModuleScoped(Class<F> clazz, TypeLiteral<? extends Provider<T>> typeLiteral) {
        bind(typeLiteral).in(Singleton.class);
        bind(clazz).annotatedWith(ModuleScoped.class).toProvider(Key.get(typeLiteral));
    }

}
