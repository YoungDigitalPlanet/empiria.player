package eu.ydp.empiria.player.client.module.simulation;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.ILifecycleModule;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.gwtcreatejs.client.handler.CompleteHandler;
import eu.ydp.gwtcreatejs.client.loader.CreateJsLoader;

public class SimulationModule extends SimpleModuleBase implements Factory<SimulationModule>, ILifecycleModule {

	protected SimulationModuleView view;
	
	protected CreateJsLoader loader;

	@Override
	public SimulationModule getNewInstance() {
		return new SimulationModule();
	}

	@Override
	public Widget getView() {
		if (view == null) {
			view = new SimulationModuleView();
		}
		return view;
	}
	
	@Override
	public void onBodyLoad() {
				
	}

	@Override
	public void onBodyUnload() {
				
	}

	@Override
	public void onSetUp() {
				
	}

	@Override
	public void onStart() {
				
	}

	@Override
	public void onClose() {
		if(loader != null){
			loader.stopSounds();
		}
	}

	@Override
	protected void initModule(Element element) {
		String src = element.getAttribute("src");
		
		initializeLoader(src);
		loader.load(src);
	}
	
	protected void initializeLoader(String resourceSrc){
		loader = new CreateJsLoader();
		loader.setLibraryURL(getLibraryURL(resourceSrc));
		loader.addCompleteHandler(new CompleteHandler() {
			
			@Override
			public void onComplete() {
				addChildView(loader.getContent().getCanvas());
			}
		});
	}

	protected void addChildView(IsWidget child) {
		SimulationModuleView parentView = (SimulationModuleView) getView();

		if (parentView != null) {
			parentView.add(child);
		}
	}
	
	protected String getLibraryURL(String resourceSrc){
		String libraryURL = resourceSrc.substring(0, resourceSrc.lastIndexOf('/') + 1);
		
		//FIXME path to libraries should be given from extension
		libraryURL += "../../../common/jslibs/";
		return libraryURL;
	}
}
