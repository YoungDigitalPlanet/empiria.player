package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.xml.client.Element;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import eu.ydp.empiria.player.client.controller.AssessmentControllerFactory;
import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.body.PlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.communication.ItemData;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventsHub;
import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionEventListener;
import eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkPopup;
import eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.IBookmarkPopupView;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.*;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.IStickiePresenter;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.StickiePresenter;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.MainFlowProcessor;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.FlowCommandsListener;
import eu.ydp.empiria.player.client.controller.item.*;
import eu.ydp.empiria.player.client.controller.multiview.animation.Animation;
import eu.ydp.empiria.player.client.controller.multiview.animation.SwipeAnimationProvider;
import eu.ydp.empiria.player.client.controller.multiview.swipe.SwipeType;
import eu.ydp.empiria.player.client.controller.multiview.swipe.SwipeTypeProvider;
import eu.ydp.empiria.player.client.controller.multiview.touch.TouchReservationHandler;
import eu.ydp.empiria.player.client.controller.report.AssessmentReportFactory;
import eu.ydp.empiria.player.client.controller.session.LessonStateReset;
import eu.ydp.empiria.player.client.controller.session.SessionDataManager;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.session.sockets.AssessmentSessionSocket;
import eu.ydp.empiria.player.client.controller.variables.ResultExtractorsFactory;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.AnswerEvaluationSupplier;
import eu.ydp.empiria.player.client.controller.variables.processor.VariableProcessingAdapter;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedAnswersManager;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ProcessingResultsToOutcomeMapConverterFactory;
import eu.ydp.empiria.player.client.controller.window.WindowResizeController;
import eu.ydp.empiria.player.client.controller.xml.XMLDataProvider;
import eu.ydp.empiria.player.client.gin.EmpiriaExListBoxDelay;
import eu.ydp.empiria.player.client.gin.binding.FlowManagerDataSupplier;
import eu.ydp.empiria.player.client.gin.binding.UniqueId;
import eu.ydp.empiria.player.client.gin.factory.*;
import eu.ydp.empiria.player.client.gin.providers.*;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.ResponseModuleScopedProvider;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.XmlElementModuleScopedProvider;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.controller.item.ResponseSocket;
import eu.ydp.empiria.player.client.module.identification.view.SelectableChoiceView;
import eu.ydp.empiria.player.client.module.identification.view.SelectableChoiceViewImpl;
import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandlerFactory;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingChildView;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingChildViewImpl;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingView;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingViewImpl;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistry;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.util.dom.redraw.ForceRedrawHack;
import eu.ydp.empiria.player.client.util.dom.redraw.ForceRedrawHackImpl;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.bus.PlayerEventsBus;
import eu.ydp.empiria.player.client.util.events.internal.emulate.TouchRecognition;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.HasTouchHandlers;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.view.player.PlayerContentView;
import eu.ydp.empiria.player.client.view.player.PlayerViewSocket;
import eu.ydp.empiria.player.client.view.player.styles.PlayerStylesController;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBoxDelays;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.jsfilerequest.client.FileRequest;
import eu.ydp.jsfilerequest.client.standard.StandardFileRequest;

