package eu.ydp.empiria.player.client.module.dragdrop;

import java.util.Vector;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.components.Rectangle;

public class DragPanel extends AbsolutePanel {

	private Vector<DragOrigin> origins;
	private Vector<DragElement> elements;
	private Vector<DragSlot> slots;
	private Vector<Integer> elementsLocations;
	
	int marginLeft = 0;
	int marginTop = 0;
	
	boolean marginSet = false;
	
	public DragPanel(){
		origins = new Vector<DragOrigin>();
		elementsLocations = new Vector<Integer>();
	}
	
	public void registerElements(Vector<DragElement> elements){
		this.elements = elements;
		for (DragElement de : this.elements){
			add(de, 0, 0);
			elementsLocations.add(-1);
		}
	}
	
	public void registerOrigins(Rectangle sourcelistRectangle){
		
		int GAP = 4;
		
		int totalWidth = 0;
		int totalHeight = 0;
		for (DragElement de : elements){
			totalWidth += de.getOffsetWidth() + GAP;
			if (de.getOffsetHeight() > totalHeight)
				totalHeight = de.getOffsetHeight(); 
		}
		int sourcelistLeft = sourcelistRectangle.getLeft() + (sourcelistRectangle.getWidth() - totalWidth)/2;
		int sourcelistTop = sourcelistRectangle.getTop() + (sourcelistRectangle.getHeight() - totalHeight)/2;
		
		int currentLeft = 0;
		for (DragElement de : elements){
			DragOrigin dro = new DragOrigin(sourcelistLeft + currentLeft, sourcelistTop, de.getOffsetWidth(), de.getOffsetHeight());
			origins.add(dro);
			currentLeft += dro.getWidth() + GAP;
		}
		
	}
	
	public void registerSlots(Vector<DragSlot> slots){
		this.slots = slots;
	}
	
	public void placeElements(){
		for (int i = 0 ; i < elements.size() ; i ++){
			if (elementsLocations.get(i) == -1) {
				setWidgetPosition(elements.get(i), origins.get(i).getLeft(), origins.get(i).getTop());
			} else if (elementsLocations.get(i) >= 0  &&  elementsLocations.get(i) < slots.size()){
				setWidgetPosition(elements.get(i), slots.get(elementsLocations.get(i)).getAbsoluteLeft() - getAbsoluteLeft(), slots.get(elementsLocations.get(i)).getAbsoluteTop() - getAbsoluteTop());
			}
		}
		if (elements.size() > 0){
			if (elementsLocations.get(0) == -1) {
				marginLeft = getWidgetLeft(elements.get(0)) - origins.get(0).getLeft();
				marginTop = getWidgetTop(elements.get(0)) - origins.get(0).getTop();
			} else {
				marginLeft = getWidgetLeft(elements.get(0)) - (slots.get(elementsLocations.get(0)).getAbsoluteLeft() - getAbsoluteLeft());
				marginTop = getWidgetTop(elements.get(0)) - (slots.get(elementsLocations.get(0)).getAbsoluteTop() - getAbsoluteTop());
			}
		}
	}
	
	public void findDestination(){
		boolean found = false;
		for (DragSlot ds : slots){
			if (elementsLocations.indexOf(slots.indexOf(ds)) != -1  &&  elementsLocations.indexOf(currDragElementIndex) != slots.indexOf(ds))
				continue;
			int left = ds.getAbsoluteLeft();
			int top = ds.getAbsoluteTop();
			int width = ds.getOffsetWidth();
			int height = ds.getOffsetHeight();
			if (new Rectangle(left, top, width, height).contains(prevMouseX, prevMouseY)){
				elementsLocations.set(currDragElementIndex, slots.indexOf(ds));
				found = true;
				break;
			}
		}
		
		if (!found)
			elementsLocations.set(currDragElementIndex, -1);
	}
	
	int prevMouseX;
	int prevMouseY;
	boolean isDrag;
	int currDragElementIndex;
	
	public void startDrag(DragElement de, int mouseX, int mouseY){
		prevMouseX = mouseX;
		prevMouseY = mouseY;
		isDrag = true;
		currDragElementIndex = elements.indexOf(de);
		
		int left = getWidgetLeft(de);
		int top = getWidgetTop(de);
		remove(de);
		insert(de, getWidgetCount());
		setWidgetPosition(de, left - marginLeft, top - marginTop);
	}
	
	public void processDrag(int mouseX, int mouseY){

		if (!isDrag)
			return;
		
		Widget currentWidget = elements.get(currDragElementIndex);
		int prevWidgetX = getWidgetLeft(currentWidget);
		int prevWidgetY = getWidgetTop(currentWidget);

		int newWidgetX = prevWidgetX+(mouseX-prevMouseX);
		int newWidgetY = prevWidgetY+(mouseY-prevMouseY);

		setWidgetPosition(currentWidget, newWidgetX - marginLeft, newWidgetY - marginTop);
		
		prevMouseX = mouseX;
		prevMouseY = mouseY;
	}
	
	public void stopDrag(){
		if (isDrag){
			isDrag = false;
			findDestination();
			placeElements();
		}
	}
	
	public Vector<Integer> getElementsLocations(){
		return elementsLocations;
	}
}
