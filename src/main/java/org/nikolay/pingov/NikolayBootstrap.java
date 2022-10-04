package org.nikolay.pingov;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Log4j2
public class NikolayBootstrap {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        log.info("Nikolay Pingov starting...");
        long start = System.currentTimeMillis();
        NikolayPingov nikolay = new NikolayPingov();
        nikolay.start();
        log.info("Nikolay started in {} ms", System.currentTimeMillis() - start);

        /*while (true) {
            log.info("Введите айпи сервера:");
            try {
                String ip = reader.readLine();
                MCServer server = nikolay.getJavaFetcher().ping(ip);
                JsonPingStructure structure = server.getStructure();
                if (structure.isOnline()) {
                    log.info("Сервер пропингован за {} мс! ({}/{})",
                            server.getPingTime(),
                            structure.getPlayers().getOnline(),
                            structure.getPlayers().getMaxOnline());
                    log.info("Мотд:");
                    log.info(" " + structure.getMotd().getClean()[0]);
                    log.info(" " + structure.getMotd().getClean()[1]);
                } else {
                    log.error("Сервер не найден! ({} ms)", server.getPingTime());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }

}
