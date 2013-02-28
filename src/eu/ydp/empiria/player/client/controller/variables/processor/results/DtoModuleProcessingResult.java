package eu.ydp.empiria.player.client.controller.variables.processor.results;


public class DtoModuleProcessingResult {

	private GeneralVariables generalVariables;
	private ConstantVariables constantVariables;
	private UserInteractionVariables userInteractionVariables;

	public DtoModuleProcessingResult(GeneralVariables generalVariables, ConstantVariables constantVariables, UserInteractionVariables userInteractionVariables) {
		this.generalVariables = generalVariables;
		this.constantVariables = constantVariables;
		this.userInteractionVariables = userInteractionVariables;
	}

	public static DtoModuleProcessingResult fromDefaultVariables(){
		GeneralVariables generalVariables = new GeneralVariables();
		ConstantVariables constantVariables = new ConstantVariables();
		UserInteractionVariables userInteractionVariables = new UserInteractionVariables();
		return new DtoModuleProcessingResult(generalVariables, constantVariables, userInteractionVariables);
	}
	
	public GeneralVariables getGeneralVariables() {
		return generalVariables;
	}

	public void setGeneralVariables(GeneralVariables generalVariables) {
		this.generalVariables = generalVariables;
	}

	public ConstantVariables getConstantVariables() {
		return constantVariables;
	}

	public void setConstantVariables(ConstantVariables constantVariables) {
		this.constantVariables = constantVariables;
	}

	public UserInteractionVariables getUserInteractionVariables() {
		return userInteractionVariables;
	}

	public void setUserInteractionVariables(UserInteractionVariables userInteractionVariables) {
		this.userInteractionVariables = userInteractionVariables;
	}
}
