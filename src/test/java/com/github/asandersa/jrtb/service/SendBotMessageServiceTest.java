package com.github.asandersa.jrtb.service;

import com.github.asandersa.jrtb.bot.JavarushTelegramBot;
import com.github.asandersa.jrtb.service.SendBotMessageService;
import com.github.asandersa.jrtb.service.SendBotMessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@DisplayName("Unit-level testing for SendBotMessageService")
public class SendBotMessageServiceTest {

    private SendBotMessageService sendBotMessageService;
    private JavarushTelegramBot javarushBot;

    @BeforeEach //перед каждым методом @Test, @RepeatedTest, @ParameterizedTest, @TestFactory в текущем классе
    public void init() {
        javarushBot = Mockito.mock(JavarushTelegramBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(javarushBot);
    }

    @Test
    public void shouldProperlySendMessage() throws TelegramApiException {

        //given — подготавливаем все необходимое к тесту
        Long chatId = 123456789l;
        String message = "test_message";


        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableHtml(true);

        //when — запускаем метод, который планируем тестировать
        sendBotMessageService.sendMessage(chatId, message);

        //then — проверяем, правильно ли отработал метод
        Mockito.verify(javarushBot).execute(sendMessage);

    }

}
