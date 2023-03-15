package com.example.demo.bot;

import com.example.demo.config.TelegramBotConfiguration;
import com.example.demo.repository.RepositoryFactory;
import com.example.demo.repository.TestersRepository;
import com.example.demo.storage.TestersDTO;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private RepositoryFactory factory;
    private final TelegramBotConfiguration config;
    static final String HELP_TEXT = "Этот бот создан для учебного проекта благодаря которому автор изучает " +
            "TelegrammBotsAPI. Он поддерживает команды:" +
            "help, start, deletedata и mydata";

    public TelegramBot(TelegramBotConfiguration config) {
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "start"));
        listOfCommands.add(new BotCommand("/mydata", "My info"));
        listOfCommands.add(new BotCommand("/testers", "List of beta-testers"));
        listOfCommands.add(new BotCommand("/help", "Info how to use this bot"));
        listOfCommands.add(new BotCommand("/question", "test message"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Somthing wrong in command list");
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                ///////////Commands
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help":
                    sendMessage(chatId, HELP_TEXT);
                    break;
                case "/mydata":
                    sendMessage(chatId, update.getMessage().getDate().toString() + update.getMessage().getChatId().toString());
                    break;
                case"/tester":
                    registerTester(update.getMessage());
                    break;
                case"/testers":
                    showAllTester(update.getMessage());
                    break;
                case"/question":
                    whatsUp(chatId);
                    break;
                ////////////Answers to the messages
                case "Привет":
                    sendMessage(chatId, "Привет!");
                    break;
                case "привет":
                    sendMessage(chatId, "Привет!");
                    break;
                case "Привет!":
                    sendMessage(chatId, "Привет!");
                    break;
                case "Пока":
                    sendMessage(chatId, "Прощай!");
                    break;
                case "пока":
                    sendMessage(chatId, "Прощай!");
                    break;
                case "Здарова":
                    sendMessage(chatId, "Здравия желаю!");
                    break;
                case "Здарова!":
                    sendMessage(chatId, "Здравия желаю!");
                    break;
                case "Здарово":
                    sendMessage(chatId, "Здравия желаю!");
                    break;
                case "Здарово!":
                    sendMessage(chatId, "Здравия желаю!");
                    break;
                case "Как жизнь?":
                    sendMessage(chatId, "Жду пока разроботчик доделает мои функции и я буду более функциональным!");
                    break;
                case "Как дела?":
                    sendMessage(chatId, "Жду пока разроботчик доделает мои функции и я буду более функциональным!");
                    break;
                case "Как дела":
                    sendMessage(chatId, "Жду пока разроботчик доделает мои функции и я буду более функциональным!");
                    break;
                case "Как жизнь":
                    sendMessage(chatId, "Жду пока разроботчик доделает мои функции и я буду более функциональным!");
                    break;
                case "Хай Гитлер!":
                    sendMessage(chatId, "Володя, пошел нахуй)))))");
                    break;
                case "Хай гитлер!":
                    sendMessage(chatId, "Володя, пошел нахуй)))))");
                    break;
                case "Хай гитлер":
                    sendMessage(chatId, "Володя, пошел нахуй)))))");
                    break;
                    case "Хай Гитлер":
                    sendMessage(chatId, "Володя, я гей)))))");
                    break;
                case "Не хворай":
                    sendMessage(chatId, "Покеда!");
                    break;
                case "Пон":
                    sendMessage(chatId, "Тампон!");
                    break;
                case "пон":
                    sendMessage(chatId, "Тампон!");
                    break;
                case "Саша, пошел нахуй)))))":
                    sendMessage(chatId, "Иди нахуй!");
                    break;
                case "Саша, пошел нахуй":
                    sendMessage(chatId, "Иди нахуй!");
                    break;
                case "Пошел нахуй":
                    sendMessage(chatId, "Иди нахуй!");
                    break;
                case "Саша пошел нахуй":
                    sendMessage(chatId, "Иди нахуй!");
                    break;
                case "Помахай":
                    sendMessage(chatId, EmojiParser.parseToUnicode(":wave:"));
                    break;
            }
        } else if (update.hasCallbackQuery()){
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if(callbackData.equals("GOOD_BUTTON")){
                String text = "Отлично!";
                EditMessageText message = new EditMessageText();
                message.setChatId(chatId);
                message.setText(text);
                message.setMessageId((int) messageId);

                try{
                    execute(message);
                }
                catch (TelegramApiException exception){
                    System.out.println("!!!!!!Telegram exception in telegram bot!!!!!!");
                }
            } else if (callbackData.equals("BAD_BUTTON")){
                String text = "Не волнуйся, это не надолго!";
                EditMessageText message = new EditMessageText();
                message.setChatId(chatId);
                message.setText(text);
                message.setMessageId((int) messageId);

                try{
                    execute(message);
                }
                catch (TelegramApiException exception){
                    System.out.println("!!!!!!Telegram exception in telegram bot!!!!!!");
                }
            }
        }
    }
    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        row.add("/start");
        row.add("/testers");

        keyboardRows.add(row);
        row = new KeyboardRow();

        row.add("/help");
        row.add("/mydata");

        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);
        try{
            execute(message);
        }
        catch (TelegramApiException exception){
            System.out.println("!!!!!!Telegram exception in telegram bot!!!!!!");
        }
    }

    private void whatsUp(long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Как у тебя дела?");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var goodButton = new InlineKeyboardButton();

        if (goodButton != null) {
            goodButton.setText("Нормально");
            goodButton.setCallbackData("GOOD_BUTTON");
        }

        var badButton = new InlineKeyboardButton();

        if (badButton != null) {
            badButton.setText("Плохо");
            badButton.setCallbackData("BAD_BUTTON");
        }

        rowInLine.add(goodButton);
        rowInLine.add(badButton);

        rowsInLine.add(rowInLine);

        markup.setKeyboard(rowsInLine);
        message.setReplyMarkup(markup);
        try{
            execute(message);
        }
        catch (TelegramApiException exception){
            System.out.println("!!!!!!Telegram exception in telegram bot!!!!!!");
        }
    }

    private void startCommandReceived(long chatId, String info){
        String answer = "Приветствую " + info + " тебя приветствует телеграмм бот: MargelovBot.";
        sendMessage(chatId, answer);

        log.info("Бот попреветствовал пользователя");
    }

    private void registerTester(Message msg){
        long chatId = msg.getChatId();
        String userName = msg.getChat().getUserName();
        TestersRepository repository = factory.getTestersRepository();

        List<TestersDTO> list = repository.getTestersByChatId(chatId);
        if (list.size() > 0) {
            sendMessage(chatId, "Вы уже зарегистрированы!");
            return;
        }
        TestersDTO tester = new TestersDTO(chatId, userName);
        repository.create(tester);
        sendMessage(chatId, "Вы зарегистрированы, как тестировщик!");
    }

    private void showAllTester(Message msg) {
        long chatId = msg.getChatId();
        String userName = msg.getChat().getUserName();
        TestersRepository repository = factory.getTestersRepository();
        List<TestersDTO> list = repository.getAll();
        StringBuilder builder = new StringBuilder();
        builder.append("Список тестеров: \n");
        for (TestersDTO tester: list) {
            builder.append(tester.getUserName() + "\n");
        }
        sendMessage(chatId, builder.toString());
    }
}
