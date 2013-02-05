package eu.ydp.empiria.player.client.module.connection.presenter;

import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.google.common.collect.Sets;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.connection.exception.CssStyleException;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.style.CssHelper;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class ConnectionStyleChacker {

	@Inject
	private CssHelper cssHelper;

	@Inject
	private StyleNameConstants styleNames;

	protected Set<String> cssClassNames = Sets.newHashSet();

	private final XMLParser xmlParser;

	private final StyleSocket styleSocket;


	@Inject
	public ConnectionStyleChacker(@Assisted StyleSocket styleSocket,XMLParser xmlParser) {
		this.xmlParser = xmlParser;
		this.styleSocket = styleSocket;
	}


	@PostConstruct
	public void postConstruct() {
		styleNames.QP_CONNECTION();
		cssClassNames.add(styleNames.QP_CONNECTION_CENTER_COLUMN());
		cssClassNames.add(styleNames.QP_CONNECTION_CORRECT());
		cssClassNames.add(styleNames.QP_CONNECTION_DISABLED());
		cssClassNames.add(styleNames.QP_CONNECTION_ITEM());
		cssClassNames.add(styleNames.QP_CONNECTION_ITEM_CONECTED());
		cssClassNames.add(styleNames.QP_CONNECTION_ITEM_CONECTED_DISABLED());
		cssClassNames.add(styleNames.QP_CONNECTION_ITEM_CONTENT());
		cssClassNames.add(styleNames.QP_CONNECTION_ITEM_SELECTED());
		cssClassNames.add(styleNames.QP_CONNECTION_ITEM_SELECTION_BUTTON());
		cssClassNames.add(styleNames.QP_CONNECTION_LEFT_COLUMN());
		cssClassNames.add(styleNames.QP_CONNECTION_NONE());
		cssClassNames.add(styleNames.QP_CONNECTION_NORMAL());
		cssClassNames.add(styleNames.QP_CONNECTION_RIGHT_COLUMN());
		cssClassNames.add(styleNames.QP_CONNECTION_WRONG());
	}

	@SuppressWarnings("PMD")
	public void areStylesCorrectThrowsExceptionWhenNot() {
		for(String className : cssClassNames){
			StringBuilder xml = new StringBuilder("<root><").append(className).append(" class=\"").append(className).append("\"/></root>");
			Map<String, String> stylesForModule = getStylesForModule(styleSocket, (Element) xmlParser.parse(xml.toString()).getDocumentElement().getFirstChild());
			if(cssHelper.checkIfEquals(stylesForModule, "display", "table-cell")){
				throw new CssStyleException("Css with display:table-cell is not supported in ConnectionModule");
			}
		}
	}

	private Map<String, String> getStylesForModule(StyleSocket styleSocket, Element moduleXml) {
		return styleSocket.getStyles(moduleXml);
	}
}
