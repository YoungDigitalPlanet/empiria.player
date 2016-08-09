package eu.ydp.empiria.player.client.module.external.common.state;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.BrowserLogger;
import eu.ydp.gwtutil.client.debug.log.Logger;

@Singleton
public class ExternalStateEncoder {

    public JSONArray encodeState(JavaScriptObject state) {
        logger.info("############ENCODE_STATE################");

//        BrowserLogger.logIsArray(state);
//        BrowserLogger.log(state);

        JavaScriptObject javaScriptObject = transformObject(state);
        JSONObject obj = new JSONObject(javaScriptObject);
        JSONArray jsonArray = new JSONArray();

        jsonArray.set(0, obj);
//        logger.info("encodeState, obj: ");
//        BrowserLogger.log(obj);
        logger.info("encodeState, array: ");
        BrowserLogger.log(jsonArray);
        return jsonArray;
    }

    private native JavaScriptObject transformObject(JavaScriptObject object) /*-{

        console.log("TRANSFORM OBJECT");

        var isArray = function (o) {
            return Object.prototype.toString.call(o) === '[object Array]';
        }


        var transformToArray = function (obj) {
            var result = [];
            for (key in obj) {
                result.push(obj[key]);

            }

            return result;
        }

        var transformObject = function(obj) {

            var result = {};

            for (field in obj) {

                var transformed = obj[field];
                if(typeof obj[field] === "object") {
                    transformed = transformObject(obj);
                }

                if (isArray(obj[field])) {
                    result[field] = transformToArray(transformed);
                } else {
                    result[field] = transformed;
                }
            }

            return result;

        }

        return transformObject(object);

    }-*/;

    @Inject
    Logger logger;

    public JavaScriptObject decodeState(JSONArray array) {
        logger.info("############DECODE_STATE################");

//        logger.info("decodeState, array" + array);

        if (array.size() > 0) {
            JSONValue jsonValue = array.get(0);
//            logger.info("decodeState, value" + jsonValue);

            JSONValue state = jsonValue.isObject().get("STATE");
//            logger.info("decodeState, state" + state);

            JavaScriptObject javaScriptObject = state.isArray().get(0).isObject().getJavaScriptObject();
//            logger.info("decodeState, javaScriptObject: " + javaScriptObject.toSource());
            return javaScriptObject;
        }

        return JavaScriptObject.createObject();
    }
}
