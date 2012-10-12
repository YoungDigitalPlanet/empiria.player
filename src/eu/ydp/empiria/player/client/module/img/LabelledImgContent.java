package eu.ydp.empiria.player.client.module.img;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_IMG_LABEL_LINE_COLOR;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_IMG_LABEL_LINE_THICKNESS;
import static eu.ydp.gwtutil.client.xml.XMLUtils.getAttributeAsDouble;
import static eu.ydp.gwtutil.client.xml.XMLUtils.getAttributeAsInt;
import static eu.ydp.gwtutil.client.xml.XMLUtils.getAttributeAsString;
import static eu.ydp.gwtutil.client.xml.XMLUtils.getFirstElementWithTagName;
import static eu.ydp.gwtutil.client.xml.XMLUtils.getText;

import java.util.Map;

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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.canvasadapter.client.CanvasAdapter;
import eu.ydp.canvasadapter.client.Context2dAdapter;
import eu.ydp.empiria.player.client.components.CanvasArrow;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.xml.XMLUtils;
public class LabelledImgContent extends Composite implements ImgContent {//NOPMD

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
	protected AbsolutePanel mainPanel;
	@UiField
	protected AbsolutePanel textPanel;
	@UiField
	protected Image image;
	@UiField
	protected CanvasAdapter canvas;

	private final StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();

	private Map<String, String> styles;

	@Override
	public void init(Element element, ModuleSocket modulesocket) {
		fillCanvas(element, modulesocket);
	}

