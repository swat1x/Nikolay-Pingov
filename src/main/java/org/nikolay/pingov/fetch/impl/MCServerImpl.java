package org.nikolay.pingov.fetch.impl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.nikolay.pingov.fetch.InfoMessage;
import org.nikolay.pingov.fetch.MCServer;
import org.nikolay.pingov.json.JsonPingStructure;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MCServerImpl implements MCServer {

    long pingTime;
    JsonPingStructure structure;

    @Override
    public InfoMessage getAsMessage() {
        if (!structure.isOnline()) {
            return new InfoMessage("Не удалось получить ответ от сервер!\nСкорее всего сервер оффлайн!", null);
        } else {
            return
                    new InfoMessage("✅ IP: " + structure.getHostname() + "\n\n" +
                            "\uD83C\uDF0D Ядро: " + structure.getVersion() + "\n" +
                            "#⃣ Протокол: " + structure.getProtocol() + "\n\n" +
                            "\uD83C\uDF0D Онлайн: " + structure.getPlayers().getOnline() + " из " + structure.getPlayers().getMaxOnline() + "\n\n" +
                            "\uD83D\uDCDC Описание:\n " + structure.getMotd().getClean()[0] + "\n" + structure.getMotd().getClean()[1] + "\n\n"
                            + "", structure.getIcon());
        }
    }
}
