package org.nikolay.pingov.json;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.apache.logging.log4j.core.config.plugins.convert.Base64Converter.parseBase64Binary;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class JsonPingStructure {

    boolean online;
    String ip;
    int port;

    JsonPingDebug debug;
    JsonPingMotd motd;
    JsonPingPlayers players;

    String version;
    Integer protocol;
    String hostname;
    @SerializedName("icon")
    String iconBase;
    String software;

    public BufferedImage getIcon() {
        String base64Image = iconBase.split(",")[1];
        byte[] imageBytes = parseBase64Binary(base64Image);

        InputStream stream = new ByteArrayInputStream(imageBytes);
        try {
            try {
                return ImageIO.read(stream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                stream.close();
            } catch (IOException ignored) {

            }
        }
    }

}
