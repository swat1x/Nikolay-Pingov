package org.nikolay.pingov.messager.impl.telegram;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.nikolay.pingov.messager.ChatMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TelegramMessage implements ChatMessage<TelegramChat, Message> {

    TelegramChat chat;
    Message source;
    String text;

    @NonFinal
    boolean answer = false;

    @Override
    public void answer() {
        answer = true;
    }

    @Override
    public boolean hasAnswer() {
        return answer;
    }
}
