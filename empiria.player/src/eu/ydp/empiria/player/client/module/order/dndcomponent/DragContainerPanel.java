package eu.ydp.empiria.player.client.module.order.dndcomponent;

import java.util.Vector;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.components.Rectangle;

public class DragContainerPanel extends AbsolutePanel {

	public DragContainerPanel(){
		super();
		isDragging = false;
		slotManager = new DragSlotManager();
		mode = DragMode.FREE;
	}
	
	
	private DragMode mode;
	
	private boolean isDragging;
	private DragElement currDraggedElement;
	private int startMouseX;
	private int startMouseY;
	private int prevMouseX;
	private int prevMouseY;
	private int marginLeft;
	private int marginTop;

	
	//private Vector<Widget> widgets;
	private DragSlotManager slotManager;
	
	public void add(Widget w){
		super.insert(w, 0, 0, getWidgetCount());
		DragElement currDragElement = new DragElement(w, slotManager.getCount());
		slotManager.addDragElement(currDragElement);
		Rectangle currSlot = slotManager.getSlot(slotManager.getCount()-1);
		super.setWidgetPosition(w, currSlot.getLeft(), currSlot.getTop());
		marginLeft = getWidgetLeft(w) - currSlot.getLeft();
		marginTop = getWidgetTop(w) - currSlot.getTop();
		
	}

	public void add(Widget child, Element container){
		add(child);
	}
	
	public void add(Widget w, int left, int top){
		add(w);
	}
	
	public void removeAll(){
		int wc = getWidgetCount();
		for (int w = 0 ; w < wc ; w ++)
			remove(getWidget(getWidgetCount()-1));

		slotManager = new DragSlotManager();
		
	}
	
	public void setAutoSize(){
		if (slotManager.getCount()==0) {
			return;
		}
		int pw = getOffsetWidth();
		int ph = getOffsetHeight();
		int lastSlotRight = slotManager.getSlot(slotManager.getCount()-1).getRight();
		int lastSlotBottom = slotManager.getSlot(slotManager.getCount()-1).getBottom();
		if (pw < lastSlotRight ||  ph < lastSlotBottom){
			setSize( String.valueOf( Math.max(pw,lastSlotRight) ), String.valueOf( Math.max(ph,lastSlotBottom)) );
		}
	}
	
	public void setDragMode(DragMode dsm){
		if (!isDragging){
			mode = dsm;
			slotManager.setSlotLayout(DragSlotLayout.fromDragServiceMode(dsm));
		}
	}

	private void organizeWidgets(){
		for (int i = 0 ; i < slotManager.getCount() ; i ++){
			
			DragElement currElement = slotManager.getDragElement(i);
			Rectangle currSlot = slotManager.getSlot(i);
			
			if (isDragging && currElement.equals(currDraggedElement))
				continue;
			
			setWidgetPosition(currElement.getWidget(), currSlot.getLeft(), currSlot.getTop());
				
		}
	}
	
	public void startDrag(int elementIndex, int mouseX, int mouseY){
		if (elementIndex < slotManager.getCount() &&  mode != DragMode.NONE){
			currDraggedElement = slotManager.getDragElementByElementIndex(elementIndex);
			startMouseX = mouseX;
			startMouseY = mouseY;
			prevMouseX = startMouseX;
			prevMouseY = startMouseY;
			isDragging = true;
			
			// move current widget to front
			
			int left = getWidgetLeft(currDraggedElement.getWidget());
			int top = getWidgetTop(currDraggedElement.getWidget());
			remove(currDraggedElement.getWidget());
			insert(currDraggedElement.getWidget(), getWidgetCount());
			setWidgetPosition(currDraggedElement.getWidget(), left-marginLeft, top-marginTop);
		}
	}
	
	public void stopDrag(){
		if (isDragging = true){
			isDragging = false;
			if (mode != DragMode.FREE)
				organizeWidgets();
		}
	}
	
	public void drag(int mouseX, int mouseY){
		if (isDragging){
			Widget currentWidget = currDraggedElement.getWidget();
			int prevWidgetX = getWidgetLeft(currentWidget);
			int prevWidgetY = getWidgetTop(currentWidget);
			
			if (mode == DragMode.FREE){
				
				int newWidgetX = prevWidgetX+(mouseX-prevMouseX);
				int newWidgetY = prevWidgetY+(mouseY-prevMouseY);
				
				setWidgetPosition(currentWidget, newWidgetX-marginLeft, newWidgetY-marginTop);
				
				@SuppressWarnings("unused")
				int currWidgetX = getWidgetLeft(currentWidget);
				@SuppressWarnings("unused")
				int currWidgetY = getWidgetTop(currentWidget);
				
			} else if (mode == DragMode.HORIZONTAL  ||  mode == DragMode.VERTICAL){
				
				int newWidgetX = prevWidgetX+(mouseX-prevMouseX);
				int newWidgetY = prevWidgetY+(mouseY-prevMouseY);
				
				setWidgetPosition(currentWidget, newWidgetX-marginLeft, newWidgetY-marginTop);
				
				int currWidgetX = getWidgetLeft(currentWidget);
				int currWidgetY = getWidgetTop(currentWidget);
				
				Rectangle floatingSlot = new Rectangle(currWidgetX, currWidgetY, currentWidget.getOffsetWidth(), currentWidget.getOffsetHeight());
				
				int currSlotIndex = slotManager.getSlotIndexByElementIndex(currDraggedElement.getElementIndex());
				
				boolean updateResult = slotManager.updateSlotsForFloatingSlot(floatingSlot,  currSlotIndex);
				
				if (updateResult)
					organizeWidgets();
				
			}
			
			prevMouseX = mouseX;
			prevMouseY = mouseY;
		}
	}
	
	public Vector<Integer> getElementsOrder(){
		Vector<Integer> v = new Vector<Integer>();
		
		int currElementIndex;
		
		for (int i = 0 ; i < slotManager.getCount() ; i ++){
			currElementIndex = slotManager.getDragElement(i).getElementIndex();
			v.add(currElementIndex);
		}
		
		return v;
	}
	
	public void setElementsOrder(Vector<Integer> v){
		
		for (int i = 0 ; i < v.size() ; i ++){
			int currElementFromSlot = slotManager.getSlotIndexByElementIndex(v.get(i));
			int currElementToSlot = i;
			if (currElementFromSlot != currElementToSlot)
				slotManager.switchDragElements(currElementFromSlot, currElementToSlot);
		}
		slotManager.organizeSlots();
		organizeWidgets();
	}
	
	
}
