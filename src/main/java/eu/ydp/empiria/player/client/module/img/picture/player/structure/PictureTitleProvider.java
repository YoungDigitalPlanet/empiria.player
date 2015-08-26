package eu.ydp.empiria.player.client.module.img.picture.player.structure;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;


@Singleton
public class PictureTitleProvider {

    public Widget getPictureTitleWidget(PicturePlayerBean bean, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        if (bean.hasTitle()) {
            Element titleXmlElement = bean.getTitleBean().getTitleName().getValue();
            Widget titleWidget = inlineBodyGeneratorSocket.generateInlineBody(titleXmlElement);
            return titleWidget;
        } else {
            return new Widget();
        }
    }

    public String getPictureTitleString(PicturePlayerBean bean) {
        if (bean.hasTitle()) {
            Element titleXmlElement = bean.getTitleBean().getTitleName().getValue();
            String titleXmlString = stripStringFromHTMLTags(titleXmlElement);
            return titleXmlString;
        } else {
            return "";
        }
    }

    private String stripStringFromHTMLTags(Element titleXmlElement) {
        return titleXmlElement.toString().replaceAll("<.*?>", "");
    }
}
