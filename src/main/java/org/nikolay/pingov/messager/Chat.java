package org.nikolay.pingov.messager;

import java.awt.image.BufferedImage;

public interface Chat<I, S extends Sender> {

    boolean isConference();

    I getId();

    S getSender();

    void sendReply(String message);

    void sendReply(String message, BufferedImage image);

}
