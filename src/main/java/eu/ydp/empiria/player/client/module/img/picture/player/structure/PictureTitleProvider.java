package eu.ydp.empiria.player.client.module.img.picture.player.structure;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;

@Singleton
public class PictureTitleProvider {

    public Widget getPictureTitleWidget(PicturePlayerBean bean, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        if (bean.hasTitle()) {
            Element titleXmlElement = bean.getTitleBean().getTitleName().getValue();
            return inlineBodyGeneratorSocket.generateInlineBody(titleXmlElement);
        } else {
            return new FlowPanel();
        }
    }

    public String getPictureTitleString(PicturePlayerBean bean) {
        if (bean.hasTitle()) {
            Element titleXmlElement = bean.getTitleBean().getTitleName().getValue();
            return stripTextFromHTMLTags(titleXmlElement);
        } else {
            return "";
        }
    }

    private String stripTextFromHTMLTags(Element titleXmlElement) {
        return titleXmlElement.toString().replaceAll("<.*?>", "");
    }
}
