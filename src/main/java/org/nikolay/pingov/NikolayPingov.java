package org.nikolay.pingov;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.nikolay.pingov.fetch.InfoMessage;
import org.nikolay.pingov.fetch.MCFetcher;
import org.nikolay.pingov.fetch.MCServer;
import org.nikolay.pingov.fetch.impl.MCBedrockFetcher;
import org.nikolay.pingov.fetch.impl.MCJavaFetcher;
import org.nikolay.pingov.messager.CommandManager;
import org.nikolay.pingov.messager.PingBot;
import org.nikolay.pingov.messager.Sender;
import org.nikolay.pingov.messager.impl.telegram.TelegramPingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.BiConsumer;

@Log4j2
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NikolayPingov {

    ExecutorService executorService;

    // Фетчеры
    MCFetcher javaFetcher;
    MCFetcher bedrockFetcher;

    CommandManager commandManager;

    PingBot telegramBot;

    public void start() {
        // Установка экзекютор сервиса для тасков
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("nikolay-worker-%d").build();
        executorService = Executors.newFixedThreadPool(4, threadFactory);

        // Запуск пингование (В разработке)
        // executorService.scheduleAtFixedRate(pingAction(), 0L, rate, timeUnit);

        // Установка фетчеров
        javaFetcher = new MCJavaFetcher();
        bedrockFetcher = new MCBedrockFetcher();

        commandManager = createCommandManager();

        // Создание ботов
        telegramBot = createTelegramBot();
    }

    private CommandManager createCommandManager() {
        CommandManager commandManager = new CommandManager(new LinkedHashMap<>());

        BiConsumer<Sender, String[]> pingAction = (sender, strings) -> {
            executorService.execute(() -> {
                log.info("Обработка команды - {}", strings[0]);
                if (strings.length < 2) {
                    sender.reply("Введите IP сервера!");
                } else {
                    String ip = strings[1];
                    MCServer server = javaFetcher.ping(ip);
                    InfoMessage message = server.getAsMessage();
                    if(message.getIcon() != null){
                        sender.reply(message.getText(), message.getIcon());
                    } else {
                        sender.reply(message.getText());
                    }
                }
            });
        };
        commandManager.registerCommand("пинг", pingAction);
        commandManager.registerCommand("сервер", pingAction);
        commandManager.registerCommand("server", pingAction);

        return commandManager;
    }

    private TelegramPingBot createTelegramBot() {
        TelegramBotsApi botsApi;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            TelegramPingBot bot = new TelegramPingBot(
                    commandManager,
                    "5486433343:AAEcT9qhujzN25ptuUaa4KVUwFg90ssMUWI",
                    "nikolat_pingov_bot",
                    874138354);
            botsApi.registerBot(bot);
            return bot;
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

//  private Runnable pingAction() {
//
//  }

}
