package org.nikolay.pingov.messager;

import java.awt.image.BufferedImage;

public interface Sender<I, C extends  Chat> {

    void setChat(C chat);

    C getChat();

    I getId();

    String getName();

    default void reply(String message){
        getChat().sendReply(message);
    }

    default void reply(String message, BufferedImage image){
        getChat().sendReply(message, image);
    }

}
