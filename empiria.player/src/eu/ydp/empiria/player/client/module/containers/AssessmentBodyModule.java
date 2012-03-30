package eu.ydp.empiria.player.client.module.containers;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;

public class AssessmentBodyModule extends ContainerModuleBase {
	
	public AssessmentBodyModule(){
		super();
		panel.setStyleName("qp-body");
	}
	
	public void initModule(Element element, BodyGeneratorSocket generator){
		initModule(element, null, null, generator);
	}
	
}
