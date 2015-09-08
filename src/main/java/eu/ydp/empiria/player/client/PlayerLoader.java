package eu.ydp.empiria.player.client;

import com.google.gwt.core.client.*;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.ContentPreloader;
import eu.ydp.empiria.player.client.gin.factory.PlayerFactory;
import eu.ydp.empiria.player.client.scripts.ScriptsLoader;
import eu.ydp.empiria.player.client.scripts.SynchronousScriptsCallback;
import eu.ydp.empiria.player.client.util.events.external.ExternalCallback;
import eu.ydp.empiria.player.client.util.events.external.ExternalEventDispatcher;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.gwtutil.client.Alternative;
import eu.ydp.gwtutil.client.debug.log.Logger;
import eu.ydp.gwtutil.client.debug.log.UncaughtExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class PlayerLoader {

    private static Player player;
    private static JavaScriptObject jsObject;
    private static String node_id;
    private static List<Alternative<String, JavaScriptObject>> extensionsToLoad = new ArrayList<>();

    private final Logger logger;
    private final ContentPreloader contentPreloader;
    private final ScriptsLoader scriptsLoader;

    @Inject
    public PlayerLoader(Logger logger, ContentPreloader contentPreloader, ScriptsLoader scriptsLoader) {
        this.logger = logger;
        this.contentPreloader = contentPreloader;
        this.scriptsLoader = scriptsLoader;
    }

    public void load() {
        contentPreloader.setPreloader();

        SynchronousScriptsCallback onScriptsLoadCallback = createPlayerInitializationCallback(logger);


        scriptsLoader.inject(onScriptsLoadCallback);
    }

    private SynchronousScriptsCallback createPlayerInitializationCallback(final Logger logger) {
        return new SynchronousScriptsCallback() {
            @Override
            public void onLoad() {
                GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler(logger));
                Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
                    @Override
                    public void execute() {
                        initJavaScriptAPI();
                    }
                });
            }
        };
    }

    private native void initJavaScriptAPI() /*-{
        // CreatePlayer
        $wnd.empiriaCreatePlayer = function (id) {
            var player = @PlayerLoader::createPlayer(Ljava/lang/String;)(id);
            player.load = function (url) {
                @eu.ydp.empiria.player.client.PlayerLoader::load(Ljava/lang/String;)(url);
            };
            player.loadFromData = function (assessmentData, itemDatas) {
                @PlayerLoader::load(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(assessmentData, itemDatas);
            };

            player.loadExtension = function (obj) {
                if (typeof obj == 'object')
                    @PlayerLoader::loadExtension(Lcom/google/gwt/core/client/JavaScriptObject;)(obj);
                else if (typeof obj == 'string')
                    @PlayerLoader::loadExtension(Ljava/lang/String;)(obj);
            };

            player.onEvent = function (funct) {
                @PlayerLoader::onEvent(*)(funct);
            };

            return player;
        };

        $wnd.getPlayerVersion = function () {
            return @eu.ydp.empiria.player.client.version.Version::getVersion()();
        };

        // Call App loaded function
        if (typeof $wnd.empiriaPlayerAppLoaded == 'function') {
            $wnd.empiriaPlayerAppLoaded();
        }
    }-*/;

    public static JavaScriptObject createPlayer(String node_id) {
        PlayerLoader.node_id = node_id;
        jsObject = JavaScriptObject.createFunction();
        return jsObject;
    }

    public static void load(final String url) {
        GWT.runAsync(new RunAsyncCallback() {
            @Override
            public void onSuccess() {
                doLoad(url);
            }

            @Override
            public void onFailure(Throwable reason) {
                throw new RuntimeException("Error while loading player!", reason);
            }
        });
    }

    private static void doLoad(final String url) {
        if (player == null) {
            PlayerFactory playerFactory = PlayerGinjectorFactory.getPlayerGinjector().getPlayerFactory();
            player = playerFactory.createPlayer(node_id, jsObject);
        }
        for (Alternative<String, JavaScriptObject> extAlt : extensionsToLoad) {
            if (extAlt.hasMain()) {
                player.loadExtension(extAlt.getMain());
            } else {
                player.loadExtension(extAlt.getOther());
            }

        }
        player.load(url);
    }

    public static void load(final JavaScriptObject assessmentData, final JavaScriptObject itemDatas) {
        GWT.runAsync(new RunAsyncCallback() {
            @Override
            public void onSuccess() {
                Document assessmentDoc = XMLParser.parse(decodeXmlDataDocument(assessmentData));
                XmlData assessmentXmlData = new XmlData(assessmentDoc, decodeXmlDataBaseURL(assessmentData));

                JsArray<JavaScriptObject> itemDatasArray = itemDatas.cast();

                XmlData itemXmlDatas[] = new XmlData[itemDatasArray.length()];
                for (int i = 0; i < itemDatasArray.length(); i++) {
                    Document itemDoc = XMLParser.parse(decodeXmlDataDocument(itemDatasArray.get(i)));
                    itemXmlDatas[i] = new XmlData(itemDoc, decodeXmlDataBaseURL(itemDatasArray.get(i)));
                }
                PlayerFactory playerFactory = PlayerGinjectorFactory.getPlayerGinjector().getPlayerFactory();
                player = playerFactory.createPlayer(node_id, jsObject);
                player.load(assessmentXmlData, itemXmlDatas);
            }

            @Override
            public void onFailure(Throwable reason) {
                throw new RuntimeException("Error while loading player!", reason);
            }
        });
    }

    private native static String decodeXmlDataDocument(JavaScriptObject data)/*-{
        if (typeof data.document == 'string')
            return data.document;
        return "";
    }-*/;

    private native static String decodeXmlDataBaseURL(JavaScriptObject data)/*-{
        if (typeof data.baseURL == 'string')
            return data.baseURL;
        return "";
    }-*/;

    public static void loadExtension(JavaScriptObject extension) {
        extensionsToLoad.add(Alternative.<String, JavaScriptObject>createForOther(extension));
    }

    public static void loadExtension(String extension) {
        extensionsToLoad.add(Alternative.<String, JavaScriptObject>createForMain(extension));
    }

    public static void onEvent(ExternalCallback callbackFunction) {
        ExternalEventDispatcher dispatcher = PlayerGinjectorFactory.getPlayerGinjector().getEventDispatcher();
        dispatcher.setCallbackFunction(callbackFunction);
    }
}
