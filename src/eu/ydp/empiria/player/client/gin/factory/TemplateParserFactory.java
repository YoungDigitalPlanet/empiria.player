package eu.ydp.empiria.player.client.gin.factory;

import com.google.gwt.xml.client.Element;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.img.template.ImgTemplateParser;

public interface TemplateParserFactory {
	ImgTemplateParser getImgTemplateParser(@Assisted Element baseElement, @Assisted ModuleSocket moduleSocket);
}
