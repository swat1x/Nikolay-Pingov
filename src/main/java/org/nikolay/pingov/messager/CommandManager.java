package org.nikolay.pingov.messager;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.nikolay.pingov.NikolayPingov;

import java.util.Map;
import java.util.function.BiConsumer;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommandManager {

    Map<String, BiConsumer<Sender, String[]>> commandMap;

    public void handleMessage(ChatMessage message) {
        boolean userMessage = !message.getChat().isConference();
        if (userMessage && message.isEmpty()) {
            message.getChat().sendReply("Сообщение должно содержать в себе текст!");
        } else if (!message.isEmpty()) {
            if (userMessage) {
                log.info("Получено сообщение - " + message.getText());
            }
            commandMap.keySet().forEach(key -> {
                if (message.getText().toLowerCase().startsWith(key)) {
                    String[] array = message.getText().split(" ");
                    commandMap.get(key).accept(message.getChat().getSender(), array);
                    message.answer();
                }
            });
            if (userMessage && !message.hasAnswer()) {
                message.getChat().sendReply("Команда не найдена!");
            }

        }
    }

    public void registerCommand(String command, BiConsumer<Sender, String[]> action) {
        command = command.toLowerCase();
        commandMap.put(command, action);
        log.info("Зарегистрирована команда {}", command);
    }

}
