package org.nikolay.pingov.fetch;

import org.nikolay.pingov.json.JsonPingStructure;

public interface MCServer {

    long getPingTime();

    JsonPingStructure getStructure();

    InfoMessage getAsMessage();

}
