package eu.ydp.empiria.player.client.module.drawing.view;

import eu.ydp.empiria.player.client.module.drawing.toolbox.tool.Tool;
import eu.ydp.gwtutil.client.util.geom.Size;

public class CanvasPresenter{
	 public void mouseDown(){}
	 public void mouseMove(){}
	 public void mouseUp(){} // na androidzie CanvasPresenter bêdzie musia³ to emulowaæ?
	 public void mouseOut(){}
	 public void setTool( Tool tool ){}
	 public void setImage(String url, Size size ){}
	 public CanvasView getView(){
		 return null;
	 }
}
