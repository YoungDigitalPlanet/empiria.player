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
import eu.ydp.gwtutil.client.debug.log.Logger;
import eu.ydp.gwtutil.client.debug.log.UncaughtExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class PlayerLoader {

    private Player player;
    private JavaScriptObject jsObject;
    private String node_id;
    private List<String> namedExtensions = new ArrayList<>();
    private List<JavaScriptObject> javaScriptExtensions = new ArrayList<>();

    private final Logger logger;
    private final ContentPreloader contentPreloader;
    private final ScriptsLoader scriptsLoader;
    private final PlayerFactory playerFactory;

    @Inject
    public PlayerLoader(Logger logger, ContentPreloader contentPreloader, ScriptsLoader scriptsLoader, PlayerFactory playerFactory) {
        this.logger = logger;
        this.contentPreloader = contentPreloader;
        this.scriptsLoader = scriptsLoader;
        this.playerFactory = playerFactory;
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
        var that = this;
        $wnd.empiriaCreatePlayer = function (id) {
            var player = that.@PlayerLoader::createPlayer(Ljava/lang/String;)(id);
            player.load = function (url) {
                that.@PlayerLoader::loadContent(*)(url);
            };
            player.loadFromData = function (assessmentData, itemDatas) {
                that.@PlayerLoader::loadStructure(*)(assessmentData, itemDatas);
            };

            player.loadExtension = function (obj) {
                if (typeof obj == 'object') {
                    that.@PlayerLoader::loadObjectExtension(*)(obj);
                }
                else if (typeof obj == 'string') {
                    that.@PlayerLoader::loadStringExtension(*)(obj);
                }
            };

            player.onEvent = function (funct) {
                that.@PlayerLoader::onEvent(*)(funct);
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

    private JavaScriptObject createPlayer(String node_id) {
        this.node_id = node_id;
        jsObject = JavaScriptObject.createFunction();
        return jsObject;
    }

    private void loadContent(final String url) {
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

    private void doLoad(final String url) {
        if (player == null) {
            player = playerFactory.createPlayer(node_id, jsObject);
        }
        for (String namedExtension : namedExtensions) {
            player.loadExtension(namedExtension);
        }
        for (JavaScriptObject javaScriptExtension : javaScriptExtensions) {
            player.loadExtension(javaScriptExtension);
        }
        player.load(url);
    }

    private void loadStructure(final JavaScriptObject assessmentData, final JavaScriptObject itemDatas) {
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
                player = playerFactory.createPlayer(node_id, jsObject);
                player.load(assessmentXmlData, itemXmlDatas);
            }

            @Override
            public void onFailure(Throwable reason) {
                throw new RuntimeException("Error while loading player!", reason);
            }
        });
    }

    private native String decodeXmlDataDocument(JavaScriptObject data)/*-{
        if (typeof data.document == 'string')
            return data.document;
        return "";
    }-*/;

    private native String decodeXmlDataBaseURL(JavaScriptObject data)/*-{
        if (typeof data.baseURL == 'string')
            return data.baseURL;
        return "";
    }-*/;

    private void loadObjectExtension(JavaScriptObject extension) {
        javaScriptExtensions.add(extension);
    }

    private void loadStringExtension(String extension) {
        namedExtensions.add(extension);
    }

    private void onEvent(ExternalCallback callbackFunction) {
        ExternalEventDispatcher dispatcher = PlayerGinjectorFactory.getPlayerGinjector().getEventDispatcher();
        dispatcher.setCallbackFunction(callbackFunction);
    }
}
