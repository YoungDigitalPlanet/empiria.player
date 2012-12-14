package eu.ydp.empiria.player.client.module.info;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;


public interface VariableInterpreterFactory {
	
	VariableInterpreter getInterpreter(DataSourceDataSupplier dataSourceDataSupplier, SessionDataSupplier sessionDataSupplier);
	
	ContentFieldRegistry getRegistry(DataSourceDataSupplier dataSourceDataSupplier, SessionDataSupplier sessionDataSupplier);
	
}
