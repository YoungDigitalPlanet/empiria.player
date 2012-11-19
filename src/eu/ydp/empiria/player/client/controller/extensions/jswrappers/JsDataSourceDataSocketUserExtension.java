package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;

public class JsDataSourceDataSocketUserExtension extends AbstractJsExtension
		implements DataSourceDataSocketUserExtension {

	protected JavaScriptObject socketJs;
	protected DataSourceDataSupplier supplier;
	
	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_SOCKET_USER_DATA_SOURCE_DATA_CLIENT;
	}

	@Override
	public void init() {
		socketJs = createDataSourceDataSocketJs();
		setDataSourceDataSocketJs(extensionJsObject, socketJs);
	}
	
	@Override
	public void setDataSourceDataSupplier(DataSourceDataSupplier supplier) {
		this.supplier = supplier;
	}
	
	private native JavaScriptObject createDataSourceDataSocketJs()/*-{
		var instance = this;
		var socket = [];
		socket.getItemTitle = function(index){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsDataSourceDataSocketUserExtension::getItemTitle(I)(index);
		}
		socket.getAssessmentTitle = function(index){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsDataSourceDataSocketUserExtension::getAssessmentTitle()();
		}
		socket.getItemsCount = function(index){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsDataSourceDataSocketUserExtension::getItemsCount()();
		}
		return socket;
	}-*/;
	
	private String getItemTitle(int index){
		return supplier.getItemTitle(index);
	}
	
	private String getAssessmentTitle(){
		return supplier.getAssessmentTitle();
	}
	
	private int getItemsCount(){
		return supplier.getItemsCount();
	}
	
	private native void setDataSourceDataSocketJs(JavaScriptObject extension, JavaScriptObject socket)/*-{
		if (typeof extension.setDataSourceDataSocket == 'function'){
			extension.setDataSourceDataSocket(socket);
		}
	}-*/;

}
