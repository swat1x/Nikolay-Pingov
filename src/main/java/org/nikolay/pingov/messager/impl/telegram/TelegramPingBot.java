package org.nikolay.pingov.messager.impl.telegram;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.nikolay.pingov.BotConstants;
import org.nikolay.pingov.messager.CommandManager;
import org.nikolay.pingov.messager.PingBot;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TelegramPingBot extends AbilityBot implements PingBot<TelegramChat> {

    // 874138354
    CommandManager commandManager;
    long startupTime;
    long creatorId;

    public TelegramPingBot(CommandManager commandManager, String token, String id, long creatorId) {
        super(token, id);
        this.commandManager = commandManager;
        this.startupTime = System.currentTimeMillis();
        this.creatorId = creatorId;


    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            TelegramSender sender = new TelegramSender(message.getFrom().getId(), message.getFrom().getFirstName());
            TelegramChat chat = new TelegramChat(this, message, !message.isUserMessage(), message.getChatId(), sender);
            sender.setChat(chat);

            if(message.isCommand()){
                chat.sendReply(BotConstants.HELP_MESSAGE);
            } else {
                TelegramMessage telegramMessage = new TelegramMessage(chat, message, message.getText());
                commandManager.handleMessage(telegramMessage);
            }
        }
    }

    @Override
    public void sendMessage(TelegramChat chat, String message) {
        try {
            execute(new SendMessage(String.valueOf(chat.getId()), message));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long creatorId() {
        return creatorId;
    }
}
