package eu.ydp.empiria.player.client.module.connection.item;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.ConnectionStyleNameConstants;

public class ConnectionItemViewRight extends AbstractConnectionItemView {
    private static ConnectionItemViewUiBinder uiBinder = GWT.create(ConnectionItemViewUiBinder.class);

    interface ConnectionItemViewUiBinder extends UiBinder<Widget, ConnectionItemViewRight> {
    }

    @Inject
    public ConnectionItemViewRight(ConnectionStyleNameConstants styleNames, @Assisted InlineBodyGeneratorSocket bodyGenerator, @Assisted PairChoiceBean bean) {
        super(styleNames, bodyGenerator, bean);
        initWidget(uiBinder.createAndBindUi(this));
        buildView();
    }
}
