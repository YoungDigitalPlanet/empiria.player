package eu.ydp.empiria.player.client.module.colorfill.structure;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;

public class ColorfillInteractionModuleJAXBParserFactoryTest extends AbstractEmpiriaPlayerGWTTestCase {
	
	private static final String COLOR_FF0000 = "#FF0000";
	private static final String COLOR_008000 = "#008000";
	private static final String VAL_999 = "999";
	private static final String VAL_777 = "777";
	private static final String VAL_666 = "666";
	private static final String VAL_333 = "333";
	private static final String MEDIA_COLORFILL_PNG = "media/colorfill.png";
	private static final int VAL_400 = 400;
	private static final int VAL_600 = 600;

	public void testXMLParseButtons() {
		// given
		String buttonsXMLNode = "<buttons>" +
				"<button rgb=\""+COLOR_008000+"\"/>" +
				"<button rgb=\"#FFFF00\"/>" +
				"<button rgb=\"#0000FF\"/>" +
				"<button rgb=\"#800000\"/>" +
				"<button rgb=\""+COLOR_FF0000+"\"/>" +
				"<eraserButton/>" +
			"</buttons>";		
		String xml = prepareXML(buttonsXMLNode);
		
		// when
		ButtonsContainer buttonsContainer = getButtonsContainer(xml);
		List<ColorButton> buttons = buttonsContainer.getButtons();
		ColorButton button0 = buttons.get(0);
		ColorButton button4 = buttons.get(4);
		EraserButton eraser = buttonsContainer.getEraserButton();
				
		// then
		assertEquals(COLOR_008000, button0.getRgb());
		assertEquals(COLOR_FF0000, button4.getRgb());
		assertNotNull(eraser);
	}
	
	public void testXMLParseButtonEraser() {
		// given
		String buttonsXMLNode = "<buttons>" +
				"<button rgb=\"#008000\"/>" +
			"</buttons>";		
		String xml = prepareXML(buttonsXMLNode);
		
		// when
		ButtonsContainer buttonsContainer = getButtonsContainer(xml);
		List<ColorButton> buttons = buttonsContainer.getButtons();
		ColorButton button0 = buttons.get(0);
		EraserButton eraser = buttonsContainer.getEraserButton();
				
		// then
		assertEquals(COLOR_008000, button0.getRgb());
		assertNull(eraser);
	}	
	
	public void testXMLParseAreas() {
		// given		
		String xml = prepareXML("<buttons></buttons>");
		
		// when
		ColorfillInteractionBean bean = parse(xml);
		AreaContainer areaSet = bean.getAreas();
		// TODO: getAreas().getAreas() !!! to be changed! ie. getAreaSet().getAreas()
		List<Area> areas = areaSet.getAreas();
		Area area0 = areas.get(0);
		Area area1 = areas.get(1);
				
		// then
		assertEquals(VAL_333, area0.getX().toString());
		assertEquals(VAL_666, area0.getY().toString());
		assertEquals(VAL_777, area1.getX().toString());
		assertEquals(VAL_999, area1.getY().toString());
	}	

	public void testXMLParseImage() {
		// given		
		String xml = prepareXML("<buttons></buttons>");
		
		// when
		ColorfillInteractionBean bean = parse(xml);
		Image image = bean.getImage();
				
		// then
		assertEquals(VAL_400, image.getHeight());
		assertEquals(VAL_600, image.getWidth());
		assertEquals(MEDIA_COLORFILL_PNG, image.getSrc());
	}		
	
	private ButtonsContainer getButtonsContainer(String xml) {
		ColorfillInteractionBean bean = parse(xml);
		// TODO: getButtons().getButtons() !!! to be changed! ie. getButtonSet().getButtons()
		ButtonsContainer buttonsContainer = bean.getButtons();
		return buttonsContainer;
	}
	
	private ColorfillInteractionBean parse(String xml) {
		ColorfillInteractionModuleJAXBParserFactory jaxbParserFactory = GWT.create(ColorfillInteractionModuleJAXBParserFactory.class);
		JAXBParser<ColorfillInteractionBean> jaxbParser = jaxbParserFactory.create();
		ColorfillInteractionBean expressionsBean = jaxbParser.parse(xml);
		return expressionsBean;
	}	
	
	private String prepareXML(String buttons) {
		return 
				"<colorfillInteraction id=\"dummy1\">" +
					buttons + 
					"<areas>" +
						"<area x=\""+VAL_333+"\" y=\""+VAL_666+"\"/>" +
						"<area x=\""+VAL_777+"\" y=\""+VAL_999+"\"/>" +
					"</areas>" +
					"<image height=\""+VAL_400+"\" src=\"media/colorfill.png\" width=\""+VAL_600+"\"/>" +
				"</colorfillInteraction>"; 
	}

}
