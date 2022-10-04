package org.nikolay.pingov.messager;

public interface ChatMessage<C extends Chat, S> {

    C getChat();

    S getSource();

    default boolean isEmpty() {
        return getText() == null || getText().isEmpty();
    }

    String getText();

    void answer();

    boolean hasAnswer();

}
