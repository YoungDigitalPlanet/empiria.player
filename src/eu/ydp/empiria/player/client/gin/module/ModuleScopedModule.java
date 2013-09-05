package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.xml.client.Element;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.PersonaService;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.binding.CachedModuleScoped;
import eu.ydp.empiria.player.client.gin.module.tutor.TutorId;
import eu.ydp.empiria.player.client.gin.module.tutor.TutorIdProvider;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.CssStylesModuleScopedProvider;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.PersonaServiceModuleScopedProvider;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.ResponseModuleScopedProvider;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.TutorConfigModuleScopedProvider;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.WithCacheCssStylesModuleScopedProvider;
import eu.ydp.empiria.player.client.gin.scopes.module.providers.XmlElementModuleScopedProvider;
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
import eu.ydp.empiria.player.client.module.draggap.DragGapModuleModel;
import eu.ydp.empiria.player.client.module.draggap.SourceListManagerAdapter;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapView;
import eu.ydp.empiria.player.client.module.drawing.model.DrawingBean;
import eu.ydp.empiria.player.client.module.drawing.model.DrawingModelProvider;
import eu.ydp.empiria.player.client.module.math.MathGapModel;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.SelectionViewBuilder;
import eu.ydp.empiria.player.client.module.selection.model.GroupAnswersControllerModel;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;
import eu.ydp.empiria.player.client.module.tutor.ActionEventGenerator;
import eu.ydp.empiria.player.client.module.tutor.ActionExecutorService;
import eu.ydp.empiria.player.client.module.tutor.CommandFactory;
import eu.ydp.empiria.player.client.module.tutor.actions.OnOkAction;
import eu.ydp.empiria.player.client.module.tutor.actions.OnPageAllOkAction;
import eu.ydp.empiria.player.client.module.tutor.actions.OnWrongAction;
import eu.ydp.empiria.player.client.module.tutor.actions.OutcomeDrivenActionTypeGenerator;
import eu.ydp.empiria.player.client.module.tutor.actions.OutcomeDrivenActionTypeProvider;
import eu.ydp.empiria.player.client.module.tutor.presenter.TutorPresenter;
import eu.ydp.empiria.player.client.module.tutor.presenter.TutorPresenterImpl;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.empiria.player.client.module.tutor.view.TutorViewImpl;
import eu.ydp.empiria.player.client.style.ModuleStyle;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopeStack;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class ModuleScopedModule extends AbstractGinModule{

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
	}

	private void bindDrawing() {
		bind(DrawingModelProvider.class).in(Singleton.class);
		bind(DrawingBean.class).annotatedWith(ModuleScoped.class).toProvider(DrawingModelProvider.class);
	}

	private void bindCssStyle() {
		bind(CssStylesModuleScopedProvider.class).in(Singleton.class);
		bind(WithCacheCssStylesModuleScopedProvider.class).in(Singleton.class);

		bind(ModuleStyle.class).annotatedWith(ModuleScoped.class).toProvider(CssStylesModuleScopedProvider.class);
		bind(ModuleStyle.class).annotatedWith(CachedModuleScoped.class).toProvider(WithCacheCssStylesModuleScopedProvider.class);
	}

	private void bindOrdering() {
		bindModuleScoped(OrderingItemsDao.class, new TypeLiteral<ModuleScopedProvider<OrderingItemsDao>>(){});
		bindModuleScoped(OrderInteractionModuleModel.class, new TypeLiteral<ModuleScopedProvider<OrderInteractionModuleModel>>(){});
		bindModuleScoped(OrderInteractionView.class, new TypeLiteral<ModuleScopedProvider<OrderInteractionView>>(){});
	}

	private void bindColorfill() {
		bindModuleScoped(ColorfillInteractionView.class, new TypeLiteral<ModuleScopedProvider<ColorfillInteractionView>>(){});
		bindModuleScoped(ColorfillInteractionModuleModel.class, new TypeLiteral<ModuleScopedProvider<ColorfillInteractionModuleModel>>(){});
		bindModuleScoped(ColorfillInteractionPresenter.class, new TypeLiteral<ModuleScopedProvider<ColorfillInteractionPresenter>>(){});
		bindModuleScoped(ColorfillModelProxy.class, new TypeLiteral<ModuleScopedProvider<ColorfillModelProxy>>(){});
		bindModuleScoped(ColorfillInteractionViewColors.class, new TypeLiteral<ModuleScopedProvider<ColorfillInteractionViewColors>>(){});
		bindModuleScoped(UserToResponseAreaMapper.class, new TypeLiteral<ModuleScopedProvider<UserToResponseAreaMapper>>(){});
		bindModuleScoped(ColorfillBeanProxy.class, new TypeLiteral<ModuleScopedProvider<ColorfillBeanProxy>>(){});
		bindModuleScoped(ColorfillInteractionStructure.class, new TypeLiteral<ModuleScopedProvider<ColorfillInteractionStructure>>(){});
		bindModuleScoped(ResponseAnswerByViewBuilder.class, new TypeLiteral<ModuleScopedProvider<ResponseAnswerByViewBuilder>>(){});
	}

	private void bindDragGap() {
		bindModuleScoped(DragGapModuleModel.class, new TypeLiteral<ModuleScopedProvider<DragGapModuleModel>>(){});
		bindModuleScoped(DragGapView.class, new TypeLiteral<ModuleScopedProvider<DragGapView>>(){});
		bindModuleScoped(SourceListManagerAdapter.class, new TypeLiteral<ModuleScopedProvider<SourceListManagerAdapter>>(){});
	}

	private void bindChoice() {
		bindModuleScoped(ChoiceModuleView.class, new TypeLiteral<ModuleScopedProvider<ChoiceModuleView>>(){});
		bindModuleScoped(ChoiceModulePresenter.class, new TypeLiteral<ModuleScopedProvider<ChoiceModulePresenter>>(){});
		bindModuleScoped(ChoiceModuleModel.class, new TypeLiteral<ModuleScopedProvider<ChoiceModuleModel>>(){});
		bindModuleScoped(MultiChoiceStyleProvider.class, new TypeLiteral<ModuleScopedProvider<MultiChoiceStyleProvider>>(){});
	}

	private void bindTutor() {
		bindModuleScoped(ActionEventGenerator.class, new TypeLiteral<ModuleScopedProvider<ActionEventGenerator>>(){});
		bindModuleScoped(TutorPresenter.class, new TypeLiteral<ModuleScopedProvider<TutorPresenterImpl>>(){});
		bindModuleScoped(TutorView.class, new TypeLiteral<ModuleScopedProvider<TutorViewImpl>>(){});
		bindModuleScoped(ActionExecutorService.class, new TypeLiteral<ModuleScopedProvider<ActionExecutorService>>(){});
		bindModuleScoped(CommandFactory.class, new TypeLiteral<ModuleScopedProvider<CommandFactory>>(){});
		bindModuleScoped(OutcomeDrivenActionTypeGenerator.class, new TypeLiteral<ModuleScopedProvider<OutcomeDrivenActionTypeGenerator>>(){});
		bindModuleScoped(OnPageAllOkAction.class, new TypeLiteral<ModuleScopedProvider<OnPageAllOkAction>>(){});
		bind(String.class).annotatedWith(TutorId.class).toProvider(TutorIdProvider.class);
		bindModuleScoped(GroupAnswersControllerModel.class, new TypeLiteral<ModuleScopedProvider<GroupAnswersControllerModel>>(){});
		bindModuleScoped(OnOkAction.class, new TypeLiteral<ModuleScopedProvider<OnOkAction>>(){});
		bindModuleScoped(OnWrongAction.class, new TypeLiteral<ModuleScopedProvider<OnWrongAction>>(){});
		bindModuleScoped(OutcomeDrivenActionTypeProvider.class, new TypeLiteral<ModuleScopedProvider<OutcomeDrivenActionTypeProvider>>(){});
		bind(TutorConfig.class).annotatedWith(ModuleScoped.class).toProvider(TutorConfigModuleScopedProvider.class);
		bind(PersonaService.class).annotatedWith(ModuleScoped.class).toProvider(PersonaServiceModuleScopedProvider.class);
	}

	private void bindSelection() {
		bindModuleScoped(SelectionModuleModel.class, new TypeLiteral<ModuleScopedProvider<SelectionModuleModel>>(){});
		bindModuleScoped(SelectionModuleView.class, new TypeLiteral<ModuleScopedProvider<SelectionModuleView>>(){});
		bindModuleScoped(SelectionViewBuilder.class, new TypeLiteral<ModuleScopedProvider<SelectionViewBuilder>>(){});
	}

	private void bindMathGap() {
		bindModuleScoped(MathGapModel.class, new TypeLiteral<ModuleScopedProvider<MathGapModel>>(){});
	}

	private <F, T extends F> void bindModuleScoped(Class<F> clazz, TypeLiteral<ModuleScopedProvider<T>> typeLiteral){
		bind(typeLiteral).in(Singleton.class);
		bind(clazz)
			.annotatedWith(ModuleScoped.class)
			.toProvider(Key.get(typeLiteral));
	}

}
