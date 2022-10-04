package org.nikolay.pingov.messager.impl.telegram;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.nikolay.pingov.messager.Sender;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TelegramSender implements Sender<Long, TelegramChat> {

    @NonFinal
    @Setter
    TelegramChat chat;
    Long id;
    String name;

}
