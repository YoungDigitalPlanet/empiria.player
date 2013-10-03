package eu.ydp.empiria.player.client.controller.extensions.internal;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.SessionDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.report.AssessmentReportFactory;
import eu.ydp.empiria.player.client.controller.report.HintInfo;
import eu.ydp.empiria.player.client.controller.report.ResultInfo;
import eu.ydp.empiria.player.client.controller.session.datasockets.AssessmentSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableUtil;
import eu.ydp.gwtutil.client.debug.gwtlogger.Logger;

public class ScormSupportExtension extends InternalExtension implements PlayerJsObjectModifierExtension, SessionDataSocketUserExtension, DataSourceDataSocketUserExtension {

	private static final Logger LOGGER = new Logger();

	protected SessionDataSupplier sessionDataSupplier;
	protected JavaScriptObject playerJsObject;
	protected DataSourceDataSupplier dataSourceDataSupplier;
	protected int masteryScore = 100;
	
	@Inject
	private AssessmentReportFactory factory;
	@Inject
	private PlayerWorkModeService workModeService;
	private ResultInfo result;
	private HintInfo hint;
	private VariableUtil variableUtil;
	
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
		AssessmentSessionDataSocket assessmentSessionDataSocket = sessionDataSupplier.getAssessmentSessionDataSocket();
		VariableProviderSocket assessmentVariableProvider = assessmentSessionDataSocket.getVariableProviderSocket();
		
		result = factory.getResultInfo(assessmentVariableProvider);
		hint = factory.getHintInfo(assessmentVariableProvider);
		variableUtil = new VariableUtil(assessmentVariableProvider);
		
		initPlayerJsObject(playerJsObject);

		initWorkMode();
	}

	private void initWorkMode() {
		if (isPreviewMode(playerJsObject)){
			workModeService.setCurrentWorkMode(PlayerWorkMode.PREVIEW);
		}
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
		playerJsObject.getMistakes = function(){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.internal.ScormSupportExtension::getMistakes()();
		}
		playerJsObject.getShowAnswers = function(){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.internal.ScormSupportExtension::getShowAnswers()();
		}
		playerJsObject.getReset = function(){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.internal.ScormSupportExtension::getReset()();
		}
		playerJsObject.getChecks = function(){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.internal.ScormSupportExtension::getChecks()();
		}
	}-*/;
	
	private native boolean isPreviewMode(JavaScriptObject playerJsObject)/*-{
		if (!!playerJsObject.enablePreviewMode){
			return playerJsObject.enablePreviewMode();
		}
		return false;
	}-*/;
	
	protected void setMasteryScore(int ms){
		masteryScore = ms;
	}

	protected int getAssessmentTime(){
		int value = sessionDataSupplier.getAssessmentSessionDataSocket().getTimeAssessmentTotal();
		return value;
	}

	protected int getScore(){
		return result.getDone();
	}

	protected int getScoreMax(){
		return result.getTodo();
	}

	protected int getErrors(){
		return result.getErrors();
	}
	
	protected int getMistakes(){
		return hint.getMistakes();
	}
	
	protected int getShowAnswers(){
		return hint.getShowAnswers();
	}
	
	protected int getReset(){
		return hint.getReset();
	}
	
	protected int getChecks(){
		return hint.getChecks();
	}
	
	protected String getLessonStatus(){
		int done = getScore();
		int todo = getScoreMax();
		int visited = variableUtil.getVariableIntValue("VISITED", 0);
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
