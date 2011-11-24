package eu.ydp.empiria.player.client.module.order.dndcomponent;

import java.util.Vector;

import eu.ydp.empiria.player.client.components.Rectangle;

public class DragSlotManager {

	public DragSlotManager(){

		layout = DragSlotLayout.HORIZONTAL;
		slots = new Vector<Rectangle>(0);
		elements = new Vector<DragElement>(0);
		
		marginX = 8;
		marginY = 8;
	}

	private DragSlotLayout layout;
	
	/* slots ordered by the slot number */
	private Vector<Rectangle> slots;
	/* Drag Elements ordered by the slot number. */
	private Vector<DragElement> elements;

	private int marginX;
	private int marginY;
	
	public boolean addDragElement(DragElement element){
		elements.add(element);
		addSlot(elements.size()-1);
		boolean result = verify();
		return result;
	}
	
	private Rectangle addSlot(int forElementIndex){
		Rectangle slot = findNextSlot(forElementIndex);
		slots.add(slot);
		return slot;
	}

	
	public int getCount(){
		return elements.size();
	}

	public Rectangle getSlot(int index){
		return slots.get(index);
	}

	public DragElement getDragElement(int index){
		return elements.get(index);
	}
	
	public void setSlotLayout(DragSlotLayout dsl){
		layout = dsl;
	}

	public void switchDragElements(int fromSlot, int toSlot){
		DragElement tmpElement = elements.get(fromSlot);
		elements.set(fromSlot, elements.get(toSlot));
		elements.set(toSlot, tmpElement);
	}
	
	public DragElement getDragElementByElementIndex(int elementIndex){
		if (elements.size() == 0)
			return null;
		
		for (DragElement currElement: elements){
			if (currElement.getElementIndex() == elementIndex)
				return currElement;
		}
		
		return null;
	}
	
	public int getSlotIndexByElementIndex(int elementIndex){
		if (elements.size() == 0)
			return 0;
		
		for (int i = 0 ; i < elements.size() ; i ++){
			if (elements.get(i).getElementIndex() == elementIndex)
				return i;
		}
		
		return 0;
	}
	
	public boolean updateSlotsForFloatingSlot(Rectangle floating, int slotIndex){
		if (slots.size() == 0)
			return false;
		
		int newSlotIndex = slotIndex;
		
		for (int i = 0 ; i < slots.size() ; i ++){
			
			int floatingBorder;
			int baseBorder = (layout == DragSlotLayout.HORIZONTAL) ? slots.get(i).getMiddleHorizontal() : slots.get(i).getMiddleVertical();
			
			if (i < slotIndex){
				floatingBorder = (layout == DragSlotLayout.HORIZONTAL) ? floating.getLeft() : floating.getTop();
				
				if (floatingBorder < baseBorder){
					newSlotIndex = i;
					break;
				}
									
			} else if (i > slotIndex){
				floatingBorder = (layout == DragSlotLayout.HORIZONTAL) ? floating.getRight() : floating.getBottom();

				if (floatingBorder > baseBorder){
					newSlotIndex = i;
					break;
				}
			}
			
		}
		
		if (slotIndex != newSlotIndex){
			
			int step = (slotIndex < newSlotIndex)? 1 : -1;			
			for (int s = slotIndex ; s != newSlotIndex ; s += step ){
				switchDragElements(s, s + step);
			}
			
			organizeSlots();
			
			return true;
		}
			
		return false;
	}
	
	protected Rectangle findNextSlot(int slotIndex){
		int left = marginX;
		int top = marginY;
		int width = 0;
		int height = 0;
		
		if (elements.size() > 0){
			
			int currElementIndex = slotIndex;
			if (currElementIndex > elements.size()-1)
				currElementIndex = elements.size()-1;
			
			DragElement currElement = elements.get(currElementIndex);
			width = currElement.getWidget().getOffsetWidth();
			height =  currElement.getWidget().getOffsetHeight();

			int prevSlotIndex = slotIndex-1;
			if (prevSlotIndex > slots.size()-1)
				prevSlotIndex = slots.size()-1;
			
			if (prevSlotIndex >= 0 && prevSlotIndex < slots.size()){
				Rectangle prevSlot = slots.get(prevSlotIndex);
				
				left = prevSlot.getLeft();
				top = prevSlot.getTop();
				if (layout == DragSlotLayout.HORIZONTAL){
					left += prevSlot.getWidth() + marginX;
				} else if (layout == DragSlotLayout.VERTICAL){
					top += prevSlot.getHeight() + marginY;
				}
			}
		}
		
		return new Rectangle(left, top, width, height);
	}
	
	private boolean verify(){
		if (slots.size() == elements.size())
			return true;
		
		if (slots.size() > elements.size()){
			int overflow = slots.size() - elements.size();
			while (overflow > 0){
				slots.remove(slots.size()-1);
				overflow--;
			}
		}
		
		if (elements.size() > slots.size()){
			int overflow = elements.size() - slots.size();
			while (overflow > 0){
				slots.remove(elements.size()-1);
				overflow--;
			}
		}
		
		return false;
			
	}

	public void organizeSlots(){
		for (int i = 0 ; i < slots.size() && i < elements.size() ; i ++){
			Rectangle nextSlot = findNextSlot(i);
			slots.set(i, nextSlot);
		}
	}
	
	
}
