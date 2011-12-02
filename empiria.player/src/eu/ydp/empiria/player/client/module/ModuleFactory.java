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
package eu.ydp.empiria.player.client.module;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

import eu.ydp.empiria.player.client.components.EmptyWidget;
import eu.ydp.empiria.player.client.module.audioplayer.AudioPlayerModule;
import eu.ydp.empiria.player.client.module.choice.ChoiceModule;
import eu.ydp.empiria.player.client.module.debug.DebugModule;
import eu.ydp.empiria.player.client.module.dragdrop.DragDropModule;
import eu.ydp.empiria.player.client.module.identification.IdentificationModule;
import eu.ydp.empiria.player.client.module.match.MatchModule;
import eu.ydp.empiria.player.client.module.math.inline.MathInlineModule;
import eu.ydp.empiria.player.client.module.math.interaction.MathModule;
import eu.ydp.empiria.player.client.module.mathexpr.MathExprInlineModule;
import eu.ydp.empiria.player.client.module.object.ObjectModule;
import eu.ydp.empiria.player.client.module.order.OrderModule;
import eu.ydp.empiria.player.client.module.prompt.PromptModule;
import eu.ydp.empiria.player.client.module.selection.SelectionModule;
import eu.ydp.empiria.player.client.module.test.TestModule;
import eu.ydp.empiria.player.client.module.text.InlineChoiceModule;
import eu.ydp.empiria.player.client.module.text.TextEntryModule;
import eu.ydp.empiria.player.client.module.text.TextEntryMultipleModule;
import eu.ydp.empiria.player.client.module.vocabox.VocaboxModule;

public abstract class ModuleFactory {
	
	protected static String[] SUPPORTED_MODULES ={"choiceInteraction", 
												"inlineChoiceInteraction", 
												"textEntryInteraction", 
												"textEntryMultipleInteraction",
												"orderInteraction",
												"matchInteraction",
												"selectionInteraction",
												"identificationInteraction",
												"testInteraction",
												"object",
												"math",
												"mathInline",
												"mathInteraction",
												"dragDropInteraction",
												"prompt",
												"qy:comment",
												"audioPlayer",
												"vocabox"};

	public static boolean isSupported(String test){
		for (int s= 0 ; s <SUPPORTED_MODULES.length ; s++)
			if (SUPPORTED_MODULES[s].compareTo(test) == 0)
				return true;
		
		return false;
	}
	
	public static Widget createWidget(Element element, ModuleSocket moduleSocket, ModuleEventsListener moduleEventsListener){
		Widget	widget = null;

		if(element.getNodeName().compareTo("choiceInteraction") == 0)
			widget = new ChoiceModule(element, moduleSocket, moduleEventsListener);
	    else if(element.getNodeName().compareTo("object") == 0)
	    	widget = new ObjectModule(element, moduleSocket, moduleEventsListener);
	    else if(element.getNodeName().compareTo("inlineChoiceInteraction") == 0)
			widget = new InlineChoiceModule(element, moduleSocket, moduleEventsListener);	
		else if(element.getNodeName().compareTo("textEntryInteraction") == 0)
			widget = new TextEntryModule(element, moduleSocket, moduleEventsListener);
		else if(element.getNodeName().compareTo("textEntryMultipleInteraction") == 0)
			widget = new TextEntryMultipleModule(element, moduleSocket, moduleEventsListener);
		else if(element.getNodeName().compareTo("testInteraction") == 0)
			widget = new TestModule(element, moduleSocket, moduleEventsListener);
		else if(element.getNodeName().compareTo("orderInteraction") == 0)
			widget = new OrderModule(element, moduleSocket, moduleEventsListener);
		else if(element.getNodeName().compareTo("matchInteraction") == 0)
			widget = new MatchModule(element, moduleSocket, moduleEventsListener);
		else if(element.getNodeName().compareTo("selectionInteraction") == 0)
			widget = new SelectionModule(element, moduleSocket, moduleEventsListener);
		else if(element.getNodeName().compareTo("identificationInteraction") == 0)
			widget = new IdentificationModule(moduleSocket, moduleEventsListener);
		else if(element.getNodeName().compareTo("math") == 0)
			widget = new MathExprInlineModule(element);
		else if(element.getNodeName().compareTo("mathInteraction") == 0)
			widget = new MathModule(element, moduleSocket, moduleEventsListener);
		else if(element.getNodeName().compareTo("dragDropInteraction") == 0)
			widget = new DragDropModule(element, moduleSocket, moduleEventsListener);
		else if(element.getNodeName().compareTo("mathInline") == 0)
			widget = new MathInlineModule(element);
		else if(element.getNodeName().compareTo("audioPlayer") == 0)
			widget = new AudioPlayerModule(element, moduleSocket, moduleEventsListener);
		else if(element.getNodeName().compareTo("vocabox") == 0)
			widget = new VocaboxModule(element);		
		else if(element.getNodeName().compareTo("prompt") == 0)
			widget = new PromptModule(element);		
		else if(element.getNodeName().compareTo("qy:comment") == 0)
			widget = new EmptyWidget();
		else if(element.getNodeType() == Node.ELEMENT_NODE)
	    	widget = new DebugModule(element);
		
		return widget;
	}
	
}
