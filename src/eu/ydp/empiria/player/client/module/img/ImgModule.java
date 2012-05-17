package eu.ydp.empiria.player.client.module.img;

import static eu.ydp.empiria.player.client.util.xml.XMLUtils.getAttributeAsInt;
import static eu.ydp.empiria.player.client.util.xml.XMLUtils.getAttributeAsString;
import static eu.ydp.empiria.player.client.util.xml.XMLUtils.getFirstElementWithTagName;
import static eu.ydp.empiria.player.client.util.xml.XMLUtils.getText;

import java.util.Map;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
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

import eu.ydp.empiria.player.client.components.CanvasArrow;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;

/**
 * Klasa odpowiedzialna za renderwoanie elementu img.
 *
 *
 * Parametry css dla canvas dodajemy je dla obiektu img:<br/>
 *  <i>line-width</i>: grubosc linii rysowanej na canvasie: <br/>
 *  <i>line-color</i>: kolor linii rysowanej na canvasie<br/>
 * <br/>
 * <br/>
 */
public class ImgModule extends Composite implements ISimpleModule,Factory<ImgModule> {

	private static IMGModuleUiBinder uiBinder = GWT.create(IMGModuleUiBinder.class);

	interface IMGModuleUiBinder extends UiBinder<Widget, ImgModule> {
	}

	protected Image img;
	protected Canvas imgCanvas = null;
	@UiField
	protected Panel containerPanel;
	@UiField
	protected Panel titlePanel;
	@UiField
	protected Panel descriptionPanel;
	@UiField
	protected Panel contentPanel;

	public ImgModule() {
		initWidget(uiBinder.createAndBindUi(this));
		img = new Image();
	}

	/**
	 * rysuje linie
	 *
	 * @param line
	 * @param context2d
	 */
	private void parseLine(Element line, Context2d context2d) {
		NodeList elements = line.getChildNodes();
		String endPoint = null;
		int lastX = 0, lastY = 0, startX = 0, startY = 0;
		for (int x = 0; x < elements.getLength(); ++x) {
			Node node = elements.item(x);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) node;
				if (!"".equals(getAttributeAsString(e, "x"))) {
					startX = lastX;
					startY = lastY;
					lastX = getAttributeAsInt(e, "x");
					lastY = getAttributeAsInt(e, "y");
				}
				if (e.getNodeName().equals("startPoint")) {
					context2d.moveTo(lastX, lastY);
				} else if (e.getNodeName().equals("endPoint")) {
					endPoint = getText(e);
				} else if (e.getNodeName().equals("lineTo")) {
					context2d.lineTo(lastX, lastY);
					context2d.stroke();
				} else if (e.getNodeName().equals("lineStyle")) {
					if (!"".equals(getAttributeAsString(e, "alpha"))) {
						context2d.setGlobalAlpha(getAttributeAsInt(e, "alpha") * 0.001);
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
		if (endPoint != null) {
			drawShape(endPoint, context2d, lastX, lastY, startX, startY);
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
	private void parseText(Element text, Element anchor, Context2d context2d, ModuleSocket ms, Panel mainPanel) {
		HorizontalPanel panel = new HorizontalPanel();
		Label label = new Label(getText(text));
		label.setStyleName("qp-img");
		panel.add(label);
		NodeList nodes = text.getChildNodes();
		for (int x = 0; x < nodes.getLength(); ++x) {
			Node node = nodes.item(x);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Widget widget = ms.getInlineBodyGeneratorSocket().generateInlineBodyForNode(node);
				widget.addStyleName("qp-img");
				if (widget != null) {
					panel.add(widget);
				}
			}
		}
		Style style = panel.getElement().getStyle();
		style.setPosition(Position.ABSOLUTE);
		style.setTop(getAttributeAsInt(anchor, "x"), Unit.PX);
		style.setLeft(getAttributeAsInt(anchor, "y"), Unit.PX);
		mainPanel.add(panel);
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
	private void drawShape(String shapeName, Context2d context, int centerX, int centerY, int startX, int startY) {
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
	 * Tworzy obiekt html5/canvas
	 *
	 * @param element
	 * @param ms
	 */
	private void fillCanvas(final Element element, final ModuleSocket ms, final Panel mainPanel) {
		String src = element.getAttribute("src");
		if (src != null)
			img.setUrl(src);
		String alt = element.getAttribute("alt");
		if (alt != null)
			img.setAltText(alt);
		String cls = element.getAttribute("class");
		if (cls != null)
			img.addStyleName(cls);
		ms.getStyles(element);
		RootPanel.get().add(img);
		img.setVisible(false);
		img.addLoadHandler(new LoadHandler() {

			@Override
			public void onLoad(LoadEvent event) {
				ImageElement imgelement = ImageElement.as(img.getElement());
				imgCanvas.setWidth(img.getWidth() + "px");
				imgCanvas.setCoordinateSpaceWidth(img.getWidth());
				imgCanvas.setHeight(img.getHeight() + "px");
				imgCanvas.setCoordinateSpaceHeight(img.getHeight());
				//imgelement.
				Context2d context2d = imgCanvas.getContext2d();
				context2d.drawImage(imgelement, 0, 0);
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
						parseText(text, anchor, context2d, ms, mainPanel);
					}
				}
				RootPanel.get().remove(img);
			}
		});
	}

	/**
	 * ustawia style dla context
	 * @param context2d
	 * @param ms
	 */
	private void setContextStyle(Context2d context2d, ModuleSocket ms){
		Map<String, String> styles =  ms.getStyles(XMLParser.createDocument().createElement("img"));
		if(styles.containsKey("line-color")){
			try{
				context2d.setStrokeStyle(CssColor.make(styles.get("line-color")).value());
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

	/**
	 * ustawia dodatkowe parametry dla img pobrane z atrybutow elemntu xml-a
	 * @param element
	 */
	private void fillImage(Element element) {
		String src = element.getAttribute("src");
		if (src != null)
			img.setUrl(src);
		String alt = element.getAttribute("alt");
		if (alt != null)
			img.setAltText(alt);
		String cls = element.getAttribute("class");
		if (cls != null)
			img.addStyleName(cls);
	}

	@Override
	public void initModule(Element element, ModuleSocket ms, ModuleInteractionListener mil) {
		contentPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		if (Canvas.isSupported()) {
			imgCanvas = Canvas.createIfSupported();
			fillCanvas(element, ms, contentPanel);
		} else {
			img.setStyleName("qp-img");
			fillImage(element);
		}

		contentPanel.add(imgCanvas != null ? imgCanvas : img);
		String id = element.getAttribute("id");
		if (id != null && !"".equals(id) && getView() != null) {
			getView().getElement().setId(id);
		}

		NodeList titleNodes = element.getElementsByTagName("title");
		if (titleNodes.getLength() > 0) {
			Widget titleWidget = ms.getInlineBodyGeneratorSocket().generateInlineBody(titleNodes.item(0));
			if (titleWidget != null) {
				titlePanel.add(titleWidget);
			}
		}
		NodeList descriptionNodes = element.getElementsByTagName("description");
		if (descriptionNodes.getLength() > 0) {
			Widget descriptionWidget = ms.getInlineBodyGeneratorSocket().generateInlineBody(descriptionNodes.item(0));
			if (descriptionWidget != null) {
				descriptionPanel.add(descriptionWidget);
			}
		}
	}

	@Override
	public Widget getView() {
		return this;
	}

	@Override
	public ImgModule getNewInstance() {
		return new ImgModule();
	}
}
