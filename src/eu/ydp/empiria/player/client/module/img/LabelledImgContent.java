package eu.ydp.empiria.player.client.module.img;

import static eu.ydp.empiria.player.client.util.XMLUtils.getAttributeAsDouble;
import static eu.ydp.empiria.player.client.util.XMLUtils.getAttributeAsInt;
import static eu.ydp.empiria.player.client.util.XMLUtils.getAttributeAsString;
import static eu.ydp.empiria.player.client.util.XMLUtils.getFirstElementWithTagName;
import static eu.ydp.empiria.player.client.util.XMLUtils.getText;

import java.util.Map;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.touch.client.Point;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.canvasadapter.client.CanvasAdapter;
import eu.ydp.canvasadapter.client.Context2dAdapter;
import eu.ydp.empiria.player.client.components.CanvasArrow;
import eu.ydp.empiria.player.client.module.ModuleSocket;

public class LabelledImgContent extends Composite  implements ImgContent{

	private static LabelledImgContentUiBinder uiBinder = GWT.create(LabelledImgContentUiBinder.class);

	interface LabelledImgContentUiBinder extends UiBinder<Widget, LabelledImgContent> {
	}

	public LabelledImgContent() {
		initWidget(uiBinder.createAndBindUi(this));
		mainPanel.setWidgetPosition(image, 0, 0);
		mainPanel.setWidgetPosition(canvas.asWidget(), 0, 0);
		mainPanel.setWidgetPosition(textPanel, 0, 0);
	}

	@UiField
	AbsolutePanel mainPanel;
	@UiField
	AbsolutePanel textPanel;
	@UiField
	Image image;
	@UiField
	CanvasAdapter canvas;
	
	private Map<String, String> styles;
	
	
	
	@Override
	public void init(Element element, ModuleSocket ms) {
		fillCanvas(element, ms);
	}

	/**
	 * Tworzy obiekt html5/canvas
	 *
	 * @param element
	 * @param ms
	 */
	private void fillCanvas(final Element element, final ModuleSocket ms) {

		image.setUrl(element.getAttribute("src"));		
		styles = ms.getStyles(element);
		image.addLoadHandler(new LoadHandler() {

			@Override
			public void onLoad(LoadEvent event) {
				ImageElement imgelement = ImageElement.as(image.getElement());
				canvas.setWidth(image.getWidth() + "px");
				canvas.setCoordinateSpaceWidth(image.getWidth());
				canvas.setHeight(image.getHeight() + "px");
				canvas.setCoordinateSpaceHeight(image.getHeight());
				mainPanel.setWidth(image.getWidth() + "px");
				mainPanel.setHeight(image.getHeight() + "px");
				textPanel.setWidth(image.getWidth() + "px");
				textPanel.setHeight(image.getHeight() + "px");
				//imgelement.
				Context2dAdapter context2d = canvas.getContext2d();
				setContextStyle(context2d,ms);
				NodeList labelList = element.getElementsByTagName("label");
				for (int x = 0; x < labelList.getLength(); ++x) {
					Element label = (Element) labelList.item(x);
					// ustawiony czas nie obslugujemy w pictureplayer
					if (getAttributeAsInt(label, "start") != 0) {
						continue;
					}
					Element anchor = getFirstElementWithTagName(label, "anchor");
					Element line = getFirstElementWithTagName(label, "line");
					Element text = getFirstElementWithTagName(label, "text");
					if (line != null) {
						parseLine(line, context2d);
					}
					if (text != null && anchor != null) {
						textPanel.add(parseText(text, anchor, context2d, ms));
					}
				}
			}
		});
	}


