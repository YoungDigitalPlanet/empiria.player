package eu.ydp.empiria.player.client.controller.data;

import com.google.common.base.Enums;
import com.google.common.base.Optional;
import com.google.gwt.xml.client.Node;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

public class WorkModeParserForAssessment {

    public Optional<PlayerWorkMode> parse(XmlData data) {
        Node node = data.getDocument()
                .getFirstChild()
                .getAttributes()
                .getNamedItem("mode");

        if (node == null) {
            return Optional.absent();
        } else {
            return Enums.getIfPresent(PlayerWorkMode.class, node.getNodeValue()
                    .toUpperCase());
        }
    }
}
