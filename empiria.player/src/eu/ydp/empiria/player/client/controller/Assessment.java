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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGenerator;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.communication.AssessmentData;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.communication.PageReference;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.controller.style.StyleLinkDeclaration;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.IGroup;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ParenthoodSocket;
import eu.ydp.empiria.player.client.module.containers.group.DefaultGroupIdentifier;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.xml.document.XMLData;
import eu.ydp.empiria.player.client.view.assessment.AssessmentBodyView;

public class Assessment {

	/** Whole assessment title */
	private String title;

	/** XML DOM of the assessment */
	private XMLData xmlData;

	private Panel pageSlot;

	public StyleLinkDeclaration styleDeclaration;

	private StyleSocket styleSocket;

	private ModulesRegistrySocket modulesRegistrySocket;

	private DisplayContentOptions options;
	
	private AssessmentBody body;
	
	private AssessmentBodyView bodyView;

	/**
	 * C'tor
	 * 
	 * @param data
	 *            XMLData object as data source
	 */
	public Assessment(AssessmentData data, DisplayContentOptions options,
			InteractionEventsListener interactionEventsListener,
			StyleSocket styleSocket, ModulesRegistrySocket modulesRegistrySocket) {

		this.xmlData = data.getData();
		this.styleSocket = styleSocket;
		this.modulesRegistrySocket = modulesRegistrySocket;
		this.options = options;

		Document document = xmlData.getDocument();
		Element rootNode = (Element) document.getElementsByTagName("assessmentTest").item(0);
		Element skinBody = getSkinBody(data.getSkinData());

		styleDeclaration = new StyleLinkDeclaration(xmlData.getDocument()
				.getElementsByTagName("styleDeclaration"), xmlData.getBaseURL());
		title = rootNode.getAttribute("title");

		initializeBody(skinBody, interactionEventsListener);
	}

	private void initializeBody(Element bodyNode,
			InteractionEventsListener interactionEventsListener) {
		if(bodyNode != null){
			body = new AssessmentBody(options, moduleSocket,
					interactionEventsListener, modulesRegistrySocket);
			bodyView = new AssessmentBodyView(body);
			bodyView.init( body.init(bodyNode) );
			pageSlot = body.getPageSlot();
		}
	}

	public Widget getSkinView() {
		return bodyView;
	}

	public Panel getPageSlot() {
		return pageSlot;
	}
	
	public void setUp() {
		if (body != null)
			body.setUp();
	}

	public void start() {
		if (body != null)
			body.start();
	}
	
	public ParenthoodSocket getAssessmentParenthoodSocket(){
		if (body != null)
			return body.getParenthoodSocket();
		return null;
	}

	/**
	 * @return assessment title
	 */
	public String getTitle() {
		return (title == null) ? "" : title;
	}
	
	protected Element getSkinBody(XMLData skinData){
		Element skinBody = null;
		
		try{
			Document skinDocument = skinData.getDocument();
			skinBody = (Element) skinDocument.getElementsByTagName("itemBody").item(0);
		}catch(Exception e){
			
		}
		
		return skinBody;				
	}

	private ModuleSocket moduleSocket = new ModuleSocket() {

		private InlineBodyGenerator inlineBodyGenerator;

		public Response getResponse(String id) {
			return null;
		}

		@Override
		public void addInlineFeedback(InlineFeedback inlineFeedback) {

		}

		@Override
		public Map<String, String> getStyles(Element element) {
			return (styleSocket != null) ? styleSocket.getStyles(element)
					: new HashMap<String, String>();
		}

		public void setCurrentPages(PageReference pr) {
			if (styleSocket != null) {
				styleSocket.setCurrentPages(pr);
			}
		}

		public InlineBodyGeneratorSocket getInlineBodyGeneratorSocket() {
			if (inlineBodyGenerator == null) {
				inlineBodyGenerator = new InlineBodyGenerator(
						modulesRegistrySocket, this, options);
			}
			return inlineBodyGenerator;
		}

		@Override
		public IModule getParent(IModule module) {
			if (body != null)
				return body.getModuleParent(module);
			return null;
		}

		@Override
		public GroupIdentifier getParentGroupIdentifier(IModule module) {
			IModule currParent = module;
			while (true){
				currParent = getParent(currParent);
				if (currParent == null  ||  currParent instanceof IGroup)
					break;
			}
			if (currParent != null)
				return ((IGroup)currParent).getGroupIdentifier();
			return new DefaultGroupIdentifier("");
		}

		@Override
		public List<IModule> getChildren(IModule parent) {
			if (body != null)
				return body.getModuleChildren(parent);
			return null;
		}

		@Override
		public Stack<IModule> getParentsHierarchy(IModule module) {
			Stack<IModule> hierarchy = new Stack<IModule>();
			IModule currParent = module;
			while (true){
				currParent = getParent(currParent);
				if (currParent == null)
					break;
				hierarchy.push(currParent);
			}
			return hierarchy;
		}

	};

}
