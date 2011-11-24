package eu.ydp.empiria.player.client.module.match.area;

public class MatchDragManager {

	private boolean isDragging;
	private int sourceIndex;
	
	public MatchDragManager(){
		isDragging = false;
	}

	public void startDrag(int index){
		sourceIndex = index;
		isDragging = true;
	}
	
	public void endDrag(){
		isDragging = false;
	}
	
	public boolean isDragging(){
		return isDragging;
	}
	
	public int getSourceIndex(){
		return sourceIndex;
	}
}
