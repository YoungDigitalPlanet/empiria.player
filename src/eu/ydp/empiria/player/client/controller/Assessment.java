/*
  The MIT License

  Copyright (c) 2009 Krzysztof Langner

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
 */
package eu.ydp.empiria.player.client.controller;

import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGenerator;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.communication.AssessmentData;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.style.StyleLinkDeclaration;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IGroup;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.InlineContainerStylesExtractor;
import eu.ydp.empiria.player.client.module.InlineFormattingContainerType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ParenthoodSocket;
import eu.ydp.empiria.player.client.module.containers.group.DefaultGroupIdentifier;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.view.assessment.AssessmentBodyView;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.json.js.YJsJsonFactory;

public class Assessment {

	/** Whole assessment title */
	private final String title;

	/** XML DOM of the assessment */
	private final XmlData xmlData;

	private Panel pageSlot;

	public StyleLinkDeclaration styleDeclaration;

	private final ModulesRegistrySocket modulesRegistrySocket;

	private final DisplayContentOptions options;

	private AssessmentBody body;

	private AssessmentBodyView bodyView;

	/**
	 * Properties instance prepared by assessmentController (based on item body
	 * properties through the page controller)
	 */
	private InteractionEventsListener interactionEventsListener;

	/**
	 * C'tor
	 * 
	 * @param data
	 *            XMLData object as data source
	 */
	public Assessment(AssessmentData data, DisplayContentOptions options, InteractionEventsListener interactionEventsListener,
			ModulesRegistrySocket modulesRegistrySocket) {

		this.xmlData = data.getData();

		this.modulesRegistrySocket = modulesRegistrySocket;
		this.options = options;

		Document document = xmlData.getDocument();
		Element rootNode = (Element) document.getElementsByTagName("assessmentTest").item(0);
		Element skinBody = getSkinBody(data.getSkinData());

		if (skinBody == null) {
			skinBody = XMLParser.parse("<itemBody><pageInPage /></itemBody>").getDocumentElement();
		}

		styleDeclaration = new StyleLinkDeclaration(xmlData.getDocument().getElementsByTagName("styleDeclaration"), xmlData.getBaseURL());
		title = rootNode.getAttribute("title");

		initializeBody(skinBody, interactionEventsListener);
	}

	private void initializeBody(Element bodyNode, InteractionEventsListener interactionEventsListener) {
		if (bodyNode != null) {
			body = new AssessmentBody(options, moduleSocket, interactionEventsListener, modulesRegistrySocket);
			bodyView = new AssessmentBodyView(body);
			bodyView.init(body.init(bodyNode));
			pageSlot = body.getPageSlot();
			this.interactionEventsListener = interactionEventsListener;
		}
	}

	public Widget getSkinView() {
		return bodyView;
	}

	public Panel getPageSlot() {
		return pageSlot;
	}

	public void setUp() {
		if (body != null) {
			body.setUp();
		}
	}

	public void start() {
		if (body != null) {
			body.start();
		}
	}

	public ParenthoodSocket getAssessmentParenthoodSocket() {
		return (body == null) ? null : body.getParenthoodSocket(); // NOPMD
	}

	/**
	 * @return assessment title
	 */
	public String getTitle() {
		return (title == null) ? "" : title;
	}

	protected Element getSkinBody(XmlData skinData) {
		Element skinBody = null;

		try {
			Document skinDocument = skinData.getDocument();
			skinBody = (Element) skinDocument.getElementsByTagName("itemBody").item(0);
		} catch (Exception e) {
			System.out.println("Skin body didn't load properly.");
		}

		return skinBody;
	}

	private final ModuleSocket moduleSocket = new ModuleSocket() {

		private InlineBodyGenerator inlineBodyGenerator;

		@Override
		public InlineBodyGeneratorSocket getInlineBodyGeneratorSocket() {
			if (inlineBodyGenerator == null) {
				inlineBodyGenerator = new InlineBodyGenerator(modulesRegistrySocket, this, options, interactionEventsListener, body.getParenthood());
			}
			return inlineBodyGenerator;
		}

		@Override
		public HasChildren getParent(IModule module) {
			if (body != null) {
				return body.getModuleParent(module);
			}
			return null;
		}

		@Override
		public GroupIdentifier getParentGroupIdentifier(IModule module) {
			IModule currParent = module;
			while (currParent != null && !(currParent instanceof IGroup)) {
				currParent = getParent(currParent);
			}
			if (currParent != null) {
				return ((IGroup) currParent).getGroupIdentifier();
			}
			return new DefaultGroupIdentifier("");
		}

		@Override
		public List<IModule> getChildren(IModule parent) {
			if (body != null) {
				return body.getModuleChildren(parent);
			}
			return null;
		}

		@Override
		public Stack<HasChildren> getParentsHierarchy(IModule module) {
			Stack<HasChildren> hierarchy = new Stack<HasChildren>();
			HasChildren currParent = getParent(module);
			while (currParent != null) {
				hierarchy.push(currParent);
				currParent = getParent(currParent);
			}
			return hierarchy;
		}

		@Override
		public Set<InlineFormattingContainerType> getInlineFormattingTags(IModule module) {
			InlineContainerStylesExtractor inlineContainerHelper = new InlineContainerStylesExtractor();
			return inlineContainerHelper.getInlineStyles(module);
		}

		@Override
		public YJsonArray getStateById(String id) {
			return YJsJsonFactory.createArray();
		}

	};

}
