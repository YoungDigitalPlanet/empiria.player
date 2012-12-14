package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;

public class PageCountValueHandler implements FieldValueHandler {

	private DataSourceDataSupplier dataSourceDataSupplier;

	@Inject
	public PageCountValueHandler(@Assisted DataSourceDataSupplier dataSourceDataSupplier){
		this.dataSourceDataSupplier = dataSourceDataSupplier;
	}
	
	@Override
	public String getValue(ContentFieldInfo info, int refItemIndex) {
		return String.valueOf(dataSourceDataSupplier.getItemsCount());
	}

}
