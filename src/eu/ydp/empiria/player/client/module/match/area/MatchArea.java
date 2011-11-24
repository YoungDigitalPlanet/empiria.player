package eu.ydp.empiria.player.client.module.match.area;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Line;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.components.TouchablePanel;
import eu.ydp.empiria.player.client.module.ITouchEventsListener;

public class MatchArea {
	
	public MatchArea(int canvasWidth, int canvasHeight, ITouchEventsListener touchListener){
		canvas = new DrawingArea(canvasWidth, canvasHeight);
		canvasId = Document.get().createUniqueId();
		canvas.getElement().setId(canvasId);
		canvas.setStylePrimaryName("qp-match-area");
		
		areaCover = new TouchablePanel(touchListener);
		areaCoverId = Document.get().createUniqueId();
		areaCover.getElement().setId(areaCoverId);
		areaCover.setStyleName("qp-match-area-container-cover");

		land = new TouchablePanel(touchListener);
		land.setStyleName("qp-match-area-container-land");
		landId = Document.get().createUniqueId();
		land.getElement().setId(landId);

		areaContainer = new AbsolutePanel();
		areaContainerId = Document.get().createUniqueId();
		areaContainer.getElement().setId(areaContainerId);
		areaContainer.setStyleName("qp-match-area-container2");
		areaContainer.add(canvas, 0, 0);
		areaContainer.add(areaCover, 0, 0);
		areaContainer.add(land, 0, 0);
		
		dragLine = new Line(0, 0, 0, 0);
		lineId = Document.get().createUniqueId();
		dragLine.getElement().setId(lineId);
		dragLine.setStyleName("qp-match-area-dragline");
		
	}

	public AbsolutePanel areaContainer;
	public TouchablePanel areaCover;
	public DrawingArea canvas;
	public TouchablePanel land;
	public Line dragLine;
	
	public String canvasId;
	public String areaContainerId;
	public String lineId;
	public String areaCoverId;
	public String landId;
	
	private int lastAreaWidth;
	private int lastAreaHeight;
	
	
	public Widget getView(){
		return areaContainer;
	}
	
	public void setCanvasSize(int w, int h){
		canvas.setWidth(w);
		canvas.setHeight(h);
		areaContainer.setSize(String.valueOf(w) + "px", String.valueOf(h) + "px");
		areaCover.setSize(String.valueOf(w) + "px", String.valueOf(h) + "px");
		land.setSize(String.valueOf(w) + "px", String.valueOf(h) + "px");
		lastAreaWidth = w;
		lastAreaHeight = h;
	}
	
	public void showDragLine(){
		try {
			canvas.add(dragLine);
		}catch (Exception e) {	}
	}
	
	public void hideDragLine(){
		canvas.remove(dragLine);
	}
	
	public void processDragLine(int x1, int y1, int x2, int y2){
			
		dragLine.setX1(x1);
		dragLine.setY1(y1);
		dragLine.setX2(x2);
		dragLine.setY2(y2);
	}

	public void addLine(Line l){
		canvas.add(l);
	}

	public void removeLine(Line l){
		canvas.remove(l);
		redrawCanvas();
	}
	
	public void clear(){
		canvas.clear();
		redrawCanvas();
	}
	
	private void redrawCanvas(){
		int w = canvas.getOffsetWidth();
		int h = canvas.getOffsetHeight();
		canvas.setWidth(0);
		canvas.setHeight(0);
		canvas.setWidth(w);
		canvas.setHeight(h);
		
	}
	
	public void resetLand(){
		areaContainer.setWidgetPosition(land, 0, 0);
		land.setSize(String.valueOf(lastAreaWidth) + "px", String.valueOf(lastAreaHeight) + "px");
	}
	public void moveLand(int x, int y){
		land.setSize(String.valueOf(50) + "px", String.valueOf(50) + "px");
		areaContainer.setWidgetPosition(land, x-25, y-25);
	}

}
