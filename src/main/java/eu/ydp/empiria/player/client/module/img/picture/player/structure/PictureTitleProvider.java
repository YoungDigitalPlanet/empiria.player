package eu.ydp.empiria.player.client.module.img.picture.player.structure;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;

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

    public String getPictutreTitleString(PicturePlayerBean bean) {

        if (bean.hasTitle()) {

            Element titleXmlElement = bean.getTitleBean().getTitleName().getValue();
            String titleXmlString = titleXmlElement.getChildNodes().toString();
            return titleXmlString;

        } else {

            return "";
        }
    }

}
