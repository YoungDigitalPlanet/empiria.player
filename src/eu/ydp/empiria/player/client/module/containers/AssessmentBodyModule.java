package eu.ydp.empiria.player.client.module.containers;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class AssessmentBodyModule extends SimpleContainerModuleBase<AssessmentBodyModule> {
	protected StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();
	public AssessmentBodyModule(){
		super();
		panel.setStyleName(styleNames.QP_BODY());
	}

	public void initModule(Element element, BodyGeneratorSocket generator){
		initModule(element, null, null, generator);
	}
	@Override
	public AssessmentBodyModule getNewInstance() {
		return new AssessmentBodyModule();
	}

}
