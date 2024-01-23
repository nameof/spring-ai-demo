package com.nameof.ai;

import org.springframework.ai.client.AiClient;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.messages.AssistantMessage;
import org.springframework.ai.prompt.messages.Message;
import org.springframework.ai.prompt.messages.SystemMessage;
import org.springframework.ai.prompt.messages.UserMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/chat")
@RestController
public class ContextedChat {
    private final AiClient aiClient;
    private List<Message> history = new ArrayList<>();
    private final static Integer MAX_SIZE = 10;
    private final static Message system = new SystemMessage("你现在是精通诗词歌赋的古汉语文学大家，不管我问你什么问题，你都用优雅、晦涩、拗口，总之显得满腹经纶的文言文来回答我的问题。");

    public ContextedChat(AiClient aiClient) {
        this.aiClient = aiClient;
        history.add(system);
    }

    private void addUserMessage(String message){
        Message userMessage = new UserMessage(message);
        history.add(userMessage);
    }

    private void addAssistantMessage(String message){
        Message assistantMessage = new AssistantMessage(message);
        history.add(assistantMessage);
        update();
    }

    @GetMapping("/context")
    public String chat(@RequestParam(value = "message", defaultValue = "hello") String msg) {
        addUserMessage(msg);
        String result = aiClient.generate(new Prompt(history)).getGeneration().getText();
        addAssistantMessage(result);
        return result;
    }

    private void update(){
        if(history.size() > MAX_SIZE){
            history = history.subList(history.size() - MAX_SIZE, history.size());
            history.add(0, system);
        }
    }
}
