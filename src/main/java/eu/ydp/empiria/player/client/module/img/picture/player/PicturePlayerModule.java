package eu.ydp.empiria.player.client.module.img.picture.player;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.img.ImgContent;
import eu.ydp.empiria.player.client.module.img.picture.player.presenter.PicturePlayerPresenter;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerBean;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerStructure;
import eu.ydp.gwtutil.client.service.json.IJSONService;

public class PicturePlayerModule implements ImgContent {

    private PicturePlayerStructure structure;
    private IJSONService ijsonService;
    private PicturePlayerPresenter presenter;

    @Inject
    public PicturePlayerModule(PicturePlayerStructure structure, IJSONService ijsonService, PicturePlayerPresenter presenter) {
        this.structure = structure;
        this.ijsonService = ijsonService;
        this.presenter = presenter;
    }

    @Override
    public Widget asWidget() {
        return presenter.getView();
    }

    @Override
    public void init(Element element, ModuleSocket moduleSocket) {
        structure.createFromXml(element.toString(), ijsonService.createArray());
        PicturePlayerBean bean = structure.getBean();
        presenter.init(bean, moduleSocket.getInlineBodyGeneratorSocket());
    }

    public void setTemplate(boolean template) {
        presenter.setTemplate(template);
    }
}
