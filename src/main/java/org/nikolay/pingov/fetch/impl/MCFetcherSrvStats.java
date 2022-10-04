package org.nikolay.pingov.fetch.impl;

import lombok.RequiredArgsConstructor;
import org.nikolay.pingov.ServerPingException;
import org.nikolay.pingov.fetch.MCFetcher;
import org.nikolay.pingov.fetch.MCServer;
import org.nikolay.pingov.util.ServerParserUtil;

@RequiredArgsConstructor
public abstract class MCFetcherSrvStats implements MCFetcher {

    public abstract String getApiUrl();

    @Override
    public MCServer ping(String ip) throws ServerPingException {
        long pingStart = System.currentTimeMillis();
        String url = getApiUrl() + ip;
        try {
            String json = ServerParserUtil.readJson(url);
            return ServerParserUtil.parseServer(pingStart, json);
        } catch (Exception e) {
            throw new ServerPingException(e.getMessage());
        }
    }

}