public class PlayerGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        //bookmark
        bind(IBookmarkPopupView.class).to(BookmarkPopup.class);
        //assesment
        bind(AssessmentSessionSocket.class).to(SessionDataManager.class);
        //labeling
        bind(LabellingView.class).to(LabellingViewImpl.class);
        bind(LabellingChildView.class).to(LabellingChildViewImpl.class);
        //core
        bind(SessionDataSupplier.class).to(SessionDataManager.class);
        bind(LessonStateReset.class).asEagerSingleton();
        bind(ExListBoxDelays.class).to(EmpiriaExListBoxDelay.class);
        bind(FileRequest.class).to(StandardFileRequest.class);
        bind(Element.class).annotatedWith(ModuleScoped.class).toProvider(XmlElementModuleScopedProvider.class);
        bind(Response.class).annotatedWith(ModuleScoped.class).toProvider(ResponseModuleScopedProvider.class);
        bind(IPlayerContainersAccessor.class).to(PlayerContainersAccessor.class);
        bind(PlayerViewSocket.class).to(PlayerContentView.class);
        bind(ModulesRegistrySocket.class).to(ModulesRegistry.class);
        bind(FeedbackInteractionEventListener.class).to(DeliveryEventsHub.class);
        bind(DataSourceDataSupplier.class).to(DataSourceManager.class);
        bind(EventsBus.class).to(PlayerEventsBus.class);
        bind(String.class).annotatedWith(UniqueId.class)
                .toProvider(UniqIdStringProvider.class);
        bind(SwipeType.class).toProvider(SwipeTypeProvider.class);
        bind(Animation.class).toProvider(SwipeAnimationProvider.class);
        bind(ForceRedrawHack.class).to(ForceRedrawHackImpl.class);
        bind(FlowDataSupplier.class).annotatedWith(FlowManagerDataSupplier.class)
                .toProvider(FlowDataSupplierProvider.class);
        bind(FlowPanel.class).annotatedWith(Names.named("multiPageControllerMainPanel"))
                .toProvider(NewFlowPanelProvider.class);
        bind(FlowDataSupplier.class).to(MainFlowProcessor.class);
        bind(FlowCommandsListener.class).to(MainFlowProcessor.class);
        bind(WindowResizeController.class).asEagerSingleton();
        bind(PlayerStylesController.class).asEagerSingleton();
        bind(GroupedAnswersManager.class).annotatedWith(PageScoped.class).toProvider(GroupedAnswersManagerPageScopeProvider.class);
        bind(ModulesVariablesProcessor.class).annotatedWith(PageScoped.class).toProvider(ModulesVariablesProcessorPageScopedProvider.class);
        bind(ModulesProcessingResults.class).annotatedWith(PageScoped.class).toProvider(ModulesProcessingResultsPageScopeProvider.class);
        bind(ResponseSocket.class).annotatedWith(PageScoped.class).toProvider(ResponseSocketPageScopeProvider.class);
        bind(VariableProcessingAdapter.class).annotatedWith(PageScoped.class).toProvider(VariableProcessingAdapterPageScopedProvider.class);
        bind(ItemData.class).annotatedWith(PageScoped.class).toProvider(ItemDataProvider.class);
        bind(XmlData.class).annotatedWith(PageScoped.class).toProvider(XMLDataProvider.class);
        bind(ItemResponseManager.class).annotatedWith(PageScoped.class).toProvider(PageScopedItemResponseManagerProvider.class);
        bind(ItemXMLWrapper.class).annotatedWith(PageScoped.class).toProvider(PageScopedItemXMLWrapperProvider.class);
        bind(AnswerEvaluationSupplier.class).annotatedWith(PageScoped.class).toProvider(AnswerEvaluationSupplierProvider.class);

        install(new GinFactoryModuleBuilder().build(ModuleFactory.class));
        install(new GinFactoryModuleBuilder().build(PageScopeFactory.class));
        install(new GinFactoryModuleBuilder().build(LinkModuleFactory.class));
        install(new GinFactoryModuleBuilder().build(AssessmentControllerFactory.class));
        install(new GinFactoryModuleBuilder().build(AssessmentReportFactory.class));
        install(new GinFactoryModuleBuilder().implement(HasTouchHandlers.class, TouchRecognition.class)
                .build(TouchRecognitionFactory.class));
        install(new GinFactoryModuleBuilder().build(FieldValueHandlerFactory.class));
        install(new GinFactoryModuleBuilder().build(ProcessingResultsToOutcomeMapConverterFactory.class));
        install(new GinFactoryModuleBuilder().implement(IStickieView.class, StickieView.class)
                .implement(IStickiePresenter.class, StickiePresenter.class)
                .build(StickieFactory.class));
        install(new GinFactoryModuleBuilder().implement(HandlerRegistration.class, TouchReservationHandler.class)
                .build(TouchReservationFactory.class));
        install(new GinFactoryModuleBuilder().build(ResultExtractorsFactory.class));
        install(new GinFactoryModuleBuilder().build(TouchHandlerFactory.class));
        install(new GinFactoryModuleBuilder().build(InlineBodyGeneratorFactory.class));
        install(new GinFactoryModuleBuilder().build(ModulesInstalatorFactory.class));
        install(new GinFactoryModuleBuilder().build(PlayerFactory.class));
        install(new GinFactoryModuleBuilder().build(AssessmentFactory.class));
    }

    @Provides
    public IStickieProperties provideStickieProperties() {
        return StickieProperties.newInstance();
    }
}
