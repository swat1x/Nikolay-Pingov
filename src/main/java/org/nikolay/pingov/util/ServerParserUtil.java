package org.nikolay.pingov.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.experimental.UtilityClass;
import org.nikolay.pingov.fetch.MCServer;
import org.nikolay.pingov.fetch.impl.MCServerImpl;
import org.nikolay.pingov.json.JsonPingStructure;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class ServerParserUtil {

    private Gson GSON = new Gson();

    public MCServer parseServer(long pingStart, String json) throws JsonSyntaxException {
        JsonPingStructure structure = GSON.fromJson(json, JsonPingStructure.class);
        return new MCServerImpl(System.currentTimeMillis() - pingStart, structure);
    }

    public String readJson(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return readAll(rd);
        } finally {
            is.close();
        }
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
