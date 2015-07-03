package eu.ydp.empiria.player.client.controller.variables;

import com.google.gwt.xml.client.Node;

public interface IVariableCreator<V> {

    public V createVariable(Node node);
}
