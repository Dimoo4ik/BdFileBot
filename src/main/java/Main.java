import org.telegram.telegrambots.meta.TelegramBotsApi;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws TelegramApiException, IOException {


        /*FileOne fileOne = new FileOne();
       fileOne.uploadingFileToMysql();*/
//        FileOne fileOne = new FileOne();
//        fileOne.downloadingFileFromMysql();




        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot = new Bot();
        telegramBotsApi.registerBot(bot);

        
    }
}
