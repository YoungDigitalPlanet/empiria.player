package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.SessionDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.report.AssessmentReportFactory;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;

public class AssessmentJsonReportExtension extends InternalExtension implements PlayerJsObjectModifierExtension, SessionDataSocketUserExtension,
		DataSourceDataSocketUserExtension {

	@Inject
	private AssessmentReportFactory factory;

	private DataSourceDataSupplier dataSupplier;

	private SessionDataSupplier sessionSupplier;

	private JavaScriptObject playerJsObject;

	@Override
	public void setPlayerJsObject(JavaScriptObject playerJsObject) {
		this.playerJsObject = playerJsObject;
	}

	@Override
	public void setDataSourceDataSupplier(DataSourceDataSupplier dataSupplier) {
		this.dataSupplier = dataSupplier;
	}

	@Override
	public void setSessionDataSupplier(SessionDataSupplier sessionDataSupplier) {
		this.sessionSupplier = sessionDataSupplier;
	}

	@Override
	public void init() {
		initPlayerJsObject(playerJsObject);
	}

	private final native void initPlayerJsObject(JavaScriptObject player)/*-{
																			var instance = this;
																			
																			player.getLessonJSONReport = function(){
																			return instance.@eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.AssessmentJsonReportExtension::getJSONReport()();
																			}		
																			}-*/;

	private String getJSONReport() {
		AssessmentJsonReportGenerator generator = factory.getAssessmentJsonReportGenerator(dataSupplier, sessionSupplier);
		AssessmentJsonReport jsonReport = generator.generate();
		return jsonReport.getJSONString();
	}
}
