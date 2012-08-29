package eu.ydp.empiria.player.client.controller.extensions.internal;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.SessionDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.gwtutil.client.NumberUtils;

public class ScormSupportExtension extends InternalExtension implements PlayerJsObjectModifierExtension, SessionDataSocketUserExtension, DataSourceDataSocketUserExtension {

	protected SessionDataSupplier sessionDataSupplier;
	protected JavaScriptObject playerJsObject;
	protected DataSourceDataSupplier dataSourceDataSupplier;
	protected int masteryScore = 100;
	
	@Override
	public void setSessionDataSupplier(SessionDataSupplier sessionDataSupplier) {
		this.sessionDataSupplier = sessionDataSupplier;
	}

	@Override
	public void setPlayerJsObject(JavaScriptObject playerJsObject) {
		this.playerJsObject = playerJsObject;
	}

	@Override
	public void setDataSourceDataSupplier(DataSourceDataSupplier supplier) {
		this.dataSourceDataSupplier = supplier;
	}

	@Override
	public void init() {
		initPlayerJsObject(playerJsObject);
	}
	
	private native void initPlayerJsObject(JavaScriptObject playerJsObject)/*-{
		var instance = this;
		playerJsObject.getScore = function(){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.internal.ScormSupportExtension::getScore()();
		}
		playerJsObject.getScoreMax = function(){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.internal.ScormSupportExtension::getScoreMax()();
		}
		playerJsObject.getErrors = function(){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.internal.ScormSupportExtension::getErrors()();
		}
		playerJsObject.getLessonStatus = function(){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.internal.ScormSupportExtension::getLessonStatus()();
		}
		playerJsObject.getAssessmentTime = function(){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.internal.ScormSupportExtension::getAssessmentTime()();
		}
		playerJsObject.setMasteryScore = function(ms){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.internal.ScormSupportExtension::setMasteryScore(I)(ms);
		}
	}-*/;
	
	protected void setMasteryScore(int ms){
		masteryScore = ms;
	}

	protected int getAssessmentTime(){
		int value = sessionDataSupplier.getAssessmentSessionDataSocket().getTimeAssessmentTotal();
		return value;
	}

	protected int getScore(){
		String valueString = sessionDataSupplier.getAssessmentSessionDataSocket().getVariableProviderSocket().getVariableValue("DONE").getValuesShort();
		int value = NumberUtils.tryParseInt(valueString, 0);
		return value;
	}

	protected int getScoreMax(){
		String valueString = sessionDataSupplier.getAssessmentSessionDataSocket().getVariableProviderSocket().getVariableValue("TODO").getValuesShort();
		int value = NumberUtils.tryParseInt(valueString, 0);
		return value;
	}

	protected int getErrors(){
		String valueString = sessionDataSupplier.getAssessmentSessionDataSocket().getVariableProviderSocket().getVariableValue("ERRORS").getValuesShort();
		int value = NumberUtils.tryParseInt(valueString, 0);
		return value;
	}
	
	protected String getLessonStatus(){
		String valueString = sessionDataSupplier.getAssessmentSessionDataSocket().getVariableProviderSocket().getVariableValue("DONE").getValuesShort();
		int done = NumberUtils.tryParseInt(valueString, 0);
		valueString = sessionDataSupplier.getAssessmentSessionDataSocket().getVariableProviderSocket().getVariableValue("TODO").getValuesShort();
		int todo = NumberUtils.tryParseInt(valueString, 0);
		valueString = sessionDataSupplier.getAssessmentSessionDataSocket().getVariableProviderSocket().getVariableValue("VISITED").getValuesShort();
		int visited = NumberUtils.tryParseInt(valueString, 0);
		int items = dataSourceDataSupplier.getItemsCount();
		String status;
		if (visited == items){
			if (todo == 0){
				status = "COMPLETED";
			} else {
				int result = 100 * done / todo;
				boolean passed = (result >= masteryScore);
				if (passed){
					status = "PASSED";
				} else {
					status = "FAILED";
				}
			}
		} else {
			status = "INCOMPLETE";
		}
		return status;
	}

}
