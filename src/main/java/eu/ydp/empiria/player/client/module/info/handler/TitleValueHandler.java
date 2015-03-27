package eu.ydp.empiria.player.client.module.info.handler;

import static eu.ydp.empiria.player.client.module.info.ContentFieldInfo.FieldType.ITEM;
import static eu.ydp.empiria.player.client.module.info.ContentFieldInfo.FieldType.TEST;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;
import eu.ydp.gwtutil.client.StringUtils;

public class TitleValueHandler implements FieldValueHandler {

	private DataSourceDataSupplier dataSourceDataSupplier;

	@Inject
	public TitleValueHandler(@Assisted DataSourceDataSupplier dataSourceDataSupplier) {
		this.dataSourceDataSupplier = dataSourceDataSupplier;
	}

	@Override
	public String getValue(ContentFieldInfo info, int refItemIndex) {
		String value = StringUtils.EMPTY_STRING;

		if (TEST.equals(info.getType())) {
			value = dataSourceDataSupplier.getAssessmentTitle();
		} else if (ITEM.equals(info.getType())) {
			value = dataSourceDataSupplier.getItemTitle(refItemIndex);
		}

		return value;
	}

}
