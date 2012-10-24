package eu.ydp.empiria.player.client.controller.body;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.xml.client.Node;

public interface BodyGeneratorSocket {
	public void generateBody(Node itemBodyNode, HasWidgets viewParent);
	public void processNode(Node node, HasWidgets viewParent);
}
