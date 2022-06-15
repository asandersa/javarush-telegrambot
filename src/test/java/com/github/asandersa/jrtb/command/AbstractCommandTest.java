package com.github.asandersa.jrtb.command;


import com.github.asandersa.jrtb.bot.JavarushTelegramBot;
import com.github.asandersa.jrtb.command.Command;
import com.github.asandersa.jrtb.repository.TelegramUserRepository;
import com.github.asandersa.jrtb.service.SendBotMessageService;
import com.github.asandersa.jrtb.service.SendBotMessageServiceImpl;
import com.github.asandersa.jrtb.service.TelegramUserService;
import com.github.asandersa.jrtb.service.TelegramUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Abstract class for testing Commands.
 */
abstract class AbstractCommandTest {

    protected JavarushTelegramBot javarushBot = Mockito.mock(JavarushTelegramBot.class);
    protected TelegramUserRepository telegramUserRepository = Mockito.mock(TelegramUserRepository.class);

    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(javarushBot);

    protected TelegramUserService telegramUserService = new TelegramUserServiceImpl(telegramUserRepository);

    abstract String getCommandName();
    abstract String getCommandMessage();
    abstract Command getCommand();

    @Test
    public void shouldProperlyExecuteCommand() throws TelegramApiException {
        //given
        Long chatId = 1234567892345L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(getCommandMessage());
        sendMessage.enableHtml(true);

        getCommand().execute(update);
        Mockito.verify(javarushBot).execute(sendMessage);

    }
    public static Update prepareUpdate(Long chatId, String commandName) {
            Update update = new Update();
            Message message = Mockito.mock(Message.class);
            Mockito.when(message.getChatId()).thenReturn(chatId);
            Mockito.when(message.getText()).thenReturn(commandName);
            update.setMessage(message);
            return update;
    }
}
