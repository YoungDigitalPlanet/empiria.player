package eu.ydp.empiria.player.client.module.img.explorable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class ExplorableImgContent extends AbstractExplorableImgContentBase {

    private static ExplorableImgContentUiBinder uiBinder = GWT.create(ExplorableImgContentUiBinder.class);

    interface ExplorableImgContentUiBinder extends UiBinder<Widget, ExplorableImgContent> {
    }

    @Inject
    private StyleSocket styleSocket;

    @UiField(provided = true)
    protected ExplorableImgWindow window;

    @Override
    protected void initUiBinder() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Inject
    public ExplorableImgContent(ExplorableImgWindowProvider explorableImgWindowProvider) {
        window = explorableImgWindowProvider.get();
    }

    @Override
    public void init(Element element, ModuleSocket moduleSocket) {
        parseStyles(styleSocket.getStyles(element));

        Element titleNodes = XMLUtils.getFirstElementWithTagName(element, EmpiriaTagConstants.ATTR_TITLE);
        final String title = XMLUtils.getTextFromChilds(titleNodes);
        window.init(windowWidth, windowHeight, element.getAttribute(EmpiriaTagConstants.ATTR_SRC), scale, scaleStep, zoomMax, title);
    }

    @Override
    protected void zoom() {
        if (zoomInClicked) {
            window.zoomIn();
        } else {
            window.zoomOut();
        }
    }
}
