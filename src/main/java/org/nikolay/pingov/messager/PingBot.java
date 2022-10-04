package org.nikolay.pingov.messager;

public interface PingBot<C extends Chat> {

    long getStartupTime();

    void sendMessage(C chat, String message);

}