	/**
	 * Tworzy obiekt html5/canvas
	 *
	 * @param element
	 * @param moduleSocket
	 */
	private void fillCanvas(final Element element, final ModuleSocket moduleSocket) {
		styles = moduleSocket.getStyles(element);
		Element titleNodes = XMLUtils.getFirstElementWithTagName(element, "title");
		 final String title  =XMLUtils.getTextFromChilds(titleNodes);
		 image.addLoadHandler(new LoadHandler() {

			@Override
			public void onLoad(LoadEvent event) {
				ImageElement imgelement = ImageElement.as(image.getElement());
				canvas.setWidth(image.getWidth() + "px");
				canvas.setCoordinateSpaceWidth(image.getWidth());
				canvas.setHeight(image.getHeight() + "px");
				canvas.setCoordinateSpaceHeight(image.getHeight());
				canvas.setTitle(title);
				mainPanel.setWidth(image.getWidth() + "px");
				mainPanel.setHeight(image.getHeight() + "px");
				textPanel.setWidth(image.getWidth() + "px");
				textPanel.setHeight(image.getHeight() + "px");
				// imgelement.
				Context2dAdapter context2d = canvas.getContext2d();
				setContextStyle(context2d);
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
						textPanel.add(parseText(text, anchor, context2d, moduleSocket));
					}
				}
			}
		});
		image.setUrl(element.getAttribute("src"));
	}

	/**
	 * rysuje linie
	 *
	 * @param line
	 * @param context2d
	 */
	private void parseLine(Element line, Context2dAdapter context2d) {//NOPMD
		NodeList elements = line.getChildNodes();
		String endPoint = null;
		String startPoint = null;
		boolean isStartSet = false;
		double lastX = 0, lastY = 0, startX = 0, startY = 0;
		double startBegX = 0, startBegY = 0, startEndX = 0, startEndY = 0;

		for (int x = 0; x < elements.getLength(); ++x) {
			Node node = elements.item(x);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if (!"".equals(getAttributeAsString(element, "x"))) {
					startX = lastX;
					startY = lastY;
					lastX = getAttributeAsDouble(element, "x");
					lastY = getAttributeAsDouble(element, "y");

					if (!isStartSet && startPoint != null) {
						startBegX = lastX;
						startBegY = lastY;
						isStartSet = true;
					}
				}
				if (element.getNodeName().equals("startPoint")) {
					context2d.moveTo(lastX, lastY);
					startPoint = getAttributeAsString(element, "type");
					startEndX = lastX;
					startEndY = lastY;
				} else if (element.getNodeName().equals("endPoint")) {
					endPoint = getText(element);
				} else if (element.getNodeName().equals("lineTo")) {
					context2d.lineTo(lastX, lastY);
					context2d.stroke();
				} else if (element.getNodeName().equals("lineStyle")) {
					if (!"".equals(getAttributeAsString(element, "alpha"))) {
						context2d.setGlobalAlpha(getAttributeAsInt(element, "alpha"));
					}
					// hex itd
					String color = getAttributeAsString(element, "color");
					if (color != null) {
						context2d.setStrokeStyle(color);
					}
					// liczba
					if (!"".equals(getAttributeAsString(element, "width"))) {
						getAttributeAsInt(element, "width");
					}
				}
			}
		}
		if (endPoint != null) {
			drawShape(endPoint, context2d, lastX, lastY, startX, startY);
		}

		if (startPoint != null) {
			drawShape(startPoint, context2d, startEndX, startEndY, startBegX, startBegY);
		}
	}

	/**
	 * Parsuje element text i generuje odpowiednie widgety
	 *
	 * @param text
	 * @param anchor
	 * @param context2d
	 * @param moduleSocket
	 * @param mainPanel
	 */
	private Panel parseText(Element text, Element anchor, Context2dAdapter context2d, ModuleSocket moduleSocket) {
		Panel panel = new FlowPanel();
		panel.setStyleName(styleNames.QP_IMG_LABELLED_TEXT_PANEL());
		Widget widget = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(text);
		if (widget != null) {
			panel.add(widget);
		}

		alignWidget(panel, anchor);
		return panel;
	}

	/**
	 * Rysuje zakonczenie linii
	 *
	 * @param shapeName
	 * @param context
	 * @param centerX
	 * @param centerY
	 * @param startX
	 * @param startY
	 */
	private void drawShape(String shapeName, Context2dAdapter context, double centerX, double centerY, double startX, double startY) {
		if ("dot".equals(shapeName)) {
			context.beginPath();
			context.arc(centerX, centerY, 3, 0, 2 * Math.PI, false);
			context.closePath();
			context.fill();
		} else if ("arrow".equals(shapeName)) {
			CanvasArrow canvas = new CanvasArrow(context, startX, startY, centerX, centerY);
			canvas.draw();
		}
	}

	/**
	 * ustawia style dla context
	 *
	 * @param context2d
	 * @param ms
	 */
	private void setContextStyle(Context2dAdapter context2d) {
		if (styles.containsKey(EMPIRIA_IMG_LABEL_LINE_COLOR)) {
			try {
				context2d.setStrokeStyle(CssColor.make(styles.get(EMPIRIA_IMG_LABEL_LINE_COLOR)).value());
			} catch (Exception e) {
				e.fillInStackTrace();
			}
		}
		if (styles.containsKey(EMPIRIA_IMG_LABEL_LINE_THICKNESS)) {
			try {
				context2d.setLineWidth(Double.valueOf(styles.get(EMPIRIA_IMG_LABEL_LINE_THICKNESS).replaceAll("\\D", "")));
			} catch (Exception e) {
			}
		}
	}

	private void alignWidget(Widget widget, Element anchorElement) {
		String horizontalAlign = getAttributeAsString(getFirstElementWithTagName(anchorElement, "x_anchor"), "anchor");
		String verticalAlign = getAttributeAsString(getFirstElementWithTagName(anchorElement, "y_anchor"), "anchor");
		Point anchorPoint = new Point(getAttributeAsDouble(anchorElement, "x"), getAttributeAsDouble(anchorElement, "y"));

		alignWidget(widget, anchorPoint, horizontalAlign, verticalAlign);
	}

	private void alignWidget(Widget widget, Point anchorPoint, String horizontalAlign, String verticalAlign) {
		Style style = widget.getElement().getStyle();
		double xPos = anchorPoint.getX();
		double yPos = anchorPoint.getY();

		if ("center".equals(horizontalAlign)) {
			xPos -= widget.getOffsetWidth() / 2;
		} else if ("right".equals(horizontalAlign)) {
			xPos -= widget.getOffsetWidth();
		}

		if ("center".equals(verticalAlign)) {
			yPos -= widget.getOffsetHeight() / 2;
		} else if ("bottom".equals(verticalAlign)) {
			yPos -= widget.getOffsetHeight();
		}

		style.setPosition(Position.ABSOLUTE);
		style.setTop(yPos, Unit.PX);
		style.setLeft(xPos, Unit.PX);
	}

}
