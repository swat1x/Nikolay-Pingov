package org.nikolay.pingov.messager.impl.telegram;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.nikolay.pingov.messager.Chat;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.ADMIN;

@Log4j2
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TelegramChat implements Chat<Long, TelegramSender> {

    AbilityBot bot;
    Message message;
    boolean conference;
    Long id;
    TelegramSender sender;

    @Override
    public void sendReply(String text) {
        sendReply(text, null);
    }

    @Override
    public void sendReply(String text, BufferedImage image) {
        log.warn("Ответ для {}: {}", sender.getName(), text.replace("\n", " "));
        try {
            if (image != null) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(image, "png", os);
                InputStream is = new ByteArrayInputStream(os.toByteArray());
                log.info("Картинка - {}", is.available());
                bot.execute(SendPhoto.builder()
                        .chatId(message.getChatId())
                        .caption(text)
                        .photo(new InputFile().setMedia(is, "Иконка сервера"))
                        .replyToMessageId(this.message.getMessageId())
                        .build());
                is.close();
                os.close();
            } else {
                bot.execute(SendMessage.builder()
                        .chatId(message.getChatId())
                        .text(text)
                        .parseMode(ParseMode.HTML)
                        .disableWebPagePreview(true)
                        .replyToMessageId(this.message.getMessageId())
                        .build());
            }
        } catch (Exception e) {
            log.error("Ошибка при отправке ответа пользователю {}", sender.getName());
            e.printStackTrace();
        }
    }

}
