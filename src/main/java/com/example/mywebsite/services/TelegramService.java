package com.example.mywebsite.services;

import com.example.mywebsite.models.Cart;
import com.example.mywebsite.models.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Service
public class TelegramService {

    private final String botToken;
    private final String chatId;
    private final TelegramBotsApi telegramBotsApi;
    private final MyTelegramBot bot;

    @Autowired
    public TelegramService(@Value("${telegram.bot.token}") String botToken, @Value("${telegram.chat.id}") String chatId) throws TelegramApiException {
        this.botToken = botToken;
        this.chatId = chatId;
        this.telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        this.bot = new MyTelegramBot(botToken);
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(Cart cart, String userName, String userPhone) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(buildOrderMessage(cart, userName,userPhone));

        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private String buildOrderMessage(Cart cart, String userName, String userPhone) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("$$$Заказ$$$\n");
        for (CartItem item : cart.getItems()) {
            messageBuilder.append("\n")
                    .append("Article: ").append(item.getProduct().getArticle())
                    .append("\nProduct: ").append(item.getProduct().getName().replace(" ", ""))
                    .append("\nPrice: ").append(item.getProduct().getPrice())
                    .append("\nQuantity: ").append(item.getQuantity())
                    .append("\n\n");
        }
        messageBuilder.append("Username: ").append(userName)
                .append("\nUserphone: ").append(userPhone);
        return messageBuilder.toString();
    }
    private class MyTelegramBot extends org.telegram.telegrambots.bots.TelegramLongPollingBot {

        private final String token;

        public MyTelegramBot(String botToken) {
            super(new DefaultBotOptions());
            this.token = botToken;
        }

        @Override
        public void onUpdateReceived(Update update) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Received an update!"); // Замените на текст сообщения

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }


        @Override
        public String getBotUsername() {
            return "crazzzyGadget_bot"; // Замените на имя вашего бота
        }

        @Override
        public String getBotToken() {
            return token;
        }
    }
}

