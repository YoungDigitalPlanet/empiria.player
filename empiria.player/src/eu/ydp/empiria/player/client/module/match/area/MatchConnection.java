package eu.ydp.empiria.player.client.module.match.area;

import org.vaadin.gwtgraphics.client.Line;

public class MatchConnection {

	public MatchConnection(MatchElement _from, MatchElement _to, String _lineId){
		from = _from.identifier;
		to = _to.identifier;
		
		line = new Line(_from.getSlotAnchorX(), _from.getSlotAnchorY(),
				_to.getSlotAnchorX(), _to.getSlotAnchorY());
		lineId = _lineId;
		line.getElement().setId(_lineId);
		line.setStyleName("qp-match-area-line");
		
	}
	
	public String from;
	public String to;
	
	public Line line;
	public String lineId;
	
	boolean compare(MatchElement _from, MatchElement _to){
		return (from.compareTo(_from.identifier) == 0  &&  to.compareTo(_to.identifier) == 0 );
	}

	public void markCorrect(){
		line.setStyleName("qp-match-area-line-correct");
	}

	public void markWrong(){
		line.setStyleName("qp-match-area-line-wrong");
	}

	public void unmark(){
		line.setStyleName("qp-match-area-line");
	}
	
	public String getAsDirectedPair(){
		return from + " " + to;
	}
}
