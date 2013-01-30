package eu.ydp.empiria.player.client.module.img.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.TemplateParserFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.img.ImgContent;
import eu.ydp.empiria.player.client.module.media.button.MediaController;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.gwtutil.xml.XMLParser;

@SuppressWarnings("PMD")
public class ImgTemplateParserJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private static class CustomGuiceModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(ModuleWrapper.class).toInstance(mock(ModuleWrapper.class));
			binder.install(new FactoryModuleBuilder().build(TemplateParserFactory.class));
		}
	}

	@BeforeClass
	public static void prepareTestEnviroment() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void restoreEnviroment() {
		GWTMockUtilities.restore();
	}


	private ImgTemplateParser instance;
	private ModuleSocket moduleSocket;
	private TemplateParserFactory factory;
	private final Widget widget = mock(Widget.class);


	public Set<String> supportedModulesWithoutFullScreen =
				Sets.newHashSet(ModuleTagName.MEDIA_TITLE.tagName(),
							  ModuleTagName.MEDIA_DESCRIPTION.tagName(),
						//	  ModuleTagName.MEDIA_FULL_SCREEN_BUTTON.tagName(),
							  ModuleTagName.MEDIA_SCREEN.tagName());

	private Document createDocument(String xmlContent){
		return XMLParser.parse(xmlContent);
	}

	private void createInstance(Document document){
		instance = spy(factory.getImgTemplateParser(document.getDocumentElement(), moduleSocket));

	}

	private MediaController<?> createMediaController(Document document,ModuleTagName moduleName){
		doReturn(mock(MediaController.class)).when(instance).createEmptyModuleWrapper();
		doReturn(mock(MediaController.class)).when(instance).createModuleWrapperForWidget(Mockito.any(Widget.class));
		Node mediaScreenNode = document.getElementsByTagName(moduleName.tagName()).item(0);
		MediaController<?> module = instance.getMediaControllerNewInstance(moduleName.tagName(), mediaScreenNode);
		return module;
	}

	@Before
	public void before() {
		GuiceModuleConfiguration moduleConfiguration = new GuiceModuleConfiguration();
		moduleConfiguration.addClassWithDisabledPostConstruct(ModuleWrapper.class);
		setUp(moduleConfiguration, new CustomGuiceModule());
		factory = injector.getInstance(TemplateParserFactory.class);
		moduleSocket = mock(ModuleSocket.class);
		InlineBodyGeneratorSocket bodyGeneratorSocket = mock(InlineBodyGeneratorSocket.class);
		doReturn(widget).when(bodyGeneratorSocket).generateInlineBody(Mockito.any(Node.class));
		doReturn(bodyGeneratorSocket).when(moduleSocket).getInlineBodyGeneratorSocket();

	}

	@Test
	public void testBeforeParse() {
		Document parse = XMLParser.parse(Templates.TEMPLATE_WITHOUT_CONTENT_WITH_FULLSCREEN);
		instance = spy(factory.getImgTemplateParser(parse.getDocumentElement(), moduleSocket));
		instance.beforeParse(mock(Node.class), null);
		verify(instance).beforeParse(Mockito.any(Node.class), Mockito.any(Widget.class));
		Mockito.verifyNoMoreInteractions(instance);
	}

	private MediaController<?> getModule(String xml,ModuleTagName moduleName) {
		Document document = createDocument(xml);
		createInstance(document);
		MediaController<?> module = createMediaController(document, moduleName);
		return module;
	}


	@Test
	public void testCreateMediaTitleNoContentInXMLElement(){
		MediaController<?> module = getModule(Templates.TEMPLATE_WITHOUT_CONTENT_WITH_FULLSCREEN, ModuleTagName.MEDIA_TITLE);
		assertNotNull(module);
	}

	@Test
	public void testCreateMediaTitleWithContentInXMLElement(){
		MediaController<?> module = getModule(Templates.TEMPLATE_WITHOUT_CONTENT_WITH_FULLSCREEN, ModuleTagName.MEDIA_TITLE);
		assertNotNull(module);
	}

	@Test
	public void testCreateMediaDescriptionNoContentInXMLElement(){
		MediaController<?> module = getModule(Templates.TEMPLATE_WITHOUT_CONTENT_WITH_FULLSCREEN, ModuleTagName.MEDIA_DESCRIPTION);
		assertNotNull(module);
	}

	@Test
	public void testCreateMediaDescriptionNoElementInXML(){
		MediaController<?> module = getModule(Templates.TEMPLATE_WITHOUT_CONTENT_WITH_DESCRIPTION, ModuleTagName.MEDIA_DESCRIPTION);

		assertNotNull(module);
		verify(instance).createEmptyModuleWrapper();
	}

	@Test
	public void testCreateMediaDescriptionNoWidgetFromElementContent(){
		Document document = createDocument(Templates.TEMPLATE_WITH_CONTENT_WITHOUT_FULLSCREEN);
		createInstance(document);
		InlineBodyGeneratorSocket bodyGeneratorSocket = mock(InlineBodyGeneratorSocket.class);
		doReturn(null).when(bodyGeneratorSocket).generateInlineBody(Mockito.any(Node.class));
		doReturn(bodyGeneratorSocket).when(moduleSocket).getInlineBodyGeneratorSocket();

		MediaController<?> module = createMediaController(document, ModuleTagName.MEDIA_DESCRIPTION);
		assertNotNull(module);
		verify(instance).createEmptyModuleWrapper();
	}

	@Test
	public void testCreateMediaDescriptionWithContentInXMLElement(){
		MediaController<?> module = getModule(Templates.TEMPLATE_WITHOUT_CONTENT_WITH_FULLSCREEN, ModuleTagName.MEDIA_DESCRIPTION);

		assertNotNull(module);
	}

	@Test
	public void testIsModuleSupported() {
		Document parse = XMLParser.parse(Templates.EMPTY_TEMPLATE);
		instance = spy(factory.getImgTemplateParser(parse.getDocumentElement(), moduleSocket));
		Function<ModuleTagName, String> convertFunction = new Function<ModuleTagName, String>() {
			@Override
			public String apply(ModuleTagName tagName) {
				return tagName.tagName();
			}
		};

		for (String moduleName : supportedModulesWithoutFullScreen) {
			assertTrue(instance.isModuleSupported(moduleName));
		}

		List<String> notSupportedModules = Lists.transform(Lists.newArrayList(ModuleTagName.values()), convertFunction);
		notSupportedModules.removeAll(supportedModulesWithoutFullScreen);

		for (String moduleName : notSupportedModules) {
			assertFalse(instance.isModuleSupported(moduleName));
		}

	}

	@Test
	public void testIsMediaFullScreenButtonModuleSupported() {
		List<String> noFullScreenTemplates =
				Lists.newArrayList(Templates.TEMPLATE_WITHOUT_CONTENT_WITH_FULLSCREEN,
								   Templates.TEMPLATE_WITH_CONTENT_AND_FULLSCREEN);

		for(String element : noFullScreenTemplates){
			Document parse = XMLParser.parse(element);
			instance = spy(factory.getImgTemplateParser(parse.getDocumentElement(), moduleSocket));
			assertTrue(instance.isModuleSupported(ModuleTagName.MEDIA_FULL_SCREEN_BUTTON.tagName()));
		}
	}
	@Test
	public void testIsMediaFullScreenButtonModuleNotSupported() {
		List<String> noFullScreenTemplates =
				Lists.newArrayList(Templates.EMPTY_TEMPLATE,
								   Templates.TEMPLATE_WITH_CONTENT_WITHOUT_FULLSCREEN,
								   Templates.TEMPLATE_WITHOUT_FULLSCREEN_AND_CONTENT);

		for(String element : noFullScreenTemplates){
			Document parse = XMLParser.parse(element);
			instance = spy(factory.getImgTemplateParser(parse.getDocumentElement(), moduleSocket));
			assertFalse(instance.isModuleSupported(ModuleTagName.MEDIA_FULL_SCREEN_BUTTON.tagName()));
		}
	}

	@Test
	public void testCreateScreenModuleDefaultImgContent(){
		Document document = createDocument(Templates.TEMPLATE_WITHOUT_CONTENT_WITH_FULLSCREEN);
		createInstance(document);
		ImgContent imgContentMock = mock(ImgContent.class);
		doReturn(imgContentMock).when(instance).createDefaultImgContent();
		MediaController<?> module = createMediaController(createDocument(Templates.TEMPLATE_WITHOUT_CONTENT_WITH_FULLSCREEN), ModuleTagName.MEDIA_SCREEN);
		assertNotNull(module);
		verify(instance).createDefaultImgContent();

	}

	@Test
	public void testCreateScreenModuleLabelledImgContent(){
		Document document = createDocument(Templates.TEMPLATE_WITHOUT_CONTENT_WITH_FULLSCREEN);
		createInstance(document);
		ImgContent imgContentMock = mock(ImgContent.class);
		doReturn(imgContentMock).when(instance).createExplorableImgContent();
		Map<String, String> configMap = new HashMap<String, String>();
		configMap.put(EmpiriaStyleNameConstants.EMPIRIA_IMG_MODE, "explorable");
		doReturn(configMap).when(moduleSocket).getStyles(Mockito.any(Element.class));

		MediaController<?> module = createMediaController(createDocument(Templates.TEMPLATE_WITHOUT_CONTENT_WITH_FULLSCREEN), ModuleTagName.MEDIA_SCREEN);
		assertNotNull(module);
		verify(instance).createExplorableImgContent();
	}

	@Test
	public void testCreateScreenModuleExplorableImgContent(){
		Document document = createDocument(Templates.TEMPLATE_WITHOUT_CONTENT_WITH_LABEL);
		createInstance(document);
		ImgContent imgContentMock = mock(ImgContent.class);
		doReturn(imgContentMock).when(instance).createLabelledImgContent();

		MediaController<?> module = createMediaController(createDocument(Templates.TEMPLATE_WITHOUT_CONTENT_WITH_FULLSCREEN), ModuleTagName.MEDIA_SCREEN);
		assertNotNull(module);
		verify(instance).createLabelledImgContent();
	}

	@Test
	public void testCreateFullScreenButon(){
		Document document = createDocument(Templates.TEMPLATE_WITHOUT_CONTENT_WITH_FULLSCREEN);
		createInstance(document);
		MediaController<?> mediaController = mock(MediaController.class);

		doReturn(mediaController).when(instance).createFullScreenButon();
		MediaController<?> module = createMediaController(createDocument(Templates.TEMPLATE_WITHOUT_CONTENT_WITH_FULLSCREEN), ModuleTagName.MEDIA_FULL_SCREEN_BUTTON);
		assertEquals(mediaController, module);

	}
}
