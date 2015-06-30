package eu.ydp.empiria.player.client.controller.item;

import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import eu.ydp.empiria.player.client.controller.variables.IVariableCreator;
import eu.ydp.empiria.player.client.controller.variables.manager.VariableManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseNodeParser;

public class ItemResponseManager extends VariableManager<Response> {

    public ItemResponseManager(NodeList responseDeclarationNodes, final ResponseNodeParser responseNodeParser) {
        super(responseDeclarationNodes, new IVariableCreator<Response>() {
            @Override
            public Response createVariable(Node node) {
                return responseNodeParser.parseResponseFromNode(node.toString());
            }
        });
    }

}
