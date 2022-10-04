package org.nikolay.pingov.fetch.impl;

public class MCJavaFetcher extends MCFetcherSrvStats {
    @Override
    public String getApiUrl() {
        return "https://api.mcsrvstat.us/2/";
    }
}
