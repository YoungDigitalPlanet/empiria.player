package eu.ydp.empiria.player.client.module.slideshow;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlideshowPlayerPresenter;
import eu.ydp.empiria.player.client.module.slideshow.slides.SlideshowController;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideshowBean;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideshowModuleStructure;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideshowPlayerBean;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.service.json.IJSONService;

public class SlideshowPlayerModule extends SimpleModuleBase {

    private final SlideshowController controller;
    private final SlideshowModuleStructure moduleStructure;
    private final IJSONService ijsonService;
    private final SlideshowPlayerPresenter presenter;
    private final SlideshowTemplateInterpreter templateInterpreter;

    @Inject
    public SlideshowPlayerModule(@ModuleScoped SlideshowController slideshowController, @ModuleScoped SlideshowPlayerPresenter presenter,
                                 SlideshowModuleStructure moduleStructure, IJSONService ijsonService, SlideshowTemplateInterpreter templateInterpreter) {
        this.controller = slideshowController;
        this.presenter = presenter;
        this.moduleStructure = moduleStructure;
        this.ijsonService = ijsonService;
        this.templateInterpreter = templateInterpreter;
    }

    @Override
    public Widget getView() {
        return presenter.getView();
    }

    @Override
    protected void initModule(Element element) {
        InlineBodyGeneratorSocket inlineBodyGeneratorSocket = getModuleSocket().getInlineBodyGeneratorSocket();

        moduleStructure.createFromXml(element.toString(), ijsonService.createArray());
        SlideshowPlayerBean slideshowPlayer = moduleStructure.getBean();
        SlideshowBean slideshow = slideshowPlayer.getSlideshowBean();

        initPager(slideshowPlayer);
        presenter.init(slideshow, inlineBodyGeneratorSocket);
        controller.init(slideshow.getSlideBeans(), inlineBodyGeneratorSocket);
    }

    private void initPager(SlideshowPlayerBean slideshowPlayer) {
        if (templateInterpreter.isPagerTemplateActivate(slideshowPlayer)) {
            int slidesSize = slideshowPlayer.getSlideshowBean().getSlideBeans().size();
            Widget pagerWidget = controller.initPager(slidesSize);
            presenter.setPager(pagerWidget);
        }
    }
}
