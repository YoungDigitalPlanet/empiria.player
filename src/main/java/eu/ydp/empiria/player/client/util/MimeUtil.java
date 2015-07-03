package eu.ydp.empiria.player.client.util;

import eu.ydp.gwtutil.client.StringUtils;

public class MimeUtil {

    public String getMimeTypeFromExtension(String url) {
        String mimeType = StringUtils.EMPTY_STRING;
        String fileType = url.substring(url.length() - 3);

        if ("mp3".equals(fileType)) {
            mimeType = "audio/mp4";
        } else if ("ogg".equals(fileType) || "ogv".equals(fileType)) {
            mimeType = "audio/ogg";
        }
        return mimeType;
    }
}