	/**
	 * rysuje linie
	 *
	 * @param line
	 * @param context2d
	 */
	private void parseLine(Element line, Context2dAdapter context2d) {
		NodeList elements = line.getChildNodes();
		String endPoint = null;
		String startPoint = null;
		boolean isStartSet = false;
		double lastX = 0, lastY = 0, startX = 0, startY = 0;
		double startBegX = 0, startBegY = 0, startEndX = 0, startEndY = 0;
		
		for (int x = 0; x < elements.getLength(); ++x) {
			Node node = elements.item(x);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) node;
				if (!"".equals(getAttributeAsString(e, "x"))) {
					startX = lastX;
					startY = lastY;
					lastX = getAttributeAsDouble(e, "x");
					lastY = getAttributeAsDouble(e, "y");
					
					if(!isStartSet && startPoint != null){
						startBegX = lastX;
						startBegY = lastY;
						isStartSet = true;
					}
				}
				if (e.getNodeName().equals("startPoint")) {
					context2d.moveTo(lastX, lastY);
					startPoint = getAttributeAsString(e, "type");
					startEndX = lastX;
					startEndY = lastY;
				} else if (e.getNodeName().equals("endPoint")) {
					endPoint = getText(e);
				} else if (e.getNodeName().equals("lineTo")) {
					context2d.lineTo(lastX, lastY);
					context2d.stroke();
				} else if (e.getNodeName().equals("lineStyle")) {
					if (!"".equals(getAttributeAsString(e, "alpha"))) {
						context2d.setGlobalAlpha(getAttributeAsInt(e, "alpha"));
					}
					// hex itd
					String color = getAttributeAsString(e, "color");
					if (color != null) {
						context2d.setStrokeStyle(color);
					}
					// liczba
					if (!"".equals(getAttributeAsString(e, "width"))) {
						getAttributeAsInt(e, "width");
					}
				}
			}
		}
		if (endPoint != null){
			drawShape(endPoint, context2d, lastX, lastY, startX, startY);
		}
		
		if(startPoint != null){
			drawShape(startPoint, context2d, startEndX, startEndY, startBegX, startBegY);
		}
	}

	/**
	 * Parsuje element text i generuje odpowiednie widgety
	 * @param text
	 * @param anchor
	 * @param context2d
	 * @param ms
	 * @param mainPanel
	 */
	private Panel parseText(Element text, Element anchor, Context2dAdapter context2d, ModuleSocket ms) {
		Panel panel = new FlowPanel();
		panel.setStyleName("qp-img-labelled-text-panel");
		Widget widget = ms.getInlineBodyGeneratorSocket().generateInlineBody(text);
		if (widget != null) {
			panel.add(widget);
		}
		
		alignWidget(panel, anchor);
		return panel;
	}

	/**
	 * Rysuje zakonczenie linii
	 * @param shapeName
	 * @param context
	 * @param centerX
	 * @param centerY
	 * @param startX
	 * @param startY
	 */
	private void drawShape(String shapeName, Context2dAdapter context, double centerX, double centerY, double startX, double startY) {
		if (shapeName.equals("dot")) {
			context.beginPath();
			context.arc(centerX, centerY, 3, 0, 2 * Math.PI, false);
			context.closePath();
			context.fill();
		} else if (shapeName.equals("arrow")) {
			CanvasArrow ca = new CanvasArrow(context, startX, startY, centerX, centerY);
			ca.draw();
		}
	}

	/**
	 * ustawia style dla context
	 * @param context2d
	 * @param ms
	 */
	private void setContextStyle(Context2dAdapter context2d, ModuleSocket ms){
		if(styles.containsKey("-empiria-img-line-color")){
			try{
				context2d.setStrokeStyle(CssColor.make(styles.get("-empiria-img-line-color")).value());
			}catch(Exception e){
				e.fillInStackTrace();
			}
		}
		if(styles.containsKey("line-width")){
			try{
				context2d.setLineWidth(Double.valueOf(styles.get("line-width").replaceAll("\\D", "")));
			}catch(Exception e){}
		}
	}
	
	private void alignWidget(Widget widget, Element anchorElement){
		String horizontalAlign = getAttributeAsString(
				getFirstElementWithTagName(anchorElement, "x_anchor"), "anchor");
		String verticalAlign = getAttributeAsString(
						getFirstElementWithTagName(anchorElement, "y_anchor"), "anchor");
		Point anchorPoint = new Point(
						getAttributeAsDouble(anchorElement, "x"), 
						getAttributeAsDouble(anchorElement, "y"));
		
		alignWidget(widget, anchorPoint, horizontalAlign, verticalAlign);
	}
	
	private void alignWidget(Widget widget, Point anchorPoint, String horizontalAlign, String verticalAlign){
		Style style = widget.getElement().getStyle();
		double xPos = anchorPoint.getX();
		double yPos = anchorPoint.getY();
		
		if(horizontalAlign.equals("center")){
			xPos -= widget.getOffsetWidth()/2;
		}else if(horizontalAlign.equals("right")){
			xPos -= widget.getOffsetWidth();
		}
		
		if(verticalAlign.equals("center")){
			yPos -= widget.getOffsetHeight()/2;
		}else if(verticalAlign.equals("bottom")){
			yPos -= widget.getOffsetHeight();
		}
		
		style.setPosition(Position.ABSOLUTE);
		style.setTop(yPos, Unit.PX);
		style.setLeft(xPos, Unit.PX);
	}

}
